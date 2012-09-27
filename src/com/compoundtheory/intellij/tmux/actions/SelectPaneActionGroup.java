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

package com.compoundtheory.intellij.tmux.actions;

import com.compoundtheory.intellij.tmux.Tmux;
import com.compoundtheory.intellij.tmux.TmuxPlugin;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Just the default menu group for the Tmux selection action
 *
 * Experimental version with a different navigational behaviour for selecting a pane
 *
 * @author Kai Koenig
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
		Tmux tmux = Tmux.getInstance();

		String[] sessions = tmux.listSessions();

		ArrayList<AnAction> sessionGroups = new ArrayList<AnAction>();

		for(String session : sessions)
		{
            String sessionID = session.trim().split(":")[0].trim();
            String sessionStatus = session.trim().split(":")[1].trim();
            String[] windows = tmux.listWindows(sessionID);

            for(String window : windows)
            {
                String windowID = window.split(":")[0].trim();
                String[] panes = tmux.listPanes(sessionID, windowID);

                for(String pane : panes)
                {
                    String target = sessionID + ":" + windowID + "." + pane.split(":")[0].trim();
                    String label = "Session " + sessionID + " " + sessionStatus + " | Window " + window + " | Pane " + pane;
                    sessionGroups.add(new SelectPaneAction(target, label, (target.equals(TmuxPlugin.currentTarget))));
                }
            }
		}

		return sessionGroups.toArray(new AnAction[sessionGroups.size()]);
	}

}