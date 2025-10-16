package Core;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CapabilityManager {


    public static UiAutomator2Options forDevice(String key)
    {
        JSONObject all = read("src/main/resources/Config/Devices.json");
        JSONObject d = all.getJSONObject(key);

        UiAutomator2Options o = new UiAutomator2Options()
                .setPlatformName(d.getString("platformName"))
                .setAutomationName(d.getString("automationName"))
                .setDeviceName(d.getString("deviceName"))
                .setUdid(d.getString("udid"));

        if (d.has("app")) o.setApp(d.getString("app"));
        if (d.has("appPackage")) o.setAppPackage(d.getString("appPackage"));
        if (d.has("appActivity")) o.setAppActivity(d.getString("appActivity"));
        if (d.has("fullReset")) o.setCapability("fullReset", d.getBoolean("fullReset"));
        if (d.has("autoGrantPermissions")) o.amend("autoGrantPermissions", d.getBoolean("autoGrantPermissions"));

        return o;
    }

    private static JSONObject read(String path)
    {
        try { return new JSONObject(new String(Files.readAllBytes(Paths.get(path)))); }
        catch (Exception e){ throw new RuntimeException("Cannot read "+path, e); }
    }
}
