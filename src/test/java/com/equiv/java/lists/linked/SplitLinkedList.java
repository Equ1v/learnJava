package com.equiv.java.lists.linked;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;

import static org.junit.jupiter.api.Assertions.*;

class SplitLinkedList {

    private List<Person> linkedList = new LinkedList<>();

    private int initSize;

    private Spliterator<Person> spliterator = linkedList.spliterator();

    @BeforeEach
    void beforeEach() {
        for (int i=0; i < 10; i++) {
            linkedList.add(new Person());
        }
        this.initSize = linkedList.size();
    }

    @Test
    @DisplayName("Check that spliterator.getComparator will return IllegalStateException")
    void gettingIllegalStateExceptionFromGetComparator() {
        assertThrows(IllegalStateException.class, () -> spliterator.getComparator());
        assertEquals(initSize, linkedList.size());
    }

    @Test
    @DisplayName("Splitting LinkedList into 2 part when the size of LinkedList is even")
    void splittingLinkedList() {
        //initialize second spliterator which split first spliterator
//        System.out.println("Spliterator size = " + spliterator.trySplit().getExactSizeIfKnown());
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that size of those spliterators is equal
        assertEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that size of original arrayList was not changed
        assertEquals(initSize, linkedList.size());
        //check that sum of sizes of spliterators is equal to linkedList size
        assertEquals(linkedList.size(), spliterator.getExactSizeIfKnown() + spliterator1.getExactSizeIfKnown());
        //initialize additional LinkedList instance which will include elements from spliterators
        List<Person> personLinkedList = new LinkedList<>();
        //add elements into personLinkedList from spliterator first via forEachRemaining
        spliterator.forEachRemaining(personLinkedList::add);
        //add elements into personLinkedList from spliterator1 via tryAdvance
        while (spliterator1.tryAdvance(personLinkedList::add)){}
        //check that personLinkedList is not equal to original linkedList but sizes are the same
        assertAll("asdasd",
                () -> assertNotEquals(linkedList, personLinkedList),
                () -> assertEquals(initSize, personLinkedList.size())
        );
        //check that spliterators are empty
        assertAll("sdfsdf",
                () -> assertEquals(0, spliterator1.getExactSizeIfKnown()),
                () -> assertEquals(0, spliterator.getExactSizeIfKnown())
        );
        //try to use tryAdvance on empty spliterator. Expect method will return false
        assertFalse(spliterator.tryAdvance(System.out::println));
    }

    @Test
    @DisplayName("Splitting LinkedList into 2 part if the size of LinkedList is odd")
    void splittingLinkedListPartTwo() {
        //add one more Person instance to linkedList to make size of LinkedList odd
        linkedList.add(new Person());
        //initialize new spliterator which split first spliterator
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that sizes of spliterator and spliterator1 is not the same
        assertNotEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that sum of sizes of spliterator and spliterator1 is equal to size of linkedList
        assertEquals(linkedList.size(), spliterator1.getExactSizeIfKnown() + spliterator.getExactSizeIfKnown());
        //initialize additional LinkedList instance which will include elements from spliterators
        List<Person> personLinkedList = new LinkedList<>();
        //add elements into personalLinkedList from spliterator1 first via tryAdvance
        while (spliterator1.tryAdvance(personLinkedList::add)){}
        //add elements into personalLinkedList from spliterator via forEachRemaining
        spliterator.forEachRemaining(personLinkedList::add);
        //check that personLinkedList is equal to original linkedList and sizes are same too
        assertAll("dsfsdf",
                () -> assertEquals(linkedList, personLinkedList),
                () -> assertEquals(linkedList.size(), personLinkedList.size())
        );
        //check that spliterators are empty
        assertAll("sdfsdf",
                () -> assertEquals(0, spliterator1.getExactSizeIfKnown()),
                () -> assertEquals(0, spliterator.getExactSizeIfKnown())
        );
        //try to use tryAdvance on empty spliterator. Expect method will return false
        assertFalse(spliterator.tryAdvance(System.out::println));
    }

    @Test
    @DisplayName("Checking characteristics of Spliterator")
    void checkingCharacteristics() {
        //use Characteristic() on Spliterator and expect that it will return 16464
        assertEquals(16464, spliterator.characteristics());
        //check characteristics
        assertAll("characteristics",
                () -> assertFalse(spliterator.hasCharacteristics(Spliterator.NONNULL)),
                () -> assertFalse(spliterator.hasCharacteristics(Spliterator.CONCURRENT)),
                () -> assertFalse(spliterator.hasCharacteristics(Spliterator.DISTINCT)),
                () -> assertFalse(spliterator.hasCharacteristics(Spliterator.IMMUTABLE)),
                () -> assertTrue(spliterator.hasCharacteristics(Spliterator.ORDERED)),
                () -> assertTrue(spliterator.hasCharacteristics(Spliterator.SIZED)),
                () -> assertFalse(spliterator.hasCharacteristics(Spliterator.SORTED)),
                () -> assertTrue(spliterator.hasCharacteristics(Spliterator.SUBSIZED))
        );
    }

    @Test
    @DisplayName("Getting all available exceptions for subList()")
    void subListExceptions() {
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.subList(20, 32));
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.subList(0, 20));
        assertThrows(IllegalArgumentException.class, () -> linkedList.subList(5, 0));
    }
}
