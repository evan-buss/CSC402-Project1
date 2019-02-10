import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
  /**
   * Two dimensional ArrayList of Integers holding the adjacency matrix
   */
  private final List<List<Integer>> matrix = new ArrayList<>();

  /**
   * Calls the loadMatrix function on the file name
   *
   * @param fileName The name of the file containing the adjacency matrix of
   *                 the graph.
   */
  public Matrix(String fileName) {
    loadMatrix(fileName); //  Load the text file into the matrix object
  }

  /**
   * Get the size of the matrix.
   * <p>
   * The matrix is always square so which dimension doesn't matter. It
   * returns the "width".
   *
   * @return Size of the matrix
   */
  public int size() {
    return matrix.size();
  }

  /**
   * Converts a matrix text file to a Matrix object.
   *
   * @param fileName Name of the file containing the text representation of
   *                 the matrix
   */
  private void loadMatrix(String fileName) {
    try {
      // Create a buffered reader to read from the given file name
      BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
      String line;
      while ((line = reader.readLine()) != null) {
        // Vector of ints that represents a row in the matrix
        List<Integer> row = new ArrayList<>();
        for (String el : line.split(" ")) {
          // Add each element to the row
          row.add(Integer.parseInt(el));
        }

        matrix.add(row); // Add each row to the matrix object
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Error reading from file");
      e.printStackTrace();
    }
  }

  /**
   * Returns the node at the specified row and column.
   *
   * @param row The node's row.
   * @param col The node's column
   * @return Value of the node at the specified row and column.
   */
  public int get(int row, int col) {
    return matrix.get(row).get(col);
  }

  /**
   * Display the matrix as a String. Each row and column is shown with its
   * node letter as label.
   *
   * @return String representation of the matrix
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // Create the column labels
    char label = 'A';
    sb.append("     ");
    for (int i = 0; i < matrix.get(0).size(); i++) {
      sb.append(String.format("%-5c", label));
      label++; // Use character addition to increment to next letter
    }
    sb.append("\n");

    // Loop through the matrix and add each row to the string output
    // Leftmost column is the row label letter
    label = 'A';
    for (List<Integer> row : matrix) {
      sb.append(String.format("%-5c", label));
      for (Integer element : row) {
        sb.append(String.format("%-5d", element));
      }
      sb.append("\n");
      label++;
    }
    return sb.toString();
  }
}
