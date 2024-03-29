package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;
/*
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;*/

    @Autowired
    private PasswordEncoder passwordEcoder;



    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        //return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return clientService.getClientsDTO();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        //return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
        return clientService.getClientDTO(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,@RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEcoder.encode(password));
        Account account = new Account("VIN"+(int)((Math.random() * (99999999 - 10000000)) + 10000000),0, LocalDateTime.now(), AccountType.AHORRO, client);

        //clientRepository.save(client);
        clientService.saveClient(client);
        //accountRepository.save(new Account("VIN"+(int)((Math.random() * (99999999 - 10000000)) + 10000000),0, LocalDateTime.now(), client));
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication) {
        //return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return new ClientDTO(clientService.getClientCurrent(authentication));
    }
}
