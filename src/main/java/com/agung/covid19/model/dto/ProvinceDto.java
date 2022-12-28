package com.agung.covid19.model.dto;

import com.agung.covid19.model.Province;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
public class ProvinceDto {
    @JsonProperty("attributes")
    private Province province;

}
