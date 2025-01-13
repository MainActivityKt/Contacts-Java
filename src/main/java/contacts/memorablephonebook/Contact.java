package contacts.memorablephonebook;

import java.time.LocalDateTime;


abstract public class Contact {
    String name;
    String phoneNumber;
    String creationDate;
    String lastEditDate;

    public Contact(String name, String phoneNumber, String creationDate, String lastEditDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
    }

    abstract String modifyField(String fieldName, String newValue);

    abstract String getStringValueOfFields();

    abstract String getModifiableFields();
}

