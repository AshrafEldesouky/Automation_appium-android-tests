// src/main/java/Reportes/ScreenshotOnFailListener.java
package Reportes;

import Core.DriverManager;
import Utilites.ScreenShot;

import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotOnFailListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // خُد الدرايفر من DriverManager (ThreadLocal)
        AppiumDriver driver = DriverManager.get();
        if (driver == null) return;

        String className = result.getTestClass().getRealClass().getSimpleName();
        String method    = result.getMethod().getMethodName();

        String path = ScreenShot.take(driver, className + "_" + method);
        if (path != null) {
            // هنستخدمه لاحقًا في Extent
            result.setAttribute("screenshot", path);  // ← كان مكتوبة resut بالغلط
        }
    }

    // بقية الميثودات مطلوبة للواجهة لكن نسيبها فاضية
    @Override public void onTestStart(ITestResult r) {}
    @Override public void onTestSuccess(ITestResult r) {}
    @Override public void onTestSkipped(ITestResult r) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
