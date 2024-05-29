import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class HomeControllerSystemTests {
    @LocalServerPort

    private int port;


    private WebDriver driver;


    @BeforeEach

    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:" + port);

    }


    @AfterEach

    public void tearDown() {

        if (driver!= null) {

            driver.quit();

        }

    }


    @Test

    public void testHome() {

        List<WebElement> products = driver.findElements(By.cssSelector(".product"));

        assertTrue(products.size() > 0);

    }


    @Test

    public void testProduct() {

        driver.findElement(By.cssSelector(".product a")).click();

        WebElement title = driver.findElement(By.cssSelector("h1"));

        assertTrue(title.isDisplayed());

    }


    @Test

    public void testCart() {

        driver.findElement(By.cssSelector(".product a")).click();

        driver.findElement(By.cssSelector(".add-to-cart")).click();

        driver.findElement(By.cssSelector(".cart a")).click();

        List<WebElement> products = driver.findElements(By.cssSelector(".cart-product"));

        assertTrue(products.size() > 0);

    }


    @Test

    public void testOrder() {

        driver.findElement(By.cssSelector(".product a")).click();

        driver.findElement(By.cssSelector(".add-to-cart")).click();

        driver.findElement(By.cssSelector(".cart a")).click();

        driver.findElement(By.cssSelector(".order-button")).click();

        WebElement title = driver.findElement(By.cssSelector("h1"));

        assertTrue(title.getText().contains("Оформление заказа"));

    }


    @Test
    public void testSuccessOrder() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).sendKeys("Test Surname");
        driver.findElement(By.cssSelector(".order-form input[name='phone']")).sendKeys("1234567890");
        driver.findElement(By.cssSelector(".order-form input[name='email']")).sendKeys("test@example.com");
        driver.findElement(By.cssSelector(".order-form input[name='address']")).sendKeys("Test Address");
        driver.findElement(By.cssSelector(".order-form input[name='delivery_date']")).sendKeys(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement title = driver.findElement(By.cssSelector("h1"));
        assertTrue(title.getText().contains("Заказ успешно оформлен!"));

    }

    @Test
    public void testAddToCart() {
        driver.findElement(By.cssSelector(".product a")).click();
        int initialCartSize = driver.findElements(By.cssSelector(".cart-product")).size();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        int finalCartSize = driver.findElements(By.cssSelector(".cart-product")).size();
        assertTrue(finalCartSize > initialCartSize);
    }

    @Test
    public void testRemoveFromCart() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        int initialCartSize = driver.findElements(By.cssSelector(".cart-product")).size();
        driver.findElement(By.cssSelector(".cart-product.remove-from-cart")).click();
        int finalCartSize = driver.findElements(By.cssSelector(".cart-product")).size();
        assertTrue(finalCartSize < initialCartSize);

    }
}


/*
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.List;

import java.util.concurrent.TimeUnit;


public class ProductDAOTest {


    private WebDriver driver;

    private ProductDAO productDAO;


    @BeforeEach

    public void setUp() {

        System.setProperty("webdriver.gecko.driver", "/path/to/geckodriver");

        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        productDAO = new ProductDAOImpl();

    }


    @Test

    public void testGetByFiltersWithName() {

        List<Product> products = productDAO.getByFilters("test", "null");

        driver.get("http://localhost:8080/products?name=test");


        List<WebElement> productElements = driver.findElements(By.className("product"));


        assertEquals(products.size(), productElements.size());


        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);

            WebElement productElement = productElements.get(i);


            assertEquals(product.getName(), productElement.findElement(By.className("name")).getText());

            assertEquals(product.getCategory().getId(), Integer.parseInt(productElement.findElement(By.className("category")).getText()));

        }

    }


    @Test

    public void testGetByFiltersWithCategory() {

        List<Product> products = productDAO.getByFilters("null", "1");

        driver.get("http://localhost:8080/products?category=1");


        List<WebElement> productElements = driver.findElements(By.className("product"));


        assertEquals(products.size(), productElements.size());


        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);

            WebElement productElement = productElements.get(i);


            assertEquals(product.getName(), productElement.findElement(By.className("name")).getText());

            assertEquals(product.getCategory().getId(), Integer.parseInt(productElement.findElement(By.className("category")).getText()));

        }

    }


    @Test

    public void testGetByFiltersWithNameAndCategory() {

        List<Product> products = productDAO.getByFilters("test", "1");

        driver.get("http://localhost:8080/products?name=test&category=1");


        List<WebElement> productElements = driver.findElements(By.className("product"));


        assertEquals(products.size(), productElements.size());


        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);

            WebElement productElement = productElements.get(i);


            assertEquals(product.getName(), productElement.findElement(By.className("name")).getText());

            assertEquals(product.getCategory().getId(), Integer.parseInt(productElement.findElement(By.className("category")).getText()));

        }

    }


    @Test

    public void testGetByFiltersWithInvalidName() {

        assertThrows(NoSuchElementException.class, () -> {

            productDAO.getByFilters("invalid", "null");

            driver.get("http://localhost:8080/products?name=invalid");

            driver.findElement(By.className("product"));

        });

    }


    @Test

    public void testGetByFiltersWithInvalidCategory() {

        assertThrows(NoSuchElementException.class, () -> {

            productDAO.getByFilters("null", "invalid");

            driver.get("http://localhost:8080/products?category=invalid");

            driver.findElement(By.className("product"));

        });

    }

        @Test
    public void testCreateOrderWithInvalidName() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).clear();
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".order-form.error"));
        assertTrue(error.isDisplayed());
        assertEquals("Введите имя", error.getText());
    }

    @Test
    public void testCreateOrderWithInvalidSurname() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).clear();
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".order-form.error"));
        assertTrue(error.isDisplayed());
        assertEquals("Введите фамилию", error.getText());
    }

    @Test
    public void testCreateOrderWithInvalidPhone() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).sendKeys("Test Surname");
        driver.findElement(By.cssSelector(".order-form input[name='phone']")).clear();
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".order-form.error"));
        assertTrue(error.isDisplayed());
        assertEquals("Введите телефон", error.getText());
    }

    @Test
    public void testCreateOrderWithInvalidEmail() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).sendKeys("Test Surname");
        driver.findElement(By.cssSelector(".order-form input[name='phone']")).sendKeys("1234567890");
        driver.findElement(By.cssSelector(".order-form input[name='email']")).clear();
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".order-form.error"));
        assertTrue(error.isDisplayed());
        assertEquals("Введите email", error.getText());
    }

    @Test
    public void testCreateOrderWithInvalidAddress() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).sendKeys("Test Surname");
        driver.findElement(By.cssSelector(".order-form input[name='phone']")).sendKeys("1234567890");
        driver.findElement(By.cssSelector(".order-form input[name='email']")).sendKeys("test@example.com");
        driver.findElement(By.cssSelector(".order-form input[name='address']")).clear();
        driver.findElement(By.cssSelector(".order-form button[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".order-form.error"));
        assertTrue(error.isDisplayed());
        assertEquals("Введите адрес", error.getText());
    }

    @Test
    public void testCreateOrderWithInvalidDeliveryDate() {
        driver.findElement(By.cssSelector(".product a")).click();
        driver.findElement(By.cssSelector(".add-to-cart")).click();
        driver.findElement(By.cssSelector(".cart a")).click();
        driver.findElement(By.cssSelector(".order-button")).click();
        driver.findElement(By.cssSelector(".order-form input[name='name']")).sendKeys("Test Name");
        driver.findElement(By.cssSelector(".order-form input[name='surname']")).sendKeys("Test Surname");
        driver.findElement(By.cssSelector(".order-form input[name='phone']")).sendKeys("1234567890");
        driver.findElement(By.cssSelector(".order-form input[name='email']")).sendKeys("test@example.com");
        driver.findElement(By.cssSelector(".order-form input[name='address']")).


    @AfterEach

    public void tearDown() {

        driver.quit();

    }

}*/