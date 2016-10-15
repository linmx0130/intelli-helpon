'use babel';

export default class IntelliHelponView {
  constructor(serializedState) {
    // Create root element
    this.element = document.createElement('div');
    this.element.classList.add('intelli-helpon');
  //  this.element.innerHTML = '<br/>';

    // Create message element
    this.header = document.createElement('p');
    this.element.appendChild(this.header);

    this.wv = document.createElement('wv');
    this.element.appendChild(this.wv);
  }

  // Returns an object that can be retrieved when package is activated
  serialize() {}
  setHeader(text){
    this.header.style.fontWeight = 'bold';
    if(text.length == 0){
      this.header.innerHTML = '<h2>Please input your search word:</h2>';
    }else{
      this.header.innerHTML = '<p style="font-size:20px">Here are search results of &nbsp; &nbsp; <span class="badge badge-large"> ' + text + '</span></h2>';
    }
  }
  // Tear down any state and detach
  destroy() {
    this.element.remove();
  }

  getElement() {
    return this.element;
  }

  selectBestItems(query, items){
      isErrorQuery = function(query){
          query = query.toLowerCase();
          errorKeywords = ['error', 'exception', 'fail', 'undefine', 'null', 'syntax'];
          for (word_count in errorKeywords){
              word = errorKeywords[word_count];
              if (query.search(word)!= -1){
                  return true;
              }
          }
          if (query.search(" ")!=-1) return true;
          return false;
      };

      errFlag = isErrorQuery(query);
      console.log(errFlag);
      if (errFlag){
          var limit = {'StackOverFlow' : 3, 'Github':1, 'OfficialDocs':2};
      }else{
          var limit = {'StackOverFlow' : 2, 'Github':1, 'OfficialDocs':3};
      }
      var count = {'StackOverFlow' : 0, 'Github':0, 'OfficialDocs':0};
      var result = [];
      for (var item_count in items){
          var item = items[item_count];
          var type = item['type'];
          if (count[type] < limit[type]){
              result.push(item);
              count[type] ++;
          }
      }
      for (var i=0; i <result.length;++i) {
          for (var j=i+1; j< result.length;++j){
              if (limit[result[j]['type']] > limit[result[i]['type']]){
                  k = result[j];
                  result[j] = result[i];
                  result[i] =k;
              }
          }
      }
      return result;
  }


  loadData(keyword, fileType){
    index = 0;
    wv = this.wv;
    wv.innerHTML = '';
    if(keyword.length == 0){
      const searchBlock = document.createElement('div');
      searchBlock.classList.add('block');

      const input = document.createElement('input');
      input.classList.add('inline-block');
      input.placeholder = 'Search';
      input.style.width = '450px';
      searchBlock.appendChild(input);

      const searchbutton = document.createElement('span');
      searchbutton.classList.add('inline-block');
      searchbutton.classList.add('icon-search');
      searchbutton.style.fontSize = '20px';
      searchbutton.style.marginLeft = '10px';
      view = this;
      searchBlock.appendChild(searchbutton);
      searchbutton.onclick = function(){
        if(index > 0){
          searchBlock.removeChild(searchBlock.lastChild);
        }
        searchKeyword = input.value;
        if(searchKeyword.length == 0){
          const warning = document.createElement('div');
          warning.classList.add('text-warning');
          warning.textContent = 'You have input nothing!';
          searchBlock.appendChild(warning);
          warning.style.marginTop = '6px';
          index += 1;
        }
        else {
          view.setHeader(searchKeyword);
          view.loadData(searchKeyword, fileType);
        }
      }
      searchBlock.style.marginBottom = '17px';
      searchBlock.style.marginLeft = '13px';

      wv.appendChild(searchBlock);
    }
    else{
      wv = this.wv;
      const loading = document.createElement('span');
      loading.classList.add('loading-spinner-large');
      loading.style.marginLeft = 'auto';
      loading.style.marginRight = 'auto';
      loading.style.marginTop = '120px';
      loading.style.marginBottom = '100px';
      loading.style.fontSize = '130px';
      wv.style.textAlign = 'center';
      wv.appendChild(loading);
      selectBestItems = this.selectBestItems;
      xmlhttp = new XMLHttpRequest();
      if (xmlhttp!= null){
          xmlhttp.open('GET','http://139.198.0.24:8080/api/query?keyword='+keyword+"&language="+fileType);
          xmlhttp.send(null);
          console.log(xmlhttp.responseText);
          xmlhttp.onload = function(){
              addBr = function(str) {
                  step = 80;
                  result = "";
                  for (var i=0;i<str.length;i+=step){
                      result = result + str.substring(i, i+step)+"<br>";
                  }
                  return result;
              };
              htmlEncode = function(value){
                var a = document.createElement('div');
                a.textContent = value;
                return a.innerHTML;
            };
              var matchItems = selectBestItems(keyword, JSON.parse(xmlhttp.responseText)['items']);
              const list = document.createElement('ul');
              list.classList.add('list-group');
            //   var title = 'Title of search keyword';
            //   var description = 'descriptiondescriptiondescription';
              var result = '';
              for(var i = 0; i < Math.min(6, matchItems.length); i++){
                item = matchItems[i];
                console.log(item);
                result += "<li class='list-item'><a href='" ;
                result += item['link'];
                if (item['title'].length>=60){
                    item['title'] = item['title'].substring(0,60)+'...';
                }
                result += "'><sapn class='icon icon-file-code text-info' style='font-size:17px'> "+
                        item['title']+
                        " </span></a><p class='message'>"+ addBr(htmlEncode(item['content'])) +" </p><li>";
              }
              wv.removeChild(loading);
              list.innerHTML = result;
              wv.appendChild(list);
              list.style.marginLeft = '20px';
              wv.style.textAlign = 'left';
              wv.style.marginTop = '12px';
          }
          //wv.appendChild(content);
      } else {
        console.log('not supported XMLHttpRequest');
      }
    }
  }
}
