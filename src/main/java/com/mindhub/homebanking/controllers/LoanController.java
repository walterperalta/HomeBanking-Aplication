package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.*;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")//asocia una URL a este controlador
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    TransactionService transactionService;



    @RequestMapping("/loans")
    public List<LoanDTO> getClients(){
        return loanService.getLoansDTO();
    }

    @RequestMapping("/loans/{id}")
    public LoanDTO getAccount(@PathVariable Long id){
        return loanService.getLoanDTO(id);
    }

    @PostMapping("/loans")
    public ResponseEntity createLoan(@RequestParam String name,@RequestParam long maxAmount,@RequestParam List<Integer> payments,@RequestParam double porcentaje){
        Loan loan = new Loan(name,maxAmount,payments,porcentaje);
        loanService.saveLoan(loan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/clients/current/loans")
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Loan loan = loanService.getLoan(loanApplicationDTO.getId());
        Client client = accountService.getAccountByNumber(loanApplicationDTO.getTarget()).getIdClient();
        Account account = accountService.getAccountByNumber(loanApplicationDTO.getTarget());
        Set <Long> loans = client.getLoans().stream().map(prestamo -> prestamo.getLoan().getId()).collect(Collectors.toSet());

        //Verif. que los datos sean correctos
        if (loanApplicationDTO.getTarget() == null || loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0 ) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        //Verif. que el prestamo exista
        if (loan == null) {
            return new ResponseEntity<>("Loan does not exist", HttpStatus.FORBIDDEN);
        }

        //Verif. que el monto solicitado no exceda el monto máximo del préstamo
        if (loanApplicationDTO.getAmount() >= loan.getMaxAmount()) {
            return new ResponseEntity<>("Overlaws maximum amount", HttpStatus.FORBIDDEN);
        }

        //Verif que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Number of payments not available", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de destino exista
        if (account == null) {
            return new ResponseEntity<>("Target account does not exist", HttpStatus.FORBIDDEN);
        }

        //Verif. que la cuenta de destino pertenezca al cliente autenticado
        if (authentication.getName() != client.getEmail()) {
            return new ResponseEntity<>("Account not valid", HttpStatus.FORBIDDEN);
        }

        //Verif. si ya tiene otorgado otro prestamo del mismo tipo
        if (loans.contains(loanApplicationDTO.getId())){
            return new ResponseEntity<>("Loan already provided",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan( loanApplicationDTO.getAmount()*((loan.getPorcentaje()/100)+1), loanApplicationDTO.getPayments(), client, loan);
        clientLoanService.saveClientLoan(clientLoan);
        Transaction transaction = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(),loan.getName()+" loan approved", LocalDateTime.now(), account);
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance()+loanApplicationDTO.getAmount());

        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
