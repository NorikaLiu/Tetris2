public class Tetris implements ArrowListener
{
	private BoundedGrid<Block> grid;	// The grid containing the Tetris pieces.
	private BlockDisplay display;		// Displays the grid.
	private Tetrad activeTetrad;		// The active Tetrad (Tetris Piece).

	// Constructs a Tetris Game
	public Tetris()
	{
		grid = new BoundedGrid<Block>(20, 10);
		display = new BlockDisplay(grid);
		display.setTitle("Tetris");
		activeTetrad = new Tetrad(grid);
		display.showBlocks();
		display.setArrowListener(this);
	}

	// Play the Tetris Game
	public void play()
	{
		while (true) {
			sleep(1);
			if (!activeTetrad.translate(1, 0)) {
				clearCompletedRows();
				activeTetrad = new Tetrad(grid);
			}
			display.showBlocks();
		}
	}


	// Precondition:  0 <= row < number of rows
	// Postcondition: Returns true if every cell in the given row
	//                is occupied; false otherwise.
	private boolean isCompletedRow(int row)
	{
		boolean full = true;
		for (int i = 0; i < grid.getNumCols(); i++) {
			if (grid.get(new Location(row, i)) == null) {
				full = false;
			}
		}
		return full;
		
	}

	// Precondition:  0 <= row < number of rows;
	//                The given row is full of blocks.
	// Postcondition: Every block in the given row has been removed, and
	//                every block above row has been moved down one row.
	private void clearRow(int row)
	{
		for (int i = 0; i < grid.getNumCols(); i++) {
			grid.get(new Location(row, i)).removeSelfFromGrid();
		}
	}

	// Postcondition: All completed rows have been cleared.
	private void clearCompletedRows()
	{
		for (int i = 0; i < grid.getNumRows(); i++) {
			if (isCompletedRow(i)) {
				clearRow(i);
			}
		}
	}

	// Sleeps (suspends the active thread) for duration seconds.
	private void sleep(double duration) {
		final int MILLISECONDS_PER_SECOND = 1000;

		int milliseconds = (int)(duration * MILLISECONDS_PER_SECOND);

		try {
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e) {
			System.err.println("Can't sleep!");
		}
	}


	// Creates and plays the Tetris game.
	public static void main(String[] args) {
		Tetris t = new Tetris();
		t.play();
	}

	@Override
	public void upPressed() {
		activeTetrad.rotate();
		display.showBlocks();
	}

	@Override
	public void downPressed() {
		activeTetrad.translate(1, 0);
		display.showBlocks();
	
	}

	@Override
	public void leftPressed() {
		activeTetrad.translate(0, -1);
		display.showBlocks();
		
		
	}

	@Override
	public void rightPressed() {
		activeTetrad.translate(0, 1);
		display.showBlocks();
	}

	@Override
	public void spacePressed() {
		
	}
}
