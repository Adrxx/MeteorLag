package com.example.adrian.meteorlag.GameAndScenes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adrian on 4/29/15.
 */
public class Stop {

    private Integer at;
    private String text;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The at
     */
    public Integer getAt() {
        return at;
    }

    /**
     *
     * @param at
     * The at
     */
    public void setAt(Integer at) {
        this.at = at;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}