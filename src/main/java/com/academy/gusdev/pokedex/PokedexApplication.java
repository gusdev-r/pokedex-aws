package com.academy.gusdev.pokedex;

import com.academy.gusdev.pokedex.domain.Pokemon;
import com.academy.gusdev.pokedex.repository.PokemonRepository;
import com.academy.gusdev.pokedex.service.PokemonService;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Bean
	CommandLineRunner init (ReactiveMongoOperations operations, PokemonService service) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
							new Pokemon(null, "Bulbassauro", "Semente", "OverGrow", 6.09),
							new Pokemon(null, "Charizard", "Fogo", "Blaze", 90.05),
							new Pokemon(null, "Caterpie", "Minhoca", "Poeira do Escudo", 2.09),
							new Pokemon(null, "Blastoise", "Marisco", "Torrente", 6.09))
					.flatMap(service::savePokemon);

			pokemonFlux
					.thenMany(service.findAllPokemons())
					.subscribe(System.out::println);
		};
	}
}
