package TestCases;

import Core.DriverManager;
import Core.ConfigLoader;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

public class BaseTest {
    @BeforeMethod
    public void setup(){
        String serverUrl = System.getProperty("serverUrl", ConfigLoader.get("serverUrl"));
        String deviceKey = System.getProperty("deviceKey", ConfigLoader.get("deviceKey"));
        System.out.println("serverUrl : "+serverUrl);
        System.out.println("deviceKey : "+deviceKey);
        DriverManager.start(deviceKey, serverUrl);
    }

    // 👇👇 أضفها عشان الـ Listener يجيب الدرايفر
    public AndroidDriver getDriver() {
        return DriverManager.get();
    }


    @AfterMethod(alwaysRun = true)
    public void teardown()
    {
        DriverManager.stop();
    }
}
