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
public class PaneActionGroup extends ActionGroup
{
	private String windowId;
	private String sessionId;

	public PaneActionGroup(String sessionId, String windowName)
	{
		super(windowName, true);

		this.sessionId = sessionId;
		this.windowId = windowName.split(":")[0].trim();
	}

	@NotNull
	@Override
	public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent)
	{
		//tmux list-panes -t 0:0 -F '#{pane_index}: #{pane_title} #{?pane_active,(active),}'

		String[] windows = CommandUtils.executeCommand(new String[]{"tmux", "list-panes", "-t", sessionId + ":" + windowId, "-F #{pane_index}: #{pane_title} #{?pane_active,(active),}"}).split("\n");

		ArrayList<AnAction> windowGroups = new ArrayList<AnAction>();

		for(String window : windows)
		{
			String target = sessionId + ":" + windowId + "." + window.split(":")[0].trim();
			windowGroups.add(new SelectPaneAction(target, window, (target.equals(TmuxPlugin.currentTarget))));
		}

		return windowGroups.toArray(new AnAction[windowGroups.size()]);
	}
}
