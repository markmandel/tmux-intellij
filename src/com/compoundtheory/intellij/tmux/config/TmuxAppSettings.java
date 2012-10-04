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

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;


/**
 * Class for managing where the tmux binary is.
 *
 * @author Mark Mandel
 */
@State(
		name = "TmuxAppSettings",
		storages = {@Storage(file = "$APP_CONFIG$/tmux.xml")}
)
public class TmuxAppSettings implements PersistentStateComponent<TmuxAppSettings>
{
	public String TMUX_OS = System.getProperty("os.name");

    public String TMUX_BINARY_PATH = (TMUX_OS.startsWith("Mac") ? "/opt/local/bin/tmux" : "tmux");

	public static TmuxAppSettings getInstance()
	{
		return ServiceManager.getService(TmuxAppSettings.class);
	}

	public TmuxAppSettings getState()
	{
		return this;
	}

	public void loadState(final TmuxAppSettings state)
	{
		XmlSerializerUtil.copyBean(state, this);
	}
}
