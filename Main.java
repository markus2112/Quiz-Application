import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Book {
    String title, author;
    boolean isIssued;

    Book(String title, String author) {
        this.title = title.trim();
        this.author = author.trim();
        this.isIssued = false;
    }

    public String toString() {
        return String.format("\"%s\" by %s [%s]", title, author, isIssued ? "Issued" : "Available");
    }
}

public class Main {
    JFrame loginFrame, mainFrame;
    ArrayList<Book> books = new ArrayList<>();
    final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createLoginWindow());
    }

    void createLoginWindow() {
        loginFrame = new JFrame("Library Login");
        loginFrame.setSize(350, 200);
        loginFrame.setLayout(new GridLayout(4, 2, 10, 10));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(userField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passField);
        loginFrame.add(new JLabel(""));
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if (user.equals("admin") && pass.equals("1234")) {
                loginFrame.dispose();
                loadBooks();
                createMainWindow();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }

    void createMainWindow() {
        mainFrame = new JFrame("Library Management System");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(8, 1, 5, 5));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        String[] btnLabels = {"Add Book", "Remove Book", "Search by Title", "Search by Author", "Issue Book", "Return Book", "Show All Books", "Exit & Save"};
        JButton[] buttons = new JButton[btnLabels.length];

        for (int i = 0; i < btnLabels.length; i++) {
            buttons[i] = new JButton(btnLabels[i]);
            mainFrame.add(buttons[i]);
        }

        buttons[0].addActionListener(e -> addBook());
        buttons[1].addActionListener(e -> removeBook());
        buttons[2].addActionListener(e -> searchBook("title"));
        buttons[3].addActionListener(e -> searchBook("author"));
        buttons[4].addActionListener(e -> issueBook());
        buttons[5].addActionListener(e -> returnBook());
        buttons[6].addActionListener(e -> showAllBooks());
        buttons[7].addActionListener(e -> {
            saveBooks();
            System.exit(0);
        });

        mainFrame.setVisible(true);
    }

    void addBook() {
        String title = JOptionPane.showInputDialog(mainFrame, "Enter Book Title:");
        String author = JOptionPane.showInputDialog(mainFrame, "Enter Author Name:");
        if (title == null || author == null || title.trim().isEmpty() || author.trim().isEmpty()) {
            showMsg("Both title and author are required.", "Input Error");
            return;
        }

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title.trim()) && b.author.equalsIgnoreCase(author.trim())) {
                showMsg("This book already exists!", "Duplicate Entry");
                return;
            }
        }

        books.add(new Book(title, author));
        saveBooks();
        showMsg("Book added successfully.");
    }

    void removeBook() {
        String title = JOptionPane.showInputDialog(mainFrame, "Enter Title to Remove:");
        if (title == null || title.trim().isEmpty()) {
            showMsg("Title cannot be empty.", "Input Error");
            return;
        }

        boolean found = false;
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book b = it.next();
            if (b.title.equalsIgnoreCase(title.trim())) {
                found = true;
                it.remove();
            }
        }

        if (found) {
            saveBooks();
            showMsg("Book(s) removed.");
        } else {
            showMsg("No book found with this title.", "Not Found");
        }
    }

    void searchBook(String mode) {
        String key = JOptionPane.showInputDialog(mainFrame, "Enter " + (mode.equals("title") ? "Title" : "Author") + ":");
        if (key == null || key.trim().isEmpty()) {
            showMsg(mode + " cannot be empty.", "Input Error");
            return;
        }

        StringBuilder result = new StringBuilder();
        for (Book b : books) {
            if (mode.equals("title") && b.title.equalsIgnoreCase(key.trim()) ||
                    mode.equals("author") && b.author.equalsIgnoreCase(key.trim())) {
                result.append(b).append("\n");
            }
        }

        showMsg(result.length() == 0 ? "No match found." : result.toString(), "Search Results");
    }

    void issueBook() {
        String title = JOptionPane.showInputDialog(mainFrame, "Enter Title to Issue:");
        if (title == null || title.trim().isEmpty()) {
            showMsg("Title cannot be empty.", "Input Error");
            return;
        }

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title.trim())) {
                if (!b.isIssued) {
                    b.isIssued = true;
                    saveBooks();
                    showMsg("Book issued.");
                    return;
                } else {
                    showMsg("Book is already issued.", "Already Issued");
                    return;
                }
            }
        }
        showMsg("Book not found.", "Not Found");
    }

    void returnBook() {
        String title = JOptionPane.showInputDialog(mainFrame, "Enter Title to Return:");
        if (title == null || title.trim().isEmpty()) {
            showMsg("Title cannot be empty.", "Input Error");
            return;
        }

        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title.trim())) {
                if (b.isIssued) {
                    b.isIssued = false;
                    saveBooks();
                    showMsg("Book returned.");
                    return;
                } else {
                    showMsg("Book is not issued.", "Invalid Return");
                    return;
                }
            }
        }
        showMsg("Book not found.", "Not Found");
    }

    void showAllBooks() {
        if (books.isEmpty()) {
            showMsg("Library is empty.", "No Books");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Book b : books) sb.append(b).append("\n");

        showMsg(sb.toString(), "Library Inventory");
    }

    void saveBooks() {
        try (PrintWriter pw = new PrintWriter(FILE_NAME)) {
            for (Book b : books)
                pw.println(b.title + "," + b.author + "," + b.isIssued);
        } catch (IOException e) {
            showMsg("Error saving books.", "File Error");
        }
    }

    void loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",", 3);
                if (parts.length == 3) {
                    Book b = new Book(parts[0], parts[1]);
                    b.isIssued = Boolean.parseBoolean(parts[2]);
                    books.add(b);
                }
            }
        } catch (IOException e) {
            showMsg("Error loading books.", "File Error");
        }
    }

    void showMsg(String msg) {
        showMsg(msg, "Library");
    }

    void showMsg(String msg, String title) {
        JOptionPane.showMessageDialog(mainFrame, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
