package com.sch.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Selenium {

    private WebDriver webDriver;
    public static String webDriverID = "webdriver.chrome.driver"; //Properties setting
    public static String webDriverPath = "C:/Users/csg13/Desktop/openAPI/selenium/chome_and_driver_ver_104/chromedriver.exe"; //webDriver경로

    public Selenium() {
        connectChrome();
    }

    private void connectChrome() {
        System.setProperty(webDriverID, webDriverPath);

        //webDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        // weDriver 생성.
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public String useDriver(String url) {
        webDriver.get(url);
        //driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        //WebElement searchLabel = driver.findElement(By.id("label-text"));
        try {
            Thread.sleep(1000); //웹 페이지의 로딩이나 JavaScript 실행 등을 기다리기 위해 대기
//            //혹은 다음과 같은 방식이 더욱 권장됨
//            WebDriverWait wait = new WebDriverWait(webDriver, 10); // 10초 동안 최대로 기다림
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("someId"))); // id가 'someId'인 엘리먼트가 나타날 때까지 기다림

            //html 콘솔창에 모두 띄우기
            String html = webDriver.getPageSource();

            System.out.println("TEST, getTitle " + webDriver.getTitle());

            return html;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        quitDriver();

        return "No result";
    }

    private void quitDriver() {
        webDriver.quit(); // webDriver 종료
    }

}
