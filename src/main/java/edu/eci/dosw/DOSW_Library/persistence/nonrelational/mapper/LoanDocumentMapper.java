package edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.LoanDocument;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.StatusEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanDocumentMapper {

    public LoanDocument toDocument(Loan loan) {
        LoanDocument doc = new LoanDocument();
        doc.setId(loan.getId());
        doc.setLoanDate(loan.getLoanDate());
        doc.setReturnDate(loan.getReturnDate());
        StatusEvent event = new StatusEvent();
        event.setStatus(loan.getStatus());
        event.setDate(loan.getLoanDate());
        doc.setStatusEvents(List.of(event));
        return doc;
    }

    public Loan toDomain(LoanDocument doc) {
        Loan loan = new Loan();
        loan.setId(doc.getId());
        loan.setLoanDate(doc.getLoanDate());
        loan.setReturnDate(doc.getReturnDate());
        if (doc.getBook() != null)
            loan.setBook(doc.getBook().getId());
        if (doc.getUser() != null)
            loan.setUser(doc.getUser().getId());
        if (doc.getStatusEvents() != null && !doc.getStatusEvents().isEmpty())
            loan.setStatus(doc.getStatusEvents().getLast().getStatus());
        return loan;
    }
}
