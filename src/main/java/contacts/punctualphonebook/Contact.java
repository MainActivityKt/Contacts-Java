package contacts.punctualphonebook;

import java.time.LocalDateTime;

abstract public class Contact {
    String name;
    String phoneNumber;
    LocalDateTime creationDate;
    LocalDateTime lastEditDate;

    public Contact(String name, String phoneNumber, LocalDateTime creationDate, LocalDateTime lastEditDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
    }

    abstract String getModifiableFields();
}

