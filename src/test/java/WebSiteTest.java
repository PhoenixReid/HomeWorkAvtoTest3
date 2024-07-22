import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class WebSiteTest {
    private WebDriver driver;

    ChromeOptions options = new ChromeOptions();

    {
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
    }

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void sendingTheForm() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем"); //Я знаю,что многим людям не нравиться буква Ё,но выписывать её из русских букв это как то чересчур
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected.trim(), text.trim());
    }

    @Test
    void sendingTheFormWithAnEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected.trim(), text.trim());
    }

    @Test
    void sendingAFormWithACalidName(){
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Artyom");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected.trim(), text.trim());
    }

    @Test
    void blankNumberField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"]")).findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected.trim(), text.trim());
    }

    @Test
    void sendingTheFormWithAValidPhoneNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7953236054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"]")).findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected.trim(), text.trim());
    }

    @Test
    void theCheckBoxIsNotChecked() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[class=\"checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid\"]")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expected.trim(), text.trim());
    }


}
