package ru.orlyanskiy.javarush.testtask.service;

import javafx.util.Pair;
import ru.orlyanskiy.javarush.testtask.domain.Book;

import java.util.List;

public interface BookService {

    Book get(Long id);
    List<Book> getAll(Integer page);

    Book read(Long id);
    Book save(Book book);
    void delete(Long id);

    String getSearchTitle();
    void setSearchTitle(String searchTitle);
    String getSearchAuthor();
    void setSearchAuthor(String searchAuthor);

    int getPageNumber();
    int getPageCount();
    List<Pair<Integer, String>> getPagination();
}
