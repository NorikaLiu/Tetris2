import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;

// Represents a Tetris piece.
public class Tetrad
{
	private Block[] blocks;	// The blocks for the piece.

	// Constructs a Tetrad.
	public Tetrad(BoundedGrid<Block> grid) {
		blocks = new Block[4];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block();
		}
		getNextTetrad(grid);
	}
	
	private void getNextTetrad(BoundedGrid<Block> grid) {
		Color color = null;
		Location[] locs = new Location[4];
		int tetra = ThreadLocalRandom.current().nextInt(0, 6);
		switch (tetra) {
			case 0: //RED BLOCK
				locs[0] = new Location(0, 5);
				locs[1] = new Location(0, 6);
				locs[2] = new Location(0, 3);
				locs[3] = new Location(0, 4);
				color = Color.RED;
				break;
			case 1: //GRAY BLOCK
				locs[0] = new Location(0, 4);
				locs[1] = new Location(0, 3);
				locs[2] = new Location(1, 4);
				locs[3] = new Location(0, 5);
				color = Color.GRAY;
				break;
			case 2: //CYAN BLOCK
				locs[0] = new Location(0, 4);
				locs[1] = new Location(0, 5);
				locs[2] = new Location(1, 4);
				locs[3] = new Location(1, 5);
				color = Color.CYAN;
				break;
			case 3: //YELLOW BLOCK
				locs[0] = new Location(2, 4);
				locs[1] = new Location(1, 4);
				locs[2] = new Location(0, 4);
				locs[3] = new Location(2, 5);
				color = Color.YELLOW;
				break;
			case 4: //MAGENTA BLOCK
				locs[0] = new Location(2, 5);
				locs[1] = new Location(1, 5);
				locs[2] = new Location(0, 5);
				locs[3] = new Location(2, 4);
				color = Color.MAGENTA;
				break;
			case 5: //BLUE BLOCK
				locs[0] = new Location(1, 4);
				locs[1] = new Location(0, 5);
				locs[2] = new Location(1, 3);
				locs[3] = new Location(0, 4);
				color = Color.BLUE;
				break;
			case 6: //GREEN BLOCK
				locs[0] = new Location(1, 4);
				locs[1] = new Location(0, 4);
				locs[2] = new Location(0, 3);
				locs[3] = new Location(1, 5);
				color = Color.GREEN;
				break;
			default:
				System.out.println("BAD BLOCK RANDOM NUMBER NOT EXIST< CHECK THREAD LOCAL RANDOM TO LINE 75 >");
				throw new RuntimeException("BAD BLOCK RANDOM NUMBER");
		}
		
		//Color the blocks in the tetrad the same color
		for (Block colorMe : blocks) colorMe.setColor(color);
		
		//Check if game over
		ArrayList<Location> loc = grid.getOccupiedLocations();
		for (Location checkMe : loc) {
			if (checkMe.equals(locs[0]) || checkMe.equals(locs[1]) || checkMe.equals(locs[2]) || checkMe.equals(locs[3])) {
				System.out.println("GAME OVER YOU LOSE TOO BAD TOO SAD");
				System.exit(0);
			}
		}
		
		//Not game over, put on grid
		addToLocations(grid, locs);
	}

	// Postcondition: Attempts to rotate this tetrad clockwise by 90 degrees
	//                about its center, if the necessary positions are empty.
	//                Returns true if successful and false otherwise.
	public synchronized boolean rotate()
	{
		BoundedGrid<Block> grid = blocks[0].getGrid();
		boolean rotated = false;
		Location[] oldLocate = removeBlocks();
		Location[] newLocate = new Location[blocks.length];
		int row = oldLocate[0].getRow();
		int col = oldLocate[0].getCol();
		for (int i = 0; i < blocks.length; i ++) {
			newLocate[i] = new Location(row - col + oldLocate[i].getCol(), row + col - oldLocate[i].getRow());
		}
		if (areEmpty(grid, newLocate)) {
			addToLocations(grid, newLocate);
			rotated = true;
		} else {
			addToLocations(grid, oldLocate);
		}
		return rotated;
	}
	
	// Postcondition: Attempts to move this tetrad deltaRow rows down and
	//						deltaCol columns to the right, if those positions are
	//						valid and empty.
	//						Returns true if successful and false otherwise.
	public synchronized boolean translate(int deltaRow, int deltaCol)
	{
		BoundedGrid<Block> grid = blocks[0].getGrid();
		Location[] oldLocs = removeBlocks();
		Location[] newLocs = new Location[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			newLocs[i] = new Location(oldLocs[i].getRow() + deltaRow, oldLocs[i].getCol() + deltaCol);
		}
		
		if (areEmpty(grid, newLocs)) {
			addToLocations(grid, newLocs);
			return true;
		} else {
			addToLocations(grid, oldLocs);
			return false;
		}
	}

	// Precondition:  The elements of blocks are not in any grid;
	//                locs.length = 4.
	// Postcondition: The elements of blocks have been put in the grid
	//                and their locations match the elements of locs.
	private synchronized void addToLocations(BoundedGrid<Block> grid, Location[] locate) {
		for(int i = 0; i < 4; i ++) {
			blocks[i].putSelfInGrid(grid, locate[i]);
		}
	}

	// Precondition:  The elements of blocks are in the grid.
	// Postcondition: The elements of blocks have been removed from the grid
	//                and their old locations returned.
	private synchronized Location[] removeBlocks() {
		Location[] locate = new Location[blocks.length];
		for (int i = 0; i < blocks.length; i++) {
			locate[i] = blocks[i].getLocation();
			blocks[i].removeSelfFromGrid();
		}
		return locate;
	}

	// Postcondition: Returns true if each of the elements of locs is valid
	//                and empty in grid; false otherwise.
	private boolean areEmpty(BoundedGrid<Block> grid, Location[] locate) {
		boolean empty = true;
		ArrayList<Location> occuLocs = grid.getOccupiedLocations();
		for (int i = 0; i < 4; i++) {
			for (Location qloc : occuLocs) {
				if (qloc.equals(locate[i])) empty = false;
			}
			if (!grid.isValid(locate[i])) empty = false;
		}
		return empty;
	}
}
