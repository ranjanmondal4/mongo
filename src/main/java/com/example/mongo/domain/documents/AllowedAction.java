package com.example.mongo.domain.documents;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class AllowedAction {
    @NonNull
    private boolean renamable;
    @NonNull
    private boolean movable;
    @NonNull
    private boolean deletable;
}
