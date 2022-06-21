package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.Utility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UtilsTests {
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = Utility.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cvvNumberIsCreated(){
        int cvvNumber = Utility.getCVV();
        assertThat(cvvNumber,is(not(nullValue())));
    }
}
