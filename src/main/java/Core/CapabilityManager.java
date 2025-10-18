package Core;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CapabilityManager {


    public static UiAutomator2Options forDevice(String key) {
        JSONObject all = read("src/main/resources/Config/Devices.json");
        JSONObject d   = all.getJSONObject(key);

        UiAutomator2Options o = new UiAutomator2Options()
                .setPlatformName(d.getString("platformName"))
                .setAutomationName(d.getString("automationName"));

        // === device info ===
        if (d.has("deviceName"))
            o.setDeviceName(d.getString("deviceName"));      // BS/localcls
        if (d.has("platformVersion"))
            o.setPlatformVersion(d.getString("platformVersion")); // BS
        if (d.has("udid"))
            o.setUdid(d.getString("udid"));                  // local فقط

        // === app source ===
        // لو عندك appPath محلي -> استخدمه، وإلا لو فيه app (bs://...) -> استخدمه كما هو
        if (d.has("appPath")) {
            String p = d.getString("appPath");
            o.setApp(java.nio.file.Paths.get(p).toAbsolutePath().toString());
        } else if (d.has("app")) {
            o.setApp(d.getString("app")); // مثال: bs://f4a2ee01...
        }

        // optional capabilities
        if (d.has("appPackage"))
            o.setAppPackage(d.getString("appPackage"));
        if (d.has("appActivity"))
            o.setAppActivity(d.getString("appActivity"));

        if (d.has("noReset"))
            o.setCapability("noReset", d.getBoolean("noReset"));
        if (d.has("fullReset"))
            o.setCapability("fullReset", d.getBoolean("fullReset"));
        if (d.has("autoGrantPermissions"))
            o.amend("autoGrantPermissions", d.getBoolean("autoGrantPermissions"));

        return o;
    }


    private static JSONObject read(String path)
    {
        try { return new JSONObject(new String(Files.readAllBytes(Paths.get(path)))); }
        catch (Exception e){ throw new RuntimeException("Cannot read "+path, e); }
    }
}
