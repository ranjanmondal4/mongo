package com.example.mongo.dto.documents;

import com.example.mongo.domain.documents.DocumentType;
import com.example.mongo.domain.documents.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface DocumentMapper {
//    @Mappings({
//            @Mapping(target="id", source="id")
//    })
    List<DocumentTypeDTO> documentTypesToDocumentTypeDTOs(List<DocumentType> documentTypes);

    List<FolderDTO> foldersToFolderDTOs(List<Folder> folder);

    FolderDTO folderToFolderDTO(Folder folder);

}
