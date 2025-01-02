package contacts.punctualphonebook;

import utils.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PunctualPhonebook {
    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String actions = "Enter action (add, remove, edit, count, info, exit): ";
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String input = "";

        while (!input.equals("exit")) {
            input = getInput(actions);

            switch (input) {
                case "add" -> addContact();
                case "remove" -> removeContact();
                case "edit" -> editContact();
                case "count" -> printCount();
                case "info" -> showInfo();
            }
            System.out.println();
        }
    }

    private static void editContact() {
        if (contacts.isEmpty()) {
            System.out.println("No records to edit!");
            return;
        }
        listContacts();
        int contactIndex = Integer.parseInt(getInput("Select a record: ")) - 1;
        Contact selectedContact = contacts.get(contactIndex);

        String fieldToEdit = getInput(
                String.format("Select a field (%s): ", selectedContact.getModifiableFields())
        ).toLowerCase();

        if (selectedContact instanceof OrganizationContact contact) {
            String updatedValue = getInput(String.format("Enter %s: ", fieldToEdit));

            switch (fieldToEdit.toLowerCase()) {
                case "address" -> contact.changeAddress(updatedValue);
                case "number" -> contact.changeNumber(updatedValue);
            }
            contact.lastEditDate = LocalDateTime.now();
        } else {
            PersonContact contact = (PersonContact) selectedContact;
            String updatedValue = getInput(String.format("Enter %s: ", fieldToEdit));

            switch (fieldToEdit) {
                case "name" -> contact.changeFirstName(updatedValue);
                case "surname" -> contact.changeSurname(updatedValue);
                case "birth" -> contact.changeBirthday(updatedValue);
                case "gender" -> contact.changeGender(updatedValue);
                case "number" -> contact.changeNumber(updatedValue);
            }
            contact.lastEditDate = LocalDateTime.now();
        }
        System.out.println("The record updated!");
    }

    private static void listContacts() {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, contacts.get(i).name);
        }
    }

    private static void addContact() {
        String contactType = getInput("Enter the type (person, organization): ");
        switch (contactType.toLowerCase()) {
            case "person" -> addPersonContact();
            case "organization" -> addOrgContact();
        }
        System.out.println("The record added.");
    }

    private static void addPersonContact() {
        String firstName = getInput("Enter the name: ");
        String lastName = getInput("Enter the surname: ");
        LocalDate birthDate;
        String number;

        String birthDateString = getInput("Enter the birth date: ");
        if (Validator.isBirthDateValid(birthDateString)) {
            birthDate = LocalDate.parse(birthDateString);
        } else {
            System.out.println("Bad birth date!");
            birthDate = null;
        }

        String genderString = getInput("Enter the gender (M, F): ");
        Gender gender = switch (genderString.toUpperCase()) {
            case "M" -> Gender.M;
            case "F" -> Gender.F;
            default -> {
                System.out.println("Bad gender!");
                yield null;
            }
        };


        String numberString = getInput("Enter the number: ");
        if (Validator.isPhoneNumberValid(numberString)) {
            number = numberString;
        } else {
            System.out.println("Wrong number format!");
            number = null;
        }

        contacts.add(new PersonContact(firstName, lastName, birthDate, gender, number));
    }

    private static void addOrgContact() {
        String name = getInput("Enter the organization name: ");
        String address = getInput("Enter the address: ");
        String number = getInput("Enter the number: ");
        if (!Validator.isPhoneNumberValid(number)) {
            System.out.println("Wrong number format!");
            number = null;
        }
        contacts.add(new OrganizationContact(name, address, number));
    }

    private static void removeContact() {
        if (contacts.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }
        listContacts();
        int contactNumber = Integer.parseInt(getInput("Select a record: "));
        contacts.remove(contactNumber - 1);
        System.out.println("The record removed!");
    }

    private static void showInfo() {
        listContacts();
        int contactIndex = Integer.parseInt(getInput("Enter index to show info: ")) - 1;
        Contact selectedContact = contacts.get(contactIndex);
        System.out.println(selectedContact);
    }

    private static void printCount() {
        System.out.printf("The Phone Book has %d records.\n", contacts.size());
    }

    private static String getInput(String message) {
        System.out.printf("%s", message);
        return sc.nextLine();
    }

}
