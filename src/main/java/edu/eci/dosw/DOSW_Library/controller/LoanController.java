package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    public LoanController(LoanService loanService, LoanMapper loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO dto) {
        return ResponseEntity.ok(loanMapper.toDTO(loanService.createLoan(loanMapper.toModel(dto))));
    }

    @PatchMapping("/{bookId}/return")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public ResponseEntity<LoanDTO> returnBook(@PathVariable String bookId) {
        return ResponseEntity.ok(loanMapper.toDTO(loanService.returnBook(bookId)));
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans().stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my-loans")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public ResponseEntity<List<LoanDTO>> getMyLoans(@RequestParam String userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId).stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList()));
    }
}