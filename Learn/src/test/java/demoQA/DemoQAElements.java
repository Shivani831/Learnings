package demoQA;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DemoQAElements {
static WebDriver driver;

    @BeforeTest
    public static void setup() 
       {driver = new EdgeDriver();
        driver.manage().window().maximize();
        System.out.println("Browser Opend successfully.");
}
    

    @Test
    public static void ElementsinDemoQA() throws InterruptedException {
    	
    	try {
        // Step 1: Launch the browser and navigate to the Elements page
        driver.get("https://demoqa.com/elements"); 
        System.out.println("DemoQA web app launched.");

        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 

        // Step 1.2: Wait for the Text Box button to be clickable
        WebElement textBoxButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Text Box']")));

        // Step 1.3: Click on the Text Box button
        textBoxButton.click();
        
        // Step 1.4: Wait for the Text Box Header
        WebElement TextBoxHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']/div//h1")));
        Assert.assertTrue(TextBoxHeader.isDisplayed(), "Text Box header is not displayed.");
        System.out.println("Text Box header displayed after clicking on text box button.");
        
        // Step 1.5: Scroll to the "Full Name" input field
        WebElement fullNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fullNameInput);

        // Step 1.6: Fill in the fields
        String[] inputFields = {"userName", "userEmail", "currentAddress", "permanentAddress"};
        String[] inputValues = {"Shivani", "Shivani.rampally@cognine.com", "2nd floor, Cognine Technologies", "2nd floor, Cognine Technologies"};
        for (int i = 0; i < inputFields.length; i++) {driver.findElement(By.id(inputFields[i])).sendKeys(inputValues[i]);}

        // Step 1.7: Click the submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        Assert.assertTrue(submitButton.isEnabled(), "Submit button was not clicked.");
        System.out.println("Submit button was clicked successfully.");

        // Step 1.8: Wait for the output to be visible & get data
        WebElement outputText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='output']/div")));
        String output = outputText.getText();

        //Step 1.9: Verify Expected v/s actual values
        String expectedOutput = "Name:Shivani\nEmail:Shivani.rampally@cognine.com\nCurrent Address :2nd floor, Cognine Technologies\nPermananet Address :2nd floor, Cognine Technologies";
        Assert.assertEquals(output, expectedOutput, "The form output does not match the expected result.");
        System.out.println("The output matches the expected result.");
 
        
        //Step 2:Wait for the Check Box button to be clickable
    	WebElement checkBoxButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Check Box']")));
    
	    // Step 2.1: Click on the Check Box button
	    checkBoxButton.click();
	
	    // Step 2.2: Verify the Check Box header
	    WebElement checkBoxHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']/div//h1")));
	    Assert.assertTrue(checkBoxHeader.isDisplayed(), "Check Box header is not displayed.");
        System.out.println("Check Box header displayed after clicking on text box button.");

	    
	    // Step 2.3: Click on the toggle (checkbox)
        WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("rct-icon-expand-all")));
        toggleButton.click();
        System.out.println("The checkbox tree expanded by clicking on toggle");
        
        //Step 2.4: Scroll up the page to view check box tree
        WebElement homeCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Home']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", homeCheckbox);
        
        //Step 2.5: Check the check box at Commands, Veu, Public, Download
        WebElement commandsCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Commands']")));
        commandsCheckBox.click();
        System.out.println("The commands checkbox under desktop section checked");
        
        WebElement veuCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Veu']")));
        veuCheckBox.click();
        System.out.println("The Veu checkbox under documents section checked");

        WebElement publicCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Public']")));
        publicCheckBox.click();
        System.out.println("The Public checkbox under office section checked");

        WebElement downloadsCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Downloads']")));
        downloadsCheckBox.click();
        System.out.println("The Downloads checkbox section checked");
        
        //Step 2.6: Verify the selected check box 
        WebElement selectedCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='result']")));
        Assert.assertTrue(selectedCheckbox.isDisplayed(), "Selected check box list not displayed");
        System.out.println("Commands, Veu, Public, Download check boxex are checked");
        
        //Step 3:Wait for the Radio button to be clickable
    	WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Radio Button']")));
    
	    // Step 3.1: Click on the Check Box button
	    radioButton.click();
	
	    // Step 3.2: Verify the Check Box header
	    WebElement radioButtonHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']/div//h1")));
	    Assert.assertTrue(radioButtonHeader.isDisplayed(), "Radio button header is not displayed.");
        System.out.println("Radio button header displayed after clicking on radio button.");
        
        //Step 3.3: Scroll up the page to select radio button
        WebElement clickRadioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Radio Button']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickRadioButton);
        
        //Step 3.4: Click on Yes at radio button
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement yesRadioButton = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        js.executeScript("arguments[0].click();", yesRadioButton);


        // Step 3.5: Verify the selected radio box 
        WebElement yesRadioButtonmeaasage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'text-success')]")));
        Assert.assertTrue(yesRadioButtonmeaasage.isDisplayed(), "Selected radio button not displayed");
        System.out.println("Yes radio box selected");

        

        // Error Handling: Check for 502/500 errors
      
    	}
    	catch(Exception e) 
    	{  handleErrorAndRetry();
    		System.out.println("ExceptionMessage:"+e.getMessage());}}	
    

    private static void handleErrorAndRetry() throws InterruptedException {
        int retryCount = 0;
        int maxRetries = 3;

        while (retryCount < maxRetries) {
            try {
                // Check for 502/500 error on the page
                String pageTitle = driver.getTitle();
                String pageSource = driver.getPageSource();

                if (pageTitle.contains("502") || pageTitle.contains("500") || 
                    pageSource.contains("502") || pageSource.contains("500")) {

                    System.out.println("Detected 502/500 error. Attempt " + (retryCount + 1) + " to refresh the page.");
                    driver.navigate().refresh();
                    retryCount++;

                    // Wait before retrying
                    Thread.sleep(3000);
                } else {
                    System.out.println("No error detected. Proceeding with test.");
                    return; // Exit function as test is back to normal
                }
            } catch (NoSuchWindowException e) {
                System.out.println("No such window detected. Retrying attempt " + (retryCount + 1));
                retryCount++;
                driver.navigate().refresh();
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred: " + e.getMessage());
                break;
            }
        }		

        if (retryCount >= maxRetries) {
            System.out.println("Maximum retry attempts reached. Stopping test.");
        }
    }




    @AfterTest
    public static void tearDown() {
        // Close the browser after the test
        driver.quit();
        System.out.println("Browser closed successfully.");
    }

    public static void main(String[] args) throws InterruptedException {
        setup();
        ElementsinDemoQA();
        tearDown(); 
    }
}
