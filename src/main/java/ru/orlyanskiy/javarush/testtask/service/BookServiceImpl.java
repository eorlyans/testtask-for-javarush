package ru.orlyanskiy.javarush.testtask.service;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.orlyanskiy.javarush.testtask.domain.Book;
import ru.orlyanskiy.javarush.testtask.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    private String searchTitle = "";
    private String searchAuthor = "";
    private int pageNumber = 1;
    private int pageCount = 1;

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getAll(Integer pageNumber) {

        if (pageNumber != null)
            this.pageNumber = pageNumber > 0 ? pageNumber : 1;

        PageRequest pageRequest = PageRequest.of(this.pageNumber - 1, 10);

        Page<Book> page;
        if (!searchTitle.isEmpty() && !searchAuthor.isEmpty())
            page = bookRepository.findAllByTitleContainsAndAuthorContainsOrderByTitleAsc(searchTitle, searchAuthor, pageRequest);
        else if (!searchTitle.isEmpty())
            page = bookRepository.findAllByTitleContainsOrderByTitleAsc(searchTitle, pageRequest);
        else if (!searchAuthor.isEmpty())
            page = bookRepository.findAllByAuthorContainsOrderByTitleAsc(searchAuthor, pageRequest);
        else
            page = bookRepository.findAllByOrderByTitleAsc(pageRequest);

        this.pageCount = page.getTotalPages();
        return page.getContent();
    }

    @Override
    public Book save(Book bookWithNewData) {

        // create
        if (bookWithNewData.getId() == null) {
            bookWithNewData.setReadAlready(false);
            return bookRepository.save(bookWithNewData);
        }

        // update
        Book book = bookRepository.findById(bookWithNewData.getId()).get();

        if (!book.equals(bookWithNewData)) {
            book.setTitle(bookWithNewData.getTitle());
            book.setDescription(bookWithNewData.getDescription());
            book.setIsbn(bookWithNewData.getIsbn());
            book.setPrintYear(bookWithNewData.getPrintYear());

            //book.setAuthor(bookWithNewData.getAuthor()); // Поле author должно быть неизменяемым с момента создания книги.
            book.setReadAlready(false);                    // А поле readAlready нужно выставить в false.
        }

        return bookRepository.save(book);
    }

    @Override
    public Book read(Long id) {
        Book book = bookRepository.findById(id).get();
        book.setReadAlready(true);
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
        this.pageNumber = 1;
    }

    @Override
    public String getSearchTitle() {
        return searchTitle;
    }

    @Override
    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
        this.pageNumber = 1;
    }

    @Override
    public String getSearchAuthor() {
        return searchAuthor;
    }

    @Override
    public void setSearchAuthor(String searchAuthor) {
        this.searchAuthor = searchAuthor;
        this.pageNumber = 1;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<Pair<Integer, String>> getPagination() {
        List<Pair<Integer, String>> list = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            list.add(new Pair<>(i+1, "/books?page=" + (i+1)));
        }
        return list;
    }
}
