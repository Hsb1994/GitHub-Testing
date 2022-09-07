package base_library;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import property_utility.Property_Utility;

public class Base_Library_Maven implements Property_Utility {
	
	String prop_path = ".\\Test Data\\config.properties";
//	String prop_path = "C:\\Users\\bisht\\eclipse-workspace\\xyz\\Test Data\\config.properties";
	public static WebDriver driver;
	
	public void launch_Url(String url) {
		ChromeOptions opt = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(opt);
		driver.get(url);
		System.out.println("WebPage Title : "+driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
	}
	
	@AfterTest
	public void close_browser() {
		driver.quit();
	}

	@Override
	public String property_getdata(String key) {
		String value = "";
		try {
			FileInputStream fs = new FileInputStream(prop_path);
			Properties prop = new Properties();
			prop.load(fs);
			value = prop.getProperty(key);
			
		} 
		catch (Exception e) {
			System.out.println("Error in Fetching Data");
			e.printStackTrace();
		}
		return value;
	}
	

}
