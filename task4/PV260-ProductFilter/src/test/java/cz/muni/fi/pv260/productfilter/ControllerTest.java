package cz.muni.fi.pv260.productfilter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class ControllerTest {

    //mock use example
    private Input inMock;
    private Output outMock;
    private Logger loggerMock;

    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;

    private Filter<Product> blackColorFilterStub = item -> item.getColor().equals(Color.BLACK);

    @Before
    public void before() throws ObtainFailedException {
        testProduct1 = new Product(1, "testProduct1", Color.BLACK, BigDecimal.ONE);
        testProduct2 = new Product(2, "testProduct2", Color.BLACK, BigDecimal.TEN);
        testProduct3 = new Product(3, "testProduct3", Color.GREEN, BigDecimal.ONE);

        inMock = mock(Input.class);
        outMock = mock(Output.class);
        loggerMock = mock(Logger.class);

        initValidMockBehavior();
    }

    private void initValidMockBehavior() throws ObtainFailedException {
        when(inMock.obtainProducts()).thenReturn(Arrays.asList(testProduct1, testProduct2, testProduct3));

    }

    private Controller createControllerWithMocks() {
        return new Controller(inMock, outMock, loggerMock);
    }

    @Test
    public void testNullInputConstructor() throws Exception {
        verifyException(() -> new Controller(null, outMock, loggerMock));

        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testNullOutputConstructor() throws Exception {
        verifyException(() -> new Controller(inMock, null, loggerMock));

        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testNullLoggerConstructor() throws Exception {
        verifyException(() -> new Controller(inMock, outMock, null));

        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Ignore
    public void testSelectWithNullFilter() throws Exception {
        Controller controller = createControllerWithMocks();

        verifyException(() -> controller.select(null));
        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectProductsByFilter() {
        Controller controller = createControllerWithMocks();

        controller.select(blackColorFilterStub);

        verify(outMock).postSelectedProducts(Arrays.asList(testProduct1, testProduct2));
    }

    @Test
    public void testSelectNoMatchFilter() {
        Controller controller = createControllerWithMocks();

        controller.select(item -> false);

        verify(outMock).postSelectedProducts(Collections.emptyList());
    }

    @Test
    public void testSelectAllMatchFilter() {
        Controller controller = createControllerWithMocks();

        controller.select(item -> true);

        verify(outMock).postSelectedProducts(Arrays.asList(testProduct1, testProduct2, testProduct3));
    }

}
