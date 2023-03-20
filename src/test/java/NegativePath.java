
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativePath {
    static WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        if (driver == null) ;
    }

    @Test
    void shouldChekInputEnglishName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Khalilova Irina");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInputNumbersInFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("222");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInputSymbolsInFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("%:?*");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInputTextInFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Khalilova Irina");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInput12NumberstInFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456788");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInputNumbersWithoutPlusInFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79012345678");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekInputSymbolsInFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("7(901)2345678");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldChekCorrectFieldsWithoutChekMark() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.className("button__content")).click();
        assertTrue(driver.findElement(By.cssSelector("label.input_invalid")).isDisplayed());
    }
    @Test
    void shouldChekEmptyFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldChekEmptyFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

}