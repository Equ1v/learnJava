package com.equiv.java.lists.linked;


import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

@DisplayName("Test suite for checking adding elements in LinkedList")
class AddDataIntoLinkedList {

    private List<Person> linkedList = new LinkedList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < new SecureRandom().nextInt(20); i++) {
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
        linkedList.add(5, person);
        //check that size of linkedList was changed and that element with index 5 is equal to person
        assertAll("woohoo",
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(person, linkedList.get(5))
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
        linkedList.add(5, person);
        //check that size of linkedList was changed and that element with index 5 is equal to person and there are 2 same Person instance
        assertAll("sdafasfd",
                () -> assertNotEquals(initSize, linkedList.size()),
                () -> assertEquals(person, linkedList.get(5)),
                () -> assertNotEquals(linkedList.indexOf(person), linkedList.lastIndexOf(person))
        );
    }
}
