package de.workshops.bookshelf.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class BookSearchRequest {

    private String author;
    private String isbn;
}
