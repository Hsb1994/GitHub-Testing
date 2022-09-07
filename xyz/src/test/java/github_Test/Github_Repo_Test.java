package github_Test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import base_library.Base_Library_Maven;
import github_pages.Github_Repo_Page;

public class Github_Repo_Test extends Base_Library_Maven {
	
	Github_Repo_Page ob;
	
	@BeforeTest
	public void before_test() {
		launch_Url(property_getdata("url"));
		ob = new Github_Repo_Page();
	}

	@Test(priority = 0)
	public void check_invalidlogin() {
		ob.check_invalidlogin();
	}
	
	@Test(priority = 1)
	public void check_validlogin() {
		ob.check_validlogin();
	}
	
	@Test(priority = 2)
	public void verify_repo_owner() {
		ob.verify_owner();
	}
	
	@Test(priority = 3)
	public void create_newrepo() {
		ob.create_newrepo();
	}
	
	@Test(priority = 4)
	public void delete_repo() {
		ob.delete_repo();
	}
	
	@Test(priority = 5)
	public void verify_repo_delete() {
		ob.verify_repo_delete();
	}
	
	@Test(priority = 6)
	public void verify_logout() {
		ob.verify_logout();
	}
}
