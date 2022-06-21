package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
		System.out.println("Inicio!");
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepostitory clientLoanRepostitory, CardRepository cardRepository){
		return (args) -> {
			LocalDateTime today = LocalDateTime.now();
			Client cliente1 = new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("morelM"));
			clientRepository.save(cliente1);
			Client cliente2 = new Client("Walter","Peralta","walter@mindhub.com",passwordEncoder.encode("peraltaW"));
			clientRepository.save(cliente2);
			Client admin = new Client("admin","admin","admin@mindhub.com", passwordEncoder.encode("12345"));
			clientRepository.save(admin);

			clientRepository.save(new Client("Juan","Perez","juanperez@mindhub.com",passwordEncoder.encode("perezJ")));

			Account cuenta1 = new Account("VIN001",5000, LocalDateTime.now(), AccountType.AHORRO, cliente1);
			accountRepository.save(cuenta1);
			Account cuenta2 = new Account("VIN002",7500, today.plusDays(1), AccountType.CORRIENTE, cliente1);
			accountRepository.save(cuenta2);
			Account cuenta3 = new Account("VIN003",3700, LocalDateTime.now(), AccountType.CORRIENTE,cliente2);
			accountRepository.save(cuenta3);
			Account cuenta4 = new Account("VIN004",6000,today.plusDays(1),AccountType.AHORRO,cliente2);
			accountRepository.save(cuenta4);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO,-2500,"Retiro de dinero",LocalDateTime.now(), cuenta1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.CREDITO,500,"Transferencia",today.plusDays(1), cuenta1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(TransactionType.CREDITO,15000,"Sueldo",LocalDateTime.now(), cuenta2);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.DEBITO,-500,"Spotify",LocalDateTime.now(), cuenta3);
			transactionRepository.save(transaction4);

			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60),20);
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24),33);
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotriz", 300000, List.of(6,12,24,36),27);
			loanRepository.save(loan3);

            ClientLoan clientLoan1 = new ClientLoan( 50000*((loan1.getPorcentaje()/100)+1), 60, cliente1, loan1);
            clientLoanRepostitory.save(clientLoan1);
            ClientLoan clientLoan2 = new ClientLoan( 50000*((loan2.getPorcentaje()/100)+1), 12, cliente1, loan2);
            clientLoanRepostitory.save(clientLoan2);

            Card card1 = new Card(CardType.DEBIT, "4839-1029-5588-9432", 617, LocalDate.of(2017,03,17), LocalDate.of(2022,04,23), CardColor.GOLD, cliente1);
            cardRepository.save(card1);
            Card card2 = new Card(CardType.CREDIT, "7483-8392-8382-2939", 117, LocalDate.now(), LocalDate.now().plusYears(5), CardColor.TITANIUM, cliente1);
            cardRepository.save(card2);
            Card card3 = new Card(CardType.DEBIT, "8483-9239-3823-3822", 438, LocalDate.now(), LocalDate.now().plusYears(5), CardColor.SILVER, cliente2);
            cardRepository.save(card3);
		};
	}



}
