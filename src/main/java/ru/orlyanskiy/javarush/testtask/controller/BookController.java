package ru.orlyanskiy.javarush.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.orlyanskiy.javarush.testtask.domain.Book;
import ru.orlyanskiy.javarush.testtask.service.BookService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping({"/books", "/", ""})
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping({"/list", "/", ""})
    public String getAllBooks(@PathParam("page") Integer page, Model model) {
        model.addAttribute("books", bookService.getAll(page));

        model.addAttribute("searchTitle", bookService.getSearchTitle());
        model.addAttribute("searchAuthor", bookService.getSearchAuthor());

        model.addAttribute("pageNumber", bookService.getPageNumber());
        model.addAttribute("pageCount", bookService.getPageCount());

        model.addAttribute("pagination", bookService.getPagination());

        return "books";
    }

    @PostMapping({"/list", "/", ""})
    public String getAllBooksWithFilter(
            @RequestParam String searchTitle,
            @RequestParam String searchAuthor) {

        bookService.setSearchTitle(searchTitle);
        bookService.setSearchAuthor(searchAuthor);

        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "book";
    }

    @PostMapping("/{id}/read")
    public String onSubmitReadForm(@RequestParam Long id) {
        Book book = bookService.read(id);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book()); //TODO: null object
        return "book-form";
    }

    @PostMapping("/create")
    public String onSubmitCreateForm(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors())
            return "book-form";

        book = bookService.save(book);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "book-form";
    }

    @PostMapping("/{id}/update")
    public String onSubmitUpdateForm(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors())
            return "book-form";

        book = bookService.save(book);
        return "redirect:/books/" + book.getId();
    }

    @PostMapping("/{id}/delete")
    public String onSubmitDeleteForm(@RequestParam Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}