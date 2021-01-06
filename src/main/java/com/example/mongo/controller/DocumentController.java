package com.example.mongo.controller;

import com.example.mongo.configuration.LocaleService;
import com.example.mongo.configuration.ResponseUtil;
import com.example.mongo.domain.documents.Folder;
import com.example.mongo.dto.documents.*;
import com.example.mongo.service.DocumentService;
import com.example.mongo.util.AppConstants;
import com.example.mongo.util.AppUtils;
import com.example.mongo.util.MessageConstants;
import com.example.mongo.util.UrlMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@Api(value = "Document")
public class DocumentController {
    private DocumentService documentService;
    private LocaleService localeService;

    @ApiOperation(value = "adds document-type and its subsection")
    @PostMapping(UrlMapping.DOCUMENT_TYPES)
    public ResponseEntity<Object> addListOfDocumentType(){
        boolean isAdded = documentService.addListOfDocumentType(AppUtils.getUserId());
        return ResponseUtil.generate(isAdded, Collections.singletonMap("isAdded", isAdded),
                isAdded ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "Enable the document type of user", notes = "Updates enable of particular document type")
    @PutMapping(UrlMapping.ENABLE_DOCUMENT_TYPE)
    public ResponseEntity<Object> enableOrDisableDocumentType(@ApiParam(value = "id of document type", required = true)
                                                                  @PathVariable String id, @PathVariable boolean enable){
        boolean isUpdated = documentService.enableOrDisable(AppUtils.getUserId(), id, enable);
        return ResponseUtil.generate(isUpdated, Collections.singletonMap("isUpdated", isUpdated),
                isUpdated ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                        localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "update the enable document-type and its subsection")
    @PutMapping(UrlMapping.UPDATE_DOCUMENT_TYPE)
    public ResponseEntity<Object> enableOrDisableDocumentType(@RequestBody UpdateDocumentTypeDTO dto){
        boolean isUpdated = documentService.enableOrDisable(AppUtils.getUserId(), dto);
        return ResponseUtil.generate(isUpdated, Collections.singletonMap("isUpdated", isUpdated),
                isUpdated ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                        localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "find document-types by user", notes = "List of documents provided")
    @GetMapping(UrlMapping.DOCUMENT_TYPES)
    public ResponseEntity<Object> getListOfDocumentType(){
        List<DocumentTypeDTO> documentTypes = documentService.getDocumentTypesByUserId(AppUtils.getUserId());
        return ResponseUtil.generate(true, Collections.singletonMap("documentTypes", documentTypes),
                localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }

    @ApiOperation(value = "update the contacts of document-type and its subsection")
    @PutMapping(UrlMapping.UPDATE_DOCUMENT_TYPE_CONTACTS)
    public ResponseEntity<Object> updateContactsToDocumentType(@RequestBody UpdateContactToDocumentTypeDTO dto){
        boolean isUpdated = documentService.updateContactsToDocumentType(AppUtils.getUserId(), dto);
        return ResponseUtil.generate(isUpdated, Collections.singletonMap("isUpdated", isUpdated),
                isUpdated ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                        localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "add root folder of user")
    @PostMapping(UrlMapping.ADD_ROOT_FOLDER)
    public ResponseEntity<Object> addRootFolder(@PathVariable String folderName, @PathVariable Folder.BelongsTo belongsTo){
        boolean isAdded = documentService.addRootFolder(AppUtils.getUserId(), folderName, belongsTo);
        return ResponseUtil.generate(isAdded, Collections.singletonMap("isAdded", isAdded),
                isAdded ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                        localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "upload legal documents of user")
    @PostMapping(value = UrlMapping.UPLOAD_LEGAL_DOCUMENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadLegalDocument(@RequestBody MultipartFile file, UploadLegalDocumentDTO dto){
        String userId = AppUtils.getUserId();
        Folder legal = documentService.findRootFolderByName(userId, AppConstants.LEGAL);
        String folderName = documentService.getFolderNameByBelongsTo(userId, dto.getFolderName(), dto.getBelongsTo());
        Folder newFolder = documentService.addFolderWithParentFolder(userId, folderName, legal, dto.getBelongsTo());
        newFolder = documentService.addDocumentToFolder(newFolder, file);
        boolean isFolderAdded = !Objects.isNull(newFolder);
        return ResponseUtil.generate(isFolderAdded, Collections.singletonMap("isUploaded", isFolderAdded),
                isFolderAdded ? localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) :
                        localeService.getMessage(MessageConstants.FAILED));
    }

    @ApiOperation(value = "get all root folder of user")
    @GetMapping(UrlMapping.ROOT_FOLDER)
    public ResponseEntity<Object> getAllRootFolderByUser(){
        List<FolderDTO> folders = documentService.getAllRootFolderByUserId(AppUtils.getUserId());
        return ResponseUtil.generate(true, folders, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }

    @ApiOperation(value = "get folder by id")
    @GetMapping(UrlMapping.FOLDER)
    public ResponseEntity<Object> getFolderByUser(@PathVariable String folderId){
        FolderDTO folder = documentService.findFolderById(AppUtils.getUserId(), folderId);
        return ResponseUtil.generate(true, folder, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }
}

