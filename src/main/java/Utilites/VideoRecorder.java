package Utilites;

import Core.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/** Start/Stop recording around a test and save MP4 to artifacts/videos */
public class VideoRecorder {
    private static final ThreadLocal<String> currentFilePath = new ThreadLocal<>();

    public static void start(String className, String methodName) {
        AndroidDriver driver = DriverManager.get();       // ← AndroidDriver
        if (driver == null) return;

        String device = String.valueOf(driver.getCapabilities().getCapability("deviceName"));
        if (device == null || "null".equals(device)) device = "device";
        device = device.replaceAll("[^a-zA-Z0-9._-]", "_");

        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = methodName + "__" + device + "__" + stamp + ".mp4";

        File dest = new File("artifacts/videos/" + className + "/" + filename);
        dest.getParentFile().mkdirs();
        currentFilePath.set(dest.getPath());

        AndroidStartScreenRecordingOptions opts =
                AndroidStartScreenRecordingOptions.startScreenRecordingOptions()
                        .withTimeLimit(Duration.ofMinutes(10))
                        .withBitRate(4_000_000)
                        .withVideoSize("1280x720");

        driver.startRecordingScreen(opts);                // ← OK على AndroidDriver
    }

    public static String stopAndSave() {
        AndroidDriver driver = DriverManager.get();       // ← AndroidDriver
        if (driver == null) return null;

        String path = currentFilePath.get();
        if (path == null) return null;

        try {
            String base64 = driver.stopRecordingScreen(); // ← OK على AndroidDriver
            byte[] data = Base64.getDecoder().decode(base64);
            Files.write(new File(path).toPath(), data);
            return path;
        } catch (Exception e) {
            return null;
        } finally {
            currentFilePath.remove();
        }
    }
}
