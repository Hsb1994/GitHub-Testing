package extent_report2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Extent_report2 implements ITestListener {
	
	static ExtentSparkReporter reporter;
	static ExtentReports reports;
	static ExtentTest test;
	
	public static void config_report() {
		
		reporter = new ExtentSparkReporter(".\\Extent Reports\\"+"report"+System.currentTimeMillis()+".html");
		reports = new ExtentReports();
		reports.attachReporter(reporter);
		
		reports.setSystemInfo("Machine", "My PC");
		reports.setSystemInfo("OS", "Windows");
		reports.setSystemInfo("Browser", "Chrome");
		reports.setSystemInfo("Tool", "Intellij");
		
		reporter.config().setDocumentTitle("Extent Report");
		reporter.config().setReportName("Himanshu Adhikari");
		reporter.config().setTheme(Theme.DARK);

	}
	
	@Override
	public void onStart(ITestContext context) {
		config_report();
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
	test = reports.createTest(result.getName())
	.log(Status.PASS, MarkupHelper.createLabel("Test Passed Successfully", ExtentColor.GREEN))
	.info(result.getThrowable());
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
	test = reports.createTest(result.getName())
	.log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED))
	.info(result.getThrowable());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
	test = reports.createTest(result.getName())
	.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped", ExtentColor.LIME))
	.info(result.getThrowable());	
	}
	
	@Override
	public void onFinish(ITestContext context) {
	reports.flush();	
	}
	

}
