package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();
    Client getClientCurrent(Authentication authentication);
    ClientDTO getClientDTO(Long id);
    void saveClient(Client client);

    Client getClientByEmail(String email);

}
