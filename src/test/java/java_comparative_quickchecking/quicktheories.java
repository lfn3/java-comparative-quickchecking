package java_comparative_quickchecking;

import org.junit.Before;
import org.junit.Test;
import org.quicktheories.quicktheories.core.Source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.quicktheories.quicktheories.QuickTheory.qt;
import static org.quicktheories.quicktheories.generators.SourceDSL.*;

public class quicktheories {
    private boolean flag;

    @Before
    public void beforeEach() {
        flag = false;
    }

    @Test
    public void simple() {
        qt().forAll(strings().allPossible().ofLengthBetween(0, 100),
                strings().allPossible().ofLengthBetween(0, 100))
                .checkAssert((s1, s2) -> assertEquals(s1.length() + s2.length(), (s1 + s2).length()));
    }

    @Test
    public void simpleFail() {
        qt().forAll(strings().allPossible().ofLengthBetween(0, 100),
                strings().allPossible().ofLengthBetween(0, 100))
                .checkAssert((s1, s2) -> assertEquals(s1.length() + s2.length() + 1, (s1 + s2).length()));
    }

    @Test
    public void matrix() {
        qt().forAll(matrixSource)
                .checkAssert(Matrix::failOn42); //Doesn't fail anywhere near reliably
    }

    @Test
    public void matrixFail() {
        int[][] arr = new int[][]{{42}};
        new Matrix(arr).failOn42();
    }

    @Test
    public void beforeRunsOnEachCheck() {
        qt().forAll(strings().allPossible().ofLengthBetween(0, 100),
                strings().allPossible().ofLengthBetween(0, 100))
                .checkAssert((s1, s2) -> {
                    beforeEach(); //Have to call it by hand.
                    assertFalse(flag);
                    flag = true;

                    assertEquals(s1.length() + s2.length(), (s1 + s2).length());
                });
    }

    private Source<Matrix> matrixSource =
            Source.of(
                    integers().between(1, 100).combine(
                            integers().between(1, 100),
                            lists().arrayListsOf(integers().all()).ofSizeBetween(1, 100), //Can't size generator based on prior generators?
                            (w, h, vals) -> {
                                int[][] arr = new int[w][h];
                                for (int i = 0; i < w; i++) {
                                    for (int j = 0; j < h; j++) {
                                        arr[i][j] = vals.get((i + j) % vals.size());
                                    }
                                }
                                return new Matrix(arr);
                            }));
}
