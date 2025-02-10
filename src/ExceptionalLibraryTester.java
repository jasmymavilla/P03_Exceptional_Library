//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03 Exceptional Library
// Course:   CS 300 Spring 2025
//
// Author:   Jasmy Mavilla
// Email:    jmavilla@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         N/A
// Online Sources:  ChatGPT: Helped with fixing Gradescope tests and ExceptionalLibraryTester.java
//                  - https://chatgpt.com/c/67a673af-48f8-8001-a463-8aa2cbeff0a6
//                  - https://chatgpt.com/c/67a7e606-a820-8001-a5d1-cb9c0e000c65
//
///////////////////////////////////////////////////////////////////////////////


import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.NoSuchElementException;
import java.text.ParseException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Test class for ExceptionalLibrary.
 * This class contains various test methods to verify the correct behavior of the methods
 * in the ExceptionalLibrary class.
 */

public class ExceptionalLibraryTester {


    /**
     * Checks the addBook() method for correct exception throws when invalid inputs are provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testAddBookInvalidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.addBook(null, "Author");
            return false;
        } catch (IllegalArgumentException e) {
        }

        try {
            ExceptionalLibrary.addBook("Title", "");
            return false;
        } catch (IllegalArgumentException e) {
        }

        try {
            ExceptionalLibrary.addBook(" ", "Author");
            return false;
        } catch (IllegalArgumentException e) {
        }

        return true;

    }


    /**
     * Checks the addBook() method for correct behavior when valid inputs are provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testAddBookValidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.addBook("Title", "Author");
            if (ExceptionalLibrary.getBooks().length != 1) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;

    }


    /**
     * Checks the removeBook() method for intended behavior when the book is not present.
     * @return true if the test passes, false otherwise
     */

    public static boolean testRemoveBookInvalidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.removeBook(1);
            return false;
        } catch (NoSuchElementException e) {
        }

        ExceptionalLibrary.addBook("Title", "Author");

        try {
            ExceptionalLibrary.removeBook(1);
            return false;
        } catch (IllegalStateException e) {
        }

        return true;

    }


    /**
     * Checks the removeBook() method for intended behavior when the book is present and valid.
     * @return true if the test passes, false otherwise
     */

    public static boolean testRemoveBookValidInputs(){

        ExceptionalLibrary.resetLibrarySystem();
        ExceptionalLibrary.addBook("Title", "Author");

        Book book = ExceptionalLibrary.removeBook(1);

        return book != null && ExceptionalLibrary.getBooks().length == 0;

    }


    /**
     * Tests the various methods for finding books (by ID and by author) when invalid inputs
     * are given.
     * @return true if the test passes, false otherwise
     */

    public static boolean testFindBooksInvalidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.findBookById(1);
            return false;
        } catch (NoSuchElementException e) {
        }

        try {
            ExceptionalLibrary.findBookByAuthor("Nonexistent Author");
            return false;
        } catch (NoSuchElementException e) {
        }

        return true;

    }


    /**
     * Tests the methods for finding books (by ID and by author) when valid books are present.
     * @return true if the test passes, false otherwise
     */

    public static boolean testFindBooksValidInputs(){

        ExceptionalLibrary.resetLibrarySystem();
        ExceptionalLibrary.addBook("Title", "Author");

        Book bookById = ExceptionalLibrary.findBookById(1);
        ArrayList<Book> booksByAuthor = ExceptionalLibrary.findBookByAuthor("Author");

        return bookById != null && booksByAuthor.size() == 1 &&
            booksByAuthor.get(0).equals(bookById);

    }


    /**
     * Checks the addSubscriber() method for correct exception throws when invalid inputs are
     * provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testAddSubscriberInvalidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.addSubscriber(null, "1234", "Address");
            return false;
        } catch (IllegalArgumentException e) {
        }

        return true;

    }


    /**
     * Checks the addSubscriber() method for correct behavior when valid inputs are provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testAddSubscriberValidInputs(){

        ExceptionalLibrary.resetLibrarySystem();

        try {
            ExceptionalLibrary.addSubscriber("Name", "1234", "Address");
            if (ExceptionalLibrary.getSubscribers().length != 1) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;

    }


    /**
     * Tests the parsePinCode() method for correct exception throws when invalid PIN is provided.
     * @return true if the test passes, false otherwise
     */
    /**
     * Tests the parsePinCode() method for correct exception throws when invalid PIN is provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testParsePinCodeInvalidInputs(){

        try {
            ExceptionalLibrary.parsePinCode("0001");
            return false;
        } catch (ParseException e) {
        }

        try {
            ExceptionalLibrary.parsePinCode("123");
            return false;
        } catch (ParseException e) {
        }

        return true;

    }


    /**
     * Tests the parsePinCode() method for intended behavior when valid PIN is provided.
     * @return true if the test passes, false otherwise
     */

    public static boolean testParsePinCodeValidInputs(){

        try {
            int pin = ExceptionalLibrary.parsePinCode("1234");
            return pin == 1234;
        } catch (ParseException e) {
            return false;
        }

    }


    public static void main(String[] args) {

        System.out.println("Running tests:");
        System.out.println("testAddBookInvalidInputs(): " +
            (testAddBookInvalidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testAddBookValidInputs(): " +
            (testAddBookValidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testRemoveBookInvalidInputs(): " +
            (testRemoveBookInvalidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testRemoveBookValidInputs(): " +
            (testRemoveBookValidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testFindBooksInvalidInputs(): " +
            (testFindBooksInvalidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testFindBooksValidInputs(): " +
            (testFindBooksValidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testAddSubscriberInvalidInputs(): " +
            (testAddSubscriberInvalidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testAddSubscriberValidInputs(): " +
            (testAddSubscriberValidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testParsePinCodeInvalidInputs(): " +
            (testParsePinCodeInvalidInputs() ? "PASSED" : "FAILED"));
        System.out.println("testParsePinCodeValidInputs(): " +
            (testParsePinCodeValidInputs() ? "PASSED" : "FAILED"));
        System.out.println("ALL TESTS: " + (testAddBookInvalidInputs() &&
            testAddBookValidInputs() &&
            testRemoveBookInvalidInputs() && testRemoveBookValidInputs() &&
            testFindBooksInvalidInputs() &&
            testFindBooksValidInputs() && testAddSubscriberInvalidInputs() &&
            testAddSubscriberValidInputs() &&
            testParsePinCodeInvalidInputs() && testParsePinCodeValidInputs() ? "PASSED" :
            "FAILED"));

    }
}
