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
/* Compilation Command: javac Paths.java Matrix.java        */
/* Execution Command: java Paths                            */
/************************************************************/

/************************************************************/
/* GRADE: 25/25                                             */
/* NOTES: Make sure to get approval before using Java       */
/*        Make sure to read specs to know what to include in*/
/*          readme.                                         */
/*        prompt() function is very long... (will fix)      */
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
  public static void prompt() {
    Scanner keyboard = new Scanner(System.in); // User input
    int startVertex, targetVertex; // User's parameters
    int matrixSize; // Loaded matrix's size

    while (true) {
      // Get the file name from user input
      System.out.print("File Name (Q to quit): ");
      String fileName = keyboard.nextLine();
      if (fileName.toLowerCase().equals("q")) { // Quit if user enters "Q/q"
        break;
      }
      // Attempt to load matrix at the given file name
      try {
        matrix = new Matrix(fileName);
        matrixSize = matrix.size();
      } catch (Exception ex) {
        System.out.println("File Could Not Be Loaded\n");
        continue;
      }

      // Display the loaded matrix & prompt for parameters
      System.out.println("Matrix:");
      System.out.println(matrix.toString());
      System.out.println("Negative vertex values exit the program.");

      System.out.print("Start Vertex (0 - " + (matrixSize - 1) + "): ");
      if ((startVertex = getVertex(keyboard, matrixSize)) == -1) {
        continue;
      }

      System.out.print("Target Vertex (0 - " + (matrixSize - 1) + "): ");
      if ((targetVertex = getVertex(keyboard, matrixSize)) == -1) {
        continue;
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
    keyboard.close(); // Close scanner when done
  }

  /**
   * Gets the user's desired vertex parameter from the console.
   * <p>
   * Ensures that the user enters a valid integer and returns the value.
   *
   * @param keyboard   Scanner to get user input
   * @param matrixSize Size of the matrix
   * @return The user's vertex number or -1 if the user enters invalid value
   * (value too large for matrix size)
   */
  private static int getVertex(Scanner keyboard, int matrixSize) {

    // Loop until next input is an integer
    while (!keyboard.hasNextInt()) {
      keyboard.next(); // Discard invalid value
      System.out.print("Vertex must be an integer...\n> ");
    }
    // Get the starting vertex
    int startVertex = keyboard.nextInt();

    if (startVertex < 0) {
      System.out.println("Exiting Program...");
      System.exit(0);
    }
    // Ensure that the given target is within the bounds, otherwise there
    // will be index errors.
    if (startVertex > matrixSize - 1) {
      System.out.println("Index is too large. Try again.");
      keyboard.nextLine();
      return -1;
    }

    return startVertex;
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
  private static void traverse(int vertex, int target, int total,
                         List<Integer> containerRef) {

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

    // Loop through the current vertex's row, navigating to each element > 0
    for (int i = 0; i < matrix.size(); i++) {
      int edgeWeight;
      if ((edgeWeight = matrix.get(vertex, i)) > 0) { // Edge found
        // Navigate to new vertex via edge. Increment total by edge weight
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
   * Shows the path cost (key) on the left with the sequential paths on the
   * right. The output is displayed from lowest to highest cost.
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
      sb.append("Cost ");
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
