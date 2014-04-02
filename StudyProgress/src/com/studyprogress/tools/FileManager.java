package com.studyprogress.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.widget.Toast;

import com.example.studyprogress.R;

public class FileManager {
	private static String REGEX_FOR_SPLITTING = "# ";

	public static String[] getCurriculaNames(int fileID, Activity a) {

		InputStream is = a.getResources().openRawResource(fileID);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String line = "";

		try {
			line = reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int lineNumber = 0;

		try {
			lineNumber = Integer.parseInt(line);
		} catch (NumberFormatException e1) {
			showFileInvalidMessage(a);
			return null;
		}

		String[] curriculaNames = new String[lineNumber];

		for (int i = 0; i < lineNumber; i++) {
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String lineContent[] = new String[2];

			lineContent = line.split(REGEX_FOR_SPLITTING, 2);
			curriculaNames[i] = lineContent[0];

		}

		return curriculaNames;

	}

	public static void showFileInvalidMessage(Activity a) {
		Toast.makeText(a.getBaseContext(), R.string.file_invalid,
				Toast.LENGTH_SHORT).show();
	}
}
