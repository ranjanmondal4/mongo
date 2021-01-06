package com.example.mongo.dto.documents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description="for updating of enable field document type & subsections")
public class UpdateDocumentTypeDTO {
    private String id;
    @ApiModelProperty(value = "enable field", allowableValues = "true/false")
    private boolean enable;
    private List<Subsection> subsections;
    @Data
    public static class Subsection {
        private String name;
        private boolean enable;
    }
}
