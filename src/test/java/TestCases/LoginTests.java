package TestCases;

import Pages.HomePage;
import Pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import Reportes.ExtentListener;

public class LoginTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);

    @Test
    public void canLogin() {
        log.info("Starting canLogin test");
        ExtentListener.log("Open app & attempt login");

        new LoginPage().login("standard_user", "secret_sauce");

        HomePage home = new HomePage();
        Assert.assertTrue(home.isLoaded(), "Home should be visible after login");

        String actual = home.title().trim();
        log.info("Home title read = [{}]", actual);
        ExtentListener.log("Home title = " + actual);

        Assert.assertEquals(actual.toUpperCase(), "PRODUCTS", "Home title should match");
    }
}
