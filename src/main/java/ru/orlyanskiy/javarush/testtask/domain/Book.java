package ru.orlyanskiy.javarush.testtask.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name="title", length=100, nullable=false)
    private String title;

    @Size(max = 255)
    @Column(name="description", length=255)
    private String description;

    @NotBlank
    @Size(max = 100)
    @Column(name="author", length=100, nullable=false)
    private String author;

    @Size(max = 20)
    @Column(name="isbn", length=20)
    private String isbn;

    @Min(1000)
    @Max(3000)
    @Column(name="print_year")
    private Integer printYear;

    @Column(name="read_already", nullable=false)
    @ColumnDefault("0")
    private Boolean readAlready;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPrintYear() {
        return printYear;
    }

    public void setPrintYear(Integer printYear) {
        this.printYear = printYear;
    }

    public Boolean getReadAlready() {
        return readAlready;
    }

    public void setReadAlready(Boolean readAlready) {
        this.readAlready = readAlready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        return printYear != null ? printYear.equals(book.printYear) : book.printYear == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (printYear != null ? printYear.hashCode() : 0);
        return result;
    }
}