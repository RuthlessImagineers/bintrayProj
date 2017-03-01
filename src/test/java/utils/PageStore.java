package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by krishnanand on 25/07/16.
 */
public class PageStore {

    private static PageStore pageStore;
    List<Object> pages = new ArrayList<>();


    public PageStore() {
        pageStore = this;
    }

    public static PageStore getInstance() {
        return pageStore;
    }

    public <T> T get(Class<T> clazz) {
        for (Object page : pages) {
            if (page.getClass() == clazz)
                return (T) page;
        }
        T page = null;
        try {
           // page = PageFactory.initElements(DriverFactory.getDriver(), clazz);
            page = PageFactory.initElements(DriverFactory.getDriver(),clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pages.add(page);
        return page;
    }

}
