package io.testomat.e2e_tests_light_ui.web.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProjectsPage {
    public void open() {
        Selenide.open("");
    }

    public void searchProject(String projectName) {
        $("#search").setValue(projectName);
    }

    public void clickOnProject(String targetProjectPath) {
        $("[href=\"" + targetProjectPath + "\"]").click();
    }

    public void isLoaded() {
        $("#container .common-flash-success").shouldBe(visible);
    }
}
