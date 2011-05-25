/**
 * 
 */
package org.example.sudoku;

/**
 * @author pitosalas
 * 
 */
public class SudokuModel {

	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	protected static final int DIFFICULTY_CONTINUE = -1;

	private static int puzzle[];

	private static final String easyPuzzle = "360000000004230800000004200"
			+ "070460003820000014500013020" + "001900000007048300000000045";
	private static final String mediumPuzzle = "650000070000506000014000005"
			+ "007009000002314700000700800" + "500000630000201000030000097";
	private static final String hardPuzzle = "009000000080605020501078000"
			+ "000000700706040102004000000" + "000720903090301080000000600";
	
	/** Cache of used tiles */
	private final int used[][][] = new int[9][9][];



/*
 * Constructor
 * Accepts an array of ints as an initializer
 */
	public SudokuModel(int newPuz[]) {
		puzzle = newPuz;
	}
	
/*
 * No initilializer 
 */
	public SudokuModel() {
		puzzle = null;
	}
		
	/** Compute the two dimensional array of used tiles */
	public void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
				// Log.d(TAG, "used[" + x + "][" + y + "] = "
				// + toPuzzleString(used[x][y]));
			}
		}
	}

	/** Given a difficulty level, come up with a new puzzle */
	public static String getSamplePuzzleAsString(int diff) {
		String puz;
		switch (diff) {
		case DIFFICULTY_HARD:
			puz = hardPuzzle;
			break;
		case DIFFICULTY_MEDIUM:
			puz = mediumPuzzle;
			break;
		case DIFFICULTY_EASY:
		default:
			puz = easyPuzzle;
			break;
		}
		return puz;
	}

/** Check that the string is a  valid: exactly 27 numerical characters and nothing else **/
	public static boolean isValidPuzzleString(String checker) {
		return checker.length() == 81 && !checker.matches("\\D");
	}

	/** Set this SudokuoModel's value to an existing puzzle array **/
	public void setPuzzle(int[] newPuzzle) {
		puzzle = newPuzzle;
	}
	
	public int[] getPuzzle() {
		return puzzle;
	}

	/** Convert this SudokuModel to a puzzle string */
	public String getPuzzleAsString() {
		StringBuilder buf = new StringBuilder();
		for (int element : puzzle) {
			buf.append(element);
		}
		return buf.toString();
	}

	/** Convert a puzzle string into an array */
	public void setPuzzleFromString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
		}
		puzzle = puz;
	}

	/** Return the tile at the given coordinates */
	private int getTile(int x, int y) {
		return puzzle[y * 9 + x];
	}

	/** Change the tile at the given coordinates */
	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x] = value;
	}

	/** Return a string for the tile at the given coordinates */
	protected String getTileString(int x, int y) {
		int v = getTile(x, y);
		if (v == 0)
			return "";
		else
			return String.valueOf(v);
	}

	/** Change the tile only if it's a valid move */
	protected boolean setTileIfValid(int x, int y, int value) {
		int tiles[] = getUsedTiles(x, y);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return false;
			}
		}
		setTile(x, y, value);
		calculateUsedTiles();
		return true;
	}

	/** Compute the used tiles visible from this position */
	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		// horizontal
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}
		// same cell block
		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}
		// compress
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}

	/** Return cached used tiles visible from the given coords */
	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}

}
