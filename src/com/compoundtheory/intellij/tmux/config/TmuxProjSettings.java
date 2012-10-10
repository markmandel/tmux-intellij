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

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * Projects setttings for a tmux project
 *
 * @author Mark Mandel
 */
@State(name = "TmuxProjSettings",
		storages = {
				@Storage(id = "default", file = "$PROJECT_FILE$"),
				@Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/tmux_project.xml", scheme = StorageScheme.DIRECTORY_BASED)
		})
public class TmuxProjSettings implements PersistentStateComponent<TmuxProjSettings>
{
	public boolean APPEND_NEW_LINE_TO_COMMAND = true;

	public static TmuxProjSettings getInstance(Project project) {
		return ServiceManager.getService(project, TmuxProjSettings.class);
	}

	public TmuxProjSettings getState()
	{
		return this;
	}

	public void loadState(TmuxProjSettings tmuxProjSettings)
	{
		XmlSerializerUtil.copyBean(tmuxProjSettings, this);
	}
}
