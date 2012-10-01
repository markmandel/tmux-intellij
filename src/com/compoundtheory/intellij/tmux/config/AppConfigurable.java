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

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Application configuration for the plugin.
 *
 * @author Mark Mandel
 */
public class AppConfigurable implements SearchableConfigurable
{
	//big thanks to: https://github.com/JetBrains/intellij-community/blob/master/plugins/groovy/src/org/jetbrains/plugins/groovy/util/SdkHomeConfigurable.java

	private JPanel myPanel;
	private TextFieldWithBrowseButton myPathField;
	private TmuxAppSettings settings;

	public AppConfigurable()
	{
		settings = TmuxAppSettings.getInstance();
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
		myPanel = new JPanel(new BorderLayout(10, 5));
		final JPanel contentPanel = new JPanel(new BorderLayout(4, 0));
		myPanel.add(contentPanel, BorderLayout.NORTH);
		contentPanel.add(new JLabel("Tmux Binary: "), BorderLayout.WEST);
		myPathField = new TextFieldWithBrowseButton();

		myPathField.setText(settings.TMUX_BINARY_PATH);

		contentPanel.add(myPathField);

		myPathField.addBrowseFolderListener("Select Tmux Binary", "", null, new FileChooserDescriptor(true, false, false, false, false, false)
		{
			@Override
			public boolean isFileSelectable(VirtualFile file)
			{
				return (file.getName().equals("tmux"));
			}
		});

		return myPanel;
	}

	public boolean isModified()
	{
		boolean modified = !myPathField.getText().equals(settings.TMUX_BINARY_PATH);

		return modified;
	}

	public void apply() throws ConfigurationException
	{
		settings.TMUX_BINARY_PATH = myPathField.getText();
	}

	public void reset()
	{
		myPathField.setText(settings.TMUX_BINARY_PATH);
	}

	public void disposeUIResources()
	{
		myPathField = null;
		myPanel = null;
	}

	@NotNull
	public String getId()
	{
		return "Tmux.App.Configuration";
	}

	public Runnable enableSearch(String s)
	{
		return null;
	}
}
