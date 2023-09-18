package com.example.grapql;

import graphql.Mutable;
import jakarta.annotation.PostConstruct;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PetController {

    Map<String, Pet> petOfIds = new HashMap<>();

    @PostConstruct
    public void init() {
        petOfIds.put("1", new Pet("1", "Mic", "black", "1"));
        petOfIds.put("2", new Pet("2", "Black", "black", null));
        petOfIds.put("3", new Pet("3", "Milu", "back-white", "1"));
    }

    @QueryMapping
    public Flux<Pet> pets() {
        return Flux.fromIterable(petOfIds.values());
    }

    @QueryMapping
    public Mono<Pet> findPet(@Argument String id) {
        Pet pet = petOfIds.get(id);
        if (pet == null) {
            return Mono.empty();
        }
        return Mono.just(pet);
    }

//    @SchemaMapping
//    public Mono<Person> owner(Pet pet, @Argument(name = "default") Boolean getDefault) {
//        if (pet.ownerID() == null) {
//            if (getDefault == true) {
//                return Mono.just(
//                        new Person("0", "Default", 0)
//                );
//            } else {
//                return Mono.empty();
//            }
//        }
//
//        return Mono.just(
//                new Person(pet.ownerID(), "Owner of " + pet.name(), 30)
//        );
//    }

    @BatchMapping(maxBatchSize = 2)
    public Mono<Map<Pet, Person>> owner(List<Pet> pets) {
        System.out.println("get owner by Batch");
        Map<Pet, Person> personByPet = new HashMap<>();
        pets.forEach(pet -> {
            personByPet.put(pet, new Person(pet.ownerID(), "owner of " + pet.name(), 30));
        });
        return Mono.just(personByPet);
    }

    @SchemaMapping
    public Mono<Person> ownerWithException(Pet pet) {
        if (pet.ownerID() == null) {
            throw new ObjectNotFoundException();
        }

        return Mono.just(
                new Person(pet.ownerID(), "Owner of " + pet.name(), 30)
        );
    }

    @MutationMapping
    public Mono<Pet> updatePetName(@Argument String id, @Argument String name) {
        Pet pet = petOfIds.get(id);
        if (pet == null) {
            return Mono.empty();
        }

        Pet newPet = new Pet(pet.id(), name, pet.color(), pet.ownerID());
        petOfIds.put(id, newPet);
        return Mono.just(newPet);
    }
}
