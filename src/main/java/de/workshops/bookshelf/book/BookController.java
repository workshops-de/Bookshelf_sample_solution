package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BookController.REQUEST_URL)
@RequiredArgsConstructor
public class BookController {

    static final String REQUEST_URL = "/";

    private final BookService bookService;

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());

        return "books";
    }

    @GetMapping("/success")
    public String redirectToSuccessUrl(Model model) {
        return getAllBooks(model);
    }
}
