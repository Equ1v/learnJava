package com.equiv.java.lists.copyonwritearray;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddDataIntoCopyOnWriteArrayList {

    private CopyOnWriteArrayList<Person> copyList = new CopyOnWriteArrayList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < 20; i++) {
            copyList.add(new Person());
        }
        initSize = copyList.size();
    }

    @Test
    @DisplayName("Just add element into tale of CopyOnWriteArrayList")
    void justAddElementIntoCopyOnWriteArrayList() {
        //initialize new instance of Person class
        Person person = new Person();
        //add person into copyList
        assertTrue(copyList.add(person));
        //check that size of copyList was changed and person is present in copyList
        assertEquals(initSize, copyList.size()-1);
        assertTrue(copyList.contains(person));
        //add person again into copyList
        assertTrue(copyList.add(person));
        //check that size of copyList was changed and person is present twice in copyList
        assertEquals(initSize, copyList.size()-2);
        assertNotEquals(copyList.indexOf(person), copyList.lastIndexOf(person));
        //try to add null into copyList
        assertTrue(copyList.add(null));
        //check that size of copyList was increased and last element is null
        assertEquals(initSize, copyList.size()-3);
        assertNull(copyList.get(copyList.size()-1));
    }

    @Test
    @DisplayName("Add elements on some index in CopyOnWriteArrayList")
    void addElementOnSomeIndexIntoCopyOnWriteArrayList() {
        //initialize new instance of Person class
        Person person = new Person();
        //add person on some index in copyList
        copyList.add(new SecureRandom().nextInt(copyList.size()), person);
        //check that size of copyList was increased and person is present in copyList
        assertEquals(initSize, copyList.size()-1);
        assertTrue(copyList.contains(person));
        //add person again
        copyList.add(new SecureRandom().nextInt(copyList.size()), person);
        //check that size of copyList was increased and person is present twice
        assertEquals(initSize, copyList.size()-2);
        assertNotEquals(copyList.indexOf(person), copyList.lastIndexOf(person));
        //try to add null element into copyList
        copyList.add(0, null);
        //check that size of copyList was increased and first element is null
        assertEquals(initSize, copyList.size()-3);
        assertNull(copyList.get(0));
    }

    @Test
    @DisplayName("Add element into CopyOnWriteArrayList by addIfAbsent")
    void addElementInCopyOnWriteArrayListIfAbsent() {
        //initialize new instance of Person class
        Person person = new Person();
        //add person into copyList via addIfAbsent();
        assertTrue(copyList.addIfAbsent(person));
        //check that size of copyList was increased and copyList includes person
        assertEquals(initSize, copyList.size()-1);
        assertTrue(copyList.contains(person));
        initSize = copyList.size();
        //try to add person again
        assertFalse(copyList.addIfAbsent(person));
        //check that size of copyList was not changed and person is present once
        assertEquals(initSize, copyList.size());
        assertEquals(copyList.indexOf(person), copyList.lastIndexOf(person));
        //add null into copyList
        assertTrue(copyList.addIfAbsent(null));
        //check that size was increased and null is present in copyList
        assertEquals(initSize, copyList.size()-1);
        assertTrue(copyList.contains(null));
        //try to add null into copyList again
        assertFalse(copyList.addIfAbsent(null));
        //check that size of copyList was not changed and null is present once
        assertEquals(initSize, copyList.size()-1);
        assertEquals(copyList.indexOf(null), copyList.lastIndexOf(null));
    }

    @Test
    @DisplayName("Add some collection into tale of CopyOnWriteArrayList")
    void addCollectionIntoCopyOnWriteArrayList() {
        //initialize new collection
        Set<Person> personSet = new HashSet<>();
        for (int i=0; i<5; i++) {
            personSet.add(new Person());
        }
        //initialize copy of copyList
        CopyOnWriteArrayList<Person> people = new CopyOnWriteArrayList<>(copyList);
        int peopleSize = people.size();
        //add personSet into copyList
        assertTrue(copyList.addAll(personSet));
        //check that size of copyList was increased and personSet is present in copyList
        assertEquals(initSize, copyList.size()-personSet.size());
        assertTrue(copyList.containsAll(personSet));
        //add personSet again
        assertTrue(copyList.addAll(personSet));
        //check that size of copyList was increased
        assertEquals(initSize, copyList.size()-2*personSet.size());
        initSize = copyList.size();
        //try to add null
        assertThrows(NullPointerException.class, () -> copyList.addAll(null));
        //check that size was not changed
        assertEquals(initSize, copyList.size());
        //try to add empty collection into copyList
        assertFalse(copyList.addAll(new HashSet<Person>()));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //try to add in copyList himself
        assertTrue(people.addAll(people));
        //check that size was increased
        assertEquals(peopleSize, people.size()/2);
    }

    @Test
    @DisplayName("Add collection into CopyOnWriteArrayList via addAllAbsent()")
    void addCollectionIntoCopyOnWriteArrayListIfAbsent() {
        //initialize new collection
        Set<Person> personSet = new HashSet<>();
        for (int i=0; i< 5; i++) {
            personSet.add(new Person());
        }
        //add personSet into copyList
        assertEquals(personSet.size(), copyList.addAllAbsent(personSet));
        //check that size of copyList was increased and copyList includes personSet
        assertEquals(initSize, copyList.size()-personSet.size());
        assertTrue(copyList.containsAll(personSet));
        initSize = copyList.size();
        //try to add personSet into copyList again
        assertEquals(0, copyList.addAllAbsent(personSet));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //try to add null collection into copyList
        assertThrows(NullPointerException.class, () -> copyList.addAllAbsent(null));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //add copy of copyList into himself
        assertEquals(0, copyList.addAllAbsent(copyList));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //add some new elements into personSet
        personSet.add(new Person());
        personSet.add(new Person());
        //add personSet into copyList
        assertEquals(2, copyList.addAllAbsent(personSet));
        //check that size of copyList was increased and personSet is present in copyList
        assertEquals(initSize, copyList.size()-2);
        assertTrue(copyList.containsAll(personSet));
    }

    @Test
    @DisplayName("Add some collection into CoyOnWriteArrayList on some index")
    void addCollectionIntoCopyOnWriteArrayListFromSomeIndex() {
        //initialize new collection
        Queue<Person> personQueue = new LinkedList<>();
        for (int i=0; i< 5; i++) {
            personQueue.add(new Person());
        }
        //add personQueue into copyList from some index
        assertTrue(copyList.addAll(new SecureRandom().nextInt(copyList.size()), personQueue));
        //check that size of copyList was increased and copyList includes personQueue
        assertEquals(initSize, copyList.size()-personQueue.size());
        assertTrue(copyList.containsAll(personQueue));
//        initSize = copyList.size();
        //add personQueue again
        assertTrue(copyList.addAll(new SecureRandom().nextInt(copyList.size()), personQueue));
        //check that size of copyList was increased
        assertEquals(initSize, copyList.size()-2*personQueue.size());
        initSize = copyList.size();
        //add empty collection into copyList
        assertFalse(copyList.addAll(new SecureRandom().nextInt(copyList.size()), new PriorityQueue<Person>()));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //try to add null into copyList
        assertThrows(NullPointerException.class, () -> copyList.addAll(new SecureRandom().nextInt(copyList.size()), null));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //add personQueue on non existent index in copyList
        assertThrows(IndexOutOfBoundsException.class, () -> copyList.addAll(copyList.size()+1, personQueue));
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
    }

    @Test
    @DisplayName("Update element in CopyOnWriteArrayList")
    void updateElementInCopyOnWriteArrayList() {
        //initialize new instance of Person class
        Person person = new Person();
        //update some element from copyList on person. Expect that .set() will return previous value
        assertNotNull(copyList.set(0, person));
        //check that size of copyList was not changed and first element is equal to person
        assertEquals(initSize, copyList.size());
        assertEquals(person, copyList.get(0));
        //set person again
        assertEquals(person, copyList.set(0, person));
        //set person on non existent index
        assertThrows(IndexOutOfBoundsException.class, () -> copyList.set(copyList.size()+1, person));
    }

    @Test
    @DisplayName("Update pararmeters in elements of COpyOnWriteArrayList")
    void updateAllElementsInCopyOnWriteArrayList() {
        //update age parameter in elements from copyList
        copyList.replaceAll(x -> {
            x.setAge(x.getAge()*2);
            return x;
        });
        //check that size of copyList was not changed
        assertEquals(initSize, copyList.size());
        //try to user null operator
        assertThrows(NullPointerException.class, () -> copyList.replaceAll(null));
    }
}
