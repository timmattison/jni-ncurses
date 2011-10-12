package com.timmattison.jnincurses;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

// Some guidance from: http://roguebasin.roguelikedevelopment.org/index.php/Java_Curses_Implementation
public class JNIncurses {
	// Geometry
	private static final int CONSOLE_X = 80;
	private static final int CONSOLE_Y = 50;

	// Colors
	private static final int BLACK = 1;
	private static final int DARK_GRAY = 2;
	private static final int LIGHT_GRAY = 3;
	private static final int WHITE = 4;
	private static final int DARK_RED = 5;
	private static final int LIGHT_RED = 6;
	private static final int DARK_GREEN = 7;
	private static final int LIGHT_GREEN = 8;
	private static final int DARK_BLUE = 9;
	private static final int LIGHT_BLUE = 10;
	private static final int DARK_CYAN = 11;
	private static final int LIGHT_CYAN = 12;
	private static final int DARK_MAGENTA = 13;
	private static final int LIGHT_MAGENTA = 14;
	private static final int DARK_YELLOW = 15;
	private static final int LIGHT_YELLOW = 16;

	// Code and variables necessary to load the shared library
	private static final String LIB_BIN = "/lib/";
	private static final String JNINCURSES = "JNIncurses";

	static {
		try {
			System.loadLibrary(JNINCURSES);
		} catch (UnsatisfiedLinkError e) {
			loadFromJar();
		}
	}

	/**
	 * When packaged into JAR extracts DLLs, places these into
	 */
	private static void loadFromJar() {
		// we need to put both DLLs to temp dir
		String path = "JNINCURSES_" + new Date().getTime();
		loadLib(path, JNINCURSES);
	}

	/**
	 * Puts library to temp dir and loads to memory
	 */
	private static void loadLib(String path, String name) {
		// Some guidance from: http://stackoverflow.com/questions/1611357/how-to-make-a-jar-file-that-include-dll-files
		name = name + ".so";

		try {
			// have to use a stream
			InputStream in = JNIncurses.class.getResourceAsStream(LIB_BIN + name);
			// always write to different location
			File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + LIB_BIN + name);
			OutputStream out = FileUtils.openOutputStream(fileOut);
			IOUtils.copy(in, out);
			in.close();
			out.close();
			System.load(fileOut.toString());
		} catch (Exception e) {
			throw new IllegalStateException("Failed to load required DLL", e);
		}
	}

	// JNI native methods
	private native void init();

	public void syncCls() {
		synchronized (this) {
			cls();
		}
	}

	private native void cls();

	public void syncSetCursor(boolean flag) {
		synchronized (this) {
			setCursor(flag);
		}
	}

	private native void setCursor(boolean flag);

	public void syncMoveCursor(int x, int y) {
		synchronized (this) {
			moveCursor(x, y);
		}
	}

	private native void moveCursor(int x, int y);

	public void syncSetColor(int color) {
		synchronized (this) {
			setColor(color);
		}
	}

	private native void setColor(int color);

	public void syncPrint(char ch) {
		synchronized (this) {
			print(ch);
		}
	}

	private native void print(char ch);

	public int syncWgetch(long windowPointer) {
		// NOTE: This isn't synchronized because may block and would cause other threads
		//         to stop updating
		return wgetch(windowPointer);
	}

	private native int wgetch(long windowPointer);

	public void syncEndwin() {
		synchronized (this) {
			endwin();
		}
	}

	private native void endwin();

	public void syncCursSet(int state) {
		synchronized (this) {
			cursSet(state);
		}
	}

	private native void cursSet(int state);

	public void syncCbreak() {
		synchronized (this) {
			cbreak();
		}
	}

	private native void cbreak();

	public void syncNoecho() {
		synchronized (this) {
			noecho();
		}
	}

	private native void noecho();

	public void syncStartColor() {
		synchronized (this) {
			startColor();
		}
	}

	private native void startColor();

	public long syncNewwin(int height, int width, int start_y, int start_x) {
		synchronized (this) {
			return newwin(height, width, start_y, start_x);
		}
	}

	private native long newwin(int height, int width, int start_y, int start_x);

	public int syncCOLS() {
		synchronized (this) {
			return COLS();
		}
	}

	private native int COLS();

	public void syncInitPair(int colorNumber, int foregroundColor, int backgroundColor) {
		synchronized (this) {
			initPair(colorNumber, foregroundColor, backgroundColor);
		}
	}

	private native void initPair(int colorNumber, int foregroundColor, int backgroundColor);

	public int syncGetColorWhite() {
		synchronized (this) {
			return getColorWhite();
		}
	}

	private native int getColorWhite();

	public int syncGetColorGreen() {
		synchronized (this) {
			return getColorGreen();
		}
	}

	private native int getColorGreen();

	public int syncGetColorRed() {
		synchronized (this) {
			return getColorRed();
		}
	}

	private native int getColorRed();

	public int syncGetColorBlack() {
		synchronized (this) {
			return getColorBlack();
		}
	}

	private native int getColorBlack();

	public void syncSetscrreg(long windowPointer, int topMargin, int bottomMargin) {
		synchronized (this) {
			setscrreg(windowPointer, topMargin, bottomMargin);
		}
	}

	private native void setscrreg(long windowPointer, int topMargin, int bottomMargin);

	public void syncScrollok(long windowPointer, int value) {
		synchronized (this) {
			scrollok(windowPointer, value);
		}
	}

	private native void scrollok(long windowPointer, int value);

	public void syncIdlok(long windowPointer, int value) {
		synchronized (this) {
			idlok(windowPointer, value);
		}
	}

	private native void idlok(long windowPointer, int value);

	public int syncGetbegx(long windowPointer) {
		synchronized (this) {
			return getbegx(windowPointer);
		}
	}

	private native int getbegx(long windowPointer);

	public int syncGetbegy(long windowPointer) {
		synchronized (this) {
			return getbegy(windowPointer);
		}
	}

	private native int getbegy(long windowPointer);

	public int syncGetmaxx(long windowPointer) {
		synchronized (this) {
			return getmaxx(windowPointer);
		}
	}

	private native int getmaxx(long windowPointer);

	public int syncGetmaxy(long windowPointer) {
		synchronized (this) {
			return getmaxy(windowPointer);
		}
	}

	private native int getmaxy(long windowPointer);

	public int syncGetx(long windowPointer) {
		synchronized (this) {
			return getx(windowPointer);
		}
	}

	private native int getx(long windowPointer);

	public int syncGety(long windowPointer) {
		synchronized (this) {
			return gety(windowPointer);
		}
	}

	private native int gety(long windowPointer);

	public void syncWmove(long windowPointer, int y, int x) {
		synchronized (this) {
			wmove(windowPointer, y, x);
		}
	}

	private native void wmove(long windowPointer, int y, int x);

	public void syncWrefresh(long windowPointer) {
		synchronized (this) {
			wrefresh(windowPointer);
		}
	}

	private native void wrefresh(long windowPointer);

	public void syncWclear(long windowPointer) {
		synchronized (this) {
			wclear(windowPointer);
			wrefresh(windowPointer);
		}
	}

	private native void wclear(long windowPointer);

	public void syncWaddstr(long windowPointer, String string) {
		synchronized (this) {
			waddstr(windowPointer, string);
		}
	}

	private native void waddstr(long windowPointer, String string);

	public void syncWattron(long windowPointer, int attr) {
		synchronized (this) {
			wattron(windowPointer, attr);
		}
	}

	private native void wattron(long windowPointer, int attr);

	public void syncWattrset(long windowPointer, int attr) {
		synchronized (this) {
			wattrset(windowPointer, attr);
		}
	}

	private native void wattrset(long windowPointer, int attr);

	public int syncABOLD() {
		synchronized (this) {
			return ABOLD();
		}
	}

	private native int ABOLD();

	public int syncASTANDOUT() {
		synchronized (this) {
			return ASTANDOUT();
		}
	}

	private native int ASTANDOUT();

	public int syncANORMAL() {
		synchronized (this) {
			return ANORMAL();
		}
	}

	private native int ANORMAL();

	public int syncABLINK() {
		synchronized (this) {
			return ABLINK();
		}
	}

	private native int ABLINK();

	public void syncBeep() {
		synchronized (this) {
			beep();
		}
	}

	private native void beep();

	public void syncFlash() {
		synchronized (this) {
			flash();
		}
	}

	private native void flash();

	public int syncColorpair(int id) {
		synchronized (this) {
			return colorpair(id);
		}
	}

	private native int colorpair(int id);

	public void syncFlushinp() {
		synchronized (this) {
			flushinp();
		}
	}

	private native void flushinp();

	public void syncTimeout(int milliseconds) {
		synchronized (this) {
			timeout(milliseconds);
		}
	}

	private native void timeout(int milliseconds);

	public JNIncurses() {
		init();
	}

}
