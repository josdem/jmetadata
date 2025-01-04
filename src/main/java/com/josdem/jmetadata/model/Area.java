package com.josdem.jmetadata.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class Area {
  private String id;
  private String name;

  @SerializedName("sort-name")
  private String sortName;

  @SerializedName("iso-3166-1-codes")
  private List<String> codes;
}
