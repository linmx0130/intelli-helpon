'use babel';

export default class IntelliHelponView {
  constructor(serializedState) {
    // Create root element
    this.element = document.createElement('div');
    this.element.classList.add('intelli-helpon');

    // Create message element
    const message = document.createElement('div');
    message.textContent = 'The IntelliHelpon package is Alive! It\'s ALIVE!';
    message.classList.add('message');
    this.element.appendChild(message);

    this.header = document.createElement('p');
    this.header.textContent = 'PlaceHolder';
    this.header.classList.add('header');
    this.element.appendChild(this.header);

    this.wv = document.createElement('wv');
    this.element.appendChild(this.wv);
  }

  // Returns an object that can be retrieved when package is activated
  serialize() {}
  setHeader(text){
      this.header.innerHTML = '<b>Results of </b> ' + text;
  }
  // Tear down any state and detach
  destroy() {
    this.element.remove();
  }

  getElement() {
    return this.element;
  }

  setCount(count) {
    var displayText= "Threr are " + count + " in the workspace.";
    this.element.children[0].textContent = displayText;
  }
  loadData(keyword){
      xmlhttp = new XMLHttpRequest();
      wv = this.wv;
      if (xmlhttp!= null){
          xmlhttp.open('GET','http://stackoverflow.com/search?q='+keyword);
          xmlhttp.send(null);
          console.log(xmlhttp.responseText);
          xmlhttp.onload = function(){
              wv.innerHTML = xmlhttp.responseText;
              console.log(xmlhttp.responseText);
          }
      } else {
        console.log('not supported XMLHttpRequest');
      }
  }
}
