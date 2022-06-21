package com.mindhub.homebanking.Services.implement;

import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    ClientLoanRepostitory clientLoanRepostitory;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepostitory.save(clientLoan);
    }
}
