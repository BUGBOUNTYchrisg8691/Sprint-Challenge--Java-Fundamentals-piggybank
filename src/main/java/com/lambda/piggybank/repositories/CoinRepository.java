package com.lambda.piggybank.repositories;

import com.lambda.piggybank.models.Coin;
import org.springframework.data.repository.CrudRepository;

public interface CoinRepository extends CrudRepository<Coin, Long>
{
}
