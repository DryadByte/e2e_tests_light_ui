package io.testomat.e2e_tests_light_ui;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPageTests extends BaseTest {

    @Test
    public void loginFindProjectWithTests() {

        open(config.getBaseUrl());
        TestConfig.Credentials credentials = config.getCredentials();
        //login
        $("#content-desktop #user_email").setValue(credentials.getEmail());
        $("#content-desktop #user_password").setValue(credentials.getPassword());
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=\"commit\"]").click();
        $(".common-flash-success").shouldBe(visible);

        //search project
        $x("//*[@id=\"search\"]").setValue("Manufacture light");

        //select project
        $("[href=\"/projects/manufacture-light/\"]").click();

        //open project
        String expectedUrl = "https://app.testomat.io/projects/manufacture-light/";
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assert expectedUrl.equals(currentUrl) : "Expected URL: " + expectedUrl + ", but found: " + currentUrl;
        $("h2").shouldHave(text("Manufacture light"));
    }
}
