package com.esandoval.practise;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherModel weatherModel = new WeatherModel();

    @RequestMapping("/weather")
    public Weather greeting(@RequestParam(value="day") Integer day) throws Exception {
        if(day > -1 && day < 3650){
            return weatherModel.getWeatherFromADay(day);
        } else {
            throw new Exception("Day is out of simulation range");
        }
    }
}