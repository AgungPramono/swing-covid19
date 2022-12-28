package com.agung.covid19.model.dto;

import com.agung.covid19.model.Province;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProvinceDto {
    @JsonProperty("attributes")
    private Province province;

}
