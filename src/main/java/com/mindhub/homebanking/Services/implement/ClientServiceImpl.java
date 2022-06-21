package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientLoanRepostitory;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client getClientCurrent(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


    ;


}
