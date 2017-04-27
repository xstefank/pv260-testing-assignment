package cz.muni.fi.pv260.ide;

import cz.muni.fi.pv260.util.ElementsUtil;
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
    private static final String CREATE_PAGE = "/Special:CreatePage";
    private static final String MAIN_PAGE = "/Main-Page";
    private static final int WAIT_DELAY = 2 * 1000;

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
    public void testArticleCreateInvalidNameResetsForm() {
        getPage(CREATE_PAGE);

        sendInputToElement(By.id(ElementsUtil.CP_TITLE_INPUT), "<a href=\"#\">gone</a>");

        WebElement resultInput = webDriver.findElement(By.id(ElementsUtil.CP_TITLE_INPUT));
        Assert.assertTrue("Title input should reset on invalid input", resultInput.getText().isEmpty());
    }

    @Test
    public void testArticleCreatePopupKnownKeywords() {
        getPage(CREATE_PAGE);

        sendInputToElement(By.id(ElementsUtil.CP_TITLE_INPUT), "do a Selenium test suite");

        WebElement resultTitleHdr = webDriver.findElement(By.id(ElementsUtil.CPR_TITLE_HDR));
        Assert.assertTrue("There should be a popup for the known keywords in article creation page",
                resultTitleHdr.getText().contains("Almost there!"));
    }

    @Test
    public void testArticleCreateValidRedirect() {
        getPage(CREATE_PAGE);

        sendInputToElement(By.id(ElementsUtil.CP_TITLE_INPUT), "do a Selenium test suite");

        WebElement resultTitleHdr = webDriver.findElement(By.id(ElementsUtil.CPR_TITLE_HDR));
        Assert.assertTrue("There should be a popup for the known keywords in article creation page",
                resultTitleHdr.getText().contains("Almost there!"));

        clickElement(By.id(ElementsUtil.CPR_ARTICLE_NONE));

        clickElement(By.xpath("//*[contains(text(), 'Write My Article')]"));

        WebElement resultArticleTag = webDriver.findElement(By.className(ElementsUtil.FIRST_HEADING));
        Assert.assertTrue("Missing information about article cration",
                resultArticleTag.getText().contains("Creating"));
        Assert.assertTrue("Missing title of the article being created",
                resultArticleTag.getText().contains("How to Do a Selenium Test Suite"));

    }

    @Test
    public void testArticleClickAndRedirect() throws InterruptedException {
        getPage(MAIN_PAGE);

        clickElement(By.id(ElementsUtil.GAT_RANDOM));

        Assert.assertTrue("Failed to redirect to random article",
                webDriver.findElement(By.id(ElementsUtil.SOCIAL_PROOF)).getText().contains("About this wikiHow"));
    }

    @Test
    public void testWriteArticleRedirect() throws InterruptedException {
        getPage(MAIN_PAGE);

        clickElement(By.id(ElementsUtil.GAT_WRITE_ARTICLE));

        Assert.assertTrue("Failed to redirect to Write an Article page",
                webDriver.findElement(By.id(ElementsUtil.ARTICLE)).getText().contains("Write an Article"));
    }

    // Category component

    @Test
    public void testCategoryRedirect() throws InterruptedException {
        getPage(MAIN_PAGE);

        clickElement(By.xpath("//*[contains(text(), 'Travel')]"));

        Assert.assertTrue("Failed to redirect to category page",
                webDriver.findElement(By.className(ElementsUtil.FIRST_HEADING)).getText().contains("Travel"));
    }

    @Test
    public void testNonexistingCategoryRedirect() throws InterruptedException {
        getPage("/Category:Hakunamatata");

        Thread.sleep(WAIT_DELAY);

        Assert.assertTrue("Failed to redirect to invalid category page",
                webDriver.findElement(By.className(ElementsUtil.FIRST_HEADING)).getText().contains("Hakunamatata") &&
                webDriver.findElement(By.className(ElementsUtil.NO_ARTICLE_TEXT)).getText()
                        .contains("wikiHow does not yet have a page with this exact name."));

    }

    // Search component

    @Test
    public void testHtmlTagsParsingAfterSearch() {
        getPage(MAIN_PAGE);

        sendInputToElement(By.name(ElementsUtil.SEARCH), "<h4><i>h4WeirdItalicTitle</i></h4>");

        Assert.assertTrue("HTML tags are not parsed in search field",
                webDriver.findElement(By.name(ElementsUtil.SEARCH))
                        .getAttribute(ElementsUtil.VALUE).contains("h4WeirdItalicTitle"));
    }

    @Test
    public void testSearchResultsArePresent() {
        getPage(MAIN_PAGE);

        sendInputToElement(By.name(ElementsUtil.SEARCH), "boil an egg");

        Assert.assertTrue("Valid search is not propagated",
                webDriver.findElement(By.name(ElementsUtil.SEARCH))
                        .getAttribute(ElementsUtil.VALUE).contains("boil an egg"));

        Assert.assertTrue("Valid search page has wrong title",
                webDriver.getTitle().equals("Search for 'boil an egg' - wikihow"));

        Assert.assertTrue("Valid search page should contain at least one article",
                webDriver.findElement(By.className(ElementsUtil.RESULT)).getText().contains("Egg"));
    }

    @Test
    public void testUnusualKeywordSearchReturnsNoResult() {
        getPage(MAIN_PAGE);

        sendInputToElement(By.name(ElementsUtil.SEARCH), "<h4><i>h4WeirdItalicTitle</i></h4>");

        String bodycontents = webDriver.findElement(By.id(ElementsUtil.BODY_CONTENTS)).getText();
        Assert.assertTrue("Invalid keywords should not find any result",
                bodycontents.contains("No results for") && bodycontents.contains("<h4><i>h4WeirdItalicTitle</i></h4>"));
    }

    private void getPage(String path) {
        webDriver.get(WIKIHOW_PAGE + path);
    }

    private void sendInputToElement(By elementBy, String input) {
        WebElement element = webDriver.findElement(elementBy);
        element.sendKeys(input);
        element.submit();
        waitForPage();
    }

    private void clickElement(By elementBy) {
        webDriver.findElement(elementBy).click();
        waitForPage();
    }

    private void waitForPage() {
        try {
            Thread.sleep(WAIT_DELAY);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }
}
