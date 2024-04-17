package com.academy.gusdev.pokedex.repository;

import com.academy.gusdev.pokedex.domain.Pokemon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends ReactiveMongoRepository<Pokemon, String> {
}
