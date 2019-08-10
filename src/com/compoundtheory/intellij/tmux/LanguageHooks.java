package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;

public class LanguageHooks {
    private static final String LANGUAGE_SCALA = "scala";

    public static String check(Editor editor, String selectedText) {
        String extension = getExtension(editor);
        switch (extension) {
            case LANGUAGE_SCALA:
                selectedText = ":paste\n" + selectedText + "\n" + "\u0004";
                break;
        }
        return selectedText;
    }

    private static String getExtension(Editor editor) {
        String extension = "";
        VirtualFile file = FileDocumentManager.getInstance().getFile(editor.getDocument());
        if (file != null) {
            extension = file.getExtension();
        }
        return extension;
    }
}
