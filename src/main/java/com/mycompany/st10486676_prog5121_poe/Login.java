/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.st10486676_prog5121_poe;

public class Login {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public Login() {
    }

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        boolean longEnough = password.length() >= 8;
        return hasCapital && hasNumber && hasSpecial && longEnough;
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }
        this.username = username;
        this.password = password;
        return "User successfully registered.";
    }

    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(this.username) && enteredPass.equals(this.password);
    }

    public String returnLoginStatus(String enteredUser, String enteredPass) {
        if (loginUser(enteredUser, enteredPass)) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}