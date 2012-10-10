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

import javax.swing.*;

/**
 * The form for
 * @author Mark Mandel
 */
public class TmuxProjectSettingsForm
{
	private JCheckBox appendNToTheCheckBox;
	private JPanel panel;

	/**
	 * Whether or not the append \n to the command to checkbox is selected
	 * @param selected
	 */
	public void setAppendNToTheCheckBoxSelected(boolean selected)
	{
		appendNToTheCheckBox.setSelected(selected);
	}

	/**
	 * Whether or not the append the new line to the checkbox is selected
	 * @return selected or not.
	 */
	public boolean isAppendNToTheCheckBoxSelected()
	{
		return appendNToTheCheckBox.isSelected();
	}

	/**
	 * Get the root level component
	 * @return the root level component
	 */
	public JComponent getComponent()
	{
		return panel;
	}
}
