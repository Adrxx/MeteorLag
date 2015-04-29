package com.example.adrian.meteorlag.GameAndScenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

/**
 * Created by Adrian on 4/29/15.
 */
public class LevelJSON {

    private String title;
    private String subtitle;
    private Integer acceleration;
    private Integer velocity;
    private Integer height;
    private Integer resistance;
    private Integer time;
    private Integer laggerRate;
    private Integer speederRate;
    private List<Stop> stops = new ArrayList<Stop>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     *
     * @param subtitle
     * The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     *
     * @return
     * The acceleration
     */
    public Integer getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @param acceleration
     * The acceleration
     */
    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    /**
     *
     * @return
     * The velocity
     */
    public Integer getVelocity() {
        return velocity;
    }

    /**
     *
     * @param velocity
     * The velocity
     */
    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    /**
     *
     * @return
     * The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The resistance
     */
    public Integer getResistance() {
        return resistance;
    }

    /**
     *
     * @param resistance
     * The resistance
     */
    public void setResistance(Integer resistance) {
        this.resistance = resistance;
    }

    /**
     *
     * @return
     * The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The laggerRate
     */
    public Integer getLaggerRate() {
        return laggerRate;
    }

    /**
     *
     * @param laggerRate
     * The laggerRate
     */
    public void setLaggerRate(Integer laggerRate) {
        this.laggerRate = laggerRate;
    }

    /**
     *
     * @return
     * The speederRate
     */
    public Integer getSpeederRate() {
        return speederRate;
    }

    /**
     *
     * @param speederRate
     * The speederRate
     */
    public void setSpeederRate(Integer speederRate) {
        this.speederRate = speederRate;
    }

    /**
     *
     * @return
     * The stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     *
     * @param stops
     * The stops
     */
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
