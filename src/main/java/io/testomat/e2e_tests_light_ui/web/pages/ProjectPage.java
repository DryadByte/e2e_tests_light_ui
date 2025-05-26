package io.testomat.e2e_tests_light_ui.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.Selenide.webdriver;

public class ProjectPage {

    public void verify(String expectedUrl, String projectName) {
        webdriver().shouldHave(WebDriverConditions.url(expectedUrl));
        $("h2").shouldHave(text(projectName));
    }

    public SelenideElement findVisibleLinkBySubPath(String targetProjectPath, String subPath) {
        return $$("[href='" + targetProjectPath + subPath + "']").find(Condition.visible);
    }

    public static String updateReadmeText() {
        $$("button").findBy(text("Edit Readme")).click();
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

    public void checkReadmeTextWasUpdated(String readmeText, String targetProjectPath) {
        $("div.ember-notify-cn.custom-notify").shouldBe(visible, Duration.ofSeconds(5)).shouldHave(text("Readme has been saved"));
        findVisibleLinkBySubPath(targetProjectPath, "readme").click();
        $("div.markdown p").shouldHave(Condition.text("Readme was edited by " + readmeText));
    }
}
