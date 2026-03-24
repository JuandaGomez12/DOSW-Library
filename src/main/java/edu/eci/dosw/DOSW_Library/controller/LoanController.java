package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO dto) {
        return ResponseEntity.ok(loanMapper.toDTO(loanService.createLoan(loanMapper.toModel(dto))));
    }

    @PatchMapping("/{bookId}/return")
    public ResponseEntity<LoanDTO> returnBook(@PathVariable String bookId) {
        return ResponseEntity.ok(loanMapper.toDTO(loanService.returnBook(bookId)));
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans().stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList()));
    }
}