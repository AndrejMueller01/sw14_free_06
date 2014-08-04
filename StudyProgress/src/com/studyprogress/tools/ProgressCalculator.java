package com.studyprogress.tools;

import com.studyprogress.properties.GlobalProperties;

public class ProgressCalculator {
	private XMLParser parser;
	private float currentECTS;
	private int maxECTS;

	public ProgressCalculator(XMLParser parser) {
		this.parser = parser;
	}

	public float calculateCurrentECTS() {
		currentECTS = parser.getCurrentEcts();
		return currentECTS;
	}

	public int getMaxECTS(boolean staticModeOn) {
		if(staticModeOn){
		int mode = parser.getCurrentCurriculum().getMode();
		switch (mode) {
		case GlobalProperties.BACH_STUD:
			maxECTS = GlobalProperties.BACH_ECTS;
			return maxECTS;
		case GlobalProperties.MAST_STUD:
			maxECTS = GlobalProperties.MASTER_ECTS;
			return maxECTS;

		case GlobalProperties.DIPL_STUD:
			maxECTS = GlobalProperties.DIPLSTUD_ECTS;
			return maxECTS;

		case GlobalProperties.LA_STUD:
			maxECTS = GlobalProperties.LAMTSTUD_ECTS;
			return maxECTS;

		case GlobalProperties.PHD_STUD:
			maxECTS = GlobalProperties.PHD_STUD_ECTS;
			return maxECTS;

		}
		return 0;
		}
        else{
			return (int) parser.getAllEcts();
		}

	}

	public float calculatePercentage() {
		float progressInPercent = 0;
		if (maxECTS != 0)
			progressInPercent = currentECTS * 100 / maxECTS;

		return progressInPercent;

	}
}
