package com.equiv.java.lists.linked;


import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@DisplayName("Test suite for checking adding elements in LinkedList")
class AddDataIntoLinkedList {

    private List<Person> linkedList = new LinkedList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < 10; i++) {
            linkedList.add(new Person());
        }
        this.initSize = linkedList.size();
    }

    @Test
    @DisplayName("Checking add new Person instance to the tale of linkedList")
    void addNewElementIntoTheTale() {
        //initialize new instance of Person
        Person person = new Person();
        //check that last element of linkedList is not equal to person
        assertNotEquals(person, linkedList.get(linkedList.size()-1));
        //add new element into the tale of linkedList and add method returns true;
        assertTrue(linkedList.add(person));
        //check that size of linked list was changed
        assertNotEquals(initSize, linkedList.size());
        initSize = linkedList.size();
        //check that last element of linkedList is now equal to person
        assertEquals(person, linkedList.get(linkedList.size()-1));
        //try to add null as element into linkedList
        assertTrue(linkedList.add(null));
        //check that size of arrayList was changed
        assertNotEquals(initSize, linkedList.size());
//        System.out.println(linkedList);
        //check that last element of linkedList is equal to null
        assertNull(linkedList.get(linkedList.size()-1));
        initSize = linkedList.size();
        //add same instance of Person into tale of linkedList and expect success
        assertAll("add second person!",
                () -> assertTrue(linkedList.add(person)),
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertNotEquals(linkedList.indexOf(person), linkedList.lastIndexOf(person))
        );
    }

    @Test
    @DisplayName("Checking add new Person instance into the middle of the linkedList")
    void addElementIntoMiddleOfLinkedList() {
        //initialize new instance of Person
        Person person = new Person();
        //add person on some index of linkedList
        linkedList.add(1, person);
        //check that size of linkedList was changed and that element with index 1 is equal to person
        assertAll("woohoo",
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(person, linkedList.get(1))
        );
        initSize = linkedList.size();
        //add null on some index of linkedList
        linkedList.add(2, null);
        //check that size of linkedList was changed and that element with index 2 is equal to null
        assertAll("fgdfg",
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertNull(linkedList.get(2))
        );
        initSize = linkedList.size();
        //add same instance of Person on some index in linkedList
        linkedList.add(1, person);
        //check that size of linkedList was changed and that element with index 1 is equal to person and there are 2 same Person instance
        assertAll("sdafasfd",
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(person, linkedList.get(1)),
                () -> assertNotEquals(linkedList.indexOf(person), linkedList.lastIndexOf(person))
        );
    }

    @Test
    @DisplayName("Add elements from collection to tale of LinkedList")
    void addElementsFromCollectionToLinkedList() {
        //initialize collection where we will take elements to add to linkedList
        List<Person> personList = new ArrayList<>();
        //fill personList with elements
        for (int i=0; i< 5; i++) {
            personList.add(new Person());
        }
        //add elements from personList to tale of linkedList and check that all elements from personList are included in linkedList
        assertAll("dfdfg",
                () -> assertTrue(linkedList.addAll(personList)),
//                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(linkedList.size(), initSize + personList.size()),
                () -> assertTrue(linkedList.containsAll(personList))
        );
        initSize = linkedList.size();
        //initialize null collection
        List<Person> nullList = null;
        //add nullList into linkedList
        assertAll("dfgdfg",
                () -> assertThrows(NullPointerException.class, () -> linkedList.addAll(nullList)),
                () -> assertEquals(initSize, linkedList.size())
        );
        //initialize empty collection
        List<Person> emptyList = new ArrayList<>();
        //add emptyList into linkedList
        assertAll("sdf",
                () -> assertFalse(linkedList.addAll(emptyList)),
                () -> assertEquals(initSize, linkedList.size())
        );
    }

    @Test
    @DisplayName("Add elements from some Collection into LinkedList from some index")
    void addElementsFromCollectionIntoLinkedListFromSomeIndex() {
        //initialize new Collection
        List<Person> personList = new ArrayList<>();
        //fill personList with elements
        for (int i=0; i<new SecureRandom().nextInt(20); i++) {
            personList.add(new Person());
        }
        //add elements from personList into linkedList from some index
        assertAll("dfg",
                () -> assertTrue(linkedList.addAll(2, personList)),
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(linkedList.size(), initSize + personList.size()),
                () -> assertTrue(linkedList.containsAll(personList))
        );
        initSize = linkedList.size();
        //initialize nullList which will be added into linkedList from some index
        List<Person> nullList = null;
        //add nullList into linkedList from some index
        assertAll("asd",
                () -> assertThrows(NullPointerException.class, () -> linkedList.addAll(1, nullList)),
                () -> assertEquals(initSize, linkedList.size()),
                () -> assertFalse(linkedList.contains(null))
        );
        //initialize empty collection
        List<Person> emptyList = new ArrayList<>();
        //add emptyList into linkedList from some index
        assertAll("sgg",
                () -> assertFalse(linkedList.addAll(1, emptyList)),
                () -> assertEquals(initSize, linkedList.size()),
                () -> assertTrue(linkedList.containsAll(emptyList))
        );
    }

    @Test
    @DisplayName("Update some element in linkedList")
    void updateElementsInLinkedList() {
        //initialize new Person instance
        Person person = new Person();
        //save some element from linkedList
        Person indexPerson = linkedList.get(2);
        //set person into 2 index in linkedList
        assertAll("sdf",
                () -> assertEquals(indexPerson, linkedList.set(2, person)),
                () -> assertEquals(person, linkedList.get(2)),
                () -> assertEquals(initSize, linkedList.size())
        );
        //set element on non exist index in linkedList
        assertAll("fg",
                () -> assertThrows(IndexOutOfBoundsException.class, () -> linkedList.set(100, person)),
                () -> assertEquals(initSize, linkedList.size())
        );
    }

    @Test
    @DisplayName("Replace all elements in LinkedList")
    void replaceElementViaReplaceAll() {
        List<Boolean> checkList = new ArrayList<>();
        linkedList.replaceAll(x -> {
            x.setName("WUSSSSSSUUUUP!");
            return x;
        });
        //expect that size of linkedList was not changed and c
        assertEquals(initSize, linkedList.size());
        linkedList.forEach(x -> {
            checkList.add(x.getName().equals("WUSSSSSSUUUUP!"));
        });
        //check that only WUSSSSSSUUUUP! in list
        assertFalse(checkList.contains(false));
    }
}