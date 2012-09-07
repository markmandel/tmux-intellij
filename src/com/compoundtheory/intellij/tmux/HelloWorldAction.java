package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @author Mark Mandel
 */
public class HelloWorldAction extends AnAction
{
	public HelloWorldAction()
	{
		super("Hello World");
	}

	public HelloWorldAction(String title)
	{
		super(title);
	}

	public void actionPerformed(AnActionEvent e)
	{
		Messages.showMessageDialog("Hello World!", "Information", Messages.getInformationIcon());
	}
}
