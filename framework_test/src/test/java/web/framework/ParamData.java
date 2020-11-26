package web.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ParamData {

    public List<HashMap<String, Object>> steps;
    private WebDriver driver;
    private WebElement currentElement;

    public void run(WebDriver driver){
        steps.forEach(step->{
           /* if(step.containsKey("chrome")){
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
            }
            if(step.containsKey("implicitly_wait")){
                driver.manage().timeouts().implicitlyWait(
                        (int)step.get("implicitly_wait"), TimeUnit.SECONDS);
            }
            if(step.containsKey("get")){
                driver.get((String) step.get("get"));
            }*/
            if(step.containsKey("find")){
                ArrayList<By> locator = new ArrayList<>();
                ((HashMap<String,String>)step.get("find")).entrySet().forEach(entry -> {
                    if(entry.getKey().contains("id")){
                        locator.add(By.id(entry.getValue())) ;
                    }else if(entry.getKey().contains("xpath")){
                        locator.add(By.xpath(entry.getValue()));
                    }else if(entry.getKey().contains("link_text")){
                        locator.add(new By.ByLinkText(entry.getValue()));
                    }else if(entry.getKey().contains("name")){
                        locator.add(By.name(entry.getValue()));
                    }
                });
                currentElement = driver.findElement(locator.get(0));
            }
            if(step.containsKey("click")){
                currentElement.click();
            }
            if(step.containsKey("send_key")){
                currentElement.sendKeys("测试部");
            }
        });
    }
}
