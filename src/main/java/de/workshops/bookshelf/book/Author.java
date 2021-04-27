package de.workshops.bookshelf.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {

    String name;
    @ManyToMany
    List<Book> books;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
