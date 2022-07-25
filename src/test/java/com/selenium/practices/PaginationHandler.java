package com.selenium.practices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PaginationHandler {
    WebDriver driver;

    @BeforeClass
    public void initializeDriver(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demo.openmrs.org/openmrs/login.htm");
    }


    @Test(priority = 3)
    public boolean isSignedInUserDisplayed(){
        System.out.println("isSignedInUserDisplayed Started.....");
        System.out.println("isSignedInUserDisplayed: "+driver.findElement(By.xpath("//*[contains(text(),'admin')]")).isDisplayed());
        return driver.findElement(By.xpath("//*[contains(text(),'admin')]")).isDisplayed();
    }
    //check the headers of the web table
    @Test(priority = 2)
    public void checkTableHeaders(){
        System.out.println("checkTableHeaders Started.....");
        List<WebElement> headerElements = driver.findElements(By.cssSelector("#patient-search-results-table>thead>tr>th"));
        List<String> headerNames = new ArrayList<String>();

        for (WebElement element : headerElements) {
            headerNames.add(element.getText());
            System.out.println(element.getText());
        }
        System.out.println("Total Header Count: "+headerNames.size());

    }
    @Test(priority = 1)
    public void checkDropDownField() throws InterruptedException {
        System.out.println("checkDropDownField Started.....");
        driver.findElement(By.id("username")).sendKeys("Admin");
        driver.findElement(By.id("password")).sendKeys("Admin123");
        driver.findElement(By.cssSelector("li[id='Outpatient Clinic']")).click();
        driver.findElement(By.id("loginButton")).click();
        Thread.sleep(2000);
        System.out.println("isSignedInUserDisplayed: "+driver.findElement(By.xpath("//*[contains(text(),'admin')]")).isDisplayed());
        driver.findElement(By.partialLinkText("Find Patient Record")).click();

        driver.findElement(By.id("patient-search")).sendKeys("Test123");
        Thread.sleep(2000);

        List<WebElement> nameElements = driver.findElements(By.cssSelector("#patient-search-results-table>tbody>tr>td:nth-child(2)"));
        System.out.println("nameElements Size: "+nameElements.size());
        if(nameElements.size()>0) {
            List<String> names = new ArrayList<String>();

            for (WebElement element : nameElements) {
                names.add(element.getText());
            }

            String nextButtonClassName = driver.findElement(By.id("patient-search-results-table_next")).getAttribute("class");
            while (!nextButtonClassName.contains("disabled")) {
                driver.findElement(By.id("patient-search-results-table_next")).click();
                nameElements = driver.findElements(By.cssSelector("#patient-search-results-table>tbody>tr>td:nth-child(2)"));
                for (WebElement element : nameElements) {
                    names.add(element.getText());
                }
                nextButtonClassName = driver.findElement(By.id("patient-search-results-table_next")).getAttribute("class");
            }
            for (String name : names) {
                if (name.contains("Sarah")) {
                    System.out.println(name);
                } else {
                    System.out.println("No searched keyword is found");
                    break;
                }
            }
            System.out.println("Total Names: " + names.size());
        }
        else{
            if(driver.findElement(By.cssSelector("#patient-search-results-table>tbody>tr>td")).getText().contains("No matching records found")){
                System.out.println("No Results Found");
            }
        }

    }



    @AfterClass
    public void closeDriver()
    {
        driver.quit();

    }
}
