package com.example.mongo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppUtils {
    public static String getUserId(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
    public static String getFileName(String nameWithExtension){
        return nameWithExtension.substring(0, nameWithExtension.indexOf("."));
    }

    public static String getExtension(String nameWithExtension){
        return nameWithExtension.substring(nameWithExtension.indexOf(".") + 1);
    }
}
