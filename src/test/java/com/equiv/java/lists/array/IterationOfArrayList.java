package com.equiv.java.lists.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test suite for checking iteration of elements in ArrayList")
class IterationOfArrayList {

    //objects of this class will be used as elements of ArrayList
    private class Person {
        String name;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        Person(int age) {
            this.age = age;
            this.name = generateRandomName(7);
        }

        //used for generating random name, because ya ustal ya Myxo}|{yk
        private String generateRandomName(int nameLength) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            if (nameLength <= 1) {
                throw new IllegalArgumentException("Name could not be less than 2 characters");
            }
            StringBuilder sb = new StringBuilder();
            while(nameLength >0) {
                nameLength--;
                char chr = characters.toCharArray()[new SecureRandom().nextInt(characters.length())];
                sb.append(chr);
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    //ArrayList which will be tested
    private List<Person> arrayList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        //initialize arrayList
        for (int i=0; i<10; i++) {
            arrayList.add(new Person(new SecureRandom().nextInt(100)));
        }
    }

    @Test
    @DisplayName("Just get some element from ArrayList")
    void getSomeElementFromArrayList() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        //get some random element from arrayList
        assertNotNull(arrayList.get(new SecureRandom().nextInt(arrayList.size())));
        //check that size of arrayList was not changed
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Try to get element with non existent index from ArrayList")
    void getElementWithNonExistentIndex() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        //get element with non existent index from arrayList. Expect that .get() will return IndexOutOfBoundException
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(arrayList.size()));
        //check that size of arrayList was not changed
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Copy elements from one ArrayList instance to another one via forEach()")
    void copyElementsFromOneArrayListToAnotherViaForEach() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        //initialize empty ArrayList
        List<Person> additionalList = new ArrayList<>();
        //copy elements from arrayList to additionalList via forEach
        arrayList.forEach(x -> additionalList.add(x));
        //check that arrayList was not changed
        assertEquals(initSize, arrayList.size());
        assertThat(arrayList, is(not(empty())));
        //check that additionalList is not empty
        assertThat(additionalList, is(not(empty())));
        assertEquals(arrayList, additionalList);
    }

    @Test
    @DisplayName("Iterating null ArrayList via forEach")
    void iteratingNullArrayListViaForEach() {
        //initialize null list
        List<Person> nullList = null;
        //check that iterating nullList will throw NPE
        assertThrows(NullPointerException.class, () -> nullList.forEach(System.out::println));
    }

    @Test
    @DisplayName("Iterating ArrayList via Iterator")
    void iteratingArrayListViaIterator() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        int i=0;
        //initialize iterator for arrayList
        Iterator<Person> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            //checking that elements were get in origin order
            assertEquals(arrayList.get(i), iterator.next());
            i++;
        }
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Iterating ArrayList via Iterator and receive Concurrent exception")
    void iteratingArrayListViaIteratorWithConcurrent() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        //initialize iterator for arrayList
        Iterator<Person> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            //remove element from arrayList straight
            arrayList.remove(0);
            //try to access the first element of arrayList via iterator. Expect Concurrent exception
            assertThrows(ConcurrentModificationException.class, () -> System.out.println(iterator.next()));
        }
        assertNotEquals(initSize, arrayList.size());
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
    @DisplayName("Make some changes in ArrayList via Iterator")
    void makeSomeChangesWhileIteratingArrayListViaIterator() {
        //save origin size of arrayList
        int initSize = arrayList.size();
        //initialize iterator for arrayList
        Iterator<Person> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAge() < 50) {
                iterator.remove();
            }
        }
        //check that size of arrayList was changed
        assertNotEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Iterating arrayList via iterator.forEachRemaining and make some changes with elements")
    void iterationArrayListViaForEachRemaining() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //initialize iterator for arrayList
        Iterator<Person> iterator = arrayList.iterator();
        iterator.forEachRemaining(x -> System.out.println(x.getName()));
        //check that size of arrayList was not changed
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Iterating arrayList via ArrayList.listIterator")
    void iteratingViaListIterator() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //initialize listIterator for arrayList
        ListIterator<Person> iterator = arrayList.listIterator();
        //print all elements from arrayList on screen
        iterator.forEachRemaining(System.out::println);
        //iterating via loop in forward order
        System.out.println("before iterator " + arrayList);
        while (iterator.hasNext()) {
            if (iterator.next().getAge() > 50) {
                iterator.remove();
            }
        }
        System.out.println("after iterator " + arrayList);
        //check that size of arrayList was changed
//        assertNotEquals(initSize, arrayList.size());
        assertThat(arrayList, is(not(empty())));
        //iterating via loop in backward order
        while (iterator.hasPrevious()) {
            //make some changes in arrayList
            if (iterator.previous().getAge() < 10) {
                iterator.previous();
                iterator.remove();
            }
        }
        //add element as first element in arrayList via listIterator.add()
        Person dummyPerson = arrayList.get(0);
        //so add new Person into head of arrayList via ListIterator.add()
        iterator.next();
        iterator.add(new Person(new SecureRandom().nextInt(100)));
        //check that head of list was changed
        assertNotEquals(dummyPerson, arrayList.get(0));
    }
}
