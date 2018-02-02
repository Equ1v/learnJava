package com.equiv.java.lists.array;

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

@DisplayName("Test suite for checking iteration of elements in ArrayList")
class IterationOfArrayList {

//    //objects of this class will be used as elements of ArrayList
//    private class Person {
//        String name;
//
//        public String getName() {
//            return name;
//        }
//
//        public int getAge() {
//            return age;
//        }
//
//        int age;
//
//        public Person(String name, int age) {
//            this.name = name;
//            this.age = age;
//        }
//
//        Person(int age) {
//            this.age = age;
//            this.name = generateRandomName(7);
//        }
//
//        //used for generating random name, because ya ustal ya Myxo}|{yk
//        private String generateRandomName(int nameLength) {
//            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//            if (nameLength <= 1) {
//                throw new IllegalArgumentException("Name could not be less than 2 characters");
//            }
//            StringBuilder sb = new StringBuilder();
//            while (nameLength > 0) {
//                nameLength--;
//                char chr = characters.toCharArray()[new SecureRandom().nextInt(characters.length())];
//                sb.append(chr);
//            }
//            return sb.toString();
//        }
//
//        @Override
//        public String toString() {
//            return "Person{" +
//                    "name='" + name + '\'' +
//                    ", age=" + age +
//                    '}';
//        }
//    }

    //ArrayList which will be tested
    private List<Person> arrayList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        //initialize arrayList
        for (int i = 0; i < 10; i++) {
            arrayList.add(new Person());
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
        int i = 0;
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
    @DisplayName("Iterating update last element in arrayList via ListIterator")
    void updateLastElementInArrayList() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //initialize listIterator for arrayList
        ListIterator<Person> iterator = arrayList.listIterator();
        //print all elements from arrayList on screen
        iterator.forEachRemaining(System.out::println);
        //check that iterator.previous() is equal to last element in arrayList
        assertEquals(arrayList.get(arrayList.size() - 1), iterator.previous());
        //save last element of arrayList
        Person dummy = arrayList.get(arrayList.size() - 1);
        //changing last element in arrayList via iterator.set() and checking that size of arrayList was not changed and last element was changed
        iterator.set(new Person());
        assertNotEquals(dummy, iterator.previous());
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Update first element in arrayList via ListIterator")
    void updateFirstElementInArrayList() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //initialize listIterator for arrayList
        ListIterator<Person> listIterator = arrayList.listIterator();
        //save first element of arrayList
        Person firstElement = arrayList.get(0);
        //check that iterator.next() is equal to first element
        assertEquals(firstElement, listIterator.next());
        //update first element via listIterator.set() and check that saved first element is not equal to arrayList.get(0)
        listIterator.set(new Person());
        assertNotEquals(firstElement, arrayList.get(0));
    }

    @Test
    @DisplayName("Checking order of arrayList via ListIterator")
    void checkingForwardAndBackwardOrder() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //initialize iterator for arrayList
        ListIterator<Person> listIterator = arrayList.listIterator();
        //initialize 2 additional arrayLists
        List<Person> forwardOrderList = new ArrayList<>();
        List<Person> backwardOrderList = new ArrayList<>();
        //add elements from arrayList to forwardOrderList via ListIterator.next()
        while (listIterator.hasNext()) {
            forwardOrderList.add(listIterator.next());
        }
        //add elements from arrayList to backWardOrderList via ListIterator.previous()
        while (listIterator.hasPrevious()) {
            backwardOrderList.add(listIterator.previous());
        }
        //check that forwardOrderList is equal to arrayList and backwardOrderList is not equal to arrayList
        assertAll("sdgfsdf",
                () -> assertEquals(arrayList, forwardOrderList),
                () -> assertNotEquals(arrayList, backwardOrderList),
                () -> assertNotEquals(forwardOrderList, backwardOrderList)
        );
        //check that backwardOrderList is equal to forwardOrderList in desc order
        Collections.reverse(backwardOrderList);
        assertAll("fgdfgd",
                () -> assertEquals(forwardOrderList, backwardOrderList),
                () -> assertEquals(arrayList, backwardOrderList)
        );
    }

    @Test
    @DisplayName("Iterating ArrayListList via listIterator with index")
    void iterateArrayListViaListIteratorWithIndex() {
        //save init size of arrayList
        int initSize = arrayList.size();
        //try to initialize new instance of Iterator with listIterator from non existent index
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.listIterator(arrayList.size()+1));
        //initialize new Iterator instance from arrayList.listIterator(int i)
        Iterator<Person> iterator = arrayList.listIterator(new SecureRandom().nextInt(initSize));
        //initialize empty instance of ArrayList
        List<Person> personList = new ArrayList<>();
        //put iterator's values into personList
        iterator.forEachRemaining(personList::add);
        //check that iterator is empty
        assertFalse(iterator.hasNext());
        //check that size of personList is not equal to size of arrayList
        assertNotEquals(initSize, personList.size());
        assertTrue(arrayList.containsAll(personList));
    }
}
