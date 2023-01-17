package pa2;

import java.util.*;

public class Grid {
	private boolean[][] grid = null;
    private ArrayList<Set<Spot> > allGroups; // All groups
    private Set<Integer> set; 
    private Set<Spot> check;
	
	public Grid(boolean[][] ingrid) {
		grid = ingrid;
     	allGroups = new ArrayList<Set<Spot> >();
     	set = new HashSet<Integer>();
     	check = new HashSet<Spot>(); 
	}

	public static void main(String[] args) {

		boolean[][] gridData = {
				{ false, false, false, false, false, false, false, false,
						false, true },
				{ false, false, false, true, true, false, false, false, false,
						true },
				{ false, false, false, false, false, false, false, false,
						false, false },
				{ false, false, false, false, true, false, false, true, false,
						false },
				{ false, false, false, true, false, false, false, true, false,
						false },
				{ false, false, false, false, false, false, false, true, true,
						false },
				{ false, false, false, false, true, true, false, false, false,
						false },
				{ false, false, false, false, false, false, false, false,
						false, false },
				{ false, false, false, false, false, false, false, false,
						false, false },
				{ false, false, false, false, false, false, false, false,
						false, false } };
		
		Grid grid = new Grid(gridData);
		ArrayList<Set<Spot>> list = grid.calcAllGroups();
		for (Set<Spot> g : list) {
			for (Spot s : g) {
				System.out.println(s);
			}
			System.out.println("");
		}
	}

	public void printAllGroups()
	{
        for(Set<Spot> g:allGroups) {
         	for(Spot s:g)
             	System.out.println(s);
	    	System.out.println("");
          }
	}

 // Add all groups into an array list and return it
 public ArrayList<Set<Spot>> calcAllGroups() {
	int groupNum = 1;
	// Iterate in a 2-D array all blocks in grid
	for (int i = 0; i < grid.length; i++) {
		for (int j = 0; j < grid[0].length; j++) {
			// Create a spot and put it into a temp set
			Set<Spot> tempSet = new HashSet<Spot>();
			Spot temp = new Spot(i, j);
			temp.setGroup(groupNum);
			// If there is a block and the block has not been seen yet
			if (grid[i][j] && !check.contains(temp)) {
				check.add(temp); // Block has now been seen
				tempSet.add(temp); // Add spot into temp set
				resetSet(); 
				tempSet = traverse(i, j, groupNum, tempSet); // Traverse and find all other in same group
				groupNum++; // Increment for next group
				allGroups.add(tempSet); // Add group to array list
			}
		}
	}
	return allGroups;
 }
 
 // Traverse every block in the same group
 private Set<Spot> traverse(int x, int y, int groupNum, Set<Spot> tempSet) {
	Spot temp = new Spot(x, y);
	temp.setGroup(groupNum);
	// If there is no block return
	 if (!grid[x][y]) { 
		 return tempSet;
	}
	 
	// If block is not in set yet, add it
	if (!tempSet.contains(temp)) {
		tempSet.add(temp);
	}
	
	// If it has not been seen yet, make it seen
	if (!check.contains(temp)) {
		check.add(temp);
	}
	
	
	int n = grid[0].length;
	int tempX, tempY;
	int k = n * x + y;
	set.add(k);
	// Check the spot one above it
	if (x > 0) {
		tempX = x - 1;
		k = n * tempX + y;
		// If there is a block and it has not been seen yet, explore its neighbors
		if (grid[tempX][y] && !set.contains(k)) {
			tempSet = traverse(tempX, y, groupNum, tempSet);
		}
	}
	
	// Check the spot below it
	if ( x < grid.length - 1) {
		tempX = x + 1;
		k = n * tempX + y;
		// If there is a block, and it has not been seen yet, explore its neighbors
		if (grid[tempX][y] && !set.contains(k)) {
			tempSet = traverse(tempX, y, groupNum, tempSet);
		}
	}
	
	// Check the spot to the left
	if (y == 0) {
		tempY = n - 1;
		tempX = x - 1;
		if (tempX > 0) {
			k = n * tempX + tempY;
			if (grid[tempX][tempY] && !set.contains(k)) {
				tempSet = traverse(tempX, tempY, groupNum, tempSet);
			}
		}
	} else {
		tempY = y - 1;
		k = n * x + tempY; 
		if (grid[x][tempY] && !set.contains(k)) {
			tempSet = traverse(x, tempY, groupNum, tempSet);
		}
	}
	
	// Check the spot to the right
	if (y == n - 1) {
		tempY = 0;
		tempX = x + 1;
		if (tempX < grid.length) {
			k = n * tempX;
			if (grid[tempX][tempY] && !set.contains(k)) {
				tempSet = traverse(tempX, tempY, groupNum, tempSet);
			}
		}
	} else {
		tempY = y + 1;
		k = n * x + tempY;
		if (grid[x][tempY] && !set.contains(k)) {
			tempSet = traverse(x, tempY, groupNum, tempSet);
		}
	}
	return tempSet;
 }
 
 private void resetSet() {
	 Set<Integer> temp = new HashSet<Integer>();
	 set = temp;
 }
 
	/**
	 * Prints out a usage message
	 */
	private static void printUsage() {
		System.out.println("Usage: java Grid <i> <j>");
		System.out.println("Find the size of the cluster of spots including position i,j");
		System.out.println("Usage: java Grid -all");
		System.out.println("Print all spots.");
	}

	/**
	 * This calls the recursive method to find the group size
	 * 
	 * @param i
	 *            the first index into grid (i.e. the row number)
	 * @param j
	 *            the second index into grid (i.e. the col number)
	 * @return the size of the group
	 */
	
	public int groupSize(int i, int j) {
		resetSet();
		return groupSize(i, j, 0);		
	}
	// Calculates the number of blocks in group
	private int groupSize(int x, int y, int c) {
		int count = c;
		
		// If there is not block, return
		if (!grid[x][y]) {
			return count;
		}
		
		count++;
		int n = grid[0].length;
		int k = n * x + y;
		set.add(k);
		
		int tempX = 0;
		int tempY = 0;
		
		// Check the spot above it
		if (x > 0) {
			tempX = x - 1;
			k = n * tempX + y;
			if (grid[tempX][y] && !set.contains(k)) {
				count = groupSize(tempX, y, count);
			}
		}
		
		// Check the spot below it
		if (x < grid.length - 1) {
			tempX = x + 1;
			k = n * tempX + y;
			if (grid[tempX][y] && !set.contains(k)) {
				count = groupSize(tempX, y, count);
			}
		}
		
		// Check the spot to the left
		if (y == 0) {
			tempY = n - 1;
			tempX = x - 1;
			if (tempX > 0) {
				k = tempX * n + tempY;
				if (grid[tempX][tempY] && !set.contains(k)) {
					count = groupSize(tempX, tempY, count);
				}
			}
		} else {
			tempY = y - 1;
			k = n * x + tempY;
			if (grid[x][tempY] && !set.contains(k)) {
				count = groupSize(x, tempY, count);
			}
		}
		
		// Check the spot to the right
		if (y == n - 1) {
			tempY = 0;
			tempX = x + 1;
			if (tempX < grid.length - 1) {
				k = n * tempX;
				if (grid[tempX][tempY] && !set.contains(k)) {
					count = groupSize(tempX, tempY, count);
				}
			}
		} else {
			tempY = y + 1;
			k = n * x + tempY;
			if (grid[x][tempY] && !set.contains(k)) {
				count = groupSize(x, tempY, count);
			}
		}
		
		return count;
	}

	
	/**
	 * Nested class to represent a filled spot in the grid
	 */
	public static class Spot {
		int i;
		int j;
		int group;
		/**
		 * Constructor for a Spot
		 * 
		 * @param i
		 *            the i-coordinate of this Spot
		 * @param j
		 *            the j-coordinate of this Spot
		 */
		public Spot(int i, int j) {
			this.i = i;
			this.j = j;
			this.group = 0; // Default. Will be set later.
		}

		/**
		 * Tests whether this Spot is equal (i.e. has the same coordinates) to
		 * another
		 * 
		 * @param o
		 *            an Object
		 * @return true if o is a Spot with the same coordinates
		 */
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o.getClass() != getClass())
				return false;
			Spot other = (Spot) o;
			return (other.i == i) && (other.j == j);

		}

		/**
		 * Returns an int based on Spot's contents
		 * another way: (new Integer(i)).hashCode()^(new Integer(j)).hashCode();
		 */
		public int hashCode() {
			return i << 16 + j; // combine i and j two halves of int
		}

		public void setGroup(int g) {group = g;}

		public int getI() {return i;}
		public int getJ() {return j;}	
		public int getGroup() {return group;}	
		/**
		 * Returns a String representing this Spot, just the coordinates. You can add group if you want.
		 */
		public String toString() {
			return "(" + i + " , " + j + ")";
		}
	}
}