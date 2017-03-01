package utils.dataUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static utils.Constants.Extenstions.*;

/**
 * Maps the data from json to class object
 *
 * Created by krishnanand on 27/07/16.
 */
public class DataMapper {

    public <T> Object mapDetails(Class<T> classtoMap, String datafileref) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        T mappedobj = gson.fromJson(getJsonDataFrom(datafileref),classtoMap);
        return mappedobj;
    }

    private JsonReader getJsonDataFrom(String datafileref) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(new File(datafileref)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }

}
