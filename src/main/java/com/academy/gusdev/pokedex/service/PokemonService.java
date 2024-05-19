package com.academy.gusdev.pokedex.service;

import com.academy.gusdev.pokedex.domain.Pokemon;
import com.academy.gusdev.pokedex.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public Flux<Pokemon> findAllPokemons() {
        return pokemonRepository.findAll();
    }
    public Mono<Pokemon> findPokemonById(String id) {
        return pokemonRepository.findById(id);
    }
    public Mono<Pokemon> savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }
    public Mono<Pokemon> updatePokemon(String id, Pokemon pokemon) {
        return pokemonRepository.findById(id)
                .flatMap(pokemonFound -> {
                    pokemonFound.setName(pokemon.getName());
                    pokemonFound.setCategory(pokemon.getCategory());
                    pokemonFound.setSkills(pokemon.getSkills());
                    pokemonFound.setHealth(pokemon.getHealth());
                    return pokemonRepository.save(pokemonFound);
                });
    }
    public Mono<Void> deleteAll() {
        return pokemonRepository.deleteAll();
    }

}
