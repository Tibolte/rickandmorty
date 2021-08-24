package fr.northborders.rickandmorty.util

import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.Origin

val originA = Origin("Saturn", "url_to_saturn")
val originB = Origin("Mars", "url_to_mars")

val characterA = Character(
    "createdA",
    "m",
    1,
    "some_url_to_image_A",
    "characterA",
    originA,
    "speciesA",
    "statusA",
    "typeA",
    "urlA",
    1,
    1,
    1,
    1
)

val characterB = Character(
    "createdB",
    "f",
    2,
    "some_url_to_image_B",
    "characterB",
    originB,
    "speciesB",
    "statusB",
    "typeB",
    "urlB",
    2,
    2,
    2,
    2
)