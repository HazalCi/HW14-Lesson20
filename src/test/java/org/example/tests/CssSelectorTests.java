package org.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class CssSelectorTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/java/org/example/drivers/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        // options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        // Page Load Timeout
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

        // Implicit Wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    @Test(priority = 0)
    public void clickButtonTest(){
        driver.get("https://demoqa.com/elements");
        WebElement btnClick = driver.findElement(By.cssSelector("div.show li:nth-child(5) span.text"));
        btnClick.click();
        WebElement result = driver.findElement(By.cssSelector("div[class*='col-md-6'] div:nth-child(2) div:last-child button.btn-primary"));
        result.click();

        WebElement resultMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#dynamicClickMessage")));
        Assert.assertTrue(resultMessage.isDisplayed());
        Assert.assertEquals(resultMessage.getText(), "You have done a dynamic click");
    }

    @Test(priority = 1)
    public void addRecordTest() {
        driver.get("https://demoqa.com/webtables");

        WebElement addButton = driver.findElement(By.cssSelector("button#addNewRecordButton"));
        addButton.click();

        driver.findElement(By.cssSelector("input#firstName")).sendKeys("Hazal");
        driver.findElement(By.cssSelector("input#lastName")).sendKeys("Cigerim");
        driver.findElement(By.cssSelector("input#userEmail")).sendKeys("hazalcigerim@gmail.com");
        driver.findElement(By.cssSelector("input#age")).sendKeys("25");
        driver.findElement(By.cssSelector("input#salary")).sendKeys("1000");
        driver.findElement(By.cssSelector("input#department")).sendKeys("R&D");
        driver.findElement(By.cssSelector("button#submit")).click();

        WebElement editButton = driver.findElement(By.cssSelector("span#edit-record-4"));
        editButton.click();

        WebElement ageField = driver.findElement(By.cssSelector("input#age"));
        ageField.clear();
        ageField.sendKeys("27");
        driver.findElement(By.cssSelector("button#submit")).click();


        WebElement editedRecord = driver.findElement(By.cssSelector(".rt-tbody .rt-tr-group:nth-child(4) .rt-td:nth-child(3)"));
        Assert.assertTrue(editedRecord.isDisplayed());
        Assert.assertEquals(editedRecord.getText(), "27");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
