package tests.testUtils;

import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.pageObjects.android.HomePageAndroid;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.utils.AppiumUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AndroidBaseTest extends AppiumUtils {

    public static AndroidDriver driver;
    public static HomePageAndroid wdHomePageAndroid;

    @BeforeClass(alwaysRun = true)
    public void setup() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src//main//resources//data.properties");
        props.load(fis);
        String ipAddress = props.getProperty("ipAddress");
        String port = props.getProperty("port");

        service = startAppiumServer(ipAddress, Integer.parseInt(port));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "14");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("app", System.getProperty("user.dir") + "//src//test//resources//app//wdiodemos.apk");
        capabilities.setCapability("appium:newCommandTimeout", 60);

        driver = new AndroidDriver(service.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS, true);

        wdHomePageAndroid = new HomePageAndroid(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
