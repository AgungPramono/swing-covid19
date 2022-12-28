package com.agung.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Province {

    @JsonProperty("Kasus_Semb")
    private Long recoveredCase;
    @JsonProperty("FID")
    private Integer FID;
    @JsonProperty("Kode_Provi")
    private Integer provCode;
    @JsonProperty("Provinsi")
    private String provinceName;
    @JsonProperty("Kasus_Posi")
    private Long positifCase;
    @JsonProperty("Kasus_Meni")
    private Long deathCase;
}

