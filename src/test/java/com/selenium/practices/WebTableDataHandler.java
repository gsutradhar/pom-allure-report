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
import java.util.List;

public class WebTableDataHandler {

    WebDriver driver;

    @BeforeClass
    public void initializeDriver(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.get("https://demoqa.com/browser-windows");
        driver.get("https://demo.openmrs.org/openmrs/login.htm");
    }

    @Test
    public void checkDropDownField() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Test class.....");
        //verifying the placeholder text

        //String placeholderText = driver.findElement(By.id("firstName")).getAttribute("placeholder");
        //System.out.println("First Placeholder: "+placeholderText);

        //verifying a button is opening in new tab
       /* driver.findElement(By.id("tabButton")).click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1)); //switches to new tab
        Thread.sleep(2000);
        System.out.println("Title of the new page: "+driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(tabs.get(0));
        System.out.println("Title of the new page: "+driver.getCurrentUrl());*/
        //verifying search data

        driver.findElement(By.id("username")).sendKeys("Admin");
        driver.findElement(By.id("password")).sendKeys("Admin123");
        driver.findElement(By.cssSelector("li[id='Outpatient Clinic']")).click();
        driver.findElement(By.id("loginButton")).click();
        Thread.sleep(2000);
        driver.findElement(By.partialLinkText("Find Patient Record")).click();

        driver.findElement(By.id("patient-search")).sendKeys("John");
        Thread.sleep(2000);
        List<WebElement> gridElements = driver.findElements(By.xpath("//table[@class='table table-sm dataTable']/tbody/tr"));
        System.out.println("Grid elements: " + gridElements.size());
        //String msg= driver.findElement(By.xpath("//td[@class='dataTables_empty']")).getText();
        //System.out.println("Grid message: "+msg);
        if (gridElements.size() > 0 && driver.findElements(By.xpath("//td[@class='dataTables_empty']")).size() == 0) {
            System.out.println("Searched Results are displayed.");
        } else if (gridElements.size() > 0 && driver.findElement(By.xpath("//td[@class='dataTables_empty']")).getText().equals("No matching records found")) {
            System.out.println("Searched Results are not found.");
        }
        //to show the single column data in 1st page
        List<WebElement> rowsWebTable = driver.findElements(By.xpath("//*[@id='patient-search-results-table']/tbody/tr"));
        System.out.println("Size: " + rowsWebTable.size());

        for (int i = 1; i < rowsWebTable.size(); i++) {
            String patientName = driver.findElement(By.xpath("//*[@id='patient-search-results-table']/tbody/tr[" + i + "]/td[2]")).getText();
            System.out.println(patientName);
        }
        Thread.sleep(2000);
        //pagination
        List<WebElement> paginationList = driver.findElements(By.xpath("//*[@class='fg-button ui-button ui-state-default']"));
        //  List<WebElement> paginationList = driver.findElements(By.xpath("//div[@class='dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers']"));
        System.out.println("paginationList Size: " + paginationList.size());

    }

    @AfterClass
    public void closeDriver()
    {
        driver.quit();

    }

}