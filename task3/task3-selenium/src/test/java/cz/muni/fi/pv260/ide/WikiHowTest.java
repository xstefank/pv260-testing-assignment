package cz.muni.fi.pv260.ide;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author <a href="mailto:xstefank122@gmail.com">Martin Stefanko</a>
 */
public class WikiHowTest {

    private static final String WIKIHOW_PAGE = "http://www.wikihow.com";

    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = new FirefoxDriver();
    }

    @After
    public void after() {
        webDriver.quit();
    }

    // Article component

    @Test
    public void testArticleCreateInvalidNameResetsForm() throws InterruptedException {
        getSubPage("/Special:CreatePage");

        WebElement titleInput = webDriver.findElement(By.id("cp_title_input"));

        titleInput.sendKeys("<a href=\"#\">gone</a>");

        titleInput.submit();

        Thread.sleep(2000);

        WebElement resultInput = webDriver.findElement(By.id("cp_title_input"));
        Assert.assertTrue("Title input should reset on invalid input", resultInput.getText().isEmpty());
    }

    @Test
    public void testArticleCreatePopupKnownKeywords() throws InterruptedException {
        getSubPage("/Special:CreatePage");

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
        getSubPage("/Special:CreatePage");

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

    @Test
    public void testArticleClickAndRedirect() throws InterruptedException {
        getSubPage("/Main-Page");

        webDriver.findElement(By.id("gatRandom")).click();

        Thread.sleep(2000);

        Assert.assertTrue("Failed to redirect to random article",
                webDriver.findElement(By.id("social_proof_sidebox")).getText().contains("About this wikiHow"));
    }

    @Test
    public void testWriteArticleRedirect() throws InterruptedException {
        getSubPage("/Main-Page");

        webDriver.findElement(By.id("gatWriteAnArticle")).click();

        Thread.sleep(2000);

        Assert.assertTrue("Failed to redirect to Write an Article page",
                webDriver.findElement(By.id("article")).getText().contains("Write an Article"));
    }

    // Category component

    @Test
    public void testCategoryRedirect() throws InterruptedException {
        getSubPage("/Main-Page");

        webDriver.findElement(By.xpath("//*[contains(text(), 'Travel')]")).click();

        Thread.sleep(2000);

        Assert.assertTrue("Failed to redirect to category page",
                webDriver.findElement(By.className("firstHeading")).getText().contains("Travel"));
    }

    @Test
    public void testNonexistingCategoryRedirect() throws InterruptedException {
        getSubPage("/Category:Hakunamatata");

        Thread.sleep(2000);

        Assert.assertTrue("Failed to redirect to invalid category page",
                webDriver.findElement(By.className("firstHeading")).getText().contains("Hakunamatata") &&
                webDriver.findElement(By.className("noarticletext")).getText()
                        .contains("wikiHow does not yet have a page with this exact name."));

    }

    // Search component

    @Test
    public void testHtmlTagsParsingAfterSearch() throws InterruptedException {
        getSubPage("/Main-Page");

        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys("<h4><i>h4WeirdItalicTitle</i></h4>");
        searchField.submit();

        Thread.sleep(2000);

        Assert.assertTrue("HTML tags are not parsed in search field",
                webDriver.findElement(By.name("search")).getAttribute("value").contains("h4WeirdItalicTitle"));
    }

    @Test
    public void testSearchResultsArePresent() throws InterruptedException {
        getSubPage("/Main-Page");

        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys("boil an egg");
        searchField.submit();

        Thread.sleep(2000);

        Assert.assertTrue("Valid search is not propagated",
                webDriver.findElement(By.name("search")).getAttribute("value").contains("boil an egg"));

        Assert.assertTrue("Valid search page has wrong title",
                webDriver.getTitle().equals("Search for 'boil an egg' - wikihow"));

        Assert.assertTrue("Valid search page should contain at least one article",
                webDriver.findElement(By.className("result")).getText().contains("Egg"));
    }

    @Test
    public void testUnusualKeywordSearchReturnsNoResult() throws InterruptedException {
        getSubPage("/Main-Page");

        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys("<h4><i>h4WeirdItalicTitle</i></h4>");
        searchField.submit();

        Thread.sleep(2000);

        String bodycontents = webDriver.findElement(By.id("bodycontents")).getText();
        Assert.assertTrue("Invalid keywords should not find any result",
                bodycontents.contains("No results for") && bodycontents.contains("<h4><i>h4WeirdItalicTitle</i></h4>"));
    }

    private void getSubPage(String path) {
        webDriver.get(WIKIHOW_PAGE + path);
    }
}
