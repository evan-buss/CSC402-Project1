/************************************************************/
/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: February 12, 2019                         */
/* Due Date: February 10, 2019                              */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #1                                   */
/* Filename: Paths.java                                     */
/* Purpose:  Traverses a matrix using a brute force         */
/*    depth-first recursive algorithm. Attempts to find all */
/*    paths from the starting vertex to the target vertex.  */
/*    Outputs the results in order from shortest to longest */
/*    path.                                                 */
/* Language: java (8)                                       */
/* Compilation Command: javac Path.java Matrix.java         */
/* Execution Command: java Path                             */
/************************************************************/

import java.util.*;

public class Paths {
  /**
   * Map containing the path length as the key and a two dimensional character
   * List as the value.
   */
  private static final Map<Integer, List<List<Integer>>> map = new HashMap<>();

  /**
   * Matrix object that holds the Directed Graph Adjacency Matrix loaded from the
   * provided text file.
   */
  private static Matrix matrix = null;

  /**
   * Calls the prompt function
   */
  public static void main(String[] args) {
    prompt();
  }

  /**
   * Prompt the user to enter program information.
   * <p>
   * The prompt will loop until the user enters an exit command allowing them to
   * enter multiple matrices and starting/target vertices.
   */
  private static void prompt() {
    Scanner keyboard = new Scanner(System.in); // Scanner to console input
    int startVertex, targetVertex, matrixSize;

    while (true) {
      // Get the file name from user input
      System.out.print("File Name (Q to quit): ");
      String fileName = keyboard.nextLine();
      if (fileName.toLowerCase().equals("q")) { // Quit if user enters "Q/q"
        break;
      }
      // Attempt to load matrix at the given file name
      // Restart the loop if invalid file given
      try {
        matrix = new Matrix(fileName);
        matrixSize = matrix.size();
      } catch (Exception ex) {
        System.err.println("File Could Not Be Loaded");
        continue;
      }

      // Display the loaded matrix
      System.out.println("Matrix:");
      System.out.println(matrix.toString());

      // Get the starting vertex
      System.out.println("Negative vertex values exit the program.");
      System.out.print("Start Vertex (0 - " + (matrixSize - 1) + "): ");
      startVertex = keyboard.nextInt();
      if (startVertex < 0) {
        System.out.println("Exiting Program...");
        break;
      }

      // Ensure that the given target is within the bounds, otherwise there
      // will be index errors.
      if (startVertex > matrixSize - 1) {
        System.err.println("Index is too large. Try again.");
        keyboard.nextLine();
        continue; // restart loop
      }

      // Get the target vertex
      System.out.print("Target Vertex (0 - " + (matrixSize - 1) + "): ");
      targetVertex = keyboard.nextInt();
      if (targetVertex < 0) {
        System.out.println("Exiting Program...");
        break;
      }

      // Ensure that the given target is within the bounds, otherwise there
      // will be index errors.
      if (targetVertex > matrixSize - 1) {
        System.err.println("Index is too large. Try again.");
        keyboard.nextLine();
        continue; // restart loop
      }
      keyboard.nextLine();

      // Call the recursive algorithm at the index of the starting vertex
      // Starting total is initialized to 0
      // Target vertex remains the same
      // Path container is an empty ArrayList
      traverse(startVertex, targetVertex, 0, new ArrayList<>());

      // Display the path maps
      System.out.println(mapToString());

      // Reset the map before loading next run
      map.clear();
    }
    keyboard.close();
  }

  /**
   * Brute force recursive algorithm to find all paths from one vertex to another.
   * <p>
   * Finds all available paths from starting vertex to target vertex. Creates a
   * map of the results.
   *
   * @param vertex       Starting vertex to be traversed from.
   * @param total        Total length of the current path (start at 0).
   * @param target       Target vertex to be traversed to.
   * @param containerRef Container storing the path sequentially.
   */
  public static void traverse(int vertex, int target, int total, List<Integer> containerRef) {

    // Get a copy of the containerRef, otherwise changes affect all instances
    List<Integer> container = new ArrayList<>(containerRef);

    // Container already has label = Cycle = No Path to Target
    if (container.contains(vertex))
      return;
    else if (vertex == target) { // We have reached the target vertex
      container.add(vertex); // Add the target vertex to the container
      addToMap(total, container); // Add total length and path to map
      return;
    } else {
      container.add(vertex); // Otherwise, just add the label to path.
    }

    // Loop through the current vertex's row, navigating to each element
    for (int i = 0; i < matrix.size(); i++) {
      int edgeWeight;
      if ((edgeWeight = matrix.get(vertex, i)) > 0) { // Edge found
        // Navigate to new vertex via that edge. Increment total by edge weight
        traverse(i, target, total + edgeWeight, container);
      }
    }
  }

  /**
   * Add a specific length and path to the map.
   * <p>
   * If the key already exists, it just adds the path list to the 2D List. If the
   * key doesn't exist, it instantiates a 2D List and adds the path.
   *
   * @param total     The length of the path. It is used as the map key.
   * @param container The path in List form.
   */
  private static void addToMap(int total, List<Integer> container) {
    if (map.containsKey(total)) {
      // Map already contains an entry, so add to existing List
      if (!map.get(total).contains(container)) {
        map.get(total).add(container);
      } else {
        System.err.println("MAP DUPLICATE");
      }
    } else {
      // Map doesn't contain entry. Create new 2D List
      List<List<Integer>> temp = new ArrayList<>();
      temp.add(container);
      map.put(total, temp);
    }
  }

  /**
   * Stringify the map in an easy-to-read format.
   * <p>
   * Shows the path length (key) on the left with the sequential paths on the
   * right. The output is displayed from shortest to longest path.
   *
   * @return Formatted string.
   */
  private static String mapToString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nDiscovered Paths\n");
    sb.append("****************\n");

    // Map to hold sorted version of path map
    // (LinkedHashMap preserves insertion order)
    Map<Integer, List<List<Integer>>> sortedMap = new LinkedHashMap<>();

    // Use Java stream to sort the existing map by key from smallest to largest
    // Put the elements into the sortedMap. This preserves the order.
    map.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .forEachOrdered(elt -> sortedMap.put(elt.getKey(), elt.getValue()));

    // Loop through sorted map. Generating a stylized string.
    sortedMap.forEach((k, v) -> {
      sb.append("Length ");
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
