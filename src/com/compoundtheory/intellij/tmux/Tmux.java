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

import com.compoundtheory.intellij.tmux.config.TmuxAppSettings;
import com.compoundtheory.intellij.tmux.config.TmuxProjSettings;
import com.compoundtheory.intellij.tmux.system.CommandUtils;
import com.intellij.openapi.components.ServiceManager;

/**
 * General utility class for manipulating tmux
 * @author Mark Mandel
 */
public class Tmux
{
	private static Tmux INSTANCE = new Tmux();
	private TmuxAppSettings settings;

	private Tmux()
	{
		settings = ServiceManager.getService(TmuxAppSettings.class);
	}

	public static Tmux getInstance()
	{
		return INSTANCE;
	}

	public String[] listSessions()
	{
		return CommandUtils.executeCommand(new String[]{settings.TMUX_BINARY_PATH, "list-sessions", "-F #{session_name}: #{?session_attached,(attached),(detached)}:  #{session_windows} windows "}).split("\n");
	}

	public String[] listWindows(String sessionID)
	{
		return CommandUtils.executeCommand(new String[]{settings.TMUX_BINARY_PATH, "list-windows", "-t", sessionID, "-F #{window_index}:#{window_name} #{?window_active,(active),}"}).split("\n");
	}

	public String[] listPanes(String sessionID, String windowID)
	{
		return CommandUtils.executeCommand(new String[]{settings.TMUX_BINARY_PATH, "list-panes", "-t", sessionID + ":" + windowID, "-F #{pane_index}: #{pane_title} #{?pane_active,(active),}"}).split("\n");
	}

	/**
	 * Sends text to a Tmux pane of your choice.
	 * @param text the text to send
	 * @param currentTarget the Tmux target string
	 * @param projSettings the current project settings
	 */
	public void sendText(String text, String currentTarget, TmuxProjSettings projSettings)
	{
		if(projSettings.APPEND_NEW_LINE_TO_COMMAND && !text.endsWith("\n"))
		{
			text += "\n";
		}

		text = ":paste\n" + text + "\n" + "\u0004";

		CommandUtils.executeCommand(new String[]{settings.TMUX_BINARY_PATH, "set-buffer", text});
		CommandUtils.executeCommand(new String[]{settings.TMUX_BINARY_PATH, "paste-buffer", "-t", TmuxPlugin.currentTarget});
	}
}
