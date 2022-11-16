package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")//asocia una URL a este controlador
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.gerTransactionsDTO();
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionDTO(id);
    }

    @Transactional
    @PostMapping("clients/current/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam Double amount,
                                           @RequestParam String description,
                                           @RequestParam String origin,
                                           @RequestParam String target,
                                           Authentication authentication) {
        Account accountOrigin = accountService.getAccountByNumber(origin);
        Account accountTarget = accountService.getAccountByNumber(target);
        Client client = accountOrigin.getIdClient();

        //Verifico que ningun campo este vacio
        if (amount.isNaN() || description.isEmpty() || origin.isEmpty() || target.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        //Verif. que la cuenta de origen exista
        if (accountOrigin == null){
            return new ResponseEntity<>("Account no exist", HttpStatus.FORBIDDEN);
        }

        //Verifico que la cuenta de origen sea distinta a la de destino
        if (accountOrigin.getNumber() == accountTarget.getNumber()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        //Verif. que la cuenta de origen pertenezca al cliente auntenticado
        if (authentication.getName() != client.getEmail()){
            return new ResponseEntity<>("La cuenta de origen no pertenece", HttpStatus.FORBIDDEN);
        }

        //Verif. que la cuenta destino exista
        if (accountTarget == null){
            return new ResponseEntity<>("Account no exist", HttpStatus.FORBIDDEN);
        }

        //Verif. que tenga fondos suficientes para hacer la transaccion
        if (accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("Saldo insuficiente", HttpStatus.FORBIDDEN);
        }

        if(amount < 100){
            return new ResponseEntity<>("Saldo insuficiente", HttpStatus.FORBIDDEN);

        }

        Transaction transactionDebit = new Transaction(TransactionType.DEBITO, -amount,description, LocalDateTime.now(), accountOrigin);
        transactionService.saveTransaction(transactionDebit);

        Transaction transactionCredit = new Transaction(TransactionType.CREDITO, amount,description, LocalDateTime.now(), accountTarget);
        transactionService.saveTransaction(transactionCredit);

        accountOrigin.setBalance(accountOrigin.getBalance()-amount);
        accountTarget.setBalance(accountTarget.getBalance()+amount);

        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountTarget);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
