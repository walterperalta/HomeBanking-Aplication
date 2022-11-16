package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utility.*;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    TransactionService transactionService;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        //return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountService.getAccountsDTO();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        //return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        return accountService.getAccountDTO(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType type, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> accountsEnable = client.getAccounts().stream().filter(account -> account.isEnable()).collect(Collectors.toSet());

        if (accountsEnable.size() > 2){
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }
        //accountRepository.save(new Account("VIN"+(int)((Math.random() * (99999999 - 10000000)) + 10000000),0.0, LocalDateTime.now(),client));
        //accountRepository.save(new Account("VIN"+(int)((Math.random() * (99999999 - 10000000)) + 10000000),0.0, LocalDateTime.now(),client));
        accountService.saveAccount(new Account(getNumberAccount(),0.0, LocalDateTime.now(), type,client));

        /*if (type == AccountType.AHORRO){
            accountService.saveAccount(new Account(getNumberAccount(),0.0, LocalDateTime.now(), AccountType.AHORRO,client));
        } else if (type== AccountType.CORRIENTE) {
            accountService.saveAccount(new Account(getNumberAccount(),0.0, LocalDateTime.now(), AccountType.CORRIENTE,client));
        }*/
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PatchMapping("accounts/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id,@RequestParam String target){

        Account account = accountService.getAccountById(id);
        Account accountTarget = accountService.getAccountByNumber(target);

        account.setEnable(false);
        account.getTransactions().stream().forEach(transaction -> transaction.setEnable(false));
        Transaction transaction = new Transaction(TransactionType.CREDITO, account.getBalance(),"proveniente de cuenta eliminada: "+account.getNumber(), LocalDateTime.now(), accountTarget);
        accountTarget.setBalance(accountTarget.getBalance()+account.getBalance());
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(account);
        accountService.saveAccount(accountTarget);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
