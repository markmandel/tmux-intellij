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
