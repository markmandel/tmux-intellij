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
		String[] sessions = CommandUtils.executeCommand(new String[]{"/opt/local/bin/tmux", "list-sessions", "-F #{session_name}: #{session_windows} windows #{?session_attached,(attached),}"}).split("\n");

		ArrayList<AnAction> sessionGroups = new ArrayList<AnAction>();

		for(String session : sessions)
		{
			WindowActionGroup sessionGroup = new WindowActionGroup(session.trim());
			sessionGroups.add(sessionGroup);
		}

		return sessionGroups.toArray(new AnAction[sessionGroups.size()]);
	}
}