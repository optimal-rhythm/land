package farmer.bla;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BLA {
  private final int _farmWidth = 400;
  private final int _farmHeight = 600;

  Integer[][] _farm = new Integer[_farmWidth][_farmHeight];
  
  public BLA() {
    // Initialize farm to 0
    for (int x = 0; x < _farmWidth; x++) {
      for (int y = 0; y < _farmHeight; y++) {
        _farm[x][y] = 0;
      }
    }
  }
  
  /**
   * Set barren regions and update farm points
   * 
   * @param barrenRects
   */
  public void setBarrenRegions(String[] barrenRects) {
    if (barrenRects.length == 0) {
      gracefulExit();
    }
    // Set barren areas to -1
    for (String barrenRect : barrenRects) {
      String[] coords = barrenRect.trim().split(" ");
      if (coords.length != 4) {
        gracefulExit();
      }
      int blX = 0, blY = 0, trX = 0, trY = 0;
      try {
        // bottom-left
        blX = Integer.parseInt(coords[0]);
        blY = Integer.parseInt(coords[1]);
        // top-right
        trX = Integer.parseInt(coords[2]);
        trY = Integer.parseInt(coords[3]);
        checkCorners(blX, blY, trX, trY);
      } catch (IllegalArgumentException ex) {
        gracefulExit();
      }
      // set all points in barren rect
      for (int x = blX; x <= trX; x++) {
        for (int y = blY; y <= trY; y++) {
          _farm[x][y] = -1;
        }
      }
    }
  }
  
  /**
   * Get neighbors of a point
   * 
   * @param x
   * @param y
   * @return list of neighboring points
   */
  private List<Integer[]> getNeighbors(int x, int y) {
    List<Integer[]> neighbors = new ArrayList<Integer[]>();
    if (x - 1 >= 0) {
      neighbors.add(new Integer[] {x - 1, y});
    }
    if (x + 1 < _farmWidth) {
      neighbors.add(new Integer[] {x + 1, y});
    }
    if (y - 1 >= 0) {
      neighbors.add(new Integer[] {x, y - 1});
    }
    if (y + 1 < _farmHeight) {
      neighbors.add(new Integer[] {x, y + 1});
    }
    return neighbors;
  }

  private boolean isPointVisited(int x, int y) {
    return (_farm[x][y] != 0);
  }

  /**
   * Checks the validity of the corners
   * 
   * @param bottomLeftX
   * @param bottomLeftY
   * @param topRightX
   * @param topRightY
   */

  public void checkCorners(int bottomLeftX, int bottomLeftY, int topRightX, int topRightY) {
    if (bottomLeftX < 0 || bottomLeftY < 0 || topRightX < 0 || topRightY < 0) {
      System.out.println("Improper rect type 1:" + bottomLeftX + " " + bottomLeftY + " " + topRightX + " " + topRightY);
      throw new IllegalArgumentException();
    }
    if (bottomLeftX > topRightX || bottomLeftY > topRightY) {
      System.out.println("Improper rect type 2:" + bottomLeftX + " " + bottomLeftY + " " + topRightX + " " + topRightY);
      throw new IllegalArgumentException();
    }
    if (bottomLeftX > _farmWidth - 1 || topRightX > _farmWidth - 1 || bottomLeftY > _farmHeight - 1
        || topRightY > _farmHeight - 1) {
      System.out.println("Improper rect type 3:" + bottomLeftX + " " + bottomLeftY + " " + topRightX + " " + topRightY);
      throw new IllegalArgumentException();
    }
  }
  


  /**
   *
   * BFS search algo to find connected fertile regions
   * 
   * @return list of areas of fertile regions
   */

  public List<Integer> computeFertileRegions() {
    int x = 0;
    int y = 0;
    LinkedList<Integer[]> list = new LinkedList<Integer[]>();
    Map<Integer, Integer> fertileRegionMap = new HashMap<Integer, Integer>();
    int currFertileRegion = 0;
    while (x < _farmWidth && y < _farmHeight) {
      // no neighbors to process, move forward
      if (list.isEmpty()) {
        if (!isPointVisited(x, y)) {
          list.add(new Integer[] {x, y});
          currFertileRegion++;
          fertileRegionMap.put(currFertileRegion, 0);
        }
        if (x == _farmWidth - 1) {
          x = 0;
          y++;
        }
        else {
          x = x + 1;
        }
      }
      // otherwise process neighbors first
      else {
        Integer[] point = list.poll();
        int currentX = point[0];
        int currentY = point[1];
        if (!isPointVisited(currentX, currentY)) {
          _farm[currentX][currentY] = currFertileRegion;
          fertileRegionMap.put(currFertileRegion, fertileRegionMap.get(currFertileRegion) + 1);
          List<Integer[]> neighbors = this.getNeighbors(currentX, currentY);
          for (Integer[] n : neighbors) {
            if (!isPointVisited(n[0], n[1])) {
              list.add(n);
            }
          }
        }
      }
    }
    List<Integer> fertileAreas = new ArrayList<Integer>();
    fertileAreas.addAll(fertileRegionMap.values());
    fertileAreas.sort(Comparator.naturalOrder());
    return fertileAreas;
  }

  private void gracefulExit() {
    System.out.println("ERROR  : Invalid barren land rectangles.");
    System.out.println("Each barren land is specified using four positive integers separated by spaces.");
    System.out.println("The first two integers should be x, y of bottom left corner and the next two integers");
    System.out.println("should be x, y of the top right corner.");
    System.out.println("Usage  : java BLA \"<BL_X BL_Y TR_X TR_Y>\"");
    System.out.println("Example: java BLA \"48 192 351 207\" \"48 392 351 407\" \"120 52 135 547\" \"260 52 275 547\"");
    System.exit(-1);
  }
  
  public static void main(String[] args) {
    long start_ts = System.currentTimeMillis();
    BLA bla = new BLA();
    bla.setBarrenRegions(args);
    List<Integer> fertileRegions = bla.computeFertileRegions();
    if (!fertileRegions.isEmpty()) {
      System.out.println(fertileRegions.stream().map(value -> value.toString()).collect(Collectors.joining(" ")));
    }
    else {
      System.out.println("Zero fertile regions");
    }
    long end_ts = System.currentTimeMillis();
    System.out.println("Finished in " + (end_ts - start_ts) + " ms.");
  }
}
