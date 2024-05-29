package com.pismennaya.shop.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8081/admin");
    }

    @AfterEach
    public void tearDown() {
        if (driver!= null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        driver.findElement(By.name("login")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("12345");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Товары", dashboard.getText());
    }

    @Test
    public void testInvalidLogin() {
        driver.findElement(By.name("username")).sendKeys("aaddmmiinn");
        driver.findElement(By.name("password")).sendKeys("54321");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".auth-error"));
        assertTrue(error.isDisplayed());
        assertEquals("Неверный логин или пароль", error.getText());
    }

    @Test
    public void testProductList() {
        testLogin();
        driver.findElement(By.linkText("Товары")).click();
        List<WebElement> products = driver.findElements(By.cssSelector(".data-table tbody tr"));
        assertTrue(products.size() > 0);
    }

    @Test
    public void testCreateProduct() {
        testLogin();
        driver.findElement(By.linkText("Добавить товары")).click();
        driver.findElement(By.name("name")).sendKeys("Тест");
        driver.findElement(By.name("price")).sendKeys("2000");
        driver.findElement(By.name("description")).sendKeys("Описание");
        driver.findElement(By.name("category")).sendKeys("1");
        driver.findElement(By.name("country")).sendKeys("Россия");
        driver.findElement(By.name("production")).sendKeys("Производитель 1");
        driver.findElement(By.name("quantity")).sendKeys("1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success-btn"));
        assertTrue(success.isDisplayed());
    }

    @Test
    public void testEditProduct() {
        testLogin();
        driver.findElement(By.linkText("Товары")).click();
        driver.findElement(By.cssSelector(".data-table tbody tr a")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Продукт 111");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
    }

    @Test
    public void testDeleteProduct() {
        testLogin();
        driver.findElement(By.linkText("Товары")).click();
        driver.findElement(By.cssSelector(".data-table tbody tr a")).click();
        driver.findElement(By.cssSelector(".danger-btn")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Товары", dashboard.getText());
    }

    @Test
    public void testCategoryList() {
        testLogin();
        driver.findElement(By.linkText("Категории")).click();
        List<WebElement> categories = driver.findElements(By.cssSelector(".data-table tbody tr"));
        assertTrue(categories.size() > 0);
    }

    @Test
    public void testCreateCategory() {
        testLogin();
        driver.findElement(By.linkText("Категории")).click();
        driver.findElement(By.linkText("Добавить категорию")).click();
        driver.findElement(By.name("name")).sendKeys("Тест");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Категории", dashboard.getText());
    }

    @Test
    public void testEditCategory() {
        testLogin();
        driver.findElement(By.linkText("Категории")).click();
        driver.findElement(By.linkText(".data-table tbody tr a.category-name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Тест 2");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Категории", dashboard.getText());
    }

    @Test
    public void testDeleteCategory() {
        testLogin();
        driver.findElement(By.linkText("Категории")).click();
        driver.findElement(By.linkText(".data-table tbody tr a.remove-category-btn")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Категории", dashboard.getText());
    }

    @Test
    public void testOrderList() {
        testLogin();
        driver.findElement(By.linkText("Заказы")).click();
        List<WebElement> orders = driver.findElements(By.cssSelector(".data-table tbody tr"));
        assertTrue(orders.size() > 0);
    }

    @Test
    public void testViewOrder() {
        testLogin();
        driver.findElement(By.linkText("Заказы")).click();
        driver.findElement(By.cssSelector(".data-table tbody tr a")).click();
        WebElement orderDetails = driver.findElement(By.cssSelector(".order-form"));
        assertTrue(orderDetails.isDisplayed());
    }

    @Test
    public void testEditOrder() {
        testLogin();
        driver.findElement(By.linkText("Заказы")).click();
        driver.findElement(By.cssSelector(".data-table tbody tr a")).click();
        driver.findElement(By.name("status")).sendKeys("2");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Заказы", dashboard.getText());
    }

    @Test
    public void testClientList() {
        testLogin();
        driver.findElement(By.linkText("Клиенты")).click();
        List<WebElement> clients = driver.findElements(By.cssSelector(".data-table tbody tr"));
        assertTrue(clients.size() > 0);
    }

    @Test
    public void testEditClient() {
        testLogin();
        driver.findElement(By.linkText("Клиенты")).click();
        driver.findElement(By.cssSelector(".data-table tbody tr a")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Тест");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h2"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Клинеты", dashboard.getText());
    }
}