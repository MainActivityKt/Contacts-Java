package contacts.memorablephonebook;

import java.io.Serializable;

public class SerializableContact extends Contact implements Serializable {
    public SerializableContact(String name, String phoneNumber, String creationDate, String lastEditDate) {
        super(name, phoneNumber, creationDate, lastEditDate);
    }

    @Override
    String modifyField(String fieldName, String newValue) {
        return "";
    }

    @Override
    String getStringValueOfFields() {
        return "";
    }

    @Override
    String getModifiableFields() {
        return "";
    }
}
