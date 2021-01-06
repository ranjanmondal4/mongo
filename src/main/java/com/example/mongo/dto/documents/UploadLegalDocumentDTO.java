package com.example.mongo.dto.documents;

import com.example.mongo.domain.documents.Folder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UploadLegalDocumentDTO {
    @NotBlank
    private String folderName;
    @NotNull
    private Folder.BelongsTo belongsTo;
}
