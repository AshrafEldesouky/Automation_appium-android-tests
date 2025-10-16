package Utilites;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Helper لأخذ سكرينشوت وإرجاع المسار */
public class ScreenShot {

    public static String take(AppiumDriver driver, String baseName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = baseName + "_" + stamp + ".png";

            File dest = new File("artifacts/screenshots/" + baseName + "_" + stamp + ".png");
            dest.getParentFile().mkdirs(); // لو الفولدر مش موجود
            Files.copy(src.toPath(), dest.toPath());

            return dest.getPath(); // هنخزّنه في نتيجة التيست
        } catch (Exception e) {
            return null; // ما نبوّظش التيست بسبب لقطة شاشة
        }
    }
}
