package com.equiv.java.lists.copyonwritearray;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.*;

class RemoveDataFromCopyOnWriteArrayList {

    private CopyOnWriteArrayList<Person> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    private int initSize;

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < 20;i++) {
            copyOnWriteArrayList.add(new Person());
        }
        initSize = copyOnWriteArrayList.size();
    }

    @Test
    @DisplayName("Remove element from CopyOnWriteArrayList by object")
    void removeElementFromCopyOnWriteArrayListByObject() {
        //initialize new instance of Person class
        Person person = new Person();
        //add person into copyOnWriteArrayList twice
        copyOnWriteArrayList.add(person);
        copyOnWriteArrayList.add(person);
        initSize = copyOnWriteArrayList.size();
        //remove person from copyOnWriteArrayList
        assertTrue(copyOnWriteArrayList.remove(person));
        //check that size of copyOnWriteArrayList was decreased and copyOnWriteArrayList include person
        assertEquals(initSize, copyOnWriteArrayList.size()+1);
        assertTrue(copyOnWriteArrayList.contains(person));
        initSize = copyOnWriteArrayList.size();
        //remove person from copyOnWriteArrayList
        assertTrue(copyOnWriteArrayList.remove(person));
        //check that size of copyOnWriteArrayList was decreased and copyOnWriteArrayList is not including person
        assertEquals(initSize, copyOnWriteArrayList.size()+1);
        assertFalse(copyOnWriteArrayList.contains(person));
        initSize = copyOnWriteArrayList.size();
        //try to remove person again
        assertFalse(copyOnWriteArrayList.remove(person));
        //check that size of copyOnWriteArrayList was not changed and person is not included into copyOnWriteArrayList
        assertEquals(initSize, copyOnWriteArrayList.size());
        assertFalse(copyOnWriteArrayList.contains(person));
        //try to remove null from copyOnWriteArrayList
        assertFalse(copyOnWriteArrayList.remove(null));
    }

    @Test
    @DisplayName("Remove element from CopyOnWriteArrayList by index")
    void removeElementFromCopyOnWriteArrayListByIndex() {
        //initialize new instance of Person class
        Person person = new Person();
        //add peron into copyOnWriteArrayList
        copyOnWriteArrayList.add(person);
        initSize = copyOnWriteArrayList.size();
        //remove last element from copyOnWriteArrayList
        assertEquals(person, copyOnWriteArrayList.remove(copyOnWriteArrayList.size()-1));
        //check that size of copyOnWriteArrayList was decreased and person is not cinluded into copyOnWriteArrayList
        assertEquals(initSize, copyOnWriteArrayList.size()+1);
        assertFalse(copyOnWriteArrayList.contains(person));
        //try to remove element from non existent index
        assertThrows(IndexOutOfBoundsException.class, () -> copyOnWriteArrayList.remove(copyOnWriteArrayList.size()+1));
    }

    @Test
    @DisplayName("Remove element from CopyOnWriteArrayList satisfies condition")
    void removeElementFromCopyOnWriteArrayListWithCondition() {
        //null
        assertThrows(NullPointerException.class, () -> copyOnWriteArrayList.removeIf(null));
        //try to remove all elements where age is more than 50
        assertTrue(copyOnWriteArrayList.removeIf(x -> x.getAge()>50));
        //check that size of copyOnWriteArrayList was changed
        assertNotEquals(initSize, copyOnWriteArrayList.size());
        initSize = copyOnWriteArrayList.size();
        //try to remove all elements where age is more that 100
        assertFalse(copyOnWriteArrayList.removeIf(x -> x.getAge()>100));
        //check that size of copyOnWriteArrayList was not changed
        assertEquals(initSize, copyOnWriteArrayList.size());
    }

    @Test
    @DisplayName("Remove collection from CopyOnWriteArrayList")
    void removeCollectionFromCopyOnWriteArrayList() {
        //initialize new collection
        List<Person> personList = new LinkedList<>();
        for (int i=0; i < 10; i++ ) {
            personList.add(new Person());
        }
        //add person list into copyOnWriteArrayList twice
        copyOnWriteArrayList.addAll(personList);
        copyOnWriteArrayList.addAll(personList);
        initSize = copyOnWriteArrayList.size();
        //remove personList from copyOnWriteArrayList
        assertTrue(copyOnWriteArrayList.removeAll(personList));
        //check that size of copyOnWriteArrayList was decreased and person is not present in copyOnWriteArrayList
        assertEquals(initSize, copyOnWriteArrayList.size()+2*personList.size());
        assertFalse(copyOnWriteArrayList.containsAll(personList));
        initSize = copyOnWriteArrayList.size();
        //remove personList from copyOnWriteArrayList again
        assertFalse(copyOnWriteArrayList.removeAll(personList));
        //check that size of copyOnWriteArrayList was not changed
        assertEquals(initSize, copyOnWriteArrayList.size());
        //try to remove null collection from copyOnWriteArrayList
        assertThrows(NullPointerException.class, () -> copyOnWriteArrayList.removeAll(null));
    }

    @Test
    @DisplayName("Retain collection from CopyOnWriteArrayList")
    void retainCollectionFromCopyOnWriteArrayList() {
        //initialize new collection
        List<Person> personList = new ArrayList<>();
        //fill personList with some elements from copyOnWriteArrayList
        for (int i=0; i < 10; i++) {
            personList.add(copyOnWriteArrayList.get(new SecureRandom().nextInt(copyOnWriteArrayList.size())));
        }
        //retain personList from copyOnWriteArrayList
        assertTrue(copyOnWriteArrayList.retainAll(personList));
        //check that size of copyOnWriteArrayList was changed
        assertNotEquals(initSize, copyOnWriteArrayList.size());
        initSize = copyOnWriteArrayList.size();
        //retain from copyOnWriteArrayList himself
        assertFalse(copyOnWriteArrayList.retainAll(copyOnWriteArrayList));
        //check that size of copyOnWriteArrayList was not changed
        assertEquals(initSize, copyOnWriteArrayList.size());
        //retain null collection from copyOnWriteArrayList
        assertThrows(NullPointerException.class, () -> copyOnWriteArrayList.retainAll(null));
        //check that size of copyOnWriteArrayList was not changed
        assertEquals(initSize, copyOnWriteArrayList.size());
        //retain empty collection from copyOnWriteArrayList
        assertTrue(copyOnWriteArrayList.retainAll(new ArrayList<Person>()));
        //check that copyOnWriteArrayList is now empty
        assertThat(copyOnWriteArrayList, is(empty()));
    }
}
