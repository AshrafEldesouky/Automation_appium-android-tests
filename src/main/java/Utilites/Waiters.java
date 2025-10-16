package Utilites;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiters {
    private final AndroidDriver driver;
    private final Duration timeout = Duration.ofSeconds(10);

    public Waiters(AndroidDriver driver)
    {
        this.driver = driver;
    }

    private WebDriverWait w() {
        return new WebDriverWait(driver, timeout);
    }

    public void waitAndClick(By by) {
        w().until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public void waitAndType(By by, String text) {
        WebElement el = w().until(ExpectedConditions.visibilityOfElementLocated(by));
        el.clear();
        el.sendKeys(text);
    }

    public String getTextWhenVisible(By by) {
        return w().until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }
    // ينتظر ظهور العنصر ويُرجع WebElement
    public WebElement visible(By by) {
        return w().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // يتحقق هل العنصر ظهر خلال المهلة
    public boolean isVisible(By by) {
        try {
            w().until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // ينتظر ظهور العنصر ويُرجع WebElement
    public WebElement visible(By by, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // يتحقق هل العنصر ظهر خلال المهلة
    public boolean isVisible(By by, long seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) { return false; }
    }
    public String getTextWhenVisible(By by, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }

}
