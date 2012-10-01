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

package com.compoundtheory.intellij.tmux.system;


import com.intellij.openapi.diagnostic.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public abstract class CommandUtils
{

	private static Logger logger = Logger.getInstance(CommandUtils.class);

	public static String executeCommand(String[] command)
	{
		logger.debug("Executing command: " + Arrays.toString(command));
		Runtime runtime = Runtime.getRuntime();
		StringBuilder result = new StringBuilder();

		try
		{
			Process commandProcess = runtime.exec(command);
			InputStreamReader in = new InputStreamReader(commandProcess.getInputStream());
			BufferedReader bufferedreader = new BufferedReader(in);

			// Read the ls output
			String line;
			while ((line = bufferedreader.readLine()) != null)
			{
				result.append(line + "\n");
			}

			// Check for ls failure
			try
			{
				if (commandProcess.waitFor() != 0)
				{
					logger.error("Error processing command: " + Arrays.toString(command));

					InputStreamReader errin = new InputStreamReader(commandProcess.getErrorStream());
					BufferedReader bufferedError = new BufferedReader(errin);

					while ((line = bufferedError.readLine()) != null)
					{
						logger.error(line);
					}
				}
			}
			catch (InterruptedException e)
			{
				logger.error(e);
			}
			finally
			{
				// Close the InputStream
				bufferedreader.close();
				in.close();
			}
		}
		catch (IOException e)
		{
			logger.error(e);
		}

		String finalResult = result.toString();

		logger.debug("Result: " + finalResult);

		return finalResult;
	}
}