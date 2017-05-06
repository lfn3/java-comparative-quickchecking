package java_comparative_quickchecking;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JUnitQuickcheck.class)
public class junit_quickcheck {
    private boolean flag;

    @Before
    public void beforeEach()
    {
        flag = false;
    }

    @Property
    public void simple(String s1, String s2)
    {
        assertEquals(s1.length() + s2.length(), (s1 + s2).length());
    }

    @Property
    public void simpleFail(String s1, String s2)
    {
        assertEquals(s1.length() + s2.length() + 1, (s1 + s2).length());
    }

    @Test
    public void example()
    {
        simple("hello", "world!");
    }

    @Property
    public void matrix(@From(MatrixGenerator.class) Matrix m)
    {
        m.failOn42(); //Doesn't fail anywhere near reliably
    }

    @Test
    public void matrixFail()
    {
        int[][] arr = new int[][]{{42}};
        matrix(new Matrix(arr));
    }

    @Property
    public void beforeRunsOnEachCheck(String s1)
    {
        assertFalse(flag);
        flag = true;

        simple(s1, "hello!");
    }

    public static class MatrixGenerator extends Generator<Matrix> {
        public MatrixGenerator() {
            super(Matrix.class);
        }

        @Override
        public Matrix generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
            int width = sourceOfRandomness.nextInt(0, 1000);
            int height = sourceOfRandomness.nextInt(0, 1000);

            int[][] matrix = new int[width][height];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    matrix[i][j] = sourceOfRandomness.nextInt();
                }
            }

            return new Matrix(matrix);
        }
    }
}
