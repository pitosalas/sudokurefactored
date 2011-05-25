/***
 * Excerpted from "Hello, Android! 3e",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband3 for more book information.
***/
package org.example.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class GameActivity extends Activity {
   private static final String TAG = "Sudoku";
   public static final String KEY_DIFFICULTY = "org.example.sudoku.difficulty";
   private static final String PREF_PUZZLE = "puzzle" ;
   
   private SudokuModel theModel;
   private PuzzleView puzzleView;
   
   public SudokuModel getModel() {
	   return theModel;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      String aPuzzleString;
      Log.d(TAG, "onCreate");

      int diff = getIntent().getIntExtra(KEY_DIFFICULTY, SudokuModel.DIFFICULTY_EASY);
      theModel = new SudokuModel();
       
	   if (diff == SudokuModel.DIFFICULTY_CONTINUE) {
		   aPuzzleString = getPreferences(MODE_PRIVATE).getString(PREF_PUZZLE, SudokuModel.getSamplePuzzleAsString(SudokuModel.DIFFICULTY_EASY));
	   } else {
		   aPuzzleString = SudokuModel.getSamplePuzzleAsString(diff);
	   }
	  theModel.setPuzzleFromString(aPuzzleString);
      theModel.calculateUsedTiles();

      puzzleView = new PuzzleView(this);
      setContentView(puzzleView);
      puzzleView.requestFocus();

      // ...
      // If the activity is restarted, do a continue next time
      getIntent().putExtra(KEY_DIFFICULTY, SudokuModel.DIFFICULTY_CONTINUE);
   }

   @Override
   protected void onResume() {
      super.onResume();
      Music.play(this, R.raw.game);
   }

   @Override
   protected void onPause() {
      super.onPause();
      Log.d(TAG, "onPause");
      Music.stop(this);

      // Save the current puzzle
      getPreferences(MODE_PRIVATE).edit().putString(PREF_PUZZLE,
    		  theModel.getPuzzleAsString()).commit();
   }
   
   
   /** Open the keypad if there are any valid moves */
   protected void showKeypadOrError(int x, int y) {
      int tiles[] = theModel.getUsedTiles(x, y);
      if (tiles.length == 9) {
         Toast toast = Toast.makeText(this,
               R.string.no_moves_label, Toast.LENGTH_SHORT);
         toast.setGravity(Gravity.CENTER, 0, 0);
         toast.show();
      } else {
         Log.d(TAG, "showKeypad: used=" + theModel.getPuzzleAsString());
         Dialog v = new Keypad(this, tiles, puzzleView);
         v.show();
      }
   }
   
   


}
