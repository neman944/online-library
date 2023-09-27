package az.library.bookservice;

import az.library.bookservice.model.Book;
import az.library.bookservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableEurekaClient
public class BookServiceApplication implements CommandLineRunner {

	private final BookRepository repository;

	public BookServiceApplication(BookRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Book book1 = new Book("Karamazov qardashlar", 1899, "F.Dostoyevski", "Qanun", "1");
		Book book2 = new Book("1984", 1949, "George Orwell", "Qanun", "2");
		Book book3 = new Book("Muxtelif predmetler haqqinda dusunceler ", 1897, "Arthur Schopenhauer", "Qanun", "3");

		List<Book> bookList = repository.saveAll(Arrays.asList(book1, book2, book3));

		System.out.println(bookList);
	}
}
