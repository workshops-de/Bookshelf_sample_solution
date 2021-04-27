package de.workshops.bookshelf.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource
@Transactional
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
