package com.equiv.java.lists.vector;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IterateVector {

    private Vector<Person> vector = new Vector<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for(int i=0; i < 10; i++) {
            vector.add(new Person());
        }
        initSize = vector.size();
    }

    @Test
    @DisplayName("Just get some element from Vector")
    void getSomeElementFromVector() {
        //get some random element from vector
        assertNotNull(vector.get(new SecureRandom().nextInt(vector.size())));
        //check that size of vector was not changed
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Try to get element with non existent index from Vector")
    void getElementWithNonExistentIndex() {
        //get element with non existent index from vector. Expect that .get() will return IndexOutOfBoundException
        assertThrows(IndexOutOfBoundsException.class, () -> vector.get(vector.size()));
        //check that size of vector was not changed
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Copy elements from one Vector instance to another one via forEach()")
    void copyElementsFromOneVectorToAnotherViaForEach() {
        //initialize empty Vector
        List<Person> vectorAdditional = new Vector<>();
        //copy elements from vector to additionalVector via forEach
        this.vector.forEach(x -> vectorAdditional.add(x));
        //check that vector was not changed
        assertEquals(initSize, vector.size());
        assertThat(vector, is(not(empty())));
        //check that additionalVector is not empty
        assertThat(vectorAdditional, is(not(empty())));
        assertEquals(vector, vectorAdditional);
    }

    @Test
    @DisplayName("Iterating null List via forEach")
    void iteratingNullListViaForEach() {
        //initialize null list
        List<Person> nullList = null;
        //check that iterating nullList will throw NPE
        assertThrows(NullPointerException.class, () -> nullList.forEach(System.out::println));
    }

    @Test
    @DisplayName("Iterating Vector via Iterator")
    void iteratingVectorViaIterator() {
        int i = 0;
        //initialize iterator for vector
        Iterator<Person> iterator = vector.iterator();
        while (iterator.hasNext()) {
            //checking that elements were get in origin order
            assertEquals(vector.get(i), iterator.next());
            i++;
        }
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Iterating Vector via Iterator and receive Concurrent exception")
    void iteratingVectorViaIteratorWithConcurrent() {
        //initialize iterator for vector
        Iterator<Person> iterator = vector.iterator();
        while (iterator.hasNext()) {
            //remove element from vector straight
            vector.remove(0);
            //try to access the first element of vector via iterator. Expect Concurrent exception
            assertThrows(ConcurrentModificationException.class, () -> System.out.println(iterator.next()));
        }
        assertNotEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Iterating null List via Iterator")
    void iteratingNullListViaIterator() {
        //initialize null list
        List<Person> nullList = null;
        //try to get iterator of null list. Expect NullPointerException
        assertThrows(NullPointerException.class, () -> nullList.iterator());
    }

    @Test
    @DisplayName("Make some changes in Vector via Iterator")
    void makeSomeChangesWhileIteratingVectorViaIterator() {
        //initialize iterator for vector
        Iterator<Person> iterator = vector.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAge() < 50) {
                iterator.remove();
            }
        }
        //check that size of vector was changed
        assertNotEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Iterating Vector via iterator.forEachRemaining and make some changes with elements")
    void iterationVectorViaForEachRemaining() {
        //initialize iterator for vector
        Iterator<Person> iterator = vector.iterator();
        iterator.forEachRemaining(x -> System.out.println(x.getName()));
        //check that size of vector was not changed
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Iterating update last element in Vector via ListIterator")
    void updateLastElementInVector() {
        //initialize listIterator for vector
        ListIterator<Person> iterator = vector.listIterator();
        //print all elements from vector on screen
        iterator.forEachRemaining(System.out::println);
        //check that iterator.previous() is equal to last element in vector
        assertEquals(vector.get(vector.size() - 1), iterator.previous());
        //save last element of vector
        Person dummy = vector.get(vector.size() - 1);
        //changing last element in vector via iterator.set() and checking that size of vector was not changed and last element was changed
        iterator.set(new Person());
        assertNotEquals(dummy, iterator.previous());
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Update first element in Vector via ListIterator")
    void updateFirstElementInVector() {
        //initialize listIterator for vector
        ListIterator<Person> listIterator = vector.listIterator();
        //save first element of vector
        Person firstElement = vector.get(0);
        //check that iterator.next() is equal to first element
        assertEquals(firstElement, listIterator.next());
        //update first element via listIterator.set() and check that saved first element is not equal to vector.get(0)
        listIterator.set(new Person());
        assertNotEquals(firstElement, vector.get(0));
    }

    @Test
    @DisplayName("Checking order of Vector via ListIterator")
    void checkingForwardAndBackwardOrder() {
        //initialize iterator for vector
        ListIterator<Person> listIterator = vector.listIterator();
        //initialize 2 additional vector
        List<Person> forwardOrderVector = new Vector<>();
        List<Person> backwardOrderVector = new Vector<>();
        //add elements from vector to forwardOrderVector via ListIterator.next()
        while (listIterator.hasNext()) {
            forwardOrderVector.add(listIterator.next());
        }
        //add elements from vector to backWardOrderVector via ListIterator.previous()
        while (listIterator.hasPrevious()) {
            backwardOrderVector.add(listIterator.previous());
        }
        //check that forwardOrderVector is equal to vector and backwardOrderVector is not equal to vector
        assertAll("sdgfsdf",
                () -> assertEquals(vector, forwardOrderVector),
                () -> assertNotEquals(vector, backwardOrderVector),
                () -> assertNotEquals(forwardOrderVector, backwardOrderVector)
        );
        //check that backwardOrderVector is equal to forwardOrderVector in desc order
        Collections.reverse(backwardOrderVector);
        assertAll("fgdfgd",
                () -> assertEquals(forwardOrderVector, backwardOrderVector),
                () -> assertEquals(vector, backwardOrderVector)
        );
    }

    @Test
    @DisplayName("Iterating Vector via listIterator with index")
    void iterateVectorViaListIteratorWithIndex() {
        //try to initialize new instance of Iterator with listIterator from non existent index
        assertThrows(IndexOutOfBoundsException.class, () -> vector.listIterator(vector.size()+1));
        //initialize new Iterator instance from vector.listIterator(int i)
        Iterator<Person> iterator = vector.listIterator(5);
        //initialize empty instance of Vector
        List<Person> personVector = new Vector<>();
        //put iterator's values into personVector
        iterator.forEachRemaining(personVector::add);
        //check that iterator is empty
        assertFalse(iterator.hasNext());
        //check that size of personVector is not equal to size of vector
        assertNotEquals(initSize, personVector.size());
        assertTrue(vector.containsAll(personVector));
    }

    @Test
    @DisplayName("Getting first and last element from Vector")
    void firstAndLastElement() {
        //initialize empty instance of Vector
        Vector<Person> personVector = new Vector<>();
        //get first and last element from personVector. Expect NoSuchElementException
        assertThrows(NoSuchElementException.class, personVector::firstElement);
        assertThrows(NoSuchElementException.class, personVector::lastElement);
        //getting first and last element from vector
        assertEquals(vector.get(0), vector.firstElement());
        assertEquals(vector.get(vector.size()-1), vector.lastElement());
    }
}
