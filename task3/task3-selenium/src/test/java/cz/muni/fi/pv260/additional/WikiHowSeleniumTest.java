package cz.muni.fi.pv260.additional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class WikiHowSeleniumTest {

    private static final String WIKIHOW_PAGE = "http://www.wikihow.com/";

    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = new FirefoxDriver();
        webDriver.get(WIKIHOW_PAGE);
    }

    @After
    public void after() {
        webDriver.quit();
    }

    @Test
    public void testSeleniumSoftwareIsOnWikipedia() throws InterruptedException {

        WebElement searchField = webDriver.findElement(By.name("search"));

        searchField.sendKeys("google");

        searchField.submit();

        Thread.sleep(2000);

        Assert.assertTrue("There is a \"How to Search Google\" wikiHow displayed",
                webDriver.getPageSource().contains("//www.wikihow.com/Search-Google"));
    }

    @Test
    public void testBrokenLinks() {
        List<String> linkList = findAllOutboundLinks();


        linkList.forEach(link -> {
            try {
                Assert.assertTrue(String.format("%s link is not valid", link),
                        isLinkBroken(new URL(link)));
            } catch (Exception e) {
                Assert.fail(String.format("\"%s\" is not a valid URL: %s", link, e));
            }
        });
    }

    private List<String> findAllOutboundLinks() {

        List<WebElement> elementList = webDriver.findElements(By.tagName("a"));

        List<String> hrefList = new ArrayList<>();

        elementList.stream().map(webElement -> webElement.getAttribute("href"))
                .filter(Objects::nonNull)
                .filter(href -> href.startsWith("#"))
                .filter(String::isEmpty)
                .forEach(href -> hrefList.add(href.startsWith("/") ? WIKIHOW_PAGE + href : href));

        return hrefList;
    }

    public boolean isLinkBroken(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.connect();
            connection.getResponseMessage();
            connection.disconnect();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
