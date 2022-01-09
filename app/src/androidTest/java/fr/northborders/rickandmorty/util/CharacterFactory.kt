package fr.northborders.rickandmorty.util

import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.Origin

val originA = Origin("Saturn", "https://rickandmortyapi.com/api/location/1")
val originB = Origin("Mars", "https://rickandmortyapi.com/api/location/1")

val characterA = Character(
    "2017-11-04T18:48:46.250Z",
    "Male",
    1,
    "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    "Rick Sanchez",
    originA,
    "Human",
    "Alive",
    "",
    "https://rickandmortyapi.com/api/character/1",
    1,
    1,
    1,
    1,
    1
)

val characterB = Character(
    "2017-11-04T18:48:46.250Z",
    "Male",
    2,
    "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    "Morty Smith",
    originB,
    "Human",
    "Alive",
    "https://rickandmortyapi.com/api/character/2",
    "urlB",
    2,
    2,
    2,
    2,
    1
)