package demoQA;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementClickInterceptedException;

public class DemoQATest {

    static WebDriver driver;
    static WebDriverWait wait; // Shared for entire class

    @BeforeTest
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Using Duration for timeouts
        System.out.println("Browser opened successfully.");
    }

    // DataProvider for URL, username, and password
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"https://demoqa.com/login", "shivani.Rampally", "P@ssword1"}};
    }

    // Method for resilient clicks
    private static void clickResilient(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el)); // Wait for visibility
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el); // Scroll to element
        wait.until(ExpectedConditions.elementToBeClickable(el)); // Wait for it to be clickable

        try {
            el.click(); // Attempt normal click
        } catch (ElementClickInterceptedException e) {
            System.out.println("Normal click intercepted, using JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el); // Fallback to JS click
        }
    }

    @Test(dataProvider = "loginData")
    public void registerAndLoginTest(String url, String username, String password) {
        // Launch browser & enter URL
        if (url != null && !url.trim().isEmpty()) {
            driver.get(url.trim()); // Ensure URL is valid and remove extra spaces
            System.out.println("DemoQA web app launched.");
        } else {
            System.out.println("URL is invalid or null. Please check the provided URL.");
            return; // Exit if URL is invalid
        }

        // Verify login header
        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userForm']/div[1]/h5")));
        Assert.assertTrue(loginHeader.isDisplayed(), "Login header missing");
        System.out.println("Login header is displayed");

        // Click "New User"
        WebElement newUserButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='New User']")));
        clickResilient(newUserButton);
        System.out.println("Clicked on NewUser button, web app navigated to register form");

        // Verify registration header
        WebElement registerHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userForm']/div[1]/h4")));
        Assert.assertTrue(registerHeader.isDisplayed(), "Register header is not displayed.");
        System.out.println("Register header is displayed");

        // Fill registration form
        driver.findElement(By.id("firstname")).sendKeys("S");
        driver.findElement(By.id("lastname")).sendKeys("rampally");
        driver.findElement(By.id("userName")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        System.out.println("User details entered");

        // Solve reCAPTCHA
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@title,'reCAPTCHA')]")));
        driver.switchTo().frame(iframe);
        WebElement checkBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".recaptcha-checkbox-border")));
        clickResilient(checkBox);
        driver.switchTo().defaultContent();

        // Wait for Register button to be clickable and click
        WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("register")));
        System.out.println("CAPTCHA solved—Register is now clickable.");
        clickResilient(registerBtn);

        // Check for existing user modal
        try {
            WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeSmallModal-ok")));
            System.out.println("Modal says: " + driver.findElement(By.id("name")).getText());
            clickResilient(ok);
        } catch (Exception e) {
            System.out.println("User exists.");
        }

        // Click "Go To Login"
        WebElement gotoLogin = wait.until(ExpectedConditions.elementToBeClickable(By.id("gotologin")));
        clickResilient(gotoLogin);

        // Wait for login form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));

        // Enter credentials and click login
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
        System.out.println("Login button is now clickable.");
        clickResilient(loginBtn);

        // Verify login transition
        try {
            wait.until(ExpectedConditions.urlContains("/profile"));
            System.out.println("Navigated to profile page after login.");
        } catch (TimeoutException e) {
            System.out.println("⚠ Login did not redirect to profile. Check for login failure.");
        }

        // Verify successful login
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Log out']")));
        Assert.assertTrue(logoutBtn.isDisplayed(), "Log out button not visible");

        String actualUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName-value"))).getText();
        Assert.assertEquals(actualUser, username, "Logged-in username should match the one submitted");
        System.out.println("Login verified: " + actualUser);
    }

    @Test
    public static void tearDown() {
        driver.quit();
        System.out.println("Browser closed successfully.");
    }
}
