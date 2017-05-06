package java_comparative_quickchecking;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import works.hypothesis.TestDataRule;
import works.hypothesis.strategies.Strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static works.hypothesis.strategies.Strategies.integers;

@RunWith(JUnitQuickcheck.class)
public class hypothesis {
    @Rule
    public final TestDataRule data = new TestDataRule();

    private boolean flag;

    @Before
    public void beforeEach()
    {
        flag = false;
    }

    @Test
    public void simple()
    {
        String s1 = Integer.toString(data.draw(integers())); //no string strategy
        String s2 = Integer.toString(data.draw(integers()));

        assertEquals(s1.length() + s2.length(), (s1 + s2).length());
    }

    @Test
    public void simpleFail()
    {
        String s1 = Integer.toString(data.draw(integers())); //no string strategy
        String s2 = Integer.toString(data.draw(integers()));

        assertEquals(s1.length() + s2.length() + 1, (s1 + s2).length());
    }

    @Test
    public void matrix()
    {
        getMatrix().failOn42(); //Fails all the time due to bad array init. see getMatrix()
    }

    @Test
    public void matrixFail()
    {
        int[][] arr = new int[][]{{42}};
        new Matrix(arr).failOn42();
    }

    private Matrix getMatrix() {
        int[][] arr = new int[data.draw(integers())][data.draw(integers())]; //no constraints - can't make sure these are +ve

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = data.draw(Strategies.integers());
            }
        }

        return new Matrix(arr);
    }

    @Test
    public void beforeRunsOnEachCheck()
    {
        assertFalse(flag);
        flag = true;

        String s1 = Integer.toString(data.draw(integers())); //no string strategy
        String s2 = Integer.toString(data.draw(integers())); //and no constraints - can't make sure these are +ve

        assertEquals(s1.length() + s2.length(), (s1 + s2).length());
    }
}
