package com.compoundtheory.intellij.tmux;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mark Mandel
 */
public class TmuxPlugin implements ApplicationComponent
{
	public static String currentTarget;

	public void initComponent()
	{
	}

	public void disposeComponent()
	{
	}

	@NotNull
	public String getComponentName()
	{
		return "Tmux." + this.getClass().getName();
	}
}
