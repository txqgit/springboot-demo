package org.spring.springboot.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.base.BaseTest;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.spring.springboot.service.impl.CityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CityRestControllerTestSpyAnno {
    private final Logger LOG = LoggerFactory.getLogger(CityRestControllerTestSpyAnno.class);

    @Spy
    private CityServiceImpl cityService;

    @InjectMocks
    private CityRestController cityRestController;

    @Test
    public void findOneCityTest_doReturn_When() {
        LOG.info(">>>>>doReturn().when() invoked>>>>>");
        City city = new City();
        city.setCityName("Beijing");
        Mockito.doReturn(city).when(cityService).findCityByName(Mockito.anyString());
        City mockResult = cityRestController.findOneCity("UnitTest");
        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }

    @Test
    public void findOneCityTest_When_thenReturn() {
        LOG.info(">>>>>when().thenReturn() invoked>>>>>");
        City city = new City();
        city.setCityName("Beijing");
        Mockito.when(cityService.findCityByName(Mockito.anyString())).thenReturn(city);
        City mockResult = cityRestController.findOneCity("UnitTest");
//        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }
}
