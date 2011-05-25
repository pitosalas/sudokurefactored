package org.example.sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author pitosalas
 * 
 */
public class SudokuModelTest {

	@Test
	public void testNull() {
		assertEquals("safe", 100, 100);
	}
	
	@Test
	public void testPuzzleStringValid() {
		assertFalse("invalidSamplePuzzle", SudokuModel.isValidPuzzleString("bad string"));
	}

	@Test
	public void testGetSamplePuzAsString() {
		assertTrue("sample puzzle is not a valid puzzle string",
				SudokuModel.isValidPuzzleString(SudokuModel
						.getSamplePuzzleAsString(SudokuModel.DIFFICULTY_EASY)));
	}
	
	@Test
	public void testSetGetPuzzleFromString() {
		String aPuzzle = "123123123123123123123123123123123123123123123123123123123123123123123123123123123";
		assertTrue("A Puzzle is valid", SudokuModel.isValidPuzzleString(aPuzzle));
		SudokuModel newModel = new SudokuModel();
		newModel.setPuzzleFromString(aPuzzle);
		assertEquals("Setting and getting puzzle with string matches",
					  newModel.getPuzzleAsString(), aPuzzle);
	}
}
