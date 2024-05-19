package com.academy.gusdev.pokedex.controller;

import com.academy.gusdev.pokedex.domain.Pokemon;
import com.academy.gusdev.pokedex.domain.PokemonEvent;
import com.academy.gusdev.pokedex.repository.PokemonRepository;
import com.academy.gusdev.pokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokemonRepository pokemonRepository;

    @GetMapping
    public Flux<Pokemon> getAllPokemon() {
        return pokemonService.findAllPokemons();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> getPokemonById(@PathVariable String id) {
        return pokemonService.findPokemonById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.savePokemon(pokemon);
    }

    @PutMapping("{id}")
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
