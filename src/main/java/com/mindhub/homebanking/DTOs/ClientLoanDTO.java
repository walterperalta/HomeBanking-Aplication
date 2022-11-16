package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private long id;

    private long loanId;

    private String name;

    private double amount;

    private int payments;

    private double porcentaje;

    public ClientLoanDTO (ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.porcentaje = clientLoan.getLoan().getPorcentaje();
    }

    public long getId() {
        return id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loan) {
        this.loanId = loan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
