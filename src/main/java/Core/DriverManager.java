package Core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URI;

public class DriverManager {

    private static final ThreadLocal<AndroidDriver> TL = new ThreadLocal<>();

    public static void start(String deviceKey, String serverUrl)
    {
        UiAutomator2Options opts = CapabilityManager.forDevice(deviceKey);
        try
        {
            TL.set(new AndroidDriver(URI.create(serverUrl).toURL(), opts));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static AndroidDriver get()
     {
         return TL.get();
     }

    public static void stop()
    {
        if (get()!=null)
        {
            get().quit();
            TL.remove();
        }
    }
}