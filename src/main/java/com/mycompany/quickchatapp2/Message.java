package com.mycompany.quickchatapp2;

/**
 * Message class - handles storing, searching and deleting messages.
 * Uses parallel ArrayLists for Sent, Stored, Disregarded, Hashes and IDs.
 */
import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

public class Message {
    // Message fields
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Parallel arrays (as ArrayLists so they can grow)
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    public static ArrayList<String> sentRecipients = new ArrayList<>();
    public static ArrayList<String> storedRecipients = new ArrayList<>();
    public static ArrayList<String> disregardedRecipients = new ArrayList<>();

    public Message(String messageID, String recipient, String messageText, String messageHash) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = messageHash;
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }

    // --- Part 1 helpers ---
    public boolean checkMessageID() { return messageID.length() == 10; }
    public int checkRecipientCell() {
        if (recipient.length() > 12 || !recipient.startsWith("+27")) return 0;
        return 1;
    }
    public String createMessageHash(int msgNum) {
        String[] words = messageText.split(" ");
        String first = words[0].toUpperCase();
        String last = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");
        return messageID.substring(0, 2) + ":" + msgNum + ":" + first + last;
    }

    // --- Part 3: Populate arrays ---
    public static void addSent(Message m) {
        sentMessages.add(m.getMessageText());
        sentRecipients.add(m.getRecipient());
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }
    public static void addStored(Message m) {
        storedMessages.add(m.getMessageText());
        storedRecipients.add(m.getRecipient());
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }
    public static void addDisregarded(Message m) {
        disregardedMessages.add(m.getMessageText());
        disregardedRecipients.add(m.getRecipient());
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageID());
    }
    public static void clearAll() {
        sentMessages.clear(); storedMessages.clear(); disregardedMessages.clear();
        messageHashes.clear(); messageIDs.clear();
        sentRecipients.clear(); storedRecipients.clear(); disregardedRecipients.clear();
    }

    // --- 2. Longest Message (checks Sent + Stored + Disregarded) ---
    public static String getLongestMessage() {
        String longest = "";
        for (String s : sentMessages) if (s.length() > longest.length()) longest = s;
        for (String s : storedMessages) if (s.length() > longest.length()) longest = s;
        for (String s : disregardedMessages) if (s.length() > longest.length()) longest = s;
        return longest;
    }

    // --- 3. Search by Message ID ---
    // For test: Message 4 ID is "0838884567" and returns "It is dinner time !"
    public static String searchByMessageID(String id) {
        // Search in test data stored in parallel lists
        // We keep a master list of all messages for ID search
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).getMessageID().equals(id)) {
                return allMessages.get(i).getMessageText();
            }
        }
        return "Message ID not found.";
    }
    // Master list to support ID search
    public static ArrayList<Message> allMessages = new ArrayList<>();
    public static void register(Message m) { allMessages.add(m); }

    // --- 4. Search by Recipient (Sent + Stored) ---
    public static String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sentRecipients.size(); i++) {
            if (sentRecipients.get(i).equals(recipient)) result.append(sentMessages.get(i)).append("\n");
        }
        for (int i = 0; i < storedRecipients.size(); i++) {
            if (storedRecipients.get(i).equals(recipient)) result.append(storedMessages.get(i)).append("\n");
        }
        if (result.length() == 0) return "No messages found for this recipient.";
        return result.toString().trim();
    }

    // --- 5. Delete by Hash ---
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            // Find hash - we stored hashes in order, need to find matching stored hash
            // Stored hashes are in messageHashes, we search there
            // To keep it simple, we check allMessages for hash match
            for (Message m : allMessages) {
                if (m.getMessageHash().equals(hash)) {
                    storedMessages.remove(m.getMessageText());
                    storedRecipients.remove(m.getRecipient());
                    messageHashes.remove(hash);
                    messageIDs.remove(m.getMessageID());
                    allMessages.remove(m);
                    return "Message: \"" + m.getMessageText() + "\" successfully deleted.";
                }
            }
        }
        // Also check sent
        for (Message m : new ArrayList<>(allMessages)) {
            if (m.getMessageHash().equals(hash)) {
                sentMessages.remove(m.getMessageText());
                messageHashes.remove(hash);
                allMessages.remove(m);
                return "Message: \"" + m.getMessageText() + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    // --- 6. Display Report ---
    public static String displayReport() {
        StringBuilder report = new StringBuilder("--- MESSAGE REPORT ---\n");
        for (Message m : allMessages) {
            report.append("Hash: ").append(m.getMessageHash()).append("\n");
            report.append("Recipient: ").append(m.getRecipient()).append("\n");
            report.append("Message: ").append(m.getMessageText()).append("\n\n");
        }
        return report.toString();
    }

    // --- JSON: Save Stored to file ---
    public static void saveStoredToJSON() {
        try (FileWriter w = new FileWriter("storedMessages.json")) {
            w.write("[\n");
            for (int i = 0; i < storedMessages.size(); i++) {
                w.write("{\"recipient\":\"" + storedRecipients.get(i) + "\",\"message\":\"" + storedMessages.get(i).replace("\"", "") + "\"}");
                if (i < storedMessages.size() - 1) w.write(",");
                w.write("\n");
            }
            w.write("]");
        } catch (IOException e) { JOptionPane.showMessageDialog(null, "Error saving JSON"); }
    }

    // --- JSON: Read file into array and display ---
    // Attribution: JSON parsing logic adapted from https://www.w3schools.com/js/js_json_parse.asp and StackOverflow JSON file reading examples
    public static String loadStoredFromJSON() {
        StringBuilder out = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("storedMessages.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("recipient")) {
                    String rec = line.substring(line.indexOf(":\"") + 2, line.indexOf("\",\""));
                    String msg = line.substring(line.lastIndexOf(":\"") + 2, line.lastIndexOf("\""));
                    if (!storedMessages.contains(msg)) {
                        storedMessages.add(msg);
                        storedRecipients.add(rec);
                    }
                    out.append("Recipient: ").append(rec).append(" | Message: ").append(msg).append("\n");
                }
            }
        } catch (IOException e) { return "No stored JSON file found."; }
        return out.toString();
    }
}