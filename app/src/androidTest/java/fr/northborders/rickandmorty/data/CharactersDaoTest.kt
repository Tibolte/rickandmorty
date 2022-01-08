package fr.northborders.rickandmorty.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.northborders.rickandmorty.data.local.CharactersDao
import fr.northborders.rickandmorty.util.characterA
import fr.northborders.rickandmorty.util.characterB
import fr.northborders.rickandmorty.util.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersDaoTest: DbTest() {
    private lateinit var charactersDao: CharactersDao

    private val chA = characterA.copy()
    private val chB = characterB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        charactersDao = db.charactersDao()

        runBlocking {
            charactersDao.insertAll(listOf(chA, chB))
        }
    }

    @Test
    fun testGetCharactersList() {
        val list = getValue(charactersDao.getCharacters())

        assertThat(list.size, Matchers.equalTo(2))
    }

    @Test
    fun testGetCharacter() {
        assertThat(getValue(charactersDao.getCharacter(chA.id)), Matchers.equalTo(chA))
    }
}