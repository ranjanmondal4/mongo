package com.example.mongo.service;

import com.example.mongo.configuration.DataNotFoundException;
import com.example.mongo.domain.Contact;
import com.example.mongo.domain.User;
import com.example.mongo.domain.documents.AllowedAction;
import com.example.mongo.domain.documents.Document;
import com.example.mongo.domain.documents.DocumentType;
import com.example.mongo.domain.documents.Folder;
import com.example.mongo.dto.documents.*;
import com.example.mongo.repo.DocumentTypeRepo;
import com.example.mongo.repo.FolderRepo;
import com.example.mongo.util.AppUtils;
import com.example.mongo.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DocumentService {
    private final UserService userService;
    private final DocumentTypeRepo documentTypeRepo;
    private final DocumentMapper documentMapper;
    private final ContactService contactService;
    private final FolderRepo folderRepo;

    public boolean addListOfDocumentType(String userId){
        User user = userService.findUserById(userId).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        List<DocumentType> documentTypes = DocumentType.addDocumentTypeForUser(user);
        return !documentTypeRepo.saveAll(documentTypes).isEmpty();
    }

    public boolean enableOrDisable(String userId, String documentTypeId, boolean enable) {
        DocumentType documentType = documentTypeRepo.findByIdAndUser_Id(documentTypeId, userId)
                .orElseThrow(()-> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));
        documentType.setEnable(enable);
        documentTypeRepo.save(documentType);
        return true;
    }

    public boolean enableOrDisable(String userId, UpdateDocumentTypeDTO dto) {
        DocumentType documentType = documentTypeRepo.findByIdAndUser_Id(dto.getId(), userId)
                .orElseThrow(()-> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));

        documentType.setEnable(dto.isEnable());
        documentType.getSubsections().forEach(subsection -> {
          Optional<UpdateDocumentTypeDTO.Subsection> foundSection = dto.getSubsections().stream().filter(section ->
                    StringUtils.equalsIgnoreCase(subsection.getName(), section.getName())).findFirst();
            foundSection.ifPresent(section -> subsection.setEnable(section.isEnable()));
        });

        documentTypeRepo.save(documentType);
        return true;
    }

    public List<DocumentTypeDTO> getDocumentTypesByUserId(String userId){
        List<DocumentType> documentTypes = documentTypeRepo.findByUser_IdAndDeleted(userId, false);
        List<DocumentTypeDTO> dtos = documentMapper.documentTypesToDocumentTypeDTOs(documentTypes);

        dtos.forEach(dto -> {
            Optional<DocumentType> document = documentTypes.stream()
                    .filter(documentType -> StringUtils.equals(dto.getId(), documentType.getId())).findFirst();
            document.ifPresent(documentType -> dto.setContactTypes(documentType.getContacts().stream()
                    .map(contactType -> DocumentTypeDTO.ContactType.of(contactType.getContact(), contactType.getType()))
                    .collect(Collectors.toList())));
        });
        return dtos;
    }

    public boolean updateContactsToDocumentType(String userId, UpdateContactToDocumentTypeDTO dto){
        DocumentType documentType = documentTypeRepo.findByIdAndUser_Id(dto.getId(), userId)
                .orElseThrow(()-> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));

        List<UpdateContactToDocumentTypeDTO.ContactType> contactTypesDTO = dto.getContactTypes();
        List<String> contactIds = contactTypesDTO.stream()
                .map(UpdateContactToDocumentTypeDTO.ContactType::getContactId).collect(Collectors.toList());

        List<Contact> contacts =contactService.findContactsByIdInAndUserId(contactIds, userId);
        List<DocumentType.ContactType> newContacts = contacts.stream().filter(contact -> contactTypesDTO.stream()
                .anyMatch(contactType -> StringUtils.equals(contactType.getContactId(), contact.getId())))
                .map(contact -> {
                    UpdateContactToDocumentTypeDTO.ContactType contType = dto.getContactTypes().stream()
                    .filter(contactType -> contactType.getContactId().equals(contact.getId()))
                    .findFirst().get();
                    return DocumentType.ContactType.of(contact, contType.getType());
        }).collect(Collectors.toList());

        documentType.setContacts(newContacts);
        documentTypeRepo.save(documentType);
        return true;
    }

    public boolean addRootFolder(String userId, String folderName, Folder.BelongsTo belongsTo){
        Optional<Folder> folderExists = folderRepo.findByNameAndUser_IdAndParentFolderIsNullAndDeletedFalse(folderName, userId);
        if(folderExists.isPresent())
            throw DataNotFoundException.of(MessageConstants.FOLDER_ALREADY_EXISTS);

        AllowedAction action = AllowedAction.of(false, false, false);
        User user = userService.findByUserId(userId);
        Folder folder = Folder.of(user, folderName, belongsTo, action);
        folderRepo.save(folder);
        return true;
    }

    public Folder findRootFolderByName(String userId, String folderName){
        return folderRepo.findByNameAndUser_IdAndParentFolderIsNullAndDeletedFalse(folderName, userId)
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.FOLDER_NOT_FOUND));
    }

    public String getFolderNameByBelongsTo(String userId, String existingName, Folder.BelongsTo belongsTo){
        User user = userService.findByUserId(userId);
        if(belongsTo == Folder.BelongsTo.CLIENT)
            return user.getFirstName() + "-" + existingName;
        else
            return belongsTo.name() + "-" + existingName;
    }

    public Folder addFolderWithParentFolder(String userId, String folderName, Folder parentFolder,
                                               Folder.BelongsTo belongsTo){
        AllowedAction action = AllowedAction.of(false, false, false);
        User user = userService.findByUserId(userId);
        Folder folder = folderRepo.findByNameAndUser_IdAndParentFolder_IdAndDeletedFalse(folderName, userId, parentFolder.getId())
                .orElseGet(() -> {
                        Folder newFolder = Folder.of(user, folderName, belongsTo, action);
                        newFolder.setParentFolder(parentFolder);
                        return newFolder;
                });
        return folderRepo.save(folder);
    }

    public Folder addDocumentToFolder(Folder folder, MultipartFile file) {
        if(Objects.isNull(folder))
            throw DataNotFoundException.of(MessageConstants.FOLDER_NOT_FOUND);

        if(Objects.isNull(file) || StringUtils.isEmpty(file.getOriginalFilename()))
            throw DataNotFoundException.of(MessageConstants.DOCUMENT_NOT_FOUND);

        String fileName = AppUtils.getFileName(file.getOriginalFilename());
        String type = AppUtils.getExtension(file.getOriginalFilename());
        Document document = Document.of(fileName, type);
        List<Document> documents = folder.getDocuments();
        if(Objects.isNull(documents)){
            documents = Collections.singletonList(document);
        } else {
            documents.add(document);
        }
        folder.setDocuments(documents);
        return folderRepo.save(folder);
    }

    public List<FolderDTO> getAllRootFolderByUserId(String userId){
        List<Folder> folders = folderRepo.findAllByUser_IdAndParentFolderIsNullAndDeletedFalse(userId);
        List<FolderDTO> folderDTOs = documentMapper.foldersToFolderDTOs(folders);
        folderDTOs.forEach(folderDTO -> {
            List<Folder> childFolders = folderRepo.findAllByUser_IdAndParentFolder_IdAndDeletedFalse(userId,
                    folderDTO.getId());
            folderDTO.setChildFolders(childFoldersToChildDTOs(childFolders));
        });
        return folderDTOs;
    }

    public FolderDTO findFolderById(String userId, String folderId){
        Folder folder = folderRepo.findByIdAndUser_IdAndDeletedFalse(folderId, userId)
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.FOLDER_NOT_FOUND));

        FolderDTO folderDTO = documentMapper.folderToFolderDTO(folder);
        List<Folder> childFolders = folderRepo.findAllByUser_IdAndParentFolder_IdAndDeletedFalse(userId, folder.getId());
        folderDTO.setChildFolders(childFoldersToChildDTOs(childFolders));
        return folderDTO;
    }

    private List<FolderDTO.ChildFolderDTO> childFoldersToChildDTOs(List<Folder> childFolders){
        return childFolders.stream().map(childFolder -> FolderDTO.ChildFolderDTO.of(childFolder.getId(),
                childFolder.getName(), childFolder.getBelongsTo(),
                Objects.isNull(childFolder.getDocuments()) ? 0 : childFolder.getDocuments().size()))
                .collect(Collectors.toList());
    }
}
