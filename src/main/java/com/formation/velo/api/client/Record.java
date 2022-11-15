package com.formation.velo.api.client;

import lombok.Getter;
import lombok.Setter;
import com.google.gson.annotations.SerializedName;
@Getter
@Setter
public class Record {

    @SerializedName("recordid")
    private String recordId;
    @SerializedName("fields")
    private Field field;
}
