package com.mycompany.quickchatapp2;

import javax.swing.JOptionPane;
import java.util.Random;

public class QuickChatApp2 {
    public static void main(String[] args) {
        // Simple login (Part 1) - keep your existing login here
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat");

        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
        Random rand = new Random();

        for (int i = 1; i <= numMessages; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient (+27...):");
            String text = JOptionPane.showInputDialog("Enter message (max 250 chars):");
            String id = String.valueOf(1000000000L + rand.nextInt(900000000));
            Message m = new Message(id, recipient, text, "");
            // Create hash after object creation
            String hash = m.createMessageHash(i);
            m = new Message(id, recipient, text, hash);
            Message.register(m);

            String[] options = {"Send", "Disregard", "Store"};
            int choice = JOptionPane.showOptionDialog(null, "Choose action for message " + i,
                    "Message Action", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (choice == 0) { Message.addSent(m); JOptionPane.showMessageDialog(null, "Sent!\nHash: " + hash); }
            else if (choice == 1) { Message.addDisregarded(m); JOptionPane.showMessageDialog(null, "Disregarded"); }
            else { Message.addStored(m); Message.saveStoredToJSON(); JOptionPane.showMessageDialog(null, "Stored for later"); }
        }

        // Part 3 Menu
        while (true) {
            String menu = "1. Display Sender & Recipient of Stored\n2. Display Longest Message\n3. Search by Message ID\n4. Search by Recipient\n5. Delete by Hash\n6. Display Report\n7. Read Stored from JSON\n8. Quit";
            String input = JOptionPane.showInputDialog(menu + "\nEnter choice:");
            if (input == null || input.equals("8")) break;
            switch (input) {
                case "1":
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < Message.storedMessages.size(); i++)
                        sb.append("Recipient: ").append(Message.storedRecipients.get(i)).append(" | Message: ").append(Message.storedMessages.get(i)).append("\n");
                    JOptionPane.showMessageDialog(null, sb.toString());
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Longest: " + Message.getLongestMessage());
                    break;
                case "3":
                    String id = JOptionPane.showInputDialog("Enter Message ID:");
                    JOptionPane.showMessageDialog(null, Message.searchByMessageID(id));
                    break;
                case "4":
                    String rec = JOptionPane.showInputDialog("Enter Recipient:");
                    JOptionPane.showMessageDialog(null, Message.searchByRecipient(rec));
                    break;
                case "5":
                    String hash = JOptionPane.showInputDialog("Enter Message Hash:");
                    JOptionPane.showMessageDialog(null, Message.deleteByHash(hash));
                    break;
                case "6":
                    JOptionPane.showMessageDialog(null, Message.displayReport());
                    break;
                case "7":
                    JOptionPane.showMessageDialog(null, Message.loadStoredFromJSON());
                    break;
            }
        }
        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.sentMessages.size());
    }
}