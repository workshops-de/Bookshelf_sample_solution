package de.workshops.bookshelf.book;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
interface BookRepository extends CrudRepository<Book, Long> {
=======
public interface BookRepository extends ListCrudRepository<Book, Long> {
>>>>>>> Enable_Spring_Boot_Actuator

    Book findByIsbn(String isbn);
}
