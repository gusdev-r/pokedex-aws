package com.academy.gusdev.pokedex.controller;

import com.academy.gusdev.pokedex.domain.Pokemon;
import com.academy.gusdev.pokedex.domain.PokemonEvent;
import com.academy.gusdev.pokedex.repository.PokemonRepository;
import com.academy.gusdev.pokedex.service.PokemonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class PokemonController {

    private PokemonService pokemonService;
    private PokemonRepository pokemonRepository;

    public PokemonController() {
    }

    public PokemonController(PokemonService pokemonService, PokemonRepository pokemonRepository) {
        this.pokemonService = pokemonService;
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping
    public Flux<Pokemon> getAllPokemon() {
        return pokemonService.getAllPokemon();
    }

    @GetMapping
    public Mono<ResponseEntity<Pokemon>> getPokemonById(@PathVariable String id) {
        return pokemonService.getPokemonById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.savePokemon(pokemon);
    }

    @PutMapping
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable (value = "id") String id,
                                                       @RequestBody Pokemon pokemon) {
        return pokemonService.updatePokemon(id, pokemon)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public Mono<ResponseEntity<Void>> deletePokemon(@PathVariable (value = "id") String id) {
        return pokemonRepository.findById(id)
                .flatMap(pokemonFound ->
                    pokemonRepository.delete(pokemonFound)
                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                    .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("delete/all")
    public Mono<Void> deleteAllPokemon() {
        return pokemonService.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> getPokemonEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(val ->
                        new PokemonEvent(val, "Product Event")
                );
    }
}
