package com.example.mongo.domain.documents;

import com.example.mongo.domain.BaseEntity;
import com.example.mongo.domain.Contact;
import com.example.mongo.domain.User;
import com.example.mongo.enums.DocumentRole;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Document(collection = "document_type")
public class DocumentType {
    @Id
    private String id;
    @NonNull
    private String name;
    @DBRef
    private User user;
    @NonNull
    protected boolean deleted = false;
    @NonNull
    private DocumentRole role;
    private List<Subsection> subsections;
    private boolean byDefault;
    private boolean enable = false;
    private List<ContactType> contacts;

    @Data
    public static class Subsection {
        private String name;
        private DocumentRole role;
        private boolean enable = false;

        public static Subsection of(String name, DocumentRole role){
            Subsection subsection = new Subsection();
            subsection.name = name;
            subsection.role = role;
            return subsection;
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class ContactType {
        @DBRef
        private Contact contact;
        @NonNull
        private Type type;
    }

    public enum Type {
        PRIMARY, SUCCESSOR;
    }

    public static List<DocumentType> addDocumentTypeForUser(User user){
        return Arrays.asList(addWill(user), addCPA(user),addRCPA(user), addRLT(user), addQCD(user), addSAHT(user),
                addAIVT(user), addDPAF(user), addDPAH(user), addLPA(user), addMHAD(user), addAD(user),
                addHR(user), addHIPPA(user));
    }

    private static DocumentType addWill(User user){
        List<Subsection> subsections = Arrays.asList(Subsection.of("Contingent Trust", DocumentRole.AGENT),
                Subsection.of("Guardian Trust", DocumentRole.AGENT),
                Subsection.of("Gun Trust", DocumentRole.AGENT),
                Subsection.of("Life Time Asset Protection Trust", DocumentRole.AGENT),
                Subsection.of("Minor Trust", DocumentRole.AGENT),
                Subsection.of("Other Testamentary Trusts (ADD)", DocumentRole.AGENT),
                Subsection.of("Pet Trust", DocumentRole.AGENT),
                Subsection.of("Safe Harbor Trust", DocumentRole.AGENT),
                Subsection.of("Tax Trust", DocumentRole.AGENT),
                Subsection.of("Temporary Holding Trust", DocumentRole.AGENT));
        return DocumentType.of(null,"Will", user, false, DocumentRole.AGENT, subsections, true,
                false, Collections.emptyList());

    }

    private static DocumentType addCPA(User user){
        return DocumentType.of(null,"Community Property Agreement", user, false, DocumentRole.AGENT, Collections.emptyList(),
                true, false, Collections.emptyList());
    }

    private static DocumentType addRCPA(User user){
        return DocumentType.of(null,"Revocation Of Community Property Agreement", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addRLT(User user){
        List<Subsection> subsections = Arrays.asList(Subsection.of("Bill of Sale", DocumentRole.AGENT),
                Subsection.of("Billfold Card", DocumentRole.AGENT),
                Subsection.of("Certificate of Trustee Authority", DocumentRole.AGENT),
                Subsection.of("Comprehensive Transfer Document", DocumentRole.AGENT),
                Subsection.of("Contingent Trust", DocumentRole.AGENT),
                Subsection.of("Funding Card", DocumentRole.AGENT),
                Subsection.of("Guardian Trust", DocumentRole.AGENT),
                Subsection.of("Gun Trust", DocumentRole.AGENT),
                Subsection.of("Life Time Asset Protection Trust", DocumentRole.AGENT),
                Subsection.of("Minor Trust", DocumentRole.AGENT),
                Subsection.of("Other Testamentary Trusts (ADD)", DocumentRole.AGENT),
                Subsection.of("Pet Trust", DocumentRole.AGENT),
                Subsection.of("Pour Over Will", DocumentRole.AGENT),
                Subsection.of("Safe Harbor Trust", DocumentRole.AGENT),
                Subsection.of("Tax Trust", DocumentRole.AGENT),
                Subsection.of("Temporary Holding Trust", DocumentRole.AGENT));
        return DocumentType.of(null,"Revocable Living Trust", user, false, DocumentRole.AGENT, subsections,true,
                false, Collections.emptyList());
    }

    private static DocumentType addQCD(User user){
        return DocumentType.of(null, "Quit Claim Deed", user, false, DocumentRole.AGENT, Collections.emptyList(),true,
                false, Collections.emptyList());
    }

    private static DocumentType addSAHT(User user){
        return DocumentType.of(null , "Stand-Alone Safe Harbor Trust", user, false, DocumentRole.AGENT, Collections.emptyList(),true,
                false, Collections.emptyList());
    }

    private static DocumentType addAIVT(User user){
        return DocumentType.of(null, "Other Inter Vivos Trust (ADD)", user, false,
                DocumentRole.AGENT, Collections.emptyList(),true,
                false, Collections.emptyList());
    }

    private static DocumentType addDPAF(User user){
        return DocumentType.of(null,"Durable Power Of Attorney Of Finance", user, false,
                DocumentRole.AGENT, Collections.emptyList(),
                true, false, Collections.emptyList());
    }

    private static DocumentType addDPAH(User user){
        return DocumentType.of(null, "Durable Power Of Attorney Of HealthCare", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addLPA(User user){
        return DocumentType.of(null, "Limited Power Of Attorney", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addMHAD(User user){
        return DocumentType.of(null, "Mental Health Advance Directive", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addAD(User user){
        return DocumentType.of(null, "Advance Directive (Living Will)", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addHR(User user){
        return DocumentType.of(null, "Handling Of Remains", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }

    private static DocumentType addHIPPA(User user){
        return DocumentType.of(null, "HIPPA Release", user, false, DocumentRole.AGENT,
                Collections.emptyList(),true, false, Collections.emptyList());
    }
}
