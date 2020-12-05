package com.lambda.piggybank.controllers;

import com.lambda.piggybank.models.Coin;

import java.text.DecimalFormat;
import java.util.List;

public class GetTotalHelper
{
	DecimalFormat df = new DecimalFormat("$###.00");
	
	public double getTotal(List<Coin> coinList)
	{
		double total = 0;
		for (Coin coin : coinList)
		{
			total += coin.getValue() * coin.getQuantity();
		}
		return total;
	}
	
	public StringBuilder getTotalWithStringBuilder(List<Coin> coinList)
	{
		double total = 0;
		StringBuilder sysout = new StringBuilder();
		
		for (Coin coin : coinList)
		{
			if (coin.getQuantity() > 1)
			{
				sysout.append(coin.getQuantity() + " " + coin.getNameplural() + "\n");
			}
			else
			{
				sysout.append(coin.getQuantity() + " " + coin.getName() + "\n");
			}
			total += coin.getValue() * coin.getQuantity();
		}
		
		sysout.append("The piggy bank holds " + df.format(total) + "\n");
		return sysout;
	}
	
}
