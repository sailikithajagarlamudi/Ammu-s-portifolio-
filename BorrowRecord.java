package com.library.model;

import java.time.LocalDate;

public class BorrowRecord {
    private int id;
    private int memberId;
    private int bookId;
    private String memberName;
    private String bookTitle;
    private LocalDate withdrawDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private int creditsEarned;

    public BorrowRecord() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public LocalDate getWithdrawDate() { return withdrawDate; }
    public void setWithdrawDate(LocalDate withdrawDate) { this.withdrawDate = withdrawDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCreditsEarned() { return creditsEarned; }
    public void setCreditsEarned(int creditsEarned) { this.creditsEarned = creditsEarned; }
}
