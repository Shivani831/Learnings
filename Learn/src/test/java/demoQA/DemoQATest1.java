package demoQA;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DemoQATest1 {

	 public static void main(String[] args) {
        
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Step 1: Open the login page
        	driver.get("https://demoqa.com/login"); // Replace with the actual login page URL
            driver.manage().window().maximize();        
            
            // Step 2: Wait for the 'New User' button to be clickable and click it
            WebElement newUserButton = driver.findElement(By.xpath("//button[text()='New User']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newUserButton);
            newUserButton.click();

            // Step 3: Fill the registration form
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))); // Adjust locator as needed
            WebElement emailField = driver.findElement(By.id("email")); // Adjust locator as needed
            WebElement passwordField = driver.findElement(By.id("password")); // Adjust locator as needed
            WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword")); // Adjust locator as needed
            WebElement registerButton = driver.findElement(By.id("registerButton")); // Adjust locator as needed

            // Fill in the details
            String username = "testuser";
            String email = "testuser@example.com";
            String password = "Password123";

            usernameField.sendKeys(username);
            emailField.sendKeys(email);
            passwordField.sendKeys(password);
            confirmPasswordField.sendKeys(password);

            // Submit the registration form
            registerButton.click();

            // Step 4: Wait for logout button to appear and click it
            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logoutButton"))); // Adjust locator as needed
            logoutButton.click();

            // Step 5: Log in with the registered details
            WebElement loginUsernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginUsername"))); // Adjust locator as needed
            WebElement loginPasswordField = driver.findElement(By.id("loginPassword")); // Adjust locator as needed
            WebElement loginButton = driver.findElement(By.id("loginButton")); // Adjust locator as needed

            loginUsernameField.sendKeys(username);
            loginPasswordField.sendKeys(password);
            loginButton.click();

            // Step 6: Verify login success
            WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("welcomeMessage"))); // Adjust locator as needed
            if (welcomeMessage.isDisplayed()) {
                System.out.println("Login successful with registered details!");
            } else {
                System.out.println("Login failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
