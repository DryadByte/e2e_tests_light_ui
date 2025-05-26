package io.testomat.e2e_tests_light_ui;

import com.codeborne.selenide.SelenideElement;
import io.testomat.e2e_tests_light_ui.web.pages.ProjectPage;
import io.testomat.e2e_tests_light_ui.web.pages.ProjectsPage;
import io.testomat.e2e_tests_light_ui.web.pages.SignInPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectPageTests extends BaseTest {

    private static final String PROJECT_NAME = "Manufacture light";
    private static final String TARGET_PROJECT_PATH = "/projects/manufacture-light/";
    private static final String EXPECTED_URL = config.getBaseUrl() + TARGET_PROJECT_PATH;
    private final ProjectsPage projectsPage = new ProjectsPage();
    private final ProjectPage projectPage = new ProjectPage();

    @BeforeAll
    public static void openTestomatAndLogin() {
        SignInPage signInPage = new SignInPage();
        signInPage.open();
        signInPage.loginUser(credentials.getEmail(), credentials.getPassword());
        signInPage.userIsSignIn();
    }

    @BeforeEach
    public void openProjectPage() {
        projectsPage.open();
    }

    @Test
    public void findAndOpenProjectTests() {
        projectsPage.searchProject(PROJECT_NAME);
        projectsPage.clickOnProject(TARGET_PROJECT_PATH);
        projectPage.verify(EXPECTED_URL, PROJECT_NAME);
    }

    @Test
    public void findProjectWithoutTests() {
        projectsPage.searchProject(PROJECT_NAME);
        SelenideElement targetProject = projectsPage.countOfProjectsShouldBeEqualTo(1, TARGET_PROJECT_PATH).first();
        int actualCountOfTests = projectsPage.getTestCasesCount(targetProject);
        Assertions.assertEquals(0, actualCountOfTests);
    }

    @Test
    public void editReadme() {
        projectsPage.searchProject(PROJECT_NAME);
        projectsPage.clickOnProject(TARGET_PROJECT_PATH);
        projectPage.verify(EXPECTED_URL, PROJECT_NAME);
        projectPage.findVisibleLinkBySubPath(TARGET_PROJECT_PATH, "readme").click();
        projectPage.findVisibleLinkBySubPath(TARGET_PROJECT_PATH, "settings/readme").click();
        String readmeText = ProjectPage.updateReadmeText();
        projectPage.checkReadmeTextWasUpdated(readmeText, TARGET_PROJECT_PATH);
    }
}
