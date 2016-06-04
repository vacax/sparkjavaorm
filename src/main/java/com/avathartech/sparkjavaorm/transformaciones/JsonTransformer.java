package com.avathartech.sparkjavaorm.transformaciones;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by vacax on 23/04/16.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
