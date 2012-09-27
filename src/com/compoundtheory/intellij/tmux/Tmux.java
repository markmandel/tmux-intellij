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

import com.compoundtheory.intellij.tmux.system.CommandUtils;
import com.compoundtheory.intellij.tmux.system.OS;

/**
 * General utility class for manipulating tmux
 * @author Mark Mandel
 */
public class Tmux
{
	private static Tmux INSTANCE = new Tmux();
	private String tmuxPath;

	private Tmux()
	{
		//TODO: make this a setting in the IDE. When I work out how.
		System.out.println("Am I a Mac? (2) : " + OS.isMac());

		if(OS.isMac())
		{
			System.out.println("Setting tmux to the full path");
			tmuxPath = tmuxPath;
		}
		else
		{
			tmuxPath = "tmux";
		}
	}

	public static Tmux getInstance()
	{
		return INSTANCE;
	}

	public String[] listSessions()
	{
		return CommandUtils.executeCommand(new String[]{tmuxPath, "list-sessions", "-F #{session_name}: #{?session_attached,(attached),(detached)}:  #{session_windows} windows "}).split("\n");
	}

	public String[] listWindows(String sessionID)
	{
		return CommandUtils.executeCommand(new String[]{tmuxPath, "list-windows", "-t", sessionID, "-F #{window_index}:#{window_name} #{?window_active,(active),}"}).split("\n");
	}

	public String[] listPanes(String sessionID, String windowID)
	{
		return CommandUtils.executeCommand(new String[]{tmuxPath, "list-panes", "-t", sessionID + ":" + windowID, "-F #{pane_index}: #{pane_title} #{?pane_active,(active),}"}).split("\n");
	}

	public void sendText(String currentLineText, String currentTarget)
	{
		CommandUtils.executeCommand(new String[]{tmuxPath, "set-buffer", currentLineText});
		CommandUtils.executeCommand(new String[]{tmuxPath, "paste-buffer", "-t", TmuxPlugin.currentTarget});
	}
}
