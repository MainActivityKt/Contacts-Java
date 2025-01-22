package contacts.memorablephonebook;

import utils.Validator;

import java.io.Serializable;
import java.time.LocalDateTime;


class OrganizationContact extends Contact {
    String address;

    public OrganizationContact(String name, String address, String phoneNumber) {
        super(name, phoneNumber, LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.address = address;
    }

    @Override
    void print() {
        System.out.printf("""
                Organization name: %s
                Address: %s
                Number: %s
                Time created: %s
                Time last edit: %s
                %n""", name, address, phoneNumber, creationDate, lastEditDate);
    }

    @Override
    void modifyField(String fieldName, String newValue) {
        switch (fieldName) {
            case "address" -> this.address = newValue;
            case "number" -> {
                if (Validator.isPhoneNumberValid(newValue)) {
                    phoneNumber = newValue;
                } else {
                    System.out.println("Wrong number format!");
                    phoneNumber = null;
                }
            }
        }
    }

    @Override
    String getStringValueOfFields() {
        return String.format("%s %s %s", name, address, phoneNumber != null ? phoneNumber : "");
    }

    @Override
    String getModifiableFields() {
        return "address, number";
    }
}
