package com.equiv.java.lists.vector;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SplitVector {

    private List<Person> vector = new Vector<>();

    private int initSize;

    private Spliterator<Person> spliterator = vector.spliterator();

    @BeforeEach
    void beforeEach() {
        for (int i=0; i<10; i++) {
            vector.add(new Person());
        }
        initSize = vector.size();
    }

    @Test
    @DisplayName("Check that spliterator.getComparator will return IllegalStateException")
    void gettingIllegalStateExceptionFromGetComparator() {
        assertThrows(IllegalStateException.class, () -> spliterator.getComparator());
        assertEquals(initSize, vector.size());
    }

    @Test
    @DisplayName("Splitting Vector into 2 part when the size of LinkedList is even")
    void splittingVector() {
        //initialize second spliterator which split first spliterator
//        System.out.println("Spliterator size = " + spliterator.trySplit().getExactSizeIfKnown());
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that size of those spliterators is equal
        assertEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that size of original vector was not changed
        assertEquals(initSize, vector.size());
        //check that sum of sizes of spliterators is equal to vector size
        assertEquals(vector.size(), spliterator.getExactSizeIfKnown() + spliterator1.getExactSizeIfKnown());
        //initialize additional vector instance which will include elements from spliterators
        List<Person> personVector = new Vector<>();
        //add elements into personVector from spliterator first via forEachRemaining
        spliterator.forEachRemaining(personVector::add);
        //add elements into personVector from spliterator1 via tryAdvance
        while (spliterator1.tryAdvance(personVector::add)){}
        //check that personVector is not equal to original vector but sizes are the same
        assertAll("asdasd",
                () -> assertNotEquals(vector, personVector),
                () -> assertEquals(initSize, personVector.size())
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
    @DisplayName("Splitting Vector into 2 part if the size of Vector is odd")
    void splittingVectorPartTwo() {
        //add one more Person instance to vector to make size of Vector odd
        vector.add(new Person());
        //initialize new spliterator which split first spliterator
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that sizes of spliterator and spliterator1 is not the same
        assertNotEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that sum of sizes of spliterator and spliterator1 is equal to size of vector
        assertEquals(vector.size(), spliterator1.getExactSizeIfKnown() + spliterator.getExactSizeIfKnown());
        //initialize additional Vector instance which will include elements from spliterators
        List<Person> personVector = new Vector<>();
        //add elements into personVector from spliterator1 first via tryAdvance
        while (spliterator1.tryAdvance(personVector::add)){}
        //add elements into personVector from spliterator via forEachRemaining
        spliterator.forEachRemaining(personVector::add);
        //check that personVector is equal to original vector and sizes are same too
        assertAll("dsfsdf",
                () -> assertEquals(vector, personVector),
                () -> assertEquals(vector.size(), personVector.size())
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
        assertThrows(IndexOutOfBoundsException.class, () -> vector.subList(20, 32));
        assertThrows(IndexOutOfBoundsException.class, () -> vector.subList(0, 20));
        assertThrows(IllegalArgumentException.class, () -> vector.subList(5, 0));
    }
}
