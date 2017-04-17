package cz.muni.fi.pv260.productfilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class AtLeastNOfFilterTest {

    private Object testObject1;
    private Object testObject2;

    private Filter<Object> filterStubIsObject1 = item -> item == testObject1;
    private Filter<Object> filterStubIsObject2 = item -> item == testObject2;
    private Filter<Object> filterStubEqualObject1 = item -> item.equals(testObject1);

    @Before
    public void before() {
        testObject1 = new Object();
        testObject2 = new Object();
    }

    @After
    public void after() {
        testObject1 = null;
        testObject2 = null;
    }

    @Test
    public void testConstructValidArgs() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(1, filterStubIsObject1);

        assertThat(result).isNotNull();
    }

    @Test
    public void testConstructWithZeroCount() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(0));

        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConctructNegativeCount() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(-1));

        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConstructFilterNeverSucceedNoFilters() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(1));

        assertThat((Exception) caughtException())
                .isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void testConstructFilterNeverSucceedWithFilter() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(2, filterStubIsObject1));

        assertThat((Exception) caughtException())
                .isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void testPassesWithExactlyOneChildFilter() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(1, filterStubIsObject1);

        assertThat(result.passes(testObject1)).isTrue();
    }

    @Test
    public void testPassesWithExactlyNChildFilters() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(2, filterStubIsObject1, filterStubEqualObject1);

        assertThat(result.passes(testObject1)).isTrue();
    }

    @Test
    public void testPassesWithLessNValidChildFilters() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(1, filterStubIsObject1, filterStubEqualObject1);

        assertThat(result.passes(testObject1)).isTrue();
    }

    @Test
    public void testPassesWithLessNSomeInvalidFilters() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(1, filterStubIsObject1, filterStubIsObject2);

        assertThat(result.passes(testObject1)).isTrue();
    }

    @Test
    public void testFailWhenNMinusOnePass() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(2, filterStubIsObject1, filterStubIsObject2);

        assertThat(result.passes(testObject1)).isFalse();
    }

    @Test
    public void testFailWhenNoFilterPass() {
        AtLeastNOfFilter<Object> result = new AtLeastNOfFilter(1, filterStubIsObject1, filterStubIsObject2);

        assertThat(result.passes(new Object())).isFalse();
    }

}
