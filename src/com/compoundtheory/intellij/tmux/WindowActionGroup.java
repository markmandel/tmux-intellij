package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Action group for a specific window
 *
 * @author Mark Mandel
 */
public class WindowActionGroup extends ActionGroup
{
	private String sessionId;

	public WindowActionGroup(String sessionName)
	{
		super(sessionName, true);

		sessionId = sessionName.split(":")[0].trim();
	}

	@NotNull
	@Override
	public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent)
	{
		String[] windows = CommandUtils.executeCommand(new String[]{"tmux", "list-windows", "-t", sessionId, "-F #{window_index}:#{window_name} #{?window_active,(active),}"}).split("\n");

		ArrayList<AnAction> windowGroups = new ArrayList<AnAction>();

		for(String window : windows)
		{
			windowGroups.add(new PaneActionGroup(sessionId, window));
		}

		return windowGroups.toArray(new AnAction[windowGroups.size()]);
	}
}
