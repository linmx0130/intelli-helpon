'use babel';

import IntelliHelponView from './intelli-helpon-view';
import { CompositeDisposable } from 'atom';

export default {

  intelliHelponView: null,
  modalPanel: null,
  subscriptions: null,
  wordCount : 0,
  activate(state) {
    this.intelliHelponView = new IntelliHelponView(state.intelliHelponViewState);
    this.modalPanel = atom.workspace.addModalPanel({
      item: this.intelliHelponView.getElement(),
      visible: false
    });

    // Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    this.subscriptions = new CompositeDisposable();

    // Register command that toggles this view
    this.subscriptions.add(atom.commands.add('atom-workspace', {
      'intelli-helpon:toggle': () => this.toggle()
    }));
  },

  deactivate() {
    this.modalPanel.destroy();
    this.subscriptions.dispose();
    this.intelliHelponView.destroy();
  },

  serialize() {
    return {
      intelliHelponViewState: this.intelliHelponView.serialize()
    };
  },

  toggle() {
    if(this.modalPanel.isVisible())
      this.modalPanel.hide();
    else{
      atom.keymaps.add('escape', "intelli-helpon:toggle", 1);
      editor = atom.workspace.getActiveTextEditor();
      filenameArr = editor.getFileName().split(".");
      fileType = filenameArr[filenameArr.length-1];
      //alert('You want to find about '+ fileType);//js, md, json, less, java, cpp, h, c
      selection = editor.getSelectedText();
      filetypeMap = {'js':'JavaScript', 'c':'C', 'cpp':'CPP', 'java':'Java'};
      typeMark = "UnSpecific";
      if (fileType in filetypeMap){
          typeMark = filetypeMap[fileType];
      }
      this.intelliHelponView.loadData(selection, typeMark);
      this.intelliHelponView.setHeader(selection);
      this.modalPanel.show();
    }
  }
};
