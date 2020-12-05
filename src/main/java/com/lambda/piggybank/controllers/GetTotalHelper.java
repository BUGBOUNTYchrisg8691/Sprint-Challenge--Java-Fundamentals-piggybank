package com.lambda.piggybank.controllers;

import com.lambda.piggybank.models.Coin;

import java.text.DecimalFormat;
import java.util.List;

public class GetTotalHelper
{
	public static double getTotal(List<Coin> coinList)
	{
		double total = 0;
		for (Coin coin : coinList)
		{
			total += coin.getValue() * coin.getQuantity();
		}
		return total;
	}
	
	public static StringBuilder getTotalWithStringBuilder(List<Coin> coinList)
	{
		DecimalFormat df = new DecimalFormat("$###.00");
		
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
