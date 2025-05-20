package io.testomat.e2e_tests_light_ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.Selenide.webdriver;
import static io.testomat.e2e_tests_light_ui.utils.StringParsers.parseIntegerFromString;

public class ProjectPageTests extends BaseTest {

    private static final String PROJECT_NAME = "Manufacture light";
    private static final String TARGET_PROJECT_PATH = "/projects/manufacture-light/";
    private static final String EXPECTED_URL = config.getBaseUrl() + TARGET_PROJECT_PATH;

    @BeforeAll
    public static void openTestomatAndLogin() {
        open(config.getBaseUrl());
        loginUser();

    }

    @BeforeEach
    public void openHomePage() {
        open(config.getBaseUrl());

    }

    @Test
    public void loginFindProjectWithTests() {
        searchProject(PROJECT_NAME);
        selectProject(TARGET_PROJECT_PATH);
        openProject(EXPECTED_URL, PROJECT_NAME);

    }

    @Test
    public void loginFindProjectWithoutTests() {
        searchProject(PROJECT_NAME);
        SelenideElement targetProject = countOfProjectsShouldBeEqualTo(1).first();
        countOfTestCasesShouldBeEqualTo(targetProject, 0);

    }

    @Test
    public void editReadme() {
        searchProject(PROJECT_NAME);
        selectProject(TARGET_PROJECT_PATH);
        openProject(EXPECTED_URL, PROJECT_NAME);
        openReadme("readme").click();
        openEditForm("settings/readme").click();
        String readmeText = updateReadmeText();
        readmeTextWasUpdated(readmeText);

    }

    private void readmeTextWasUpdated(String readmeText) {
        $("div.ember-notify-cn.custom-notify").shouldBe(visible, Duration.ofSeconds(5)).shouldHave(text("Readme has been saved"));
        openEditForm("readme").click();
        $("div.markdown p").shouldHave(Condition.text("Readme was edited by " + readmeText));
    }

    private static String updateReadmeText() {
        $x("//button[.//text()[contains(., 'Edit Readme')]]").click();
        switchTo().frame($("div.frame-container iframe"));
        SelenideElement textArea = $("div.view-lines.monaco-mouse-cursor-text");
        textArea.shouldBe(visible).click();
        $("div.monaco-editor").click();
        SelenideElement input = $("textarea.inputarea");
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        String word = RandomStringUtils.randomAlphabetic(8);
        input.sendKeys("Readme was edited by " + word);
        switchTo().defaultContent();
        $("button.primary-btn.btn-text-and-icon.btn-md").click();
        return word;
    }

    private SelenideElement openEditForm(String subPath) {
        return $$("[href='" + TARGET_PROJECT_PATH + subPath + "']").find(Condition.visible);
    }

    private SelenideElement openReadme(String subPath) {
        return $("[href='" + TARGET_PROJECT_PATH + subPath + "']");
    }


    private void openProject(String expectedUrl, String projectName) {
        webdriver().shouldHave(WebDriverConditions.url(expectedUrl));
        $("h2").shouldHave(text(projectName));
    }

    private void selectProject(String targetProjectPath) {
        $("[href=\"" + targetProjectPath + "\"]").click();
    }

    private void searchProject(String projectName) {
        $("#search").setValue(projectName);
    }

    private static void loginUser() {
        TestConfig.Credentials credentials = config.getCredentials();
        $("#content-desktop #user_email").setValue(credentials.getEmail());
        $("#content-desktop #user_password").setValue(credentials.getPassword());
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=commit]").click();
        $(".common-flash-success").shouldBe(visible);
    }

    private static void countOfTestCasesShouldBeEqualTo(SelenideElement targetProject, int expectedCount) {
        String countOfTests = targetProject.$("p").getText();
        Integer actualCountOfTests = parseIntegerFromString(countOfTests);
        Assertions.assertEquals(0, actualCountOfTests);
    }

    private ElementsCollection countOfProjectsShouldBeEqualTo(int expectedSize) {
        return $$(String.format("#grid ul li:has(a[href='%s'])", TARGET_PROJECT_PATH)).filter(visible).shouldHave(size(expectedSize));
    }
}
