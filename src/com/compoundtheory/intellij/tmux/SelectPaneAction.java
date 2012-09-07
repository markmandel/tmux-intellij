package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Mark Mandel
 */
public class SelectPaneAction extends AnAction
{
	private String target;
	private String title;

	public SelectPaneAction()
	{
		super("-");
	}

	public SelectPaneAction(String target, String title, boolean selected)
	{
		super(title, title, determineIcon(selected));
		this.target = target;
		this.title = title;
	}

	public void actionPerformed(AnActionEvent e)
	{
		//Messages.showMessageDialog("Selected " + target, "Information", Messages.getInformationIcon());
		TmuxPlugin.currentTarget = this.target;

		Messages.showMessageDialog("Tmux Pane '" + this.title + "' selected.", "Tmux Pane Selected", Messages.getInformationIcon());

		System.out.println("Target set to: " + this.target);
	}

	private static Icon determineIcon(boolean selected)
	{
		System.out.println("determineIcon: " + selected);

		Icon icon = null;

		if(selected)
		{
			System.out.println("Icon selected!");
			icon = IconLoader.getIcon("/actions/checked.png");
		}

		return icon;
	}
}
