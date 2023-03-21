
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
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        if (driver == null) ;
    }

    @Test
    void shouldCheckInputEnglishName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Khalilova Irina");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInputNumbersInFieldName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("222");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInputSymbolsInFieldName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("%:?*");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInputTextInFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Khalilova Irina");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInput12NumbersInFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456788");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInputNumbersWithoutPlusInFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79012345678");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckInputSymbolsInFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("7(901)2345678");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCheckCorrectFieldsWithoutCheckMark() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.className("button__content")).click();
        assertTrue(driver.findElement(By.cssSelector("label.input_invalid")).isDisplayed());
    }
    @Test
    void shouldCheckEmptyFieldName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79189871057");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldCheckEmptyFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Халилова-Шарова Ирина");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

}