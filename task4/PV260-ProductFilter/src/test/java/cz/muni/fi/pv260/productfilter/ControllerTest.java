package cz.muni.fi.pv260.productfilter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.CatchException.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

    private List<Product> testProducts;

    private Filter<Product> blackColorFilterStub = item -> item.getColor().equals(Color.BLACK);

    private Controller controller;

    @Before
    public void before() throws ObtainFailedException {
        testProducts = Arrays.asList(
                new Product(1, "testProduct1", Color.BLACK, BigDecimal.ONE),
                new Product(2, "testProduct2", Color.BLACK, BigDecimal.TEN),
                new Product(3, "testProduct3", Color.GREEN, BigDecimal.ONE)
        );

        inMock = mock(Input.class);
        outMock = mock(Output.class);
        loggerMock = mock(Logger.class);

        when(inMock.obtainProducts()).thenReturn(testProducts);
        controller = new Controller(inMock, outMock, loggerMock);
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
        verifyException(() -> controller.select(null));
        assertThat((Exception) caughtException())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectProductsByFilter() {
        controller.select(blackColorFilterStub);

        List<Product> expected = new ArrayList<>(testProducts);
        expected.remove(testProducts.get(2));
        verify(outMock).postSelectedProducts(expected);
    }

    @Test
    public void testSelectNoMatchFilter() {
        controller.select(item -> false);

        verify(outMock).postSelectedProducts(Collections.emptyList());
    }

    @Test
    public void testSelectAllMatchFilter() {
        controller.select(item -> true);

        verify(outMock).postSelectedProducts(testProducts);
    }

    @Test
    public void testSelectLogsSuccessMessage() {
        controller.select(item -> true);

        String expectedMessage = String.format("Successfully selected %d out of %d available products."
                , testProducts.size(), testProducts.size());

        verify(loggerMock).setLevel("INFO");
        verify(loggerMock).log(Controller.class.getSimpleName(), expectedMessage);
    }

    @Test
    public void testSelectLogsExceptionOnObtainFailure() throws Exception {
        setupInputFailure();

        controller.select(item -> true);

        String expectedMessage = String.format("Filter procedure failed with exception: %s"
                , ObtainFailedException.class.getName());

        verify(loggerMock).setLevel("ERROR");
        verify(loggerMock).log(Controller.class.getSimpleName(), expectedMessage);
    }

    @Test
    public void testSelectDoNotPassDataOnObtainFailure() throws ObtainFailedException {
        setupInputFailure();

        controller.select(item -> true);

        verify(outMock, never()).postSelectedProducts(anyCollection());
    }

    private void setupInputFailure() throws ObtainFailedException {
        when(inMock.obtainProducts()).thenThrow(ObtainFailedException.class);
    }

}
