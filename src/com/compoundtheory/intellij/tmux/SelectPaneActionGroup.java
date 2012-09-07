package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Just the default menu group for the Tmux selection action
 *
 * @author Mark Mandel
 */
public class SelectPaneActionGroup extends ActionGroup
{
	private ArrayList<AnAction> actions;

	public SelectPaneActionGroup()
	{
		super();
	}

	@NotNull
	@Override
	public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent)
	{
		String[] sessions = CommandUtils.executeCommand(new String[]{"tmux", "list-sessions", "-F #{session_name}: #{session_windows} windows #{?session_attached,(attached),}"}).split("\n");

		ArrayList<AnAction> sessionGroups = new ArrayList<AnAction>();

		for(String session : sessions)
		{
			WindowActionGroup sessionGroup = new WindowActionGroup(session.trim());
			sessionGroups.add(sessionGroup);
		}

		return sessionGroups.toArray(new AnAction[sessionGroups.size()]);
	}
}