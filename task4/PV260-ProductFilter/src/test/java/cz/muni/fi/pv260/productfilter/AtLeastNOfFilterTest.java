package cz.muni.fi.pv260.productfilter;

import org.junit.Test;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class AtLeastNOfFilterTest {

    private Filter<Integer> filterStubEqualOne = item -> item == 1;
    private Filter<Integer> filterStubGreaterEqualOne = item -> item >= 1;

    @Test
    public void testConstructValidArgs() {
        AtLeastNOfFilter<Integer> result = new AtLeastNOfFilter(1, filterStubEqualOne);

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
    public void testConstructFilterCannotSucceedNoFilters() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(1));

        assertThat((Exception) caughtException())
                .isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void testConstructFilterCannotSucceedWithFilter() throws Exception {
        verifyException(() -> new AtLeastNOfFilter(2, filterStubEqualOne));

        assertThat((Exception) caughtException())
                .isInstanceOf(FilterNeverSucceeds.class);
    }

    @Test
    public void testPassesWithOneChildFilter() {
        AtLeastNOfFilter<Integer> result = new AtLeastNOfFilter(1, filterStubEqualOne);

        assertThat(result.passes(Integer.valueOf(1))).isTrue();
    }

    @Test
    public void testPassesWithNChildFilters() {
        AtLeastNOfFilter<Integer> result = new AtLeastNOfFilter(2, filterStubEqualOne, filterStubGreaterEqualOne);

        assertThat(result.passes(Integer.valueOf(1))).isTrue();
    }

}
