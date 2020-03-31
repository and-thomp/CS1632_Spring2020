import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 *
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 *
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 *
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	Cell[][] cells;	//cells to write to MainPanel
	MainPanel mp;	//create instance for MainPanel to test
	Cell testCell;	//cell to test toString() method
	//MainFrame mf;
	@Mock
	Cell tCell;	//top cell in the pattern
	@Mock
	Cell mCell;	//middle cell in the pattern
	@Mock
	Cell bCell;	//bottom cell in the pattern
	@Mock
	Cell lCell;	//left cell in the pattern
	@Mock
	Cell rCell;	//right cell in the pattern
	@Mock
	Cell deadCell;	//dead cell to populate all other cells on the board

	@Before
	public void setUp() {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif
		 * Start from the vertical bar on a 5X5 matrix as shown in the GIF.
		 */
		mp = new MainPanel(5);	//create new MainPanel to test

		cells = new Cell[5][5];	//create a board to set up in the mainPanel

		testCell = new Cell();	//initialize cell to test toString method

		tCell = Mockito.mock(Cell.class);	//top cell in the pattern
		Mockito.when(tCell.getAlive()).thenReturn(true);

		mCell = Mockito.mock(Cell.class);	//middle cell in the pattern
		Mockito.when(mCell.getAlive()).thenReturn(true);

		bCell = Mockito.mock(Cell.class);	//bottom cell in the pattern
		Mockito.when(bCell.getAlive()).thenReturn(true);

		lCell = Mockito.mock(Cell.class);	//left cell in the pattern

		rCell = Mockito.mock(Cell.class);	//right cell in the pattern

		deadCell = Mockito.mock(Cell.class);	//dead cell to populate the board with
		Mockito.when(deadCell.getAlive()).thenReturn(false);

		for (int i = 0; i < 5; i++) {	//initialize cells on the board
			for (int j = 0; j < 5; j++) {
				cells[i][j] = deadCell;
			}
		}

		cells[1][2] = tCell;	//place mock cells
		cells[2][1] = lCell;
		cells[2][2] = mCell;
		cells[2][3] = rCell;
		cells[3][2] = bCell;

		mp.setCells(cells);	//set the cells
	}

	@After
	public void tearDown(){

	}

	/* TODO: Write the three pinning unit tests for the three optimized methods */


	/**
	*Test Case for MainPanel calculateNextIteration()
	*Preconditions: The MainPanel cell[][] board has been initialized, the cells at [1][2], [2][2], [3][2] have been set to true
	*Execution Steps: call MainPanel.calculateNextIteration()
	*Postconditions: for each mock cell, getAlive() has been called exactly 9 times (once in the call for iterateCell(), 8 times for getNumNeighbors()), 
	*/
	@Test
	public void mainPanel_calculateNextIterationTest() {
		mp.calculateNextIteration();
		Mockito.verify(tCell, times(9)).getAlive();
		Mockito.verify(bCell, times(9)).getAlive();
		Mockito.verify(mCell, times(9)).getAlive();
		Mockito.verify(lCell, times(9)).getAlive();
		Mockito.verify(rCell, times(9)).getAlive();
	}


	/**
	*Test Case for MainPanel iterateCell()
	*Preconditions: The MainPanel cell[][] board has been initialized, the cells at [1][2], [2][2], [3][2] have been set to true
	*Execution Steps: call MainPanel.iterateCell()
	*Postconditions: the state (alive or dead) has changed according to the pattern
	*/
	@Test
	public void mainPanel_iterateCellTest() {
		assertEquals(true, mp.iterateCell(2, 1));
		assertEquals(true, mp.iterateCell(2, 2));
		assertEquals(true, mp.iterateCell(2, 3));
		assertEquals(false, mp.iterateCell(1, 2));
		assertEquals(false, mp.iterateCell(3, 2));
	}

	/**
	*Test Case for Cell toString()
	*Preconditions: The cell has been initialized
	*Execution Steps: call Cell.toString()
	*Postconditions: The return value of the toString() method is "."
	*/
	@Test
	public void cell_toStringTest() {
		assertEquals(".", testCell.toString());
	}

}
