package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utility;
import com.mindhub.homebanking.utils.Utility.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.Utility.*;

@RestController
@RequestMapping("api")
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;

    /*@Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;*/

    @GetMapping("/clients/current/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardService.getCardDTO(id);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication){
        Client client = clientService.getClientCurrent(authentication);
        Set <Card> tipo = client.getCards().stream().filter(tarjeta -> tarjeta.getType().equals(cardType) && tarjeta.isEnable()).collect(Collectors.toSet());
        //Set <Card> color = client.getCards().stream().filter(tarjeta -> tarjeta.getType().equals(cardColor) && tarjeta.getType().equals(cardType) && tarjeta.isEnable()).collect(Collectors.toSet());
        Set<Card> color = tipo.stream().filter(card -> card.getColor().equals(cardColor)).collect(Collectors.toSet());

        if (tipo.size() >= 3 ){
            return new ResponseEntity<>("You reach the limit of cards", HttpStatus.FORBIDDEN);
        }
        if (color.size() >=1){
            return new ResponseEntity<>("You reach the limit of cards", HttpStatus.FORBIDDEN);
        }
        String cardNumber = getCardNumber();
        int cvv = getCVV();

        Card card = new Card(cardType,cardNumber, cvv, LocalDate.now(), LocalDate.now().plusYears(5),cardColor, client);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        Card card = cardService.getCard(id);

        card.setEnable(false);

        cardService.saveCard(card);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
