package com.timmattison.jnincurses;

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
