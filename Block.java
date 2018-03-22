
import java.awt.Color;

public class Block
{
	private BoundedGrid<Block> grid;
	private Location location;
	private Color color;

	// Constructs a blue block, because blue is the greatest color ever!
	public Block()
	{
		color = Color.BLUE;
		grid = null;
		location = null;
	}

	// Gets the color of this block.
	public Color getColor()
	{
		return color;
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Sets the color of this block to newColor.
	public void setColor(Color newColor)
	{
		color = newColor;
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Gets the grid of this block,
	// or null if this block is not contained in a grid.
	public BoundedGrid<Block> getGrid()
	{
		return grid;
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Gets the location of this block,
	// or null if this block is not contained in a grid.
	public Location getLocation()
	{
		return location;
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Removes this block from its grid.
	// Precondition:  this block is contained in a grid.
	public void removeSelfFromGrid()
	{
		grid.remove(location);
		grid = null;
		location = null;
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Puts this block into location loc of grid gr.
	// If there is another block at loc, it is removed.
	// Precondition:  (1) this block is not contained in a grid.
	//                (2) loc is valid in gr.
	public void putSelfInGrid(BoundedGrid<Block> gr, Location loc)
	{
		if (gr.get(loc) != null) {
			gr.get(loc).removeSelfFromGrid();
		}
		gr.put(loc, this);
		location = loc;
		grid = gr;
		
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Moves this block to newLocation.
	// If there is another block at newLocation, it is removed.
	// Precondition:  (1) this block is contained in a grid.
	//                (2) newLocation is valid in the grid of this block.
	public void moveTo(Location newLocation)
	{
		grid.remove(location);
		this.putSelfInGrid(grid, newLocation);
		//throw new RuntimeException("INSERT MISSING CODE HERE");
	}

	// Returns a string with the location and color of this block.
	public String toString()
	{
		return "Block[location=" + location + ",color=" + color + "]";
	}
}
