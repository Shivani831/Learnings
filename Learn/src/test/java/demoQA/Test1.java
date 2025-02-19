package demoQA;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        
        WebDriver driver = new ChromeDriver();
        
        // Open a website to verify if it's working
        driver.get("https://www.amazon.in");
        driver.manage().window().maximize();
        Thread.sleep(5000);
        // Quit the driver after test
        driver.quit();
    }
}