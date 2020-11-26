package web.framework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.utils.GetCookie;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class FrameworkTest2 {


    public static WebDriver driver;


    @BeforeAll
    public static void setCookie() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new  ChromeDriver();
        File file = new File("cookies.yaml");
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // 判断本地是否存有cookie
        if(!file.exists()){
            GetCookie.getWeiXinCookie(driver);
        }
        // 获取本地cookie
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String,Object>>> typeReference = new TypeReference<List<HashMap<String,Object>>>(){};
        List<HashMap<String,Object>> cookies =mapper.readValue(file, typeReference);
        // 设置到网页中
        cookies.forEach(cookieMap ->{
            driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(),cookieMap.get("value").toString()));
        });
        // 刷新页面
        driver.navigate().refresh();
    }


    @Test
    void addDepartment() throws IOException {
        PageObjectModel model = new PageObjectModel();
        model.executeTest("addDepartment",driver);
    }



}
