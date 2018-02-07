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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RemoveDataFromVector {

    private Vector<Person> vector = new Vector<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i< new SecureRandom().nextInt(20); i++) {
            vector.add(new Person());
        }
        initSize = vector.size();
    }

    @Test
    @DisplayName("Just remove element from Vector by index")
    void removeElementFromVectorBySomeIndex() {
        //remove some element from vector by index and check that size of vector was changed and other stuff
        assertNotNull(vector.remove(new SecureRandom().nextInt(vector.size())));
        assertEquals(initSize, vector.size()+1);
        //save size;
        initSize = vector.size();
        //remove element from non exist index. Expect IndexOutOfBoundException and size of vector was not changed
        assertThrows(IndexOutOfBoundsException.class, () -> vector.remove(vector.size()));
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Remove element from Vector by using object")
    void removeElementFromVectorByObject() {
        //initialize new instance of Person which will be added into vector
        Person person = new Person();
        //add person into vector
        vector.add(new SecureRandom().nextInt(vector.size()), person);
        //remove person from vector. It should returns true
        assertTrue(vector.remove(person));
        //and size of vector should be equal to origin
        assertEquals(initSize, vector.size());
        //person is not included into vector
        assertFalse(vector.contains(person));
        //remove non exist person from vector. It should returns false
        assertFalse(vector.remove(person));
        //and size of vector should not been changed
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Remove element from Vector which is shown twice")
    void removeTwiceElementFromVector() {
        //initialize new instance of Person
        Person person = new Person();
        //add person into vector on random place twice
        vector.add(new SecureRandom().nextInt(vector.size()), person);
        vector.add(new SecureRandom().nextInt(vector.size()), person);
        //check that size of vector was changed
        assertEquals(initSize, vector.size()-2);
        //remove person from vector
        assertTrue(vector.remove(person));
        //check that size of vector was changed and person is still included into vector
        assertEquals(initSize, vector.size()-1);
        assertTrue(vector.contains(person));
    }

    @Test
    @DisplayName("Remove collection from Vector")
    void removeCollectionFromVector() {
        //initialize new collection
        List<Person> personList = new LinkedList<>();
        //fill personList with elements
        for (int i=0; i<new SecureRandom().nextInt(20); i++) {
            personList.add(new Person());
        }
        //add personList into vector
        assertTrue(vector.addAll(new SecureRandom().nextInt(vector.size()), personList));
        //check that size of vector was changed
        assertEquals(initSize, vector.size()-personList.size());
        //remove personList from vector. It should return true;
        assertTrue(vector.removeAll(personList));
        //check that size of vector is now equal to origin size and vector is not including personList
        assertEquals(initSize, vector.size());
        assertFalse(vector.containsAll(personList));
    }

    @Test
    @DisplayName("Remove null and empty collection from Vector")
    void removeNullAndEmptyCollectionFromVector() {
        //initialize null and empty collection
        List<Person> nullList = null;
        List<Person> emptyList = new LinkedList<>();
        //remove nullList from linkedList. Expect NPE
        assertThrows(NullPointerException.class, () -> vector.removeAll(nullList));
        //remove emptyList from vector. Expect false
        assertFalse(vector.removeAll(emptyList));
    }

    @Test
    @DisplayName("Remove elements from Vector if they satisfies condition")
    void removeIfElementsFromVector() {
        //remove elements from vector via removeIf
        assertTrue(vector.removeIf(x -> x.getAge()<50));
        //size of vector should be changed
        assertNotEquals(initSize, vector.size());
        initSize = vector.size();
        //remove elements from vector via removeIf in case no one element satisfies condition. Expect false
        assertFalse(vector.removeIf(x -> x.getAge()>100));
        //size of vector should not been changed
        assertEquals(initSize, vector.size());
        //remove elements from vector via removeIf in case of filter = null. Expect NPE
        assertThrows(NullPointerException.class, () -> vector.removeIf(null));
        //size of vector should not been changed
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Retain elements from Vector")
    void retainElementsFromVector() {
        //initialize a copy of vector
        List<Person> personList = new Vector<>();
        personList.addAll(vector);
        //initialize empty collection
        List<Person> emptyList = new LinkedList<>();
        //retain personList from vector
        assertFalse(vector.retainAll(personList));
        //retain collection from vector which is the almost same as vector
        personList.remove(new SecureRandom().nextInt(personList.size()));
        assertTrue(vector.retainAll(personList));
        //check that size of vector was changed
        assertEquals(initSize, vector.size()+1);
        //retain null from vector. Expect NPE
        assertThrows(NullPointerException.class, () -> vector.retainAll(null));
        //retain empty collection from vector. Expects true and empty vector
        assertTrue(vector.retainAll(emptyList));
        assertThat(vector, is(empty()));
        assertEquals(0, vector.size());
    }

    @Test
    @DisplayName("Just remove element from Vector by index via removeElementAt()")
    void removeElementFromVectorBySomeIndexViaRemoveElementAt() {
        //save some index from vector
        int index = new SecureRandom().nextInt(vector.size());
        //save person from for index from vector
        Person person = vector.elementAt(index);
        //remove some element from vector by index and check that size of vector was changed and other stuff
        vector.removeElementAt(index);
        assertEquals(initSize, vector.size()+1);
        assertFalse(vector.contains(person));
        //save size;
        initSize = vector.size();
        //remove element from non exist index. Expect ArrayIndexOutOfBoundException and size of vector was not changed
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> vector.removeElementAt(vector.size()));
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Remove element from Vector by using object via removeElement")
    void removeElementFromVectorByObjectViaRemoveElement() {
        //initialize new instance of Person which will be added into vector
        Person person = new Person();
        //add person into vector
        vector.add(new SecureRandom().nextInt(vector.size()), person);
        //remove person from vector. It should returns true
        assertTrue(vector.removeElement(person));
        //and size of vector should be equal to origin
        assertEquals(initSize, vector.size());
        //person is not included into vector
        assertFalse(vector.contains(person));
        //remove non exist person from vector. It should returns false
        assertFalse(vector.removeElement(person));
        //and size of vector should not been changed
        assertEquals(initSize, vector.size());
    }
}
