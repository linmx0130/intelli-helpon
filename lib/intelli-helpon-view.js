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

  loadData(keyword){
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
          view.loadData(searchKeyword);
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

      /*content = document.createElement('div');
      xmlhttp = new XMLHttpRequest();
      if (xmlhttp!= null){
          xmlhttp.open('GET','http://stackoverflow.com/search?q='+keyword);
          xmlhttp.send(null);
          console.log(xmlhttp.responseText);
          xmlhttp.onload = function(){
              wv.removeChild(loading);
              content.innerHTML = xmlhttp.responseText;
              console.log(xmlhttp.responseText);
          }
          wv.appendChild(content);
      } else {
        console.log('not supported XMLHttpRequest');
      }*/

      const list = document.createElement('ul');
      list.classList.add('list-group');
      title = 'Title of search keyword';
      description = 'descriptiondescriptiondescription';
      result = '';
      for(var i = 0; i < 10; i++){
        result += "<li class='list-item'><a href='' ><sapn class='icon icon-file-code text-info' style='font-size:17px'> "+ title
              + " </span> <p class='message'>"+ description +" </p></a><li>";
      }
      wv.removeChild(loading);
      list.innerHTML = result;
      wv.appendChild(list);
      list.style.marginLeft = '20px';
      wv.style.textAlign = 'left';
      wv.style.marginTop = '12px';
    }

  }
}
