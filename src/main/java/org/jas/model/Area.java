package org.jas.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Area {
    private String id;
    private String name;
    @SerializedName("sort-name")
    private String sortName;
    @SerializedName("iso-3166-1-codes")
    private List<String> codes;
}
