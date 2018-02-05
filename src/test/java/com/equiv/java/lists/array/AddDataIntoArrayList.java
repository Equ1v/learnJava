package com.equiv.java.lists.array;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Add data into ArrayList Test Suite")
class AddDataIntoArrayList {

    //Objects of this class will be used for testing add functionality of ArrayList
//    private class Person {
//        String name;
//        int age;
//
//        Person(String name, int age) {
//            this.name = name;
//            this.age = age;
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

    private List<Person> arrayList = new ArrayList<>();

    //Make pre-req data
    @BeforeEach
    void beforeEach() {
        arrayList.add(new Person());
        arrayList.add(new Person());
        arrayList.add(new Person());
        arrayList.add(new Person());
        arrayList.add(new Person());
    }

    @Test
    @DisplayName("Add new object to tale")
    void addNewPersonToTaleOfList() {
        //initialize new Person
        Person newPerson = new Person();
        //check that default size of ArrayList is 5
        assertThat(arrayList.size(), is(5));
        //Add new element to tale and check that method add() returns true
        assertThat(arrayList.add(newPerson), equalTo(true));
        //check that size of list was increased
        assertThat(arrayList.size(), is(6));
        //check that added element is a last element
        assertThat(arrayList.indexOf(newPerson), is(5));
        //add this Person twice. Expect that it will be added too
        assertEquals(arrayList.add(newPerson), true);
        //expect that size of ArrayList was increased
        assertEquals(arrayList.size(), 7);
        //expect that lastIndex of newPerson is 6
        assertEquals(arrayList.lastIndexOf(newPerson), 6);
        //expect that amount of newPersons is 2
        int k=0;
        for (Person person: arrayList) {
            if (person == newPerson)
                k++;
        }
        assertEquals(k, 2);
    }

    @Test
    @DisplayName("Insert new Person in the middle of the existing list")
    void insertPersonIntoList() {
        //initialize new Person
        Person newPerson = new Person();
        //Insert new Person into 2nd index
        arrayList.add(2, newPerson);
        //Check that list size was increased
        assertThat(arrayList.size(), is(6));
        //check that added object has 2nd index
        assertThat(arrayList.indexOf(newPerson), is(2));
    }

    @Test
    @DisplayName("Getting IndexOutOfBoundsException when add Person on nonexistent index")
    void getIndexOutOfBoundsException() {
        //initialize new Person
        Person newPerson = new Person();
        //Trying to add Person to nonexistent index and IndexOutOfBoundsException expected
        assertThrows(IndexOutOfBoundsException.class, ()-> arrayList.add(7, newPerson));
        //expect that size of the list was not increased
        assertThat(arrayList.size(), is(5));
        //expect that last element is not equal to newPerson
        assertThat(arrayList.get(arrayList.size()-1), is(not(newPerson)));
    }

    @Test
    @DisplayName("Add elements to tale of ArrayList from another collection ")
    void addAllElementsToTaleOfListFromAnotherCollection() {
        //Saving first ArrayList size
        int currentListSize = arrayList.size();
        //initialize additional collection from which we will take elements
        List<Person> anotherList = new ArrayList<>();
        anotherList.add(new Person());
        anotherList.add(new Person());
        anotherList.add(new Person());
        //expect that .addAll will return true;
        assertThat(arrayList.addAll(anotherList), is(true));
        //expect that size of arrayList is now 8
        assertThat(arrayList.size(), is(currentListSize+anotherList.size()));
        //expect that arrayList includes anotherList
        for (int i=currentListSize; i<arrayList.size();i++) {
            assertThat(arrayList.get(i), is(anotherList.get(i-currentListSize)));
        }
    }

    @Test
    @DisplayName("Add null Collection to ArrayList and expect NullPointerException was thrown")
    void addNullCollectionToArrayList() {
        //initialize null collection
        List<Person> nullList = null;
        //trying to add null collection to ArrayList. Expect that NPE would be thrown
        assertThrows(NullPointerException.class, () -> arrayList.addAll(nullList));
        //expect size of the list was not changed
        assertEquals(arrayList.size(), 5);
    }

    @Test
    @DisplayName("Add empty Collection to ArrayList and expect that .addAll() ill return false")
    void addEmptyCollectionToArrayList() {
        //initialize empty collection
        List<Person> emptyList = new ArrayList<>();
        //trying to add empty collection to ArrayList. Expect that .addAll() will return false
        assertThat(arrayList.addAll(emptyList), is(false));
        //expect that size of the ArrayList was not changed
        assertEquals(arrayList.size(), 5);
    }


    @Test
    @DisplayName("Add collection to ArrayList from some index")
    void addCollectionToArrayListFromSomeIndex() {
        //initialize Collection with data
        List<Person> anotherList = new ArrayList<>();
        anotherList.add(new Person());
        anotherList.add(new Person());
        anotherList.add(new Person());
        //save first size of ArrayList
        int firstArrayListSize = arrayList.size();
        //add some collection into arrayList from some index. Expect that addAll() will return true;
        assertEquals(true, arrayList.addAll(2, anotherList));
        //expect that size of the list was increased
        assertEquals(firstArrayListSize+anotherList.size(), arrayList.size());
        //expect that arrayList include anotherList
        assertEquals(true, arrayList.containsAll(anotherList));
    }

    @Test
    @DisplayName("Add collection to ArrayList from nonexistent index")
    void addCollectionToArrayListFromNonexistentIndex() {
        //initialize another collection
        List<Person> anotherList = new ArrayList<>();
        anotherList.add(new Person());
        anotherList.add(new Person());
        anotherList.add(new Person());
        //save initial size of test ArrayList
        int size = arrayList.size();
        //trying to add collection to ArrayList from nonexistent index. Expect IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.addAll(10, anotherList));
        //expect that size of ArrayList was not increased
        assertEquals(size, arrayList.size());
    }

    @Test
    @DisplayName("Add null collection to ArrayList from some index")
    void addNullCollectionToArrayListFromSomeIndex() {
        //initialize null collection
        List<Person> nullList = null;
        //trying to add nullList to arrayList from some index. Expect NPE
        assertThrows(NullPointerException.class, () -> arrayList.addAll(0, nullList));
    }
}
