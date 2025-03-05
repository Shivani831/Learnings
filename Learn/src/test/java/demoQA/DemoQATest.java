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
        driver.findElement(By.id("firstname")).sendKeys("shivani");
        driver.findElement(By.id("lastname")).sendKeys("rampally");
        driver.findElement(By.id("userName")).sendKeys("shivani.Rampally");
        driver.findElement(By.id("password")).sendKeys("P@ssword1");
        System.out.println("User Details entered");
        

        // Step 7: Handle "I'm not a robot" checkbox
        try {

    	    System.out.println("Before Captcha checkbox.");

        	WebElement captchaCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("recaptcha-anchor")));

        	// Check if the element is visible
        	if (captchaCheckbox.isDisplayed()) {
        	    // Scroll into view and click
        	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", captchaCheckbox);
        	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", captchaCheckbox);
        	    System.out.println("Captcha checkbox clicked.");
        	} else {
        	    System.out.println("Captcha checkbox is not displayed.");
        	}
         } catch (Exception e) {
            System.out.println(e + "Captcha checkbox interaction failed.");
        }

        // Step 8: Click the "Register" button
        WebElement registerButton = driver.findElement(By.id("register"));
        registerButton.click();
        System.out.println("Clicked on Register button");


        // Step 9: Click on Back to Login button
        WebElement loginButtonAfterRegister = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Back to Login']")));
        Assert.assertTrue(loginButtonAfterRegister.isDisplayed(), "'Back to Login' button is not displayed.");
        loginButtonAfterRegister.click();
        System.out.println("User navigated to Login page");

        // Step 11: Log in with registered username and password
        driver.findElement(By.id("userName")).sendKeys("shivani.Rampally");
        driver.findElement(By.id("password")).sendKeys("P@ssword1");
        driver.findElement(By.id("login")).click();

        // Step 12: Verify successful login by checking the username on the page
        WebElement userProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Profile']")));
        Assert.assertTrue(userProfile.isDisplayed(), "User login was not successful.");
        System.out.println("User Logged in sucessfully");

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




