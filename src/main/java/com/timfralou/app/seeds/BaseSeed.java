package com.timfralou.app.seeds;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseSeed {
    public final String basePath = "src/main/java/com/timfralou/app/seeds/jsons/";
    public final ObjectMapper objectMapper = new ObjectMapper();
}
