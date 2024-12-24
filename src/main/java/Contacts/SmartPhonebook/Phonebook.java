package Contacts.SmartPhonebook;

import java.util.ArrayList;
import java.util.Scanner;

public class Phonebook {

    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String actions = "Enter action (add, remove, edit, count, list, exit): ";
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
                case "list" -> listContacts();
            }
        }
    }

    private static void editContact() {
        if (contacts.isEmpty()) {
            System.out.println("No records to edit!");
            return;
        }
        listContacts();
        int contactNumber = Integer.parseInt(getInput("Select a record: "));
        Contact selectedContact = contacts.get(contactNumber - 1);
        String field = getInput("Select a field (name, surname, number): ").toLowerCase();
        switch (field) {
            case "name" -> selectedContact.changeFirstName(sc);
            case "surname" -> selectedContact.changeLastName(sc);
            case "number" -> selectedContact.changePhoneNumber(sc);
        }
        System.out.println("The record updated!");
    }

    private static void listContacts() {
        for (int i = 0; i < contacts.size(); i++) {
            String contact = contacts.get(i).toString();
            System.out.printf("%d. %s\n", i + 1, contact);
        }
    }

    private static void addContact() {
        String firstName = getInput("Enter the name: ");
        String lastName = getInput("Enter the surname: ");
        String number = getInput("Enter the number: ");
        contacts.add(new Contact(firstName, lastName, number));
        System.out.println("The record added.");
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

    private static void printCount() {
        System.out.printf("The Phone Book has %d records.\n", contacts.size());
    }

    private static String getInput(String message) {
        System.out.printf("%s", message);
        return sc.nextLine();
    }

}
