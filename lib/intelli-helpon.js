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
      editor = atom.workspace.getActiveTextEditor();
      words = editor.getText().split(/\s+/).length;
      this.intelliHelponView.setCount(words);
      this.intelliHelponView.loadData(editor.getSelectedText());
      this.intelliHelponView.setHeader(editor.getSelectedText());
      this.modalPanel.show();
    }
  }
};
