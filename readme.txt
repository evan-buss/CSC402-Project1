Compiling the Program
===================================

    javac Paths.java Matrix.java


Running the Program
===================================

    java Paths


Data Structure Choices
===================================

    Paths.java
        I decided to use a map, specifically a HashMap, to store the paths
        associated with specific cost lengths. HashMap was chosen because
        it is one of the most basic implementations of the Map interface.

        The map's key is an Integer representing the cost of all the paths
        stored as values. The value of the map is a two dimensional List of
        Integers. Each path object is a list that is added to the two
        dimensional list when it is found. I choose to use a List because
        it is sequential, meaning that the vertices added to the path would
        always be in the correct order. Also, lists are resizeable unlike
        arrays. This allowed me to insert new elements at any time without
        knowing the required size at the start.

        Before displaying the paths map, I sorted it from smallest to
        largest key and inserted the elements and keys it into another type
        of Map called a LinkedHashMap. This implementation of the Map
        interface preserves insertion order so it is perfect for ensuring
        the output would always remain the same.

    Matrix.java
        I decided to use a two dimensional List of Integers to store the
        Matrix object. This allowed me to easily handle variable sized
        matrices, as well as enabling easy access to elements using the
        List's get() method ("matrix.get(0).get(1)").


Documentation Website
===================================

    http://unixweb.kutztown.edu/~ebuss376/CSC402/Project1/package-summary.html

NOTES
===================================

    I know that the pdf says to name the file "paths.cpp" all lowercase...
    Because I am using Java, it follows the standard Java naming convention
    where all classes start with an uppercase first letter.

    I used JavaDocs instead of Doxygen because it is specifically for Java.

    I rewrote my own Matrix.java class that implements only the functions of
    Matrix.cpp needed for this project.

