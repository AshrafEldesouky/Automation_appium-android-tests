package Pages;


import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePage extends PageBase{


    private static final By ProductText_Homepage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

    public boolean isLoaded()
    {
//        System.out.println("IsVisible(ProductText_Homepage) = "+isVisible(ProductText_Homepage));
        return isVisible(ProductText_Homepage,20);
    }            // أو isVisible(...,20)
    public String title()
    {
        System.out.println("textOf(ProductText_Homepage) = "+textOf(ProductText_Homepage));
        return textOf(ProductText_Homepage,20);
    }

}
