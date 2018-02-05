package com.equiv.java.lists.linked;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test suite for checking removing elements from LinkedList")
class RemoveDataFromLinkedList {

    private List<Person> linkedList = new LinkedList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < new SecureRandom().nextInt(20); i++) {
            linkedList.add(new Person());
        }
        initSize = linkedList.size();
    }

    @Test
    @DisplayName("Just remove element from LinkedList by index")
    void removeElementFromLinkedListBySomeIndex() {
        //remove some element from linkedList by index and check that size of linkedList was changed and other stuff
        assertNotNull(linkedList.remove(new SecureRandom().nextInt(linkedList.size())));
        assertEquals(initSize, linkedList.size()+1);
        initSize = linkedList.size();
        //remove element from non exist index. Expect IndexOutOfBoundException and size of linked list was not changed
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.remove(linkedList.size()));
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Remove element from LinkedList by using object")
    void removeElementFromLinkedListByObject() {
        //initialize new instance of Person which will be added into linkedList
        Person person = new Person();
        //add person into linkedList
        linkedList.add(new SecureRandom().nextInt(linkedList.size()), person);
        //remove person from linkedList. It should returns true
        assertTrue(linkedList.remove(person));
        //and size of linked list should be equal to origin
        assertEquals(initSize, linkedList.size());
        //person is not included into linkedList
        assertFalse(linkedList.contains(person));
        //remove non exist person from linkedList. It should returns false
        assertFalse(linkedList.remove(person));
        //and size of linkedList should not been changed
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Remove element from LinkedList which is shown twice")
    void removeTwiceElementFromLinkedList() {
        //initialize new instance of Person
        Person person = new Person();
        //add person into linkedList on random place twice
        linkedList.add(new SecureRandom().nextInt(linkedList.size()), person);
        linkedList.add(new SecureRandom().nextInt(linkedList.size()), person);
        //check that size of linkedList was changed
        assertEquals(initSize, linkedList.size()-2);
        //remove person from linkedList
        assertTrue(linkedList.remove(person));
        //check that size of linkedList was changed and person is still included into linkedList
        assertEquals(initSize, linkedList.size()-1);
        assertTrue(linkedList.contains(person));
    }

    @Test
    @DisplayName("Remove collection from LinkedList")
    void removeCollectionFromLinkedList() {
        //initialize new collection
        List<Person> personList = new ArrayList<>();
        //fill personList with elements
        for (int i=0; i<new SecureRandom().nextInt(20); i++) {
            personList.add(new Person());
        }
        //add personList into LinkedList
        assertTrue(linkedList.addAll(new SecureRandom().nextInt(linkedList.size()), personList));
        //check that size of linkedList was changed
        assertEquals(initSize, linkedList.size()-personList.size());
        //remove personList from linedList. It should return true;
        assertTrue(linkedList.removeAll(personList));
        //check that size of linkedList is now equal to origin size and linkedList is not including personList
        assertEquals(initSize, linkedList.size());
        assertFalse(linkedList.containsAll(personList));
    }

    @Test
    @DisplayName("Remove null and empty collection from LinkedList")
    void removeNullAndEmptyCollectionFromLinkedList() {
        //initialize null and empty collection
        List<Person> nullList = null;
        List<Person> emptyList = new ArrayList<>();
        //remove nullList from linkedList. Expect NPE
        assertThrows(NullPointerException.class, () -> linkedList.removeAll(nullList));
        //remove emptyList from linkedList. Expect false
        assertFalse(linkedList.removeAll(emptyList));
    }

    @Test
    @DisplayName("Remove elements from LinkedList if they satisfies condition")
    void removeIfElementsFromLinkedList() {
        //remove elements from linkedList via removeIf
        assertTrue(linkedList.removeIf(x -> x.getAge()<50));
        //size of linkedList should be changed
        assertNotEquals(initSize, linkedList.size());
        initSize = linkedList.size();
        //remove elements from linkedList via removeIf in case no one element satisfies condition. Expect false
        assertFalse(linkedList.removeIf(x -> x.getAge()>100));
        //size of linkedList should not been changed
        assertEquals(initSize, linkedList.size());
        //remove elements from linkedList via removeIf in case of filter = null. Expect NPE
        assertThrows(NullPointerException.class, () -> linkedList.removeIf(null));
        //size of linkedList should not been changed
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Retain elements from LinkedList")
    void retainElementsFromLinkedList() {
        //initialize a copy of linkedList
        List<Person> personList = new LinkedList<>();
        personList.addAll(linkedList);
        //initialize empty collection
        List<Person> emptyList = new LinkedList<>();
        //retain personList from linkedList
        assertFalse(linkedList.retainAll(personList));
        //retain collection from linkedList which is the almost same as linkedList
        personList.remove(new SecureRandom().nextInt(personList.size()));
        assertTrue(linkedList.retainAll(personList));
        //check that size of linkedList was changed
        assertEquals(initSize, linkedList.size()+1);
        //retain null from linkedList. Expect NPE
        assertThrows(NullPointerException.class, () -> linkedList.retainAll(null));
        //retain empty collection from linkedList. Expects true and empty linkedList
        assertTrue(linkedList.retainAll(emptyList));
        assertThat(linkedList, is(empty()));
        assertEquals(0, linkedList.size());
    }
}
