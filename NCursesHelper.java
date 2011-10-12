package com.timmattison.jnincurses;

import org.apache.commons.lang.StringUtils;

import com.ethermetrics.datacube.qc.Globals;
import com.ethermetrics.datacube.qc.Utils;
import com.ethermetrics.datacube.qc.commandline.CommandLineProcessor;
import com.ethermetrics.datacube.qc.commandline.CommandLineResult;
import com.ethermetrics.datacube.qc.constants.ProgressStringConstants;
import com.ethermetrics.datacube.qc.exceptions.ExternalProcessException;
import com.ethermetrics.datacube.qc.exceptions.InvalidMACAddressException;
import com.ethermetrics.datacube.qc.exceptions.ManufacturingRecordMissingException;
import com.ethermetrics.datacube.qc.exceptions.NCursesException;
import com.ethermetrics.datacube.qc.exceptions.NCursesNotConfiguredYetException;
import com.ethermetrics.datacube.qc.exceptions.UnexpectedFlashStationNumberException;
import com.ethermetrics.datacube.qc.tests.Test;

public class NCursesHelper {

	public static void alertUser(int count) {
		for (int loop = 0; loop < count; loop++) {
			NCursesHelper.alertUser();
		}
	}

	private static void alertUser() {
		JNIncurses ncurses = Globals.getNcurses();
		ncurses.syncBeep();
		ncurses.syncFlash();
	}

        public static int intValue(boolean enable) {
                if (windowPointerValid()) {
                        return enable ? 1 : 0;
                } else {
                        return fakeIntValue;
                }
        }
}
