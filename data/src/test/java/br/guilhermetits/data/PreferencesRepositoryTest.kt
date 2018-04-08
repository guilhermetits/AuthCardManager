package br.guilhermetits.data

import android.content.Context
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PreferencesRepositoryTest {
    private lateinit var sut: PreferencesRepository

    @Before()
    fun setup() {
        val sharedPreferences = RuntimeEnvironment.application.getSharedPreferences(
                "teste", Context.MODE_PRIVATE)
        val gson = GsonBuilder().create()
        sut = PreferencesRepository(sharedPreferences, gson)
    }

    @Test
    fun preferencesRepository_saveobject_success() {
        val person = Person(10, "Joazinho",
                Address("Rua de ladrilhos de brilhantes", "Infancia", 777))

        sut.putValue("pessoa", person)
        val recoveredPerson = sut.getValue<Person>("pessoa")

        assertEquals(person, recoveredPerson)
    }

    data class Person(val age: Int, val name: String, val subclass: Address)
    data class Address(val street: String, val city: String, val number: Int)
}