package de.workshops.bookshelf.book;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    Book findByIsbn(String isbn);
}
