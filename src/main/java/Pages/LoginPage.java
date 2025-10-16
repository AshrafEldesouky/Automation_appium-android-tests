package Pages;

import io.appium.java_client.AppiumBy;
import Core.DriverManager;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends PageBase {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private static final By USERNAME = AppiumBy.accessibilityId("test-Username");
    private static final By PASSWORD = AppiumBy.accessibilityId("test-Password");
    private static final By LOGIN_BTN = AppiumBy.accessibilityId("test-LOGIN");


    public void login(String username_Val, String password_Val){
        log.info("Logging in with user={}", username_Val);

        Wait().waitAndType(USERNAME, username_Val);
        Wait().waitAndType(PASSWORD, password_Val);
        Wait().waitAndClick(LOGIN_BTN);

        log.debug("Login button tapped");
    }
}
