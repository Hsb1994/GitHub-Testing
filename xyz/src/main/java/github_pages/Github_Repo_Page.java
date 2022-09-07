package github_pages;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import base_library.Base_Library_Maven;

public class Github_Repo_Page extends Base_Library_Maven {
	
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	SoftAssert soft = new SoftAssert();
	
	public Github_Repo_Page() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(text(),'Sign in')]")
	private WebElement signin;
	
	@FindBy(id = "login_field")
	private WebElement username;
	
	@FindBy(id = "password")
	private WebElement password;

	@FindBy(name = "commit")
	private WebElement submit_signin;

	@FindBy(xpath = "//div[@class=\"px-2\"]")
	private WebElement flash_error;
	
	@FindBy(xpath = "//a[@data-hydro-click-hmac=\"1faa7cea859c6eca0d900853fbbf85115c16a75dcd4772c9865826053bb0b2ae\"]")
	private WebElement new_button;
	
	@FindBy(id = "repository-owner")
	private WebElement owner_name;
	
	@FindBy(id = "repository_name")
	private WebElement repo_name;
	
	@FindBy(xpath = "//input[@id='repository_name']//following::dd[1]")
	private WebElement input_check;
	
	@FindBy(id = "repository_description")
	private WebElement description;
	
	@FindBy(id = "repository_visibility_private")
	private WebElement private_repo_button;
	
	@FindBy(id = "repository_auto_init")
	private WebElement add_readme;
	
	@FindBy(xpath = "//button[contains(text(),'Create repository')]")
	private WebElement create_button;
	
	@FindBy(id = "issues-tab")
	private WebElement issues;

	@FindBy(xpath = "//span[text()='New issue']")
	private WebElement new_issue;
	
	@FindBy(id = "issue_title")
	private WebElement issue_title;
	
	@FindBy(id = "issue_body")
	private WebElement issue_body;
	
	@FindBy(xpath = "//button[@type=\"submit\" and @class=\"btn-primary btn\"]")
	private WebElement issue_submit;
	
	@FindBy(xpath = "//a[contains(text(),'New issue')]")
	private WebElement new_issue2;
	
	@FindBy(id = "new_comment_field")
	private WebElement new_comment;
	
	@FindBy(xpath = "//button[contains(text(),'Comment')]")
	private WebElement comment_submit;
	
	@FindBy(xpath = "//a[contains(text(),'#')]")
	private WebElement hash_tag;
	
	@FindBy(xpath = "//*[@id=\"partial-discussion-header\"]/div[1]/div/h1/span[1]")
	private WebElement issue_name_check;
	
	
	
	
	
	@FindBy(id = "settings-tab")
	private WebElement setting;
	
	@FindBy(xpath = "//summary[contains(text(),'Delete this repository')]")
	private WebElement delete_repo_button;
	
	@FindBy(xpath = "//span[text()='I understand the consequences, delete this repository']")
	private WebElement understand_button;
	
	@FindBy(xpath = "//*[@id=\"options_bucket\"]/div[9]/ul/li[4]/details/details-dialog/div[3]/p[3]/strong")
	private WebElement repo_add_name;
	
	@FindBy(xpath = "//input[@aria-label=\"Type in the name of the repository to confirm that you want to delete this repository.\"]")
	private WebElement understand_input;
	
	@FindBy(xpath = "//div[@id=\"js-flash-container\"]/div/div")
	private WebElement delete_message;
	
	@FindBy(xpath = "/html/body/div[1]/header/div[7]/details/summary")
	private WebElement profile_avatar;
	
	@FindBy(xpath = "//button[contains(text(),'Sign out')]")
	private WebElement logout;
	
	public void check_invalidlogin() {
		signin.click();
		wait.until(ExpectedConditions.elementToBeClickable(username));
		username.sendKeys("Hsb1994");
		password.sendKeys("zxcv123");
		submit_signin.click();
		soft.assertEquals(flash_error.getText(), "Incorrect username or password.");
		System.out.println(flash_error.getText()+" Please Login again !!");
	}
	
	public void check_validlogin() {
		try {
		username.clear();
		username.sendKeys(property_getdata("username"));
		password.clear();
		password.sendKeys(property_getdata("password"));
		submit_signin.click();
		if(new_button.isDisplayed()) {
			System.out.println("Successfully Logged In");	
		}
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	public void verify_owner() {
		wait.until(ExpectedConditions.elementToBeClickable(new_button));
		new_button.click();
		try {
			soft.assertEquals(owner_name.getText(), "Hsb1994");
			System.out.println("Owner Verified");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void create_newrepo() {
		repo_name.sendKeys(property_getdata("repo"));
		description.sendKeys("This is My First Repository");
		private_repo_button.click();
		add_readme.click();
		String text = input_check.getText();
		soft.assertEquals(text, "Your new repository will be created as Testing-Demo.");
		System.out.println(text);
		create_button.click();
		issues.click();
		new_issue.click();
		issue_title.sendKeys(property_getdata("issue1"));
		issue_body.sendKeys(property_getdata("body1"));
		issue_submit.click();
		new_issue2.click();
		wait.until(ExpectedConditions.elementToBeClickable(issue_title));
		issue_title.sendKeys(property_getdata("issue2"));
		issue_body.sendKeys(property_getdata("body2"));
		issue_submit.click();
		new_comment.sendKeys(property_getdata("comment"));
		comment_submit.click();
		hash_tag.click();
		soft.assertTrue(true, issue_name_check.getText());
		
	}
	
	public void delete_repo() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		wait.until(ExpectedConditions.elementToBeClickable(setting)).click();
		js.executeScript("arguments[0].scrollIntoView();", delete_repo_button);
		delete_repo_button.click();
		try {
			soft.assertTrue(!understand_button.isEnabled());
			String text = repo_add_name.getText();
			understand_input.sendKeys(text);
			understand_button.click();
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("Error in deleting repository");
		}
	}
	
	public void verify_repo_delete() {
		try {
			soft.assertTrue(delete_message.isDisplayed());
			System.out.println(delete_message.getText());
			System.out.println("Current WebTitle is : "+driver.getTitle());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Successfully Deletion message Found");
		}
	}
	
	public void verify_logout() {
		profile_avatar.click();
		logout.click();
		System.out.println("Successfully Logged Out");
		System.out.println("Page Title : "+driver.getTitle());
		System.out.println("GitHUb Testing Done Successfully");
	}
}
