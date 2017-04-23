package cz.muni.fi.pv260.ide;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class WikiHowTest {

    private static final String WIKIHOW_PAGE = "http://www.wikihow.com/";

    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = new FirefoxDriver();
    }

    @After
    public void after() {
        webDriver.quit();
    }

    @Test
    public void testArticleCreateInvalidNameResetsForm() throws InterruptedException {
        getSubPage("Special:CreatePage");

        WebElement titleInput = webDriver.findElement(By.id("cp_title_input"));

        titleInput.sendKeys("<a href=\"#\">gone</a>");

        titleInput.submit();

        Thread.sleep(2000);

        WebElement resultInput = webDriver.findElement(By.id("cp_title_input"));
        Assert.assertTrue("Title input should reset on invalid input", resultInput.getText().isEmpty());
    }

    @Test
    public void testArticleCreatePopupKnownKeywords() throws InterruptedException {
        getSubPage("Special:CreatePage");

        WebElement titleInput = webDriver.findElement(By.id("cp_title_input"));
        titleInput.sendKeys("do a Selenium test suite");
        titleInput.submit();

        Thread.sleep(2000);

        WebElement resultTitleHdr = webDriver.findElement(By.id("cpr_title_hdr"));
        Assert.assertTrue("There should be a popup for the known keywords in article creation page",
                resultTitleHdr.getText().contains("Almost there!"));
    }

    @Test
    public void testArticleCreateValidRedirect() throws InterruptedException {
        getSubPage("Special:CreatePage");

        WebElement titleInput = webDriver.findElement(By.id("cp_title_input"));
        titleInput.sendKeys("do a Selenium test suite");
        titleInput.submit();

        Thread.sleep(2000);

        WebElement resultTitleHdr = webDriver.findElement(By.id("cpr_title_hdr"));
        Assert.assertTrue("There should be a popup for the known keywords in article creation page",
                resultTitleHdr.getText().contains("Almost there!"));

        webDriver.findElement(By.id("cpr_article_none")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//*[contains(text(), 'Write My Article')]")).click();

        Thread.sleep(2000);

        WebElement resultArticleTag = webDriver.findElement(By.className("firstHeading"));
        Assert.assertTrue("Missing information about article cration",
                resultArticleTag.getText().contains("Creating"));
        Assert.assertTrue("Missing title of the article being created",
                resultArticleTag.getText().contains("How to Do a Selenium Test Suite"));

    }

    private void getSubPage(String path) {
        webDriver.get(WIKIHOW_PAGE + path);
    }
}
