package com.selenium.practices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PickListDataHandler {

    WebDriver driver;
    @BeforeClass
    public void initializeDriver(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://register.rediff.com/commonreg/index.php?redr=http://portfolio.rediff.com/money/jsp/loginnew.jsp?redr=home");
    }

    @Test
    public void verifyPickListsData(){
        System.out.println("verifyPickListsData is started...");
        Select countryList = new Select(driver.findElement(By.id("country")));
        // Get all options
        List<WebElement> allOptionsElement = countryList.getOptions();
        // Creating a list to store drop down options
        List options = new ArrayList();

        // Storing options in list
        for(WebElement optionElement : allOptionsElement)
        {
            options.add(optionElement.getText());
            System.out.println(optionElement.getText());
        }
        // Removing "Select" option as it is not actual option
        options.remove("Select");

        // Default order of option in drop down
        System.out.println("Options in dropdown with Default order :"+options);

    }

    @AfterClass
    public void closeDriver()
    {
        driver.quit();

    }
}
