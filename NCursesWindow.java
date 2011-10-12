package com.timmattison.jnincurses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class NCursesWindow {
	private static final long fakeWindowPointer = Long.MIN_VALUE;
	private static final int fakeIntValue = Integer.MIN_VALUE;
	private static final Coordinate fakeCoordinate = new Coordinate(fakeIntValue, fakeIntValue);

	private long windowPointer;
	private JNIncurses ncurses;
	private ArrayList<String> strings;

	public NCursesWindow() {
		this.ncurses = null;
		this.windowPointer = fakeWindowPointer;
	}

	public NCursesWindow(JNIncurses ncurses, long windowPointer) {
		this.ncurses = ncurses;
		this.windowPointer = windowPointer;
	}

	public NCursesWindow(JNIncurses ncurses, int height, int width, int start_y, int start_x) {
		this.ncurses = ncurses;
		this.windowPointer = this.ncurses.syncNewwin(height, width, start_y, start_x);
	}

	private boolean windowPointerValid() {
		// Is the window pointer valid?
		if (windowPointer != fakeWindowPointer) {
			// Yes
			return true;
		} else {
			// No, we cannot call any ncurses functions
			return false;
		}
	}

	// These methods access ncurses directly
	public void setScrollingRegion(int top, int bottom) {
		if (windowPointerValid()) {
			ncurses.syncSetscrreg(windowPointer, top, bottom);
		}
	}

	public void enableScrolling(boolean enable) {
		if (windowPointerValid()) {
			ncurses.syncScrollok(windowPointer, intValue(enable));
		}
	}

	public void enableInsertDeleteLine(boolean enable) {
		if (windowPointerValid()) {
			ncurses.syncIdlok(windowPointer, intValue(enable));
		}
	}

	public int getUpperLeftCornerX() {
		if (windowPointerValid()) {
			return ncurses.syncGetbegx(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	public int getUpperLeftCornerY() {
		if (windowPointerValid()) {
			return ncurses.syncGetbegx(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	public Coordinate getUpperLeftCorner() {
		if (windowPointerValid()) {
			return new Coordinate(getUpperLeftCornerX(), getUpperLeftCornerY());
		} else {
			return fakeCoordinate;
		}
	}

	public int getLowerRightCornerX() {
		if (windowPointerValid()) {
			return ncurses.syncGetmaxx(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	public int getLowerRightCornerY() {
		if (windowPointerValid()) {
			return ncurses.syncGetmaxy(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	private void refresh() {
		if (windowPointerValid()) {
			ncurses.syncWrefresh(windowPointer);
		}
	}

	public void move(int y, int x) {
		if (windowPointerValid()) {
			ncurses.syncWmove(windowPointer, y, x);
		}
	}

	public void clear() {
		if (windowPointerValid()) {
			ncurses.syncWclear(windowPointer);
		}
	}

	public int getCurrentXPosition() {
		if (windowPointerValid()) {
			return ncurses.syncGetx(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	public int getCurrentYPosition() {
		if (windowPointerValid()) {
			return ncurses.syncGety(windowPointer);
		} else {
			return fakeIntValue;
		}
	}

	private void addstr(String string) {
		if (windowPointerValid()) {
			ncurses.syncWaddstr(windowPointer, string);
		}
	}

	private void attributeOn(int attribute) {
		if (windowPointerValid()) {
			ncurses.syncWattron(windowPointer, attribute);
		}
	}

	private void attributeSet(int attribute) {
		if (windowPointerValid()) {
			ncurses.syncWattrset(windowPointer, attribute);
		}
	}

	private int colorPair(int id) {
		if (windowPointerValid()) {
			return ncurses.syncColorpair(id);
		} else {
			return fakeIntValue;
		}
	}

	private void timeout(int milliseconds) {
		if (windowPointerValid()) {
			ncurses.syncTimeout(milliseconds);
		}
	}

	// These methods access ncurses through other NCursesWindow methods, not directly
	public Coordinate getLowerRightCorner() {
		if (windowPointerValid()) {
			return new Coordinate(getLowerRightCornerX(), getLowerRightCornerY());
		} else {
			return fakeCoordinate;
		}
	}

	public int getWidth() {
		if (windowPointerValid()) {
			return getLowerRightCornerX() - getUpperLeftCornerX();
		} else {
			return fakeIntValue;
		}
	}

	public int getHeight() {
		if (windowPointerValid()) {
			return getLowerRightCornerY() - getUpperLeftCornerY();
		} else {
			return fakeIntValue;
		}
	}

	private ArrayList<String> getStrings() {
		if (windowPointerValid()) {
			if (strings == null) {
				strings = new ArrayList<String>();
			}

			return strings;
		} else {
			return new ArrayList<String>();
		}
	}

	public void appendString(String string) {
		if (windowPointerValid()) {
			// Add this string to our list of strings
			getStrings().add(string);

			// Clear the window and move to the top of it
			clear();
			move(0, 0);

			int counter = 0;

			// Loop through all of the strings
			for (String currentString : getStrings()) {
				print(currentString);

				// Increment the counter and move to the next line
				counter++;
				move(counter, 0);
			}

			// Refresh the window
			refresh();
		}
	}

	public void move(Coordinate coordinate) {
		if (windowPointerValid()) {
			move(coordinate.getY(), coordinate.getX());
		}
	}

	public Coordinate getCurrentCursorPosition() {
		if (windowPointerValid()) {
			return new Coordinate(getCurrentXPosition(), getCurrentYPosition());
		} else {
			return fakeCoordinate;
		}
	}

	public void print(String string) {
		if (windowPointerValid()) {
			addstr(string);
			refresh();
		}
	}

	public void setBold() {
		if (windowPointerValid()) {
			attributeOn(ncurses.syncABOLD());
		}
	}

	public void setStandout() {
		if (windowPointerValid()) {
			attributeOn(ncurses.syncASTANDOUT());
		}
	}

	public void setNormal() {
		if (windowPointerValid()) {
			attributeSet(ncurses.syncANORMAL());
		}
	}

	public void setBlink() {
		if (windowPointerValid()) {
			attributeOn(ncurses.syncABLINK());
		}
	}

	public void moveX(int x) {
		if (windowPointerValid()) {
			move(getCurrentYPosition(), x);
		}
	}

	public void returnToStartOfLine() {
		if (windowPointerValid()) {
			moveX(0);
		}
	}

	public void println(String string) {
		if (windowPointerValid()) {
			print(string + "\n");
		}
	}

	public void setGreenText() {
		if (windowPointerValid()) {
			attributeSet(colorPair(NCursesHelper.GREEN));
			setStandout();
		}
	}

	public void setRedText() {
		if (windowPointerValid()) {
			attributeSet(colorPair(NCursesHelper.RED));
			setStandout();
		}
	}

	public void clearLine() {
		if (windowPointerValid()) {
			// Get the current Y position just in case we wrap to the next line
			int y = getCurrentYPosition();

			// Go to the start of the line
			returnToStartOfLine();

			// Print enough spaces to fill the window width
			print(StringUtils.repeat(" ", getWidth()));

			// Move back to the beginning of the line.  Don't use returnToStartOfLine since ncurses should have moved us to the
			//   next line since we went right to the edge of the terminal.  returnToStartOfLine will put us under the line we
			//   just cleared.
			move(y, 0);
		}
	}

	public void setNormalStandout() {
		if (windowPointerValid()) {
			setNormal();
			setStandout();
		}
	}

	public void moveToNextLine() {
		if (windowPointerValid()) {
			move(getCurrentYPosition() + 1, 0);
		}
	}

	public Character waitForCharacters(boolean flush, Character... characters) {
		if (windowPointerValid()) {
			return waitForCharacters(flush, Arrays.asList(characters));
		} else {
			return null;
		}
	}

	Character waitForCharacters(boolean flush, List<Character> characterList) {
		if (windowPointerValid()) {
			Map<Integer, Character> map = convertCharacterListToMap(characterList);

			return waitForCharacters(flush, map);
		} else {
			return null;
		}
	}

	private Map<Integer, Character> convertCharacterListToMap(List<Character> characterList) {
		Map<Integer, Character> map = new HashMap<Integer, Character>();

		for (Character character : characterList) {
			map.put((int) character, character);
		}

		return map;
	}

	public Character waitForCharacters(boolean flush, Map<Integer, Character> map) {
		if (windowPointerValid()) {
			// Do we want to flush the keyboard buffer?
			if (flush) {
				// Yes, flush any existing characters in the buffer
				ncurses.syncFlushinp();
			}

			// Poll at 50 millisecond intervals for keypresses
			timeout(50);

			// Get the first character
			int character = ncurses.syncWgetch(windowPointer);

			while (!map.keySet().contains(character)) {
				// Get the next character
				character = ncurses.syncWgetch(windowPointer);
				// XXX - Normally we'd check for updates here
				// check_for_updates()
			}

			// They pressed one of the keys, set the timeout back to infinity
			timeout(-1);

			// Return the character
			return map.get(character);
		} else {
			return null;
		}
	}

	public void waitForCharacter(boolean flush, Character character) {
		if (windowPointerValid()) {
			waitForCharacters(flush, Arrays.asList(character));
		}
	}

	public void waitForEnter(boolean flush) {
		if (windowPointerValid()) {
			waitForCharacter(flush, '\n');
		}
	}

	public void waitForSpacebar(boolean flush) {
		if (windowPointerValid()) {
			waitForCharacter(flush, ' ');
		}
	}
}
