package com.equiv.java.lists.linked;

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

class IterateLinkedList {

    private List<Person> linkedList = new LinkedList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i< new SecureRandom().nextInt(20); i++) {
            linkedList.add(new Person());
        }
        initSize = linkedList.size();
    }

    @Test
    @DisplayName("Just get some element from LinkedList")
    void getSomeElementFromLinkedList() {
        //get some random element from linkedList
        assertNotNull(linkedList.get(new SecureRandom().nextInt(linkedList.size())));
        //check that size of linkedList was not changed
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Try to get element with non existent index from LinkedList")
    void getElementWithNonExistentIndex() {
        //get element with non existent index from linkedList. Expect that .get() will return IndexOutOfBoundException
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.get(linkedList.size()));
        //check that size of linkedList was not changed
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Copy elements from one LinkedList instance to another one via forEach()")
    void copyElementsFromOneLinkedListToAnotherViaForEach() {
        //initialize empty LinkedList
        List<Person> additionalList = new LinkedList<>();
        //copy elements from linkedList to additionalList via forEach
        linkedList.forEach(x -> additionalList.add(x));
        //check that linkedList was not changed
        assertEquals(initSize, linkedList.size());
        assertThat(linkedList, is(not(empty())));
        //check that additionalList is not empty
        assertThat(additionalList, is(not(empty())));
        assertEquals(linkedList, additionalList);
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
    @DisplayName("Iterating LinkedList via Iterator")
    void iteratingLinkedListViaIterator() {
        int i = 0;
        //initialize iterator for linkedList
        Iterator<Person> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            //checking that elements were get in origin order
            assertEquals(linkedList.get(i), iterator.next());
            i++;
        }
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Iterating LinkedList via Iterator and receive Concurrent exception")
    void iteratingLinkedListViaIteratorWithConcurrent() {
        //initialize iterator for linkedList
        Iterator<Person> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            //remove element from linkedList straight
            linkedList.remove(0);
            //try to access the first element of linkedList via iterator. Expect Concurrent exception
            assertThrows(ConcurrentModificationException.class, () -> System.out.println(iterator.next()));
        }
        assertNotEquals(initSize, linkedList.size());
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
    @DisplayName("Make some changes in LinkedList via Iterator")
    void makeSomeChangesWhileIteratingLinkedListViaIterator() {
        //initialize iterator for linkedList
        Iterator<Person> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAge() < 50) {
                iterator.remove();
            }
        }
        //check that size of linkedList was changed
        assertNotEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Iterating LinkedList via iterator.forEachRemaining and make some changes with elements")
    void iterationLinkedListViaForEachRemaining() {
        //initialize iterator for linkedList
        Iterator<Person> iterator = linkedList.iterator();
        iterator.forEachRemaining(x -> System.out.println(x.getName()));
        //check that size of linkedList was not changed
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Iterating update last element in LinkedList via ListIterator")
    void updateLastElementInLinkedList() {
        //initialize listIterator for linkedList
        ListIterator<Person> iterator = linkedList.listIterator();
        //print all elements from linkedList on screen
        iterator.forEachRemaining(System.out::println);
        //check that iterator.previous() is equal to last element in linkedList
        assertEquals(linkedList.get(linkedList.size() - 1), iterator.previous());
        //save last element of linkedList
        Person dummy = linkedList.get(linkedList.size() - 1);
        //changing last element in linkedList via iterator.set() and checking that size of linkedList was not changed and last element was changed
        iterator.set(new Person());
        assertNotEquals(dummy, iterator.previous());
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Update first element in LinkedList via ListIterator")
    void updateFirstElementInLinkedList() {
        //initialize listIterator for linkedList
        ListIterator<Person> listIterator = linkedList.listIterator();
        //save first element of linkedList
        Person firstElement = linkedList.get(0);
        //check that iterator.next() is equal to first element
        assertEquals(firstElement, listIterator.next());
        //update first element via listIterator.set() and check that saved first element is not equal to linkedList.get(0)
        listIterator.set(new Person());
        assertNotEquals(firstElement, linkedList.get(0));
    }

    @Test
    @DisplayName("Checking order of LinkedList via ListIterator")
    void checkingForwardAndBackwardOrder() {
        //initialize iterator for linkedList
        ListIterator<Person> listIterator = linkedList.listIterator();
        //initialize 2 additional linkedLists
        List<Person> forwardOrderList = new LinkedList<>();
        List<Person> backwardOrderList = new LinkedList<>();
        //add elements from linkedList to forwardOrderList via ListIterator.next()
        while (listIterator.hasNext()) {
            forwardOrderList.add(listIterator.next());
        }
        //add elements from linkedList to backWardOrderList via ListIterator.previous()
        while (listIterator.hasPrevious()) {
            backwardOrderList.add(listIterator.previous());
        }
        //check that forwardOrderList is equal to linkedList and backwardOrderList is not equal to linkedList
        assertAll("sdgfsdf",
                () -> assertEquals(linkedList, forwardOrderList),
                () -> assertNotEquals(linkedList, backwardOrderList),
                () -> assertNotEquals(forwardOrderList, backwardOrderList)
        );
        //check that backwardOrderList is equal to forwardOrderList in desc order
        Collections.reverse(backwardOrderList);
        assertAll("fgdfgd",
                () -> assertEquals(forwardOrderList, backwardOrderList),
                () -> assertEquals(linkedList, backwardOrderList)
        );
    }

    @Test
    @DisplayName("Iterating LinkedList via listIterator with index")
    void iterateLinkedListViaListIteratorWithIndex() {
        //try to initialize new instance of Iterator with listIterator from non existent index
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.listIterator(linkedList.size()+1));
        //initialize new Iterator instance from linkedList.listIterator(int i)
        Iterator<Person> iterator = linkedList.listIterator(new SecureRandom().nextInt(initSize));
        //initialize empty instance of LinkedList
        List<Person> personList = new LinkedList<>();
        //put iterator's values into personList
        iterator.forEachRemaining(personList::add);
        //check that iterator is empty
        assertFalse(iterator.hasNext());
        //check that size of personList is not equal to size of linkedList
        assertNotEquals(initSize, personList.size());
        assertTrue(linkedList.containsAll(personList));
    }
}
