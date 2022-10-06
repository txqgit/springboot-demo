package org.spring.springboot.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;

@RunWith(MockitoJUnitRunner.class)
public class CityRestControllerTestMockAnno {
    @Mock
    private CityService cityService;

    @InjectMocks
    private CityRestController cityRestController;

    @Test
    public void findOneCityTest_doReturn_When() {
        City city = new City();
        city.setCityName("Beijing");
        Mockito.doReturn(city).when(cityService).findCityByName(Mockito.anyString());
        City mockResult = cityRestController.findOneCity("UnitTest");
        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }

    @Test
    public void findOneCityTest_When_thenReturn() {
        City city = new City();
        city.setCityName("Beijing");
        Mockito.when(cityService.findCityByName(Mockito.anyString())).thenReturn(city);
        City mockResult = cityRestController.findOneCity("UnitTest");
        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }
}
