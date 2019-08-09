package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.impl.EditorComponentImpl;

public class LanguageHooks {
    private static final String LANGUAGE_SCALA = "scala";

    public static String check(AnActionEvent e, String selectedText) {
        if (LANGUAGE_SCALA.equals(getExtension(e))) {
            selectedText = ":paste\n" + selectedText + "\n" + "\u0004";
        }
        return selectedText;
    }

    private static String getExtension(AnActionEvent e) {
        String extension = "";
        EditorComponentImpl contextComponent = (EditorComponentImpl) e.getDataContext().getData("contextComponent");
        if (contextComponent != null) {
            extension = contextComponent.getEditor().getVirtualFile().getExtension();
        }
        return extension;
    }
}
