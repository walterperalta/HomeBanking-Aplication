package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> gerTransactionsDTO();
    TransactionDTO getTransactionDTO(Long id);
    void saveTransaction(Transaction transaction);
}
