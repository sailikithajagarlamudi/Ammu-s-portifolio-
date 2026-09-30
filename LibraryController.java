package com.library.controller;

import com.library.model.BorrowRecord;
import com.library.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LibraryController {
    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public Object books() {
        return service.books();
    }

    @GetMapping("/members")
    public Object members() {
        return service.members();
    }

    @GetMapping("/borrowings")
    public Object borrowings() {
        return service.allBorrowings();
    }

    @GetMapping("/borrowings/active")
    public Object activeBorrowings() {
        return service.activeBorrowings();
    }

    @GetMapping("/dashboard")
    public Object dashboard() {
        return service.dashboard();
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestBody Map<String, Integer> request) {
        try {
            BorrowRecord result = service.borrow(
                    request.get("memberId"),
                    request.get("bookId")
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/return/{borrowId}")
    public ResponseEntity<?> returnBook(@PathVariable int borrowId) {
        try {
            return ResponseEntity.ok(service.returnBook(borrowId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
