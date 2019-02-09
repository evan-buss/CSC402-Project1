// File: TestMatrix.cpp
// Permits compiplation of Matrix class

#include <iostream>
#include <fstream>
#include "Matrix.h"

int main()
{ Matrix<int> TheMatrix;
  ifstream inf("testSmall.txt");
  inf >> TheMatrix;
  cout << TheMatrix;
}