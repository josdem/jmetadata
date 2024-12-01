package org.jas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReleaseGroup {
    private String id;
    @JsonProperty("type-id")
    private String typeId;
    @JsonProperty("primary-type-id")
    private String primaryTypeId;
    private String title;
    @JsonProperty("primary-type")
    private String primaryType;
}
