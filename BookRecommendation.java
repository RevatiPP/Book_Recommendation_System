import java.io.*;
import java.util.*;

public class BookRecommendation {
    private static List<Book> books = new ArrayList<>();

    // Load books from CSV file
    public static void loadBooksFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header (Title, Genre)
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                books.add(new Book(data[0], data[1])); // Use Book class without modifying it
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    // Get unique genres from books
    public static Set<String> getAllGenres() {
        Set<String> genres = new HashSet<>();
        for (Book book : books) {
            genres.add(book.getGenre());
        }
        return genres;
    }

    // Recommend books based on genre
    public static List<String> recommendBooks(String genre) {
        List<String> recommendations = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                recommendations.add(book.getTitle());
            }
        }
        return recommendations;
    }

    public static void main(String[] args) {
        // Load books from CSV
        loadBooksFromFile("books.csv");

        // Display available genres
        System.out.println("Available genres:");
        Set<String> genres = getAllGenres();
        for (String genre : genres) {
            System.out.println("- " + genre);
        }

        // Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter your favorite genre from the list above: ");
        String genre = scanner.nextLine();

        // If genre does not exist, ask again
        while (!genres.contains(genre)) {
            System.out.println("Invalid genre. Please choose from the available genres.");
            System.out.print("\nEnter your favorite genre: ");
            genre = scanner.nextLine();
        }

        // Get recommendations
        List<String> recommendedBooks = recommendBooks(genre);

        // Display recommendations
        if (recommendedBooks.isEmpty()) {
            System.out.println("No books found in this genre.");
        } else {
            System.out.println("\nRecommended Books:");
            for (String book : recommendedBooks) {
                System.out.println("- " + book);
            }
        }
    }
}
