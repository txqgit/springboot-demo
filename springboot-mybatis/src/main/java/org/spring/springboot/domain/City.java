package org.spring.springboot.domain;

public class City {

    /**
     * 城市编号
     */
    private Long cityId;

    /**
     * 省份编号
     */
    private String provinceName;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long id) {
        this.cityId = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
