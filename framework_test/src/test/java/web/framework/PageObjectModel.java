package web.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;

public class PageObjectModel {
    // todo:方法集合
    public HashMap<String, PageObjectMethod> methods = new HashMap<>();
    // todo:断言集合


    public WebElement currentElement;
    public WebDriver driver;

    /**
     * 调用内部方法执行测试
     * @param methodName 测试方法名
     * @param driver
     * @throws IOException
     */
    public void executeTest( WebDriver driver) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
        this.driver = driver;
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            PageObjectModel model = mapper.readValue(PageObjectModel.class.getResourceAsStream("/framework/project_data.yaml"),
                    PageObjectModel.class);
            executeTestSteps(model.methods.get(Thread.currentThread().getStackTrace()[2].getMethodName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行测试具体步骤
     * @param steps
     */
    private void executeTestSteps(PageObjectMethod steps) {
        steps.steps.forEach(step -> {
            if (step.containsKey("id")) {
                currentElement = driver.findElement(By.id(step.get("id").toString()));
            } else if (step.containsKey("xpath")) {
                currentElement = driver.findElement(By.xpath(step.get("xpath").toString()));
            } else if (step.containsKey("name")) {
                currentElement = driver.findElement(By.name(step.get("name").toString()));
            } else if (step.containsKey("link_text")) {
                currentElement = driver.findElement(new By.ByLinkText(step.get("link_text").toString()));
            }
            if (step.containsKey("click")) {
                currentElement.click();
            }
            if (step.containsKey("send_key")) {
                currentElement.sendKeys(step.get("send_key").toString());
            }
        });

    }
}
