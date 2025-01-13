package contacts;

import java.util.Scanner;

class SingleContact {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the person:");
        String firstName = sc.nextLine();

        System.out.println("Enter the surname of the person:");
        String lastName = sc.nextLine();

        System.out.println("Enter the number:");
        String number = sc.nextLine();

        System.out.println();
        System.out.println("A record created!");
        System.out.println("A Phone Book with a single record created!");

        sc.close();
    }
}
