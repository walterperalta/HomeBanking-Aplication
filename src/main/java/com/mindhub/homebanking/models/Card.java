package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private CardType type;
    private String number;
    private long cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private String cardHolder;
    private CardColor color;

    private boolean enable = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() { }

    public Card(CardType type, String number, long cvv, LocalDate fromDate, LocalDate thruDate, CardColor color, Client client) {
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.color = color;
        this.client = client;
    }

    public long getId() { return id; }

    public CardType getType() { return type; }

    public void setType(CardType type) { this.type = type; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public long getCvv() { return cvv; }

    public void setCvv(int cvv) { this.cvv = cvv; }

    public LocalDate getFromDate() { return fromDate; }

    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getThruDate() { return thruDate; }

    public void setThruDate(LocalDate thruDate) { this.thruDate = thruDate; }

    public String getCardHolder() { return this.client.getFirstName()+" "+this.client.getLastName(); }

    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public CardColor getColor() { return color; }

    public void setColor(CardColor color) { this.color = color; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
