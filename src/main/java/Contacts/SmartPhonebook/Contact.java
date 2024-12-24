package Contacts.SmartPhonebook;

import java.util.Scanner;

import static utils.Validator.isPhoneNumberValid;

class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;


    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (isPhoneNumberValid(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = null;
        }
    }

    public void changeFirstName(Scanner sc) {
        System.out.print("Enter name: ");
        firstName = sc.nextLine();
    }

    public void changeLastName(Scanner sc) {
        System.out.print("Enter surname: ");
        this.lastName = sc.nextLine();
    }

    public void changePhoneNumber(Scanner sc) {
        System.out.print("Enter number: ");
        String number = sc.nextLine();
        if (isPhoneNumberValid(number)) {
            this.phoneNumber = number;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s", firstName, lastName, phoneNumber != null ? phoneNumber : "[no number]");
    }
}
