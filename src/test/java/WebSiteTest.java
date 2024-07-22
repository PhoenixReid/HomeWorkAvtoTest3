import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class WebSiteTest {
    private WebDriver driver;

    ChromeOptions options = new ChromeOptions();{
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
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void SendingTheForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем"); //Я знаю,что многим людям не нравиться буква Ё,но выписывать её из русских букв это как то чересчур
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals( expected.trim(), text.trim());
    }

    @Test
    void AnEmptyNameField(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals( expected.trim(), text.trim());
    }

    @Test
    void BlankNumberField(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"]")).findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals( expected.trim(), text.trim());
    }

    @Test
    void TheCheckBoxIsNotChecked(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Артем");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79532376054");
        driver.findElement(By.cssSelector("[class=\"button__content\"]")).click();
        String text = driver.findElement(By.cssSelector("[class=\"checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid\"]")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals( expected.trim(), text.trim());
    }




}
