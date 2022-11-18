package base_library;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import property_utility.Property_Utility;

public class Base_Library_Maven implements Property_Utility {
	
	String prop_path = ".\\Test Data\\config.properties";
	String exl_path  = "";
	
	public static WebDriver driver;
	
	public void launch_Url(String url) {
		WebDriverManager.chromedriver().setup();
//		System.setProperty("webdriver.chrome.driver","C:\\Users\\bisht\\git\\GitHub-Testing\\xyz\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		System.out.println("WebPage Title : "+driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
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
	
	public void after_method(ITestResult result) {
		try {
			if(result.isSuccess()) {
				get_screenshot("Passed", result.getMethod().getMethodName());
			}
			else if(result.getStatus() == ITestResult.FAILURE){
				get_screenshot("Failed", result.getMethod().getMethodName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String date_time() {
		Date dt = new Date();
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy hh_mm_a");
		String time = date.format(dt);
		return time;
	}
	
	public String get_data(String key) {
		String value1 = "";
	try {
		FileInputStream fs = new FileInputStream(prop_path);
		Properties prop = new Properties();
		prop.load(fs);
		value1 = prop.getProperty(key);
		} 
	catch (Exception e) {
		System.out.println("Issue in get_data");
		e.printStackTrace();
		}
		return value1;
	}
	
	public String get_data(int sheetNo, int row, int col) {
		String value2 = "";
	try {
			FileInputStream fs = new FileInputStream(exl_path);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sheet = wb.getSheetAt(sheetNo);
			value2 = sheet.getRow(row).getCell(col).getStringCellValue();
		} 
	catch (Exception e) {
			System.out.println("Issue in get_exceldata");
			e.printStackTrace();
		}
		return value2;
		}

	public void Handle_Win(int WinNo) {
		ArrayList<String> list = new ArrayList<String>(driver.getWindowHandles());
//		String x = list.get(WinNo);
		driver.switchTo().window(list.get(WinNo));
	}

	public void Double_Click(WebElement we) {
		Actions act = new Actions(driver);
		act.doubleClick(we).build().perform();
	}

	public void Right_Click(WebElement we) {
		Actions act = new Actions(driver);
		act.contextClick(we).build().perform();
	}

	public void Mouse_Move(WebElement we) {
		Actions act = new Actions(driver);
		act.moveToElement(we).build().perform();
	}

	public void Click_Me(WebElement we) {
		we.click();
	}

	public void get_screenshot(String Folder_name, String File_name) {
//		String location = System.getProperty("user.dir");
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy hh_mm_a");
			String path = System.getProperty("user.dir")+"\\ScreenShot\\"+Folder_name+"\\"+File_name+" "+format.format(date)+".png";
//			EventFiringWebDriver fire = new EventFiringWebDriver(driver);
//			File shot = fire.getScreenshotAs(OutputType.FILE);
			File shot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(shot, new File(path));
			System.out.println("Screenshot Captured Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in Screenshot");
		}
	}

	public void Upload_Utility(WebElement we, String file_path) {
		Actions act = new Actions(driver);
		try {
			Robot rob = new Robot();
			act.click(we).build().perform();
			rob.delay(2000);
			StringSelection select = new StringSelection(file_path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
			rob.keyPress(KeyEvent.VK_CONTROL);
		    rob.keyPress(KeyEvent.VK_V);
		    rob.keyRelease(KeyEvent.VK_CONTROL);
		    rob.keyRelease(KeyEvent.VK_V);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("File Uploaded Successfully");
			
		} catch (AWTException e) {
			e.printStackTrace();
			System.out.println("Error in File Upload");
		}
	}

	public void drop_down(WebElement we, String name) {
		Select sel = new Select(we);
		sel.selectByVisibleText(name);
	}
	
	public String[][] getExcelData(String sheetName) {
		String[][] ExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(exl_path);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sheet = wb.getSheet(sheetName);
			int Cols = sheet.getRow(0).getPhysicalNumberOfCells();
			int Rows = sheet.getPhysicalNumberOfRows();
			
			ExcelData = new String[Rows-1][Cols];
			
			for (int r= 1 ; r < Rows; r++) {
				for (int c=0; c < Cols; c++) {
				
					switch (sheet.getRow(r).getCell(c).getCellType()) {

					case STRING :
						ExcelData[r-1][c] = sheet.getRow(r).getCell(c).getStringCellValue();
						break;
					case NUMERIC :
						double number_value = sheet.getRow(r).getCell(c).getNumericCellValue();
						ExcelData[r-1][c] = NumberToTextConverter.toText(number_value);
						break;
					case FORMULA :
						number_value = sheet.getRow(r).getCell(c).getNumericCellValue();
						ExcelData[r-1][c] = NumberToTextConverter.toText(number_value); 
						break;
					default:
						System.out.println("No case matched, error");
						break;
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return ExcelData;
	}

	
}
