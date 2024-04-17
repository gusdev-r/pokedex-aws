package com.academy.gusdev.pokedex.service;

import com.academy.gusdev.pokedex.domain.Pokemon;
import com.academy.gusdev.pokedex.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PokemonService {

    private PokemonRepository pokemonRepository;

    public PokemonService() {
    }

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Flux<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }
    public Mono<Pokemon> getPokemonById(String id) {
        return pokemonRepository.findById(id);
    }
    public Mono<Pokemon> savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }
    public Mono<Pokemon> updatePokemon(String id, Pokemon pokemon) {
        return pokemonRepository.findById(id)
                .flatMap(pokemonFound -> {
                    pokemonFound.setName(pokemon.getName());
                    pokemonFound.setName(pokemon.getCategory());
                    pokemonFound.setName(pokemon.getSkills());
                    pokemonFound.setHealth(pokemon.getHealth());
                    return pokemonRepository.save(pokemonFound);
                });
    }
    public Mono<Void> deleteAll() {
        return pokemonRepository.deleteAll();
    }

}
