package com.library.dao;

import com.library.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {
    private final JdbcTemplate jdbc;

    public BookDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM books ORDER BY id",
                (rs, rowNum) -> new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getBoolean("available")
                ));
    }

    public Book findById(int id) {
        List<Book> books = jdbc.query("SELECT * FROM books WHERE id = ?",
                (rs, rowNum) -> new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getBoolean("available")
                ), id);
        return books.isEmpty() ? null : books.get(0);
    }

    public int setAvailability(int id, boolean available) {
        return jdbc.update("UPDATE books SET available = ? WHERE id = ?", available, id);
    }

    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM books", Integer.class);
    }

    public int availableCount() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM books WHERE available = TRUE", Integer.class);
    }
}
