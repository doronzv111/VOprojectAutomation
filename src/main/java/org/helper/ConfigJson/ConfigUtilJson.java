package org.helper.ConfigJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigUtilJson {

    public static ConfigLoaderJson readConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), ConfigLoaderJson.class);
    }
}
