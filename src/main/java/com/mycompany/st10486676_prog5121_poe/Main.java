/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.st10486676_prog5121_poe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("Welcome to PingZA - Ping. Delivered. Read.");

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        login = new Login(firstName, lastName);

        System.out.print("Please enter your username (your username must contain _ and 5 or less characters): ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password (Your password must 8 or more characteristics which should consists of, capital, number, special): ");
        String password = scanner.nextLine();

        System.out.print("Please enter Cell Number (+27...): ");
        String cell = scanner.nextLine();

        String regResult = login.registerUser(username, password);
        System.out.println(regResult);

        if (regResult.contains("successfully")) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Enter Username: ");
            String enteredUser = scanner.nextLine();

            System.out.print("Enter Password: ");
            String enteredPass = scanner.nextLine();

            boolean loginSuccess = login.loginUser(enteredUser, enteredPass);
            String status = login.returnLoginStatus(enteredUser, enteredPass);
            System.out.println(status);
        }
        scanner.close();
    }
}