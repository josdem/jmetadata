package com.josdem.jmetadata.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ReleaseGroup {
  private String id;

  @SerializedName("type-id")
  private String typeId;

  @SerializedName("primary-type-id")
  private String primaryTypeId;

  private String title;

  @SerializedName("primary-type")
  private String primaryType;
}
