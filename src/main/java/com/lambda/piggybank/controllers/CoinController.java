package com.lambda.piggybank.controllers;

import com.lambda.piggybank.models.Coin;
import com.lambda.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		sysout += "The piggy bank holds " + df.format(total);
		System.out.println(sysout);
		return new ResponseEntity<>(df.format(total), HttpStatus.OK);
	}
	
	@GetMapping(value = "/money/{amount}", produces = "application/json")
	public ResponseEntity<?> deleteMoney(@PathVariable double amount)
	{
		List<Coin> coinList = new ArrayList<>();
		coinRepository.findAll().iterator().forEachRemaining(coinList::add);
		coinList.sort((c1, c2) -> (int) (c2.getValue() * 100 - c1.getValue() * 100));
		
		int amtX100 = (int)(amount * 100);
		StringBuilder deletionOut = new StringBuilder();
		
		for (Coin coin : coinList)
		{
			if (amtX100 == 0) break;
			if (amtX100 < (int)(coin.getValue() * 100))
			{
				continue;
			}
			else if (amtX100 < (int)((coin.getValue() * 100) * coin.getQuantity()))
			{
				long cp = amtX100;
				amtX100 %= coin.getValue() * 100;
				long quant = coin.getQuantity();
				long newAmt = quant - ((cp - amtX100) / (long)(coin.getValue() * 100));
				coin.setQuantity(newAmt);
				deletionOut.append(quant - newAmt > 1 ? "\nRemoved " + (quant - newAmt) + " " + coin.getNameplural()
						: "\nRemoved " + (quant - newAmt) + " " + coin.getName());
			}
			else
			{
				amtX100 -= (int)((coin.getValue() * 100) * coin.getQuantity());
				long quant = coin.getQuantity();
				coin.setQuantity(0);
				deletionOut.append(quant > 1 ? "\nRemoved " + quant + " " + coin.getNameplural() :
						"\nRemoved " + quant + " " + coin.getName());
			}
		}
		
		System.out.println(deletionOut);
		System.out.println("The new piggy bank contents: ");
		
		StringBuilder coins = new StringBuilder();
		for (Coin coin : coinList)
		{
			if (coin.getQuantity() > 0)
			{
				coins.append(coin.getQuantity() > 1 ? coin.getQuantity() + " " + coin.getNameplural() + "\n" :
						coin.getQuantity() + " " + coin.getName() + "\n");
			}
		}
		
		System.out.println(coins);
		
		return new ResponseEntity<>(coinList, HttpStatus.OK);
	}
}
