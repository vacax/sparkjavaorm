package com.avathartech.sparkjavaorm.transformaciones;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Clase para la transformación de los objetos a Json.
 * Created by vacax on 23/04/16.
 */
public class JsonTransformer implements ResponseTransformer {

    //Libreria de Google para la serialización de json a objeto y viceversa.
    private Gson gson = new Gson();

    /**
     * Quien realiza el conversión.
     * @param model
     * @return
     */
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
