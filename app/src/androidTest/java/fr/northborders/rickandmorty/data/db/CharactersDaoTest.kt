package fr.northborders.rickandmorty.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import fr.northborders.rickandmorty.data.local.CharactersDao
import fr.northborders.rickandmorty.util.characterA
import fr.northborders.rickandmorty.util.characterB
import fr.northborders.rickandmorty.util.getValue
import kotlinx.coroutines.runBlocking
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

        Truth.assertThat(list.size).isEqualTo(2)
    }

    @Test
    fun testGetCharacter() {
        Truth.assertThat(getValue(charactersDao.getCharacter(chA.id))).isEqualTo(chA)
    }
}