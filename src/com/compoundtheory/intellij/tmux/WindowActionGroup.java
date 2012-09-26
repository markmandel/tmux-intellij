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
