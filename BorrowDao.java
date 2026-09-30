package com.library.dao;

import com.library.model.BorrowRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class BorrowDao {
    private final JdbcTemplate jdbc;

    public BorrowDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int create(int memberId, int bookId, LocalDate withdrawDate, LocalDate dueDate) {
        return jdbc.update(
                "INSERT INTO borrow_records(member_id, book_id, withdraw_date, due_date) VALUES (?, ?, ?, ?)",
                memberId, bookId, Date.valueOf(withdrawDate), Date.valueOf(dueDate)
        );
    }

    public List<BorrowRecord> findActive() {
        return findByStatus("BORROWED");
    }

    public List<BorrowRecord> findAll() {
        return jdbc.query("""
                SELECT br.*, m.name member_name, b.title book_title
                FROM borrow_records br
                JOIN members m ON m.id = br.member_id
                JOIN books b ON b.id = br.book_id
                ORDER BY br.id DESC
                """, this::map);
    }

    public BorrowRecord findById(int id) {
        List<BorrowRecord> rows = jdbc.query("""
                SELECT br.*, m.name member_name, b.title book_title
                FROM borrow_records br
                JOIN members m ON m.id = br.member_id
                JOIN books b ON b.id = br.book_id
                WHERE br.id = ?
                """, this::map, id);
        return rows.isEmpty() ? null : rows.get(0);
    }

    public int completeReturn(int id, LocalDate returnDate, String status, int credits) {
        return jdbc.update("""
                UPDATE borrow_records
                SET return_date = ?, status = ?, credits_earned = ?
                WHERE id = ?
                """, Date.valueOf(returnDate), status, credits, id);
    }

    private List<BorrowRecord> findByStatus(String status) {
        return jdbc.query("""
                SELECT br.*, m.name member_name, b.title book_title
                FROM borrow_records br
                JOIN members m ON m.id = br.member_id
                JOIN books b ON b.id = br.book_id
                WHERE br.status = ?
                ORDER BY br.id DESC
                """, this::map, status);
    }

    private BorrowRecord map(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        BorrowRecord r = new BorrowRecord();
        r.setId(rs.getInt("id"));
        r.setMemberId(rs.getInt("member_id"));
        r.setBookId(rs.getInt("book_id"));
        r.setMemberName(rs.getString("member_name"));
        r.setBookTitle(rs.getString("book_title"));
        r.setWithdrawDate(rs.getDate("withdraw_date").toLocalDate());
        r.setDueDate(rs.getDate("due_date").toLocalDate());
        Date returnDate = rs.getDate("return_date");
        if (returnDate != null) r.setReturnDate(returnDate.toLocalDate());
        r.setStatus(rs.getString("status"));
        r.setCreditsEarned(rs.getInt("credits_earned"));
        return r;
    }
}
