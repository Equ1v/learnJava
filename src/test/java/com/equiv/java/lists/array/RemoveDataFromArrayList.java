package com.equiv.java.lists.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Remove data from ArrayList Test Suite")
class RemoveDataFromArrayList {

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
    @DisplayName("Remove element from tale of ArrayList")
    void removeElementFromTale() {
        //check that initial size of ArrayList is 10
        assertEquals(10, arrayList.size());
        //save the last element in ArrayList
        Person willBeDeleted = arrayList.get(arrayList.size()-1);
//        System.out.println(willBeDeleted.toString());
        //Remove last element from ArrayList and check that .remove() returns deleted element and this element is equal to saved one
        assertEquals(willBeDeleted, arrayList.remove(arrayList.size()-1));
        //check that size of ArrayList was decreased
        assertEquals(9, arrayList.size());
    }

    @Test
    @DisplayName("Remove element from ArrayList by index and receive IndexOutOfBoundException")
    void removeElementWithException() {
        //check that initial size of ArrayList is 10
        assertEquals(10, arrayList.size());
        //check that IndexOutOfBound exception will be thrown if we try to remove element from ArrayList with nonexistent index
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(100));
        //check that size of ArrayList was not changed
        assertEquals(10, arrayList.size());
    }

    @Test
    @DisplayName("Remove element by Object from ArrayList")
    void removeElementByObject() {
        //check that initial size of ArrayList is 10
        assertEquals(10, arrayList.size());
        //add new Person object into random place in ArrayList
        Person newOne = new Person(30);
        arrayList.add(new SecureRandom().nextInt(arrayList.size()), newOne);
        //check that size of ArrayList was increased
        assertEquals(11, arrayList.size());
        //Remove element newOne from ArrayList and check that .remove() returns true
        assertTrue(arrayList.remove(newOne));
        //check that size of ArrayList was decreased and ArrayList does not include newOne element
        assertAll("arrayList",
                () -> assertEquals(10, arrayList.size()),
                () -> assertFalse(arrayList.contains(newOne)));
    }

    @Test
    @DisplayName("Remove nonexistent element from ArrayList and expect that .remove() will return false")
    void removeNonexistentElementFromList() {
        //check that initial size of ArrayList is 10
        assertEquals(10, arrayList.size());
        //initialize new example of Person class
        Person newOne = new Person(86);
        //Remove newOne element from ArrayList. Expect .remove() will return false and check that size of ArrayList was not changed
        assertAll("blabla",
                () -> assertFalse(arrayList.remove(newOne)),
                () -> assertEquals(10, arrayList.size()),
                () -> assertFalse(arrayList.contains(newOne))
        );
    }

    @Test
    @DisplayName("Remove some collection from ArrayList")
    void removeAllCollectionFromArrayList() {
        //check that initial size of ArrayList is equal to 10
        assertEquals(10, arrayList.size());
        //initialize new ArrayList which will be added and removed from test list
        List<Person> additionOne = new ArrayList<>();
        for (int i=0; i<5; i++) {
            additionOne.add(new Person(new SecureRandom().nextInt(100)));
        }
        //add additionOne collection into arrayList.
        // Save initial size of arrayList
        int initialSize = arrayList.size();
        arrayList.addAll(additionOne);
        //check that size was increased and arrayList include additionOne
        assertAll("fhdf",
                () -> assertEquals(15, arrayList.size()),
                () -> assertTrue(arrayList.containsAll(additionOne))
        );
        //Remove collection additionOne from arrayList and expect that .removeAll will return true;
        assertAll("fgdfg",
                () -> assertTrue(arrayList.removeAll(additionOne)),
                () -> assertEquals(initialSize, arrayList.size()),
                () -> assertFalse(arrayList.containsAll(additionOne))
        );
    }

    @Test
    @DisplayName("Remove un match collection from ArrayList")
    void removeUnMatchCollectionFromArrayList() {
        //check that initial size of ArrayList is equal to 10
        assertEquals(10, arrayList.size());
        //save initial size of tested arraylist
        int initialSize = arrayList.size();
        //initialize new ArrayList which will be added to tested ArrayList
        List<Person> additionOne = new ArrayList<>();
        for(int i=0; i<5; i++) {
            additionOne.add(new Person(new SecureRandom().nextInt(100)));
        }
        arrayList.addAll(additionOne);
        //add new element to additionOne arraylist to make it unmatch with tested arraylist
        additionOne.add(new Person(new SecureRandom().nextInt(100)));
        //Remove collection additionOne from arrayList and expect that .removeAll will return true;
        assertAll("gfdgdfghgjhg",
                () -> assertTrue(arrayList.removeAll(additionOne)),
                () -> assertEquals(initialSize, arrayList.size()),
                () -> assertFalse(arrayList.containsAll(additionOne))
        );
    }

    @Test
    @DisplayName("Remove fully unmatch collection from arrayList")
    void removeFullUnMatchCollectionFromArrayList() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //initialize new ArrayList which will be deleted from arrayList
        List<Person> additionOne = new ArrayList<>();
        for(int i=0; i<5; i++) {
            additionOne.add(new Person(new SecureRandom().nextInt(100)));
        }
        //remove additionOne collection from arrayList. Expect that .removeAll() will return false
        assertAll("fgjfggeyubvd",
                () -> assertFalse(arrayList.removeAll(additionOne)),
                () -> assertEquals(initialSize, arrayList.size()),
                () -> assertFalse(arrayList.containsAll(additionOne))
        );
    }

    @Test
    @DisplayName("Remove null collection from arrayList")
    void removeNullCollectionFromArrayList() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //initialize null List collection which will be removed from arrayList
        List<Person> additionOne = null;
        //remove additionOne List from arrayList. Expect that .removeAll will return NPE
        assertAll("dfhgfjkkgf",
                () -> assertThrows(NullPointerException.class, () -> arrayList.removeAll(additionOne)),
                () -> assertEquals(initialSize, arrayList.size())
        );
    }

    @Test
    @DisplayName("Remove element from ArrayList when this element are shown twice in ArrayList")
    void removeElementFromArrayListIfElementIncludedTwice() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //Make new Person element;
        Person newPerson = new Person(new SecureRandom().nextInt(100));
        //add newPerson to arrayList twice on random place
        arrayList.add(new SecureRandom().nextInt(arrayList.size()), newPerson);
        arrayList.add(new SecureRandom().nextInt(arrayList.size()), newPerson);
        //save last index of newPerson in arrayList
        int lastIndexOfPerson = arrayList.lastIndexOf(newPerson);
        //remove newPerson from arrayList and check that first match element was deleted
        assertAll("fjkhkhlh",
                () -> assertTrue(arrayList.remove(newPerson)),
                () -> assertEquals(initialSize+1, arrayList.size()),
                () -> assertTrue(arrayList.contains(newPerson)),
                () -> assertEquals(lastIndexOfPerson-1, arrayList.indexOf(newPerson)),
                () -> assertEquals(arrayList.indexOf(newPerson), arrayList.lastIndexOf(newPerson))
        );
    }

    @Test
    @DisplayName("Remove element from arrayList via removeIf()")
    void removeElementViaRemoveIfFromArrayList() {
        //save initialSize of arrayList
        int initialSize = arrayList.size();
        //initialize new Person element
        Person newPerson = new Person(10);
        arrayList.add(new SecureRandom().nextInt(arrayList.size()), newPerson);
        //remove all elements which age >=10. Expect that removeIf will return true;
        assertAll("fghhkfcd",
                () -> assertTrue(arrayList.removeIf((Person person) -> person.getAge() >=10)),
                () -> assertNotEquals(initialSize, arrayList.size())
        );
    }

    @Test
    @DisplayName("Retain all elements of Collection from arrayList")
    void retainCollectionFromArrayList() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //save original state of arrayList
        List<Person> originalArrayList = new ArrayList<>(arrayList);
        //initialize new ArrayList of Persons
        List<Person> additionList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            additionList.add(new Person(new SecureRandom().nextInt(100)));
        }
        //add additionList to arrayList
        arrayList.addAll(additionList);
        //using retainAll on arrayList. Expect that arrayList will include only elements from additionList
        assertAll("ghbff",
                () -> assertTrue(arrayList.retainAll(additionList)),
                () -> assertEquals(additionList.size(), arrayList.size()),
                () -> assertEquals(additionList, arrayList),
                () -> assertFalse(arrayList.containsAll(originalArrayList))
        );
    }

    @Test
    @DisplayName("Retain null collection from arrayList")
    void retainNullCollectionFromArrayList() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //save initial arrayList
        List<Person> originalArrayList = new ArrayList<>(arrayList);
        //initialize null ArrayList
        List<Person> additionList = null;
        //retain null collection from arraylist
        assertAll("kljkgh",
                () -> assertThrows(NullPointerException.class, () -> arrayList.retainAll(additionList)),
                () -> assertEquals(initialSize, arrayList.size()),
                () -> assertEquals(originalArrayList, arrayList)
        );
    }

    @Test
    @DisplayName("Retain non exist collection in arrayList from arrayList")
    void retainNonExistCollectionFromArrayList() {
        //save initial size of arrayList
        int initialSize = arrayList.size();
        //save original state of arrayList
        List<Person> originalArrayList = new ArrayList<>(arrayList);
        //initialize collection which will try to retain from arrayList
        List<Person> additionList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            additionList.add(new Person(new SecureRandom().nextInt(100)));
        }
        //retain additionList from arrayList. Expect that retainAll will erase arrayList
        assertAll("dghgj",
                () -> assertTrue(arrayList.retainAll(additionList)),
                () -> assertNotEquals(initialSize, arrayList.size()),
                () -> assertNotEquals(originalArrayList, arrayList),
                () -> assertFalse(arrayList.containsAll(additionList)),
                () -> assertThat(arrayList, is(empty()))
        );
    }

    @Test
    @DisplayName("Retain same collection with original one")
    void retainSameCollection() {
        //save original size of arrayList
        int initialSize = arrayList.size();
        //initialize copy of arrayList
        List<Person> originalArrayList = new ArrayList<>(arrayList);
        //retain originalArrayList from arrayList
        assertAll("fghc",
                () -> assertFalse(arrayList.retainAll(originalArrayList)),
                () -> assertEquals(originalArrayList, arrayList),
                () -> assertEquals(initialSize, arrayList.size()),
                () -> assertThat(arrayList, is(not(empty())))
        );
    }
}
