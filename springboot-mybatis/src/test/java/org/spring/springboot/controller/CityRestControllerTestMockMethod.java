package org.spring.springboot.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;

import java.lang.reflect.Field;

public class CityRestControllerTestMockMethod {
    private CityService cityService;

    private CityRestController cityRestController;

    @Test
    public void findOneCityTest_setMethod() {
        // 等价于@Mock
        cityService = Mockito.mock(CityService.class);

        // 等价于@InjectMock
        cityRestController = new CityRestController();
        cityRestController.setCityService(cityService);

        City city = new City();
        city.setCityName("Beijing");
        Mockito.doReturn(city).when(cityService).findCityByName(Mockito.anyString());
        City mockResult = cityRestController.findOneCity("UnitTest");
        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }

    @Test
    public void findOneCityTest_reflect() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        // 等价于@Mock
        cityService = Mockito.mock(CityService.class);

        // 等价于@InjectMock
        cityRestController = new CityRestController();
        Class<?> clazz = Class.forName("org.spring.springboot.controller.CityRestController");
        Field field = clazz.getDeclaredField("cityService");
        field.setAccessible(true);
        field.set(cityRestController, cityService);

        City city = new City();
        city.setCityName("Beijing");
        Mockito.doReturn(city).when(cityService).findCityByName(Mockito.anyString());
        City mockResult = cityRestController.findOneCity("UnitTest");
        Assert.assertEquals(city.getCityName(), mockResult.getCityName());
    }
}
