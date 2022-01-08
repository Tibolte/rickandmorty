package fr.northborders.rickandmorty.util

import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.Origin
import java.util.concurrent.ThreadLocalRandom

class CharactersFactory {
    companion object Factory {

        fun makeDummyList(): List<Character> {
            val list = mutableListOf<Character>()
            for (i in 0..19) {
                list.add(i, makeCharacter())
            }
            return list
        }

        fun randomString(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }

        fun makeStringList(count: Int): List<String> {
            val items: MutableList<String> = mutableListOf()
            repeat(count) {
                items.add(randomString())
            }
            return items
        }

        fun makeOrigin(): Origin {
            return Origin(
                randomString(),
                randomString()
            )
        }

        fun makeCharacter(): Character {
            return Character(
                randomString(),
                randomString(),
                randomInt(),
                randomString(),
                randomString(),
                makeOrigin(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomInt(),
                randomInt(),
                randomInt(),
                randomInt(),
                randomInt()
            )
        }
    }

}