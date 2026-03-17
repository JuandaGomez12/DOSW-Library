package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanValidator {

    public void validate(Loan loan) {
        if (loan.getBook() == null || loan.getBook().isBlank())
            throw new IllegalArgumentException("El ID del libro en el préstamo no puede estar vacío.");
        if (loan.getUser() == null || loan.getUser().isBlank())
            throw new IllegalArgumentException("El ID del usuario en el préstamo no puede estar vacío.");
        if (loan.getLoanDate() == null || loan.getLoanDate().isBlank())
            throw new IllegalArgumentException("La fecha del préstamo no puede estar vacía.");
    }
}