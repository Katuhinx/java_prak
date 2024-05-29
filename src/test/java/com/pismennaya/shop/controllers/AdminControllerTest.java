import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerSystemTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:" + port + "/admin");
    }

    @AfterEach
    public void tearDown() {
        if (driver!= null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement dashboard = driver.findElement(By.cssSelector("h1"));
        assertTrue(dashboard.isDisplayed());
        assertEquals("Admin Dashboard", dashboard.getText());
    }

    @Test
    public void testInvalidLogin() {
        driver.findElement(By.name("username")).sendKeys("invalid");
        driver.findElement(By.name("password")).sendKeys("invalid");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".error"));
        assertTrue(error.isDisplayed());
        assertEquals("Invalid username or password", error.getText());
    }

    @Test
    public void testProductList() {
        testLogin();
        driver.findElement(By.linkText("Products")).click();
        List<WebElement> products = driver.findElements(By.cssSelector(".product"));
        assertTrue(products.size() > 0);
    }

    @Test
    public void testCreateProduct() {
        testLogin();
        driver.findElement(By.linkText("Create Product")).click();
        driver.findElement(By.name("name")).sendKeys("Test Product");
        driver.findElement(By.name("price")).sendKeys("10.99");
        driver.findElement(By.name("description")).sendKeys("Test product description");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Product created successfully", success.getText());
    }

    @Test
    public void testEditProduct() {
        testLogin();
        driver.findElement(By.linkText("Products")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Updated Test Product");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Product updated successfully", success.getText());
    }

    @Test
    public void testDeleteProduct() {
        testLogin();
        driver.findElement(By.linkText("Products")).click();
        driver.findElement(By.linkText("Delete")).click();
        WebElement confirm = driver.findElement(By.cssSelector(".confirm"));
        assertTrue(confirm.isDisplayed());
        assertEquals("Are you sure you want to delete this product?", confirm.getText());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Product deleted successfully", success.getText());
    }

    @Test
    public void testCategoryList() {
        testLogin();
        driver.findElement(By.linkText("Categories")).click();
        List<WebElement> categories = driver.findElements(By.cssSelector(".category"));
        assertTrue(categories.size() > 0);
    }

    @Test
    public void testCreateCategory() {
        testLogin();
        driver.findElement(By.linkText("Create Category")).click();
        driver.findElement(By.name("name")).sendKeys("Test Category");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Category created successfully", success.getText());
    }

    @Test
    public void testEditCategory() {
        testLogin();
        driver.findElement(By.linkText("Categories")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Updated Test Category");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Category updated successfully", success.getText());
    }

    @Test
    public void testDeleteCategory() {
        testLogin();
        driver.findElement(By.linkText("Categories")).click();
        driver.findElement(By.linkText("Delete")).click();
        WebElement confirm = driver.findElement(By.cssSelector(".confirm"));
        assertTrue(confirm.isDisplayed());
        assertEquals("Are you sure you want to delete this category?", confirm.getText());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Category deleted successfully", success.getText());
    }

    @Test
    public void testOrderList() {
        testLogin();
        driver.findElement(By.linkText("Orders")).click();
        List<WebElement> orders = driver.findElements(By.cssSelector(".order"));
        assertTrue(orders.size() > 0);
    }

    @Test
    public void testViewOrder() {
        testLogin();
        driver.findElement(By.linkText("Orders")).click();
        driver.findElement(By.linkText("View")).click();
        WebElement orderDetails = driver.findElement(By.cssSelector(".order-details"));
        assertTrue(orderDetails.isDisplayed());
    }

    @Test
    public void testEditOrder() {
        testLogin();
        driver.findElement(By.linkText("Orders")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.name("status")).sendKeys("Shipped");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Order updated successfully", success.getText());
    }

    @Test
    public void testClientList() {
        testLogin();
        driver.findElement(By.linkText("Clients")).click();
        List<WebElement> clients = driver.findElements(By.cssSelector(".client"));
        assertTrue(clients.size() > 0);
    }

    @Test
    public void testEditClient() {
        testLogin();
        driver.findElement(By.linkText("Clients")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Updated Test Client");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement success = driver.findElement(By.cssSelector(".success"));
        assertTrue(success.isDisplayed());
        assertEquals("Client updated successfully", success.getText());
    }
}