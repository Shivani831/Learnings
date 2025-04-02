package demoQA;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class DemoQATest {
		
    static WebDriver driver;

    @BeforeTest
    public static void setup() {
        // Set up WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Browser Opend successfully.");

    }

    @Test
    public static void registerAndLoginTest() {
        // Step 1: Launch the browser and navigate to the login page
        driver.get("https://demoqa.com/login");
        System.out.println("DemoQA web app launched.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));  

        // Step 2: Verify "Login in Book Store" header is available on the page
        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userForm']/div[1]/h5")));
        Assert.assertTrue(loginHeader.isDisplayed(), "Login in Book Store header is not displayed.");
        System.out.println("Login in Book Store header is displayed");


        // Step 3: Scroll to "New User" button and click
        WebElement newUserButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='New User']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newUserButton);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(newUserButton));
            newUserButton.click();
        } catch (Exception e) {
            Actions actions = new Actions(driver);
            actions.moveToElement(newUserButton).click().perform();
        }
        System.out.println("NewUser button clicked for registration process");

        // Step 4: Verify "Register to Book Store" header
        WebElement registerHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userForm']/div[1]/h4")));
        Assert.assertTrue(registerHeader.isDisplayed(), "Register header is not displayed.");
        System.out.println("Register header is displayed");


        // Step 5: Fill in the registration form fields
        driver.findElement(By.id("firstname")).sendKeys("S");
        driver.findElement(By.id("lastname")).sendKeys("rampally");
        driver.findElement(By.id("userName")).sendKeys("shivani.Rampally");
        driver.findElement(By.id("password")).sendKeys("P@ssword1");
        System.out.println("User Details entered");
        

        // Step 7: Handle "I'm not a robot" checkbox
        //Step 7.1: Switch to IFrame
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'reCAPTCHA')]")));
        driver.switchTo().frame(iframe);
        System.out.println("Switched to reCAPTCHA iframe");
        
		//Step 7.2: Click on Check box	
        WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='recaptcha-checkbox-border']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);
        System.out.println("reCAPTCHA checkbox clicked");
     	driver.switchTo().defaultContent();
     	
     	 // Step 8: Wait for 10 seconds before clicking "Register"
        try {
            Thread.sleep(10000);  // Waiting for 30 seconds to allow page elements to stabilize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	
        // Step 9: Click the "Register" button
        WebElement registerButton = driver.findElement(By.id("register"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
        registerButton.click();
        System.out.println("Clicked on Register button");
        
        //Step 10: Verify is the user exist
        try {
            WebElement userExistMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='name']")));
            if (userExistMsg.isDisplayed()) {
                System.out.println("User already exists. Navigating to login.");
                WebElement backToLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("gotologin")));
                backToLoginBtn.click();
            }
        } catch (Exception e) {
            System.out.println("User is new. Proceeding with login.");
        }
        
        // Step 11: Log in with registered username and password
        driver.findElement(By.id("userName")).sendKeys("shivani.Rampally");
        driver.findElement(By.id("password")).sendKeys("P@ssword1");
        driver.findElement(By.id("login")).click();
        System.out.println("User logged in sucessfully");


        // Step 12: Verify successful login by checking the username on the page
        // Step 12.1 : Verify the presence of logout button
        WebElement LogoutButtonPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Log out')]")));
        System.out.println("User navigated to user profile page and logout button visible");
        WebElement userNameLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName-value")));
        String actualUserName = userNameLabel.getText();
        String expectedUserName = "shivani.Rampally";
        Assert.assertEquals(actualUserName, expectedUserName, "Displayed username does not match the logged-in username!");
        System.out.println("Logged in user profile name verified ");

    }



    @Test
    public static void tearDown() {
        driver.quit();
        System.out.println("Browser closed successfully.");

    }
    public static void main(String[] args) {
    	setup();
    	registerAndLoginTest();
    	tearDown(); 	
	}
}




