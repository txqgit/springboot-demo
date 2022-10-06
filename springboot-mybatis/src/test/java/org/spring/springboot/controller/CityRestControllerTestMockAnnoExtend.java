package org.spring.springboot.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.spring.springboot.base.BaseTest;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;

public class CityRestControllerTestMockAnnoExtend extends BaseTest {
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
}
