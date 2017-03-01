package utils.luggageUtils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuggagePreview {

    private List<WebElement> luggageGroupName;
    private List<WebElement> luggageGroupCount;
    private Map<String,String> previewMap = new HashMap<>();

    public LuggagePreview(List<WebElement> luggageGroupName, List<WebElement> luggageGroupCount) {
        this.luggageGroupName = luggageGroupName;
        this.luggageGroupCount = luggageGroupCount;
    }

    public Map<String,String> mapGroupPreviewNameAndCount() {

        for(int i = 0; i<luggageGroupName.size(); i++) {
            previewMap.put(luggageGroupName.get(i).getText(),luggageGroupCount.get(i).getText());
        }
        return previewMap;
    }

}
