package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoansDTO();
    LoanDTO getLoanDTO(Long id);
    Loan getLoan(Long id);
    void saveLoan(Loan loan);
}
