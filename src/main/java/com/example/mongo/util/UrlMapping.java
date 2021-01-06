package com.example.mongo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMapping {
    public static final String V1 = "/api/v1";
    public static final String DOCUMENT_TYPES = V1 + "/user/document-types";
    public static final String ENABLE_DOCUMENT_TYPE = V1 + "/user/document-type/{id}/{enable}";
    public static final String UPDATE_DOCUMENT_TYPE = V1 + "/user/document-type";
    public static final String UPDATE_DOCUMENT_TYPE_CONTACTS = V1 + "/user/document-type/contacts";
    public static final String ADD_ROOT_FOLDER = V1 + "/user/root-folder/{folderName}/belongs/{belongsTo}";
    public static final String UPLOAD_LEGAL_DOCUMENT = V1 + "/user/upload-legal-doc";
    public static final String ROOT_FOLDER = V1 + "/user/root-folder";
    public static final String FOLDER = V1 + "/user/folder/{folderId}";
}
