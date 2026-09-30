package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.BorrowDao;
import com.library.dao.MemberDao;
import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class LibraryService {
    private final BookDao bookDao;
    private final MemberDao memberDao;
    private final BorrowDao borrowDao;

    public LibraryService(BookDao bookDao, MemberDao memberDao, BorrowDao borrowDao) {
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.borrowDao = borrowDao;
    }

    public List<Book> books() { return bookDao.findAll(); }
    public List<Member> members() { return memberDao.findAll(); }
    public List<BorrowRecord> activeBorrowings() { return borrowDao.findActive(); }
    public List<BorrowRecord> allBorrowings() { return borrowDao.findAll(); }

    public Map<String, Integer> dashboard() {
        return Map.of(
                "totalBooks", bookDao.count(),
                "availableBooks", bookDao.availableCount(),
                "members", memberDao.count(),
                "activeBorrowings", activeBorrowings().size()
        );
    }

    @Transactional
    public BorrowRecord borrow(int memberId, int bookId) {
        Member member = memberDao.findById(memberId);
        if (member == null) throw new IllegalArgumentException("Member not found.");

        Book book = bookDao.findById(bookId);
        if (book == null) throw new IllegalArgumentException("Book not found.");
        if (!book.isAvailable()) throw new IllegalArgumentException("Book is already withdrawn.");

        LocalDate withdrawDate = LocalDate.now();
        LocalDate dueDate = withdrawDate.plusDays(7);

        borrowDao.create(memberId, bookId, withdrawDate, dueDate);
        bookDao.setAvailability(bookId, false);

        List<BorrowRecord> rows = borrowDao.findAll();
        return rows.get(0);
    }

    @Transactional
    public BorrowRecord returnBook(int borrowId) {
        BorrowRecord record = borrowDao.findById(borrowId);
        if (record == null) throw new IllegalArgumentException("Borrow record not found.");
        if (!"BORROWED".equals(record.getStatus())) {
            throw new IllegalArgumentException("This book has already been returned.");
        }

        LocalDate returnDate = LocalDate.now();
        int credits;
        String status;

        if (!returnDate.isAfter(record.getDueDate())) {
            credits = 10;
            status = "RETURNED_ON_TIME";
        } else {
            credits = 0;
            status = "RETURNED_LATE";
        }

        borrowDao.completeReturn(borrowId, returnDate, status, credits);
        bookDao.setAvailability(record.getBookId(), true);
        memberDao.addCredits(record.getMemberId(), credits);

        return borrowDao.findById(borrowId);
    }
}
