package ru.orlyanskiy.javarush.testtask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.orlyanskiy.javarush.testtask.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);

    Page<Book> findAllByTitleContainsOrderByTitleAsc(String title, Pageable pageable);
    Page<Book> findAllByAuthorContainsOrderByTitleAsc(String title, Pageable pageable);
    Page<Book> findAllByTitleContainsAndAuthorContainsOrderByTitleAsc(String title, String author, Pageable pageable);

}