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

package com.compoundtheory.intellij.tmux.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 * @author Mark Mandel
 */
public class ProjConfigurable implements Configurable
{
	private TmuxProjectSettingsForm form;
	private Project project;
	private TmuxProjSettings settings;

	public ProjConfigurable(Project project)
	{
		this.project = project;
		settings = TmuxProjSettings.getInstance(project);
	}

	@Nls
	public String getDisplayName()
	{
		return "Tmux";
	}

	public Icon getIcon()
	{
		return null;
	}

	public String getHelpTopic()
	{
		return null;
	}

	public JComponent createComponent()
	{
		form = new TmuxProjectSettingsForm();
		return form.getComponent();
	}

	public boolean isModified()
	{
		return settings.APPEND_NEW_LINE_TO_COMMAND != form.isAppendNToTheCheckBoxSelected();
	}

	public void apply() throws ConfigurationException
	{
		settings.APPEND_NEW_LINE_TO_COMMAND = form.isAppendNToTheCheckBoxSelected();
	}

	public void reset()
	{
		form.setAppendNToTheCheckBoxSelected(settings.APPEND_NEW_LINE_TO_COMMAND);
	}

	public void disposeUIResources()
	{
		form = null;
	}
}
