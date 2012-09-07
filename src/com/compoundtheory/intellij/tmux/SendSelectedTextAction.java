package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.Messages;

/**
 * @author Mark Mandel
 */
public class SendSelectedTextAction extends AnAction
{
	public void actionPerformed(AnActionEvent e)
	{
		//thanks La Clojure - https://github.com/JetBrains/la-clojure/blob/clojure-idea-11/src/org/jetbrains/plugins/clojure/repl/actions/RunSelectedTextAction.java
		Editor editor = e.getData(DataKeys.EDITOR);

		if(editor == null)
		{
			return;
		}

		SelectionModel selectionModel = editor.getSelectionModel();
		String selectedText = selectionModel.getSelectedText();

		if (selectedText == null || selectedText.trim().length() == 0) {
			return;
		}

		if(TmuxPlugin.currentTarget == null)
		{
			Messages.showErrorDialog(e.getProject(), "Please select a pane before sending text to Tmux.", "Error sending data to Tmux");
			return;
		}

		CommandUtils.executeCommand(new String[]{"tmux", "set-buffer", selectedText});
		CommandUtils.executeCommand(new String[]{"tmux", "paste-buffer", "-t", TmuxPlugin.currentTarget});
	}
}
