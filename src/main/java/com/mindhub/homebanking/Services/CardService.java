package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.models.Card;

public interface CardService {
    void saveCard(Card card);

    CardDTO getCardDTO(Long id);

    Card getCard(Long id);
}
