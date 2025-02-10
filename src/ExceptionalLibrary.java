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


// Main method
public class ExceptionalLibrary {

  private static ArrayList<Book> books = new ArrayList<>();
  private static ArrayList<Subscriber> subscribers = new ArrayList<>();


  /**
   * Resets the library system by clearing the lists of books and subscribers,
   * and resetting the book ID counter.
   */
  public static void resetLibrarySystem() {

    books.clear();
    subscribers.clear();
    Book.resetBookId();

  }


  /**
   * Returns an array of all the books currently in the library.
   *
   * @return an array of Book objects representing the library's books.
   */
  public static Book[] getBooks() {

    return books.toArray(new Book[0]);

  }


  /**
   * Returns an array of all the subscribers currently in the library system.
   *
   * @return an array of Subscriber objects representing the library's subscribers.
   */
  public static Subscriber[] getSubscribers() {

    return subscribers.toArray(new Subscriber[0]);

  }


  /**
   * Returns a string representation of the library's inventory; each
   * book's details are represented as a string on a new line.
   *
   * @return a string representation of all the books in the library.
   */
  public static String getLibraryInventoryAsString() {

    StringBuilder inventory = new StringBuilder();
    for (Book book : books) {
      inventory.append(book.toString()).append("\n");
    }
    return inventory.toString().trim();

  }


  /**
   * Adds a new book to the library's inventory. If the title or author is
   * invalid, an IllegalArgumentException is thrown.
   *
   * @param title  the title of the book.
   * @param author the author of the book.
   * @throws IllegalArgumentException if the title or author is blank or null.
   */
  public static void addBook(String title, String author) {

    if (title == null || title.isBlank() || author == null || author.isBlank()) {
      throw new IllegalArgumentException("ERROR: Title and author cannot be blank.");
    }
    books.add(new Book(title, author));

  }


  /**
   * Removes a book from the library's inventory. If the book is borrowed, an
   * IllegalStateException is thrown.
   *
   * @param bookId the ID of the book to remove.
   * @return the book that was removed.
   * @throws IllegalStateException if the book is borrowed and cannot be removed.
   */
  public static Book removeBook(int bookId) {

    Book book = findBookById(bookId);

    if (!book.isAvailable()) {
      throw new IllegalStateException("Book unavailable. It was checked " +
          "out and not yet returned.");
    }

    books.remove(book);
    return book;

  }


  /**
   * Searches for a book by its unique identifier.
   *
   * @param bookId the ID of the book to find.
   * @return the book with the specified ID.
   * @throws NoSuchElementException if no book with the given ID is found.
   */
  public static Book findBookById(int bookId) {

    for (Book book : books) {
      if (book.getID() == bookId) {
        return book;
      }
    }
    throw new NoSuchElementException("Book not found");

  }


  /**
   * Retrieves a list of books written by a specific author.
   *
   * @param author the author whose books to find.
   * @return a list of Book objects written by the specified author.
   */
  public static ArrayList<Book> findBookByAuthor(String author) {

    ArrayList<Book> result = new ArrayList<>();
    for (Book book : books) {
      if (book.getAuthor().equalsIgnoreCase(author)) {
        result.add(book);
      }

    }

    if (result.isEmpty()) {
      throw new NoSuchElementException("ERROR: No books found by author " + author);
    }
    return result;

  }


  /**
   * Registers a new subscriber and assigns a library card. The subscriber's name,
   * PIN, and address must not be blank. If the PIN format is invalid or there
   * are no more cards available, an exception is thrown.
   *
   * @param name the name of the subscriber.
   * @param pin the PIN code of the subscriber.
   * @param address the address of the subscriber.
   * @throws IllegalArgumentException if the name or address is blank or null.
   * @throws IllegalStateException if no more cards are available.
   * @throws ParseException if the PIN format is invalid.
   */
  public static void addSubscriber(String name, String pin, String address) {

    if (name == null || name.isBlank() || address == null || address.isBlank()) {
      throw new IllegalArgumentException("ERROR: Name and address cannot be blank.");
    }
    try {
      int parsedPin = parsePinCode(pin);
      Subscriber newSubscriber = new Subscriber(name, parsedPin, address);
      subscribers.add(newSubscriber);
    } catch (InstantiationException e) {
      throw new IllegalStateException("ERROR: Cannot create a new subscriber. " +
          "No more cards available.");
    } catch (ParseException e) {
      throw new IllegalArgumentException("ERROR: Invalid PIN format.");
    }

  }


  /**
   * Finds a subscriber by their unique library card barcode.
   *
   * @param cardBarCode the barcode of the subscriber's library card.
   * @return the subscriber with the specified barcode.
   * @throws NoSuchElementException if no subscriber with the given barcode is found.
   */
  public static Subscriber findSubscriber(int cardBarCode) {

    for (Subscriber subscriber : subscribers) {
      if (subscriber.getCARD_BAR_CODE() == cardBarCode) {
        return subscriber;
      }
    }
    throw new NoSuchElementException("ERROR: Barcode " +
        cardBarCode + " not found.");

  }


  /**
   * Parses a PIN code from a string.
   *
   * @param s the string representing the PIN code.
   * @return the parsed integer value of the PIN code.
   * @throws ParseException if the PIN format is invalid.
   */
  public static int parsePinCode(String s) throws ParseException {

    if (s == null) {
      throw new ParseException("ERROR: PIN cannot be null.", 0);
    }

    if (s.length() != 4) {
      throw new ParseException("ERROR: PIN must be a 4-digit number.", 0);
    }

    try {
      int pin = Integer.parseInt(s);
      if (pin < 1000 || pin > 9999) {
        throw new ParseException("ERROR: PIN must be a 4-digit " +
            "number in the range 1000 to 9999.", 0);
      }
      return pin;
    } catch (NumberFormatException e) {
      throw new ParseException("ERROR: Invalid PIN format.", 0);
    }

  }


  /**
   * Saves the current list of books to a file. Each book is written as "title:author".
   *
   * @param file the file where the books should be saved.
   */
  public static void saveBooks(File file) {

    if (!file.exists()) {
      System.out.println("Error: File Not Found");
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.err.println("Failed to create the file: " + e.getMessage());
        return;
      }
    }

    try (PrintWriter writer = new PrintWriter(file)) {
      for (Book book : books) {
        writer.println(book.getTitle().trim() + ":" + book.getAuthor().trim());
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }

  }


  /**
   * Reads a file and loads books into the library.
   *
   * @param file the file to read the book data from.
   */
  public static boolean loadBooks(File file) {

    boolean booksLoadedSuccessfully = true;

    try (Scanner scanner = new Scanner(file)) {
      int lineNumber = 1;

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
          lineNumber++;
          continue;
        }

        try {
          String[] parts = line.split(":");

          if (parts.length != 2) {
            System.err.println("Error: Invalid format at line " + lineNumber);
            booksLoadedSuccessfully = false;
          } else {
            String title = parts[0].trim();
            String author = parts[1].trim();

            if (title.isEmpty() || author.isEmpty()) {
              System.err.println("Error: Title or author is empty at line " + lineNumber);
              booksLoadedSuccessfully = false;
            } else {
              books.add(new Book(title, author));
            }
          }

        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
          System.err.println("Error: Skipping invalid line at line " + lineNumber);
          booksLoadedSuccessfully = false;
        }

        lineNumber++;
      }
    } catch (FileNotFoundException e) {
      System.err.println("Error: File not found - " + e.getMessage());
      booksLoadedSuccessfully = false;
    }

    return booksLoadedSuccessfully;

  }


}
