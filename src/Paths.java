import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paths {
  /**
   * Map containing the path length as the key and a two dimensional
   * character List as the value.
   */
  private static Map<Integer, List<List<Character>>> map =
      new HashMap<>();

  /**
   * Matrix object that holds the Directed Graph Adjacency Matrix loaded from
   * the provided text file.
   */
  private static Matrix matrix = null;

  /**
   * Creates new matrix from file name given as first argument. Finds all
   * possible paths from the "Starting Node" to the "Target Node" of the matrix.
   *
   * @param args <br>
   *             1: File Name - name of file containing Directed Graph Matrix
   *             <br>
   *             2: Starting Node - letter of starting node (A, B, C, ...)
   *             <br>
   *             3: Target Node - letter of target node (A, B, C, ...)
   */
  public static void main(String[] args) {
    if (args.length == 3) {
      // Load matrix from file
      matrix = new Matrix(args[0]);

      System.out.println("Matrix:");
      System.out.println(matrix.toString());
      System.out.println("Starting Vertex: " + args[1]);
      System.out.println("Target Vertex: " + args[2] + "\n");

      traverse(args[1].charAt(0) - 'A', 0, args[2].charAt(0),
          new ArrayList<>());

      System.out.println(mapToString());
    } else {
      System.out.println("Usage: java Paths [fileName] [Starting Node] " +
          "[Target Node]");
      System.exit(-1);
    }
  }

  /**
   * Brute force recursive algorithm to find all paths from one vertex to
   * another.
   * <p>
   * Finds all available paths from starting node to
   * target node. Creates a map of the results.
   *
   * @param node         Starting node to be traversed from.
   * @param total        Total length of the current path.
   * @param target       Target node to be traversed to.
   * @param containerRef Container storing the path sequentially.
   */
  public static void traverse(int node, int total, char target,
                              List<Character> containerRef) {
    // Get a copy of the containerRef, otherwise changes affect all instances
    List<Character> container = new ArrayList<>(containerRef);
    // System.out.println(container);

    // Generate nodeLabel (A, B, C, ...) using character arithmetic
    char nodeLabel = (char) ('A' + node);
    // Container already has label = Cycle = No Path to Target
    if (container.contains(nodeLabel)) return;
    else if (nodeLabel == target) { // We have reached the target node
      container.add(nodeLabel);     // Add the target node to the container
      addToMap(total, container);   // Add length and path to map
      return;
    } else {
      container.add(nodeLabel); // Otherwise, just add the label to path.
    }

    // Loop through the current node's row, navigating to each element
    for (int i = 0; i < matrix.size(); i++) {
      int edgeWeight;
      if ((edgeWeight = matrix.get(node, i)) > 0) { // Edge found
        // Navigate to new node via that edge. Increment total by edge weight
        traverse(i, total + edgeWeight, target, container);
      }
    }
  }

  /**
   * Add a specific length and path to the map.
   * <p>
   * If the key already exists, it just adds the path list to the 2D List.
   * If the key doesn't exist, it instantiates a 2D List and adds the path.
   *
   * @param total     The length of the path. It is used as the map key.
   * @param container The path in List form.
   */
  private static void addToMap(int total, List<Character> container) {
    if (map.containsKey(total)) {
      // Map already contains an entry, so add to existing List
      if (!map.get(total).contains(container)) {
        map.get(total).add(container);
      } else {
        System.err.println("MAP DUPLICATE");
      }
    } else {
      // Map doesn't contain entry. Create new 2D List
      List<List<Character>> temp = new ArrayList<>();
      temp.add(container);
      map.put(total, temp);
    }
  }

  /**
   * Stringify the map in an easy-to-read format.
   * <p>
   * Shows the path length (key) on the left with the sequential paths on the
   * right. The output is displayed from  shortest to longest path.
   *
   * @return Formatted string.
   */
  private static String mapToString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Length   Path\n");
    sb.append("***********************\n");
    map.forEach((k, v) -> {
      sb.append(k);
      sb.append(":\n");
      v.forEach(list -> {
        sb.append("    ");
        list.forEach(elt -> {
          sb.append(elt);
          sb.append(" ");
        });
        sb.append("\n");
      });
    });
    return sb.toString();
  }
}
