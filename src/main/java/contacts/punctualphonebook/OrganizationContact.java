package contacts.punctualphonebook;

import utils.Validator;

import java.time.LocalDateTime;

class OrganizationContact extends Contact {
    String address;

    public OrganizationContact(String name, String address, String phoneNumber) {
        super(name, phoneNumber, LocalDateTime.now(), LocalDateTime.now());
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("""
                Organization name: %s
                Address: %s
                Number: %s
                Time created: %s
                Time last edit: %s
                """, name, address, phoneNumber, creationDate, lastEditDate);
    }

    @Override
    String getModifiableFields() {
        return "address, number";
    }

    void changeAddress(String newAddress) {
        address = newAddress;
    }

    void changeNumber(String updatedNumber) {
        if (Validator.isPhoneNumberValid(updatedNumber)) {
            phoneNumber = updatedNumber;
        } else {
            System.out.println("Wrong number format!");
            phoneNumber = null;
        }
    }
}
