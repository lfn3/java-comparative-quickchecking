package java_comparative_quickchecking;

public class Matrix {
    private final int[][] array;
    public Matrix(int[][] arr)
    {
        array = arr;
    }

    public void failOn42()
    {
        for (int[] vector: array) {
            for (int entry: vector) {
                if (entry == 42)
                {
                    throw new IllegalStateException("42!");
                }
            }
        }
    }
}
