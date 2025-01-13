package contacts.memorablephonebook;

import contacts.GENDER;
import utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

class PersonContact extends SerializableContact {
    String firstname;
    String lastName;
    LocalDate birthday;
    GENDER gender;

    public PersonContact(String firstName, String lastName, LocalDate birthday, GENDER gender, String phoneNumber) {
        super(firstName + " " + lastName, phoneNumber, LocalDateTime.now().toString(), LocalDateTime.now().toString());
        this.firstname = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("""
                Name: %s
                Surname: %s
                Birth date: %s
                Gender: %s
                Number: %s
                Time created: %s
                Time last edit: %s
                """, firstname, lastName, birthday != null ? birthday : "[no data]",
                gender != null ? gender : "[no data]", phoneNumber != null ? phoneNumber : "[no data]",
                creationDate, lastEditDate);
    }



    void changeFirstName(String updatedFirstName) {
        firstname = updatedFirstName;
    }

    void changeSurname(String updatedSurname) {
        lastName = updatedSurname;
    }

    void changeNumber(String updatedNumber) {
        if (Validator.isPhoneNumberValid(updatedNumber)) {
            phoneNumber = updatedNumber;
        } else {
            System.out.println("Wrong number format!");
            phoneNumber = null;
        }
    }

    void changeBirthday(String updatedBirthday) {
        if (Validator.isBirthDateValid(updatedBirthday)) {
            birthday = LocalDate.parse(updatedBirthday);
        } else {
            System.out.println("Bad birth date!");
            birthday = null;
        }
    }

    void changeGender(String updatedGender) {
        gender = switch (updatedGender.toUpperCase()) {
            case "M" -> GENDER.M;
            case "F" -> GENDER.F;
            default -> {
                System.out.println("Bad gender!");
                yield null;
            }
        };
    }

    @Override
    String modifyField(String fieldName, String newValue) {
        return "";
    }

    @Override
    String getStringValueOfFields() {
        return String.format("%s %s %s %s %s", firstname, lastName, birthday != null ? birthday : "",
                gender != null ? gender : "", phoneNumber != null ? phoneNumber : "");
    }

    @Override
    public String getModifiableFields() {
        return "name, surname, birth, gender, number";
    }
}
