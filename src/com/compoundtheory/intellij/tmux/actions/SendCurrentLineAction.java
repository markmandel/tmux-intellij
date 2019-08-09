/*
 * Copyright 2012 Mark Mandel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compoundtheory.intellij.tmux.actions;

import com.compoundtheory.intellij.tmux.LanguageHooks;
import com.compoundtheory.intellij.tmux.Tmux;
import com.compoundtheory.intellij.tmux.TmuxPlugin;
import com.compoundtheory.intellij.tmux.config.TmuxProjSettings;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;

/**
 * @author Kai Koenig
 */
public class SendCurrentLineAction extends AnAction
{
	public void actionPerformed(AnActionEvent e)
	{
		//thanks La Clojure - https://github.com/JetBrains/la-clojure/blob/clojure-idea-11/src/org/jetbrains/plugins/clojure/repl/actions/RunSelectedTextAction.java
		Editor editor = e.getData(DataKeys.EDITOR);

		if(editor == null)
		{
			return;
		}

		CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();
		Project project = e.getData(DataKeys.PROJECT);
		TmuxProjSettings settings = TmuxProjSettings.getInstance(project);

        int currentCaretOffset = caretModel.getOffset();
        int currentLine = document.getLineNumber(currentCaretOffset);
        int startOffset = document.getLineStartOffset(currentLine);
        int endOffset = document.getLineEndOffset(currentLine);

        // if the line is empty (or the caret is not even in the editor) we don't do anything
        if (endOffset - startOffset == 0) {
            return;
        }

        TextRange currentLineTextRange = new TextRange(startOffset,endOffset);
        if (currentLineTextRange == null) {
            return;
        }

        String currentLineText = document.getText(currentLineTextRange);
        if (currentLineText == null || currentLineText.trim().length() == 0) {
            return;
        }

		if(TmuxPlugin.currentTarget == null)
		{
			Messages.showErrorDialog(e.getProject(), "Please select a pane before sending text to Tmux.", "Error sending data to Tmux");
			return;
		}
		currentLineText = LanguageHooks.check(e, currentLineText.trim());

		Tmux.getInstance().sendText(currentLineText, TmuxPlugin.currentTarget, settings);
	}
}
