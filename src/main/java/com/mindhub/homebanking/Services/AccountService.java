package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDTO();
    AccountDTO getAccountDTO(Long id);
    Account getAccountByNumber(String number);

    Account getAccountById(Long id);

    void saveAccount(Account account);
}
