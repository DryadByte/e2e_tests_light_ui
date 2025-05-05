package io.testomat.e2e_tests_light_ui;

import com.codeborne.selenide.WebDriverConditions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;

public class ProjectPageTests extends BaseTest {

    @Test
    public void loginFindProjectWithTests() {

        open(config.getBaseUrl());
        TestConfig.Credentials credentials = config.getCredentials();
        //login
        $("#content-desktop #user_email").setValue(credentials.getEmail());
        $("#content-desktop #user_password").setValue(credentials.getPassword());
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=commit]").click();
        $(".common-flash-success").shouldBe(visible);

        String projectName = "Manufacture light";
        String projectSlug = "manufacture-light";
        String expectedUrl = config.getBaseUrl() + "/projects/" + projectSlug + "/";


        //search project
        $("#search").setValue(projectName);

        //select project
        $("[href=\"/projects/" + projectSlug + "/\"]").click();

        //open project
        webdriver().shouldHave(WebDriverConditions.url(expectedUrl));
        $("h2").shouldHave(text(projectName));
    }
}
