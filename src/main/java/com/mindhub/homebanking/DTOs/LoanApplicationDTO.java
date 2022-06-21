package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class LoanApplicationDTO {

    private long id;

    //private String name;

    private double amount;

    private int payments;

    private String target;

    public  LoanApplicationDTO(){ };

    public LoanApplicationDTO( long id, double amount, int payments, String target) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.target = target;
    }
/*public LoanApplicationDTO(ClientLoan clientLoan, Account account, ){
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.target = account.getNumber();
    }*/


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public void setName(long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
