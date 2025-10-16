package Core;



import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties P = new Properties();
    static {
        try (InputStream is = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("Config/Global.properties")) {
            if (is == null) throw new RuntimeException("Global.properties not found");
            P.load(is);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    public static String get(String key)
    {
        return P.getProperty(key);
    }
}

