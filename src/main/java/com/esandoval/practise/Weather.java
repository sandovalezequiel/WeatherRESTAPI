package com.esandoval.practise;

public class Weather {

    private final Integer day;
    private final String weather;

    public Weather(Integer day, String type) {
        this.day = day;
        this.weather = type;
    }

    public Integer getDay() {
        return day;
    }

    public String getWeather() {
        return weather;
    }
}