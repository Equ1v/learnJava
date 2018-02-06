package com.equiv.java.lists.vector;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddDataIntoVector {

    private List<Person> vector = new Vector<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i< new SecureRandom().nextInt(20); i++) {
            vector.add(new Person());
        }
        initSize = vector.size();
    }

    @Test
    @DisplayName("Checking add new Person instance to the tale of Vector")
    void addNewElementIntoTheTale() {
        //initialize new instance of Person
        Person newPerson = new Person();
        //check that last element of vector is not equal to newPerson
        assertNotEquals(newPerson, vector.get(vector.size()-1));
        //add new element into the tale of vector and add method returns true;
        assertTrue(vector.add(newPerson));
        //check that size of linked list was changed
        assertNotEquals(initSize, vector.size());
        initSize = vector.size();
        //check that last element of vector is now equal to newPerson
        assertEquals(newPerson, vector.get(vector.size()-1));
        //try to add null as element into vector
        assertTrue(vector.add(null));
        //check that size of vector was changed
        assertNotEquals(initSize, vector.size());
        //check that last element of vector is equal to null
        assertNull(vector.get(vector.size()-1));
        initSize = vector.size();
        //add same instance of Person into tale of vector and expect success
        assertAll("add second newPerson!",
                () -> assertTrue(vector.add(newPerson)),
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertNotEquals(vector.indexOf(newPerson), vector.lastIndexOf(newPerson))
        );
    }

    @Test
    @DisplayName("Checking add new Person instance into the middle of the Vector")
    void addElementIntoMiddleOfVector() {
        //initialize new instance of Person
        Person newPerson = new Person();
        //add person on some index of vector
        vector.add(1, newPerson);
        //check that size of vector was changed and that element with index 1 is equal to person
        assertAll("woohoo",
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertEquals(newPerson, vector.get(1))
        );
        initSize = vector.size();
        //add null on some index of vector
        vector.add(2, null);
        //check that size of vector was changed and that element with index 2 is equal to null
        assertAll("fgadfg",
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertNull(vector.get(2))
        );
        initSize = vector.size();
        //add same instance of Person on some index in vector
        vector.add(1, newPerson);
        //check that size of vector was changed and that element with index 1 is equal to person and there are 2 same Person instance
        assertAll("sdafasfd",
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertEquals(newPerson, vector.get(1)),
                () -> assertNotEquals(vector.indexOf(newPerson), vector.lastIndexOf(newPerson))
        );
    }

    @Test
    @DisplayName("Add elements from collection to tale of Vector")
    void addElementsFromCollectionToVector() {
        //initialize collection where we will take elements to add to vector
        List<Person> personList = new LinkedList<>();
        //fill personList with elements
        for (int i=0; i< new SecureRandom().nextInt(20); i++) {
            personList.add(new Person());
        }
        //add elements from personList to tale of vector and check that all elements from personList are included in vector
        assertAll("dfdfg",
                () -> assertTrue(vector.addAll(personList)),
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertEquals(vector.size(), initSize + personList.size()),
                () -> assertTrue(vector.containsAll(personList))
        );
        initSize = vector.size();
        //initialize null collection
        List<Person> nullList = null;
        //add nullList into vector
        assertAll("dfgdfg",
                () -> assertThrows(NullPointerException.class, () -> vector.addAll(nullList)),
                () -> assertEquals(initSize, vector.size())
        );
        //initialize empty collection
        List<Person> emptyList = new ArrayList<>();
        //add emptyList into vector
        assertAll("sdf",
                () -> assertFalse(vector.addAll(emptyList)),
                () -> assertEquals(initSize, vector.size())
        );
    }

    @Test
    @DisplayName("Add elements from some Collection into Vector from some index")
    void addElementsFromCollectionIntoVectorFromSomeIndex() {
        //initialize new Collection
        List<Person> personList = new LinkedList<>();
        //fill personList with elements
        for (int i=0; i<new SecureRandom().nextInt(20); i++) {
            personList.add(new Person());
        }
        //add elements from personList into vector from some index
        assertAll("dfg",
                () -> assertTrue(vector.addAll(2, personList)),
                () -> assertNotEquals(initSize, vector.size()),
                () -> assertEquals(vector.size(), initSize + personList.size()),
                () -> assertTrue(vector.containsAll(personList))
        );
        initSize = vector.size();
        //initialize nullList which will be added into vector from some index
        List<Person> nullList = null;
        //add nullList into vector from some index
        assertAll("asda",
                () -> assertThrows(NullPointerException.class, () -> vector.addAll(1, nullList)),
                () -> assertEquals(initSize, vector.size()),
                () -> assertFalse(vector.contains(null))
        );
        //initialize empty collection
        List<Person> emptyList = new LinkedList<>();
        //add emptyList into vector from some index
        assertAll("sgg",
                () -> assertFalse(vector.addAll(1, emptyList)),
                () -> assertEquals(initSize, vector.size()),
                () -> assertTrue(vector.containsAll(emptyList))
        );
    }

    @Test
    @DisplayName("Update some element in vector")
    void updateElementsInVector() {
        //initialize new Person instance
        Person person = new Person();
        //save some element from vector
        Person indexPerson = vector.get(2);
        //set person into 2 index in vector
        assertAll("sdf",
                () -> assertEquals(indexPerson, vector.set(2, person)),
                () -> assertEquals(person, vector.get(2)),
                () -> assertEquals(initSize, vector.size())
        );
        //set element on non exist index in vector
        assertAll("fg",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> vector.set(100, person)),
                () -> assertEquals(initSize, vector.size())
        );
    }
}
