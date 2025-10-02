import java.util.*;

class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issueBook() { this.isIssued = true; }
    public void returnBook() { this.isIssued = false; }

    @Override
    public String toString() {
        return title + " by " + author + (isIssued ? " [Issued]" : " [Available]");
    }
}

class User {
    private String name;
    private int userId;
    private List<Book> borrowedBooks;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getUserId() { return userId; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) { borrowedBooks.add(book); }
    public void returnBook(Book book) { borrowedBooks.remove(book); }

    @Override
    public String toString() {
        return "User: " + name + " (ID: " + userId + ")";
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) { books.add(book); }
    public void addUser(User user) { users.add(user); }

    public void issueBook(String title, int userId) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isIssued()) {
                for (User user : users) {
                    if (user.getUserId() == userId) {
                        book.issueBook();
                        user.borrowBook(book);
                        System.out.println("✅ Book issued: " + book.getTitle() + " to " + user.getName());
                        return;
                    }
                }
            }
        }
        System.out.println("❌ Book not available or user not found!");
    }

    public void returnBook(String title, int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                for (Book book : user.getBorrowedBooks()) {
                    if (book.getTitle().equalsIgnoreCase(title)) {
                        book.returnBook();
                        user.returnBook(book);
                        System.out.println("✅ Book returned: " + book.getTitle() + " by " + user.getName());
                        return;
                    }
                }
            }
        }
        System.out.println("❌ Return failed! Book/User not found.");
    }

    public void showBooks() {
        System.out.println("\n📚 Library Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void showUsers() {
        System.out.println("\n👤 Registered Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            System.out.println("\n===== 📖 Library Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Show Books");
            System.out.println("4. Show Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author name: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(title, author));
                    System.out.println("✅ Book added successfully!");
                    break;

                case 2:
                    System.out.print("Enter user name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter user ID: ");
                    int userId = sc.nextInt();
                    library.addUser(new User(name, userId));
                    System.out.println("✅ User added successfully!");
                    break;

                case 3:
                    library.showBooks();
                    break;

                case 4:
                    library.showUsers();
                    break;

                case 5:
                    System.out.print("Enter book title to issue: ");
                    String issueTitle = sc.nextLine();
                    System.out.print("Enter user ID: ");
                    int issueUserId = sc.nextInt();
                    library.issueBook(issueTitle, issueUserId);
                    break;

                case 6:
                    System.out.print("Enter book title to return: ");
                    String returnTitle = sc.nextLine();
                    System.out.print("Enter user ID: ");
                    int returnUserId = sc.nextInt();
                    library.returnBook(returnTitle, returnUserId);
                    break;

                case 7:
                    System.out.println("👋 Exiting... Thank you!");
                    break;

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}
