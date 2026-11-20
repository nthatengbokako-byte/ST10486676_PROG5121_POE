package com.mycompany.quickchatapp2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private void setupTestData() {
        Message.clearAll();
        Message.allMessages.clear();
        // Message 1 - Sent
        Message m1 = new Message("1000000001", "+27834557896", "Did you get the cake?", "10:1:DIDCAKE");
        // Message 2 - Stored
        Message m2 = new Message("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "10:2:WHERETIME.");
        // Message 3 - Disregarded
        Message m3 = new Message("1000000003", "+27834484567", "Yohooo, I am at your gate.", "10:3:YOHOOOGATE.");
        // Message 4 - Sent, ID = 0838884567 for test 3
        Message m4 = new Message("0838884567", "+27838884567", "It is dinner time !", "08:4:ITTIME");
        // Message 5 - Stored
        Message m5 = new Message("1000000005", "+27838884567", "Ok, I am leaving without you.", "10:5:OKYOU.");

        Message.addSent(m1); Message.register(m1);
        Message.addStored(m2); Message.register(m2);
        Message.addDisregarded(m3); Message.register(m3);
        Message.addSent(m4); Message.register(m4);
        Message.addStored(m5); Message.register(m5);
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        setupTestData();
        assertEquals(2, Message.sentMessages.size());
        assertTrue(Message.sentMessages.contains("Did you get the cake?"));
        assertTrue(Message.sentMessages.contains("It is dinner time !"));
    }

    @Test
    public void testDisplayLongestMessage() {
        setupTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.getLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        setupTestData();
        assertEquals("It is dinner time !", Message.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchByRecipient() {
        setupTestData();
        String result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash() {
        setupTestData();
        String result = Message.deleteByHash("10:2:WHERETIME.");
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        setupTestData();
        String report = Message.displayReport();
        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}
