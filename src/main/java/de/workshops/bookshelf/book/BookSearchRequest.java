package de.workshops.bookshelf.book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

record BookSearchRequest (@NotBlank @Size(min = 3) String author, String isbn) {
}
