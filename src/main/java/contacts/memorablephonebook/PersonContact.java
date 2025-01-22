package contacts.memorablephonebook;

import contacts.GENDER;
import utils.Validator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

class PersonContact extends Contact {
    String firstname;
    String lastName;
    String birthday;
    GENDER gender;

    public PersonContact(String firstName, String lastName, LocalDate birthday, GENDER gender, String phoneNumber) {
        super(firstName + " " + lastName, phoneNumber, LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.firstname = firstName;
        this.lastName = lastName;
        this.birthday = birthday != null ? birthday.toString() : null;
        this.gender = gender;
    }

    @Override
    void print() {
        System.out.printf("""
                        Name: %s
                        Surname: %s
                        Birth date: %s
                        Gender: %s
                        Number: %s
                        Time created: %s
                        Time last edit: %s
                        %n""", firstname, lastName, birthday != null ? birthday : "[no data]",
                gender != null ? gender : "[no data]", phoneNumber != null ? phoneNumber : "[no data]",
                creationDate, lastEditDate);
    }

    @Override
    void modifyField(String fieldName, String newValue) {
        switch (fieldName) {
            case "name" -> this.firstname = newValue;
            case "surname" -> this.lastName = newValue;
            case "birth" -> {
                if (Validator.isBirthDateValid(newValue)) {
                    birthday = LocalDate.parse(newValue).toString();
                } else {
                    System.out.println("Bad birth date!");
                    birthday = null;
                }
            }
            case "gender" -> {
                this.gender = switch (newValue.toUpperCase()) {
                    case "M" -> GENDER.M;
                    case "F" -> GENDER.F;
                    default -> {
                        System.out.println("Bad gender!");
                        yield null;
                    }
                };
            }
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
        return String.format("%s %s %s %s %s", firstname, lastName, birthday != null ? birthday : "",
                gender != null ? gender : "", phoneNumber != null ? phoneNumber : "");
    }

    @Override
    String getModifiableFields() {
        return "name, surname, birth, gender, number";
    }
}
