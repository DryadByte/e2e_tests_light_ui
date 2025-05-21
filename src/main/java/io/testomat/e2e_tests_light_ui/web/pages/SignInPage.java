package io.testomat.e2e_tests_light_ui.web.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SignInPage {

    public void open() {
        Selenide.open("/users/sign_in");
    }

    public void loginUser(String email, String password) {
        $("#content-desktop #user_email").setValue(email);
        $("#content-desktop #user_password").setValue(password);
        $("#content-desktop #user_remember_me").click();
        $("#content-desktop [name=commit]").click();
    }
}
