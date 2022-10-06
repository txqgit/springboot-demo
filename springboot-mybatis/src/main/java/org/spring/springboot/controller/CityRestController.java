package org.spring.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityRestController {
    private final Logger LOG = LoggerFactory.getLogger(CityRestController.class);

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        LOG.debug(">>>>>>>>>>Enter Controller");
        LOG.info(">>>>>>>>>>Enter Controller");
        LOG.warn(">>>>>>>>>>Enter Controller");
        LOG.error(">>>>>>>>>>Enter Controller");
        return cityService.findCityByName(cityName);
    }

}
