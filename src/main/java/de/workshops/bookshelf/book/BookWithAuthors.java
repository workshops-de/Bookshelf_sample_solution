package de.workshops.bookshelf.book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "bookWithAuthors", types = { Book.class })
public interface BookWithAuthors {

    @Value("#{target.id}")
    Long getId();

    String getTitle();

    String getDescription();

    String getIsbn();

    @Value("#{target.getAuthors()}")
    Set<Author> getAuthors();
}
