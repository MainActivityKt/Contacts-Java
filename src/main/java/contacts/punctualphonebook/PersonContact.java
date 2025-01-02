package contacts.punctualphonebook;

import utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

class PersonContact extends Contact {
    String firstname;
    String lastName;
    LocalDate birthday;
    Gender gender;


    public PersonContact(String firstName, String lastName, LocalDate birthday, Gender gender, String phoneNumber) {
        super(firstName + " " + lastName, phoneNumber, LocalDateTime.now(), LocalDateTime.now());
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
                """, firstname, lastName, birthday != null ? birthday : "[no data]", gender,
                phoneNumber != null ? phoneNumber : "[no data]", creationDate, lastEditDate);
    }

    void changeFirstName(String updatedFirstName) {
        name = updatedFirstName;
    }

    void changeSurname(String updatedSurname) {
        name = updatedSurname;
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
            case "M" -> Gender.M;
            case "F" -> Gender.F;
            default -> {
                System.out.println("Bad gender!");
                yield null;
            }
        };
    }

    @Override
    String getModifiableFields() {
        return "name, surname, birth, gender, number";
    }


}
