package Pages;

import Core.DriverManager;
import Utilites.Waiters;          // ← هنا التصحيح
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PageBase {
    protected AndroidDriver d() { return (AndroidDriver) DriverManager.get(); }

    // اختياري: كاش بدل ما ننشئ Waiters كل مرة
    private Waiters w;
    protected Waiters Wait() {
        if (w == null) w = new Waiters(d());
        return w;
    }
    // === helpers تستخدم الـ Waiters ===
    protected WebElement el(By locator)
    {
        return Wait().visible(locator);
    }
    protected boolean isVisible(By locator)
    {
        return Wait().isVisible(locator);
    }
    protected String textOf(By locator)
    {
        return el(locator).getText();
    }
    protected boolean isVisible(By locator, long s)
    {
        return Wait().isVisible(locator, s);
    }
    protected String textOf(By locator, long s)
    {
        return Wait().getTextWhenVisible(locator, s);
    }

}