package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BookController.REQUEST_URL)
@RequiredArgsConstructor
class BookController {

    static final String REQUEST_URL = "/";

    private final BookService bookService;

    @GetMapping
    String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());

        return "books";
    }

    @GetMapping("/success")
    String redirectToSuccessUrl(Model model) {
        return getAllBooks(model);
    }
}
