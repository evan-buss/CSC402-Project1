/************************************************************/
/* Author: Evan Buss                                        */
/* Major: Computer Science                                  */
/* Creation Date: February 10, 2019                         */
/* Due Date: February 14, 2019                              */
/* Course: CSC402 - Data Structures 2                       */
/* Professor: Dr. Spiegel                                   */
/* Assignment: Project #1                                   */
/* Filename: Matrix.java                                    */
/* Purpose:  Matrix reads in a matrix from a given text     */
/*    file and converts it to an object that can be         */
/*    accessed.                                             */
/* Language: java (8)                                       */
/* Compilation Command: java Path.java Matrix.java          */
/* Execution Command: java Path                             */
/************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
   * @param fileName The name of the file containing the adjacency matrix of the
   *                 graph.
   */
  public Matrix(String fileName) throws Exception {
    loadMatrix(fileName); // Load the text file into the matrix object
  }

  /**
   * Get the size of the matrix.
   * <p>
   * The matrix is always square so which dimension doesn't matter. It returns the
   * "width".
   *
   * @return Size of the matrix
   */
  public int size() {
    return matrix.size();
  }

  /**
   * Converts a matrix text file to a Matrix object.
   *
   * @param fileName Name of the file containing the text representation of the
   *                 matrix
   */
  private void loadMatrix(String fileName) throws Exception {
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
   * Display the matrix as a String. Each row and column is labelled with its
   * corresponding index number.
   *
   * @return String representation of the matrix
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // Create the column labels
    int label = 0;
    sb.append("#     ");
    for (int i = 0; i < matrix.get(0).size(); i++) {
      sb.append(String.format("%-5d", label));
      label++; // Use character addition to increment to next letter
    }
    sb.append("\n\n");

    // Loop through the matrix and add each row to the string output
    // Leftmost column is the row label letter
    label = 0;
    for (List<Integer> row : matrix) {
      sb.append(String.format("%-6d", label));
      for (Integer element : row) {
        sb.append(String.format("%-5d", element));
      }
      sb.append("\n");
      label++;
    }
    return sb.toString();
  }
}
