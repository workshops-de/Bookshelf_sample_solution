package de.workshops.bookshelf.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookRepository extends CrudRepository<Book, Long> {

    Book findByIsbn(String isbn);
}
