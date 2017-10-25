package ua.novaposhta.test.helper.elements;

import org.openqa.selenium.By;
import ua.novaposhta.test.helper.Assertions;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;

public class PathFinder {
    public PathFinder() {
    }

    public String path(String path, ArrayList list){
        Assertions a = new Assertions();
        if (a.elementIsVisible($(By.xpath(path)))) {
            return path;
        } else {
            for (Object selector : list) {
                path = selector.toString();
                if ($(By.xpath(String.valueOf(selector))).isDisplayed()) {
                    path = selector.toString();
                }
            }
        }
        return path;
    }
}
