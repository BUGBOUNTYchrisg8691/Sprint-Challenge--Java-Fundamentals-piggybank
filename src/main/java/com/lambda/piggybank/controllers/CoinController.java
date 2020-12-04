package com.lambda.piggybank.controllers;

import com.lambda.piggybank.models.Coin;
import com.lambda.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{
	@Autowired
	CoinRepository coinRepository;
	
	@GetMapping(value ="/total", produces = "application/json")
	public ResponseEntity<?> getTotal()
	{
		List<Coin> coinList = new ArrayList<>();
		coinRepository.findAll().iterator().forEachRemaining(coinList::add);
		
		DecimalFormat df = new DecimalFormat("$###.00");
		
		double total = 0;
		String sysout = "";
		for (Coin coin : coinList)
		{
			if (coin.getQuantity() > 1)
			{
				sysout += coin.getQuantity() + " " + coin.getNameplural() + "\n";
			}
			else
			{
				sysout += coin.getQuantity() + " " + coin.getName() + "\n";
			}
			total += coin.getValue() * coin.getQuantity();
		}
		
		sysout += "The piggy bank hold " + df.format(total);
		System.out.println(sysout);
		return new ResponseEntity<>(df.format(total), HttpStatus.OK);
	}
}
