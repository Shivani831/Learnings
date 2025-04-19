package demoQA;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class DemoQACookie {
    private static WebDriver driver;
    private static final String baseUrl = "https://demoqa.com";
    private static final String cookieFilePath = "Cookies.data";

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Ensuring the driver maximizes the window without using maximizeCurrentWindow() to avoid the issue
        driver.manage().window().setSize(new Dimension(1920, 1080));  // Manually setting a large window size
    }

    @Test(priority = 1)
    public void registerUserAndSaveCookies() throws IOException, InterruptedException {
        driver.get(baseUrl + "/register");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.findElement(By.id("firstname")).sendKeys("S");
        driver.findElement(By.id("lastname")).sendKeys("Rampally");
        driver.findElement(By.id("userName")).sendKeys("shivani.Rampally");
        driver.findElement(By.id("password")).sendKeys("P@ssword1");

        System.out.println("Please complete CAPTCHA manually within 20 seconds...");
        Thread.sleep(20000);

        WebElement registerButton = driver.findElement(By.id("register"));
        registerButton.click();
        System.out.println("Clicked on Register button");

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

        // Confirm successful navigation before saving cookies
        if (driver.getCurrentUrl().contains("/profile")) {
            saveCookies();
        } else {
            System.err.println("Registration might have failed. Cookies not saved.");
        }
    }

    @Test(priority = 2)
    public void loginWithCookiesAndVerifyProfile() throws Exception {
        driver.get(baseUrl);
        loadCookies();
        driver.navigate().to(baseUrl + "/profile");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement userLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName-value")));
        String actualUserName = userLabel.getText();
        System.out.println("Displayed Username: " + actualUserName);

        Assert.assertEquals(actualUserName, "shivani.Rampally", "Username does not match.");
        System.out.println("User successfully logged in and profile verified via cookies.");
    }

    private void saveCookies() throws IOException {
        File cookieFile = new File(cookieFilePath);
        if (cookieFile.exists()) cookieFile.delete();
        cookieFile.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cookieFile))) {
            for (Cookie ck : driver.manage().getCookies()) {
                writer.write("Name=" + ck.getName());
                writer.newLine();
                writer.write("Value=" + ck.getValue());
                writer.newLine();
                writer.write("Domain=" + ck.getDomain());
                writer.newLine();
                writer.write("Path=" + ck.getPath());
                writer.newLine();
                writer.write("Expiry=" + ck.getExpiry());
                writer.newLine();
                writer.write("IsSecure=" + ck.isSecure());
                writer.newLine();
                writer.write("-----");
                writer.newLine();
            }
        }
        System.out.println("Cookies saved successfully.");
    }

    private void loadCookies() throws Exception {
        File cookieFile = new File(cookieFilePath);
        if (!cookieFile.exists()) {
            throw new FileNotFoundException("Cookie file not found.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(cookieFile))) {
            String name = null, value = null, domain = null, path = null, expiry = null, isSecure = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name=")) name = line.substring(5);
                else if (line.startsWith("Value=")) value = line.substring(6);
                else if (line.startsWith("Domain=")) domain = line.substring(7);
                else if (line.startsWith("Path=")) path = line.substring(5);
                else if (line.startsWith("Expiry=")) expiry = line.substring(7);
                else if (line.startsWith("IsSecure=")) isSecure = line.substring(9);
                else if (line.equals("-----")) {
                    Date expiryDate = null;
                    if (!"null".equals(expiry)) {
                        expiryDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(expiry);
                    }

                    // Adding the cookies to the browser
                    Cookie cookie = new Cookie(name, value, domain, path, expiryDate, Boolean.parseBoolean(isSecure));
                    driver.manage().addCookie(cookie);
                    System.out.println("Loaded cookie: " + name);
                    // Reset all cookie data for the next iteration
                    name = value = domain = path = expiry = isSecure = null;
                }
            }
        }
        System.out.println("Cookies loaded successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
