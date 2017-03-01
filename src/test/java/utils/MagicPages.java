package utils;

import java.util.HashMap;


public class MagicPages {

    private static HashMap<String, Object> pages = new HashMap<>();

    public void storePage(String name, Object page) {
        pages.put(name,page);
    }

    public <T> T getPage(String name) {
        return (T) pages.get(name);
    }

}
