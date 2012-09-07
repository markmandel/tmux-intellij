package com.compoundtheory.intellij.tmux;


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