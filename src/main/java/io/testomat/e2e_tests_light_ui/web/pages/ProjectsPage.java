package io.testomat.e2e_tests_light_ui.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.testomat.e2e_tests_light_ui.utils.StringParsers.parseIntegerFromString;

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

    public ElementsCollection countOfProjectsShouldBeEqualTo(int expectedSize, String targetProjectPath) {
        return $$(String.format("#grid ul li a[href='%s']", targetProjectPath)).filter(visible).shouldHave(size(expectedSize));
    }

    public int getTestCasesCount(SelenideElement targetProject) {
        String countOfTests = targetProject.$("p").getText();
        return parseIntegerFromString(countOfTests);
    }
}
