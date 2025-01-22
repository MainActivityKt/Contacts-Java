package contacts.memorablephonebook;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import contacts.GENDER;
import utils.Validator;
import java.io.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class MemorablePhonebook {

    static final String MENU_ITEMS = "Enter action (add, list, search, count, exit): ";
    static final String LIST_ACTIONS = "Enter action ([number], back): ";
    static final String RECORD_ACTIONS = "Enter action (edit, delete, menu):";
    static final String SEARCH_ACTIONS = "Enter action ([number], back, again): ";
    static String input = "";

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Contact> contacts = new ArrayList<>();
    static File file;
    static OPTION currentOption;

    static Moshi moshi = new Moshi.Builder().build();
    static JsonAdapter<PersonContact> personAdapter = moshi.adapter(PersonContact.class);
    static JsonAdapter<OrganizationContact> orgAdapter = moshi.adapter(OrganizationContact.class);

    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            file = new File(args[1]);
        } else {
            file = new File("phonebook.db");
            file.createNewFile();
            file.deleteOnExit();
        }

        while (!input.equalsIgnoreCase("exit")) {
            currentOption = OPTION.MENU;
            input = getInput(currentOption, MENU_ITEMS);

            switch (input) {
                case "add" -> addContact();
                case "list" -> listContacts();
                case "search" -> searchContacts();
                case "count" -> printCount();
            }
            System.out.println();
        }
    }

    private static void editContact(Contact contact) {

        String fieldToEdit = getInput(String.format("Select a field (%s): ", contact.getModifiableFields())).toLowerCase();
        String newValue = getInput(String.format("Enter %s: ", fieldToEdit));

        contact.modifyField(fieldToEdit, newValue);
        contact.lastEditDate = LocalDateTime.now().toString();

        System.out.println("The record updated!");
    }

    private static void listContacts() {
        currentOption = OPTION.LIST;
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, contacts.get(i).name);
        }
        System.out.println();
        input = getInput(currentOption, LIST_ACTIONS);
        try {
            int index = Integer.parseInt(input);
            printRecord(index - 1);
        } catch (NumberFormatException e) {
            if (input.equals("back")) {
                currentOption = OPTION.MENU;
            }
        }
    }

    private static void addContact() {
        String contactType = getInput("Enter the type (person, organization): ");
        switch (contactType.toLowerCase()) {
            case "person" -> addPersonContact();
            case "organization" -> addOrgContact();
        }
        System.out.println("The record added.");
        updateFile();
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
        GENDER gender = switch (genderString.toUpperCase()) {
            case "M" -> GENDER.M;
            case "F" -> GENDER.F;
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

    private static void printCount() {
        System.out.printf("The Phone Book has %d records.\n", contacts.size());
    }

    private static void searchContacts() {
        currentOption = OPTION.SEARCH;

        List<Contact> foundItems = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        do {
            String query = getInput("Enter search query: ").toLowerCase();
            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = contacts.get(i);
                if (contact.getStringValueOfFields().toLowerCase().contains(query)) {
                    foundItems.add(contact);
                    indexes.add(i);
                }
            }

            System.out.printf("Found %d results:\n", foundItems.size());
            for (int i = 0; i < foundItems.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, foundItems.get(i).name);
            }
            System.out.println();

            input = getInput(currentOption, SEARCH_ACTIONS);
            try {
                int index = Integer.parseInt(input);
                printRecord(indexes.get(index - 1));
            } catch (NumberFormatException e) {
                foundItems.clear();
                indexes.clear();
            }
        } while (input.equalsIgnoreCase("again"));
    }

    private static void printRecord(int index) {
        currentOption = OPTION.RECORD;
        Contact currentContact = contacts.get(index);

        currentContact.print();
        System.out.println();

        while (!input.equalsIgnoreCase("menu")) {
            input = getInput(currentOption, "Enter action (edit, delete, menu): ").toLowerCase();
            switch (input) {
                case "edit" -> editContact(currentContact);
                case "delete" -> {
                    contacts.remove(index);
                    System.out.println("The record removed!\n");
                    updateFile();
                    return;
                }
            }
        }
        currentOption = OPTION.MENU;
        System.out.println();
    }

    private static void updateFile() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("");
            for (Contact contact: contacts) {
                if (contact instanceof PersonContact) {
                    writer.append("p").append(personAdapter.toJson((PersonContact) contact));
                } else if (contact instanceof OrganizationContact) {
                    writer.append("o").append(orgAdapter.toJson((OrganizationContact) contact));
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    private static String getInput(String message) {
        System.out.printf("%s", message);
        return sc.nextLine();
    }

    private static String getInput(OPTION option, String message) {
        System.out.printf("[%s] %s", option.name().toLowerCase(), message);
        return sc.nextLine();
    }
}