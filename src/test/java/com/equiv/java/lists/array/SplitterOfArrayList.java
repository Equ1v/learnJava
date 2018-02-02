package com.equiv.java.lists.array;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Spliterator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test suite for test ArrayList.Splitterator functionality")
public class SplitterOfArrayList {

    public int getInitSize() {
        return initSize;
    }

//    private class Person {
//        int age;
//        String name;
//
//        public Person(int age) {
//            this.age = age;
//
//            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprstuvwxyz";
//            StringBuilder stringBuilder = new StringBuilder();
//            int nameSize = 10;
//            while (nameSize>0) {
//                nameSize--;
//                char c = characters.toCharArray()[new SecureRandom().nextInt(characters.length())];
//                stringBuilder.append(c);
//            }
//            this.name = stringBuilder.toString();
//        }
//
//        public int getAge() {
//            return age;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public String toString() {
//            return "Person{" +
//                    "age=" + age +
//                    ", name='" + name + '\'' +
//                    '}';
//        }
//    }

    //initialize empty ArrayList
    private ArrayList<Person> arrayList = new ArrayList<>();

    //Save init size of arrayList
    private int initSize;

    //initialize Spliterator for arrayList
    private Spliterator<Person> spliterator = arrayList.spliterator();

    @BeforeEach
    void beforeEach() {
        for(int i=0; i<10; i++) {
            arrayList.add(new Person());
        }
        this.initSize = arrayList.size();
    }

    @Test
    @DisplayName("Check that spliterator.getComparator will return IllegalStateException")
    void gettingIllegalStateExceptionFromGetComparator() {
        assertThrows(IllegalStateException.class, () -> spliterator.getComparator());
        assertEquals(initSize, arrayList.size());
    }

    @Test
    @DisplayName("Splitting ArrayList into 2 part when the size of ArrayList is even")
    void splittingArrayList() {
        //initialize second spliterator which split first spliterator
//        System.out.println("Spliterator size = " + spliterator.trySplit().getExactSizeIfKnown());
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that size of those spliterators is equal
        assertEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that size of original arrayList was not changed
        assertEquals(initSize, arrayList.size());
        //check that sum of sizes of spliterators is equal to arrayList size
        assertEquals(arrayList.size(), spliterator.getExactSizeIfKnown() + spliterator1.getExactSizeIfKnown());
        //initialize additional ArrayList instance which will include elements from spliterators
        ArrayList<Person> personArrayList = new ArrayList<>();
        //add elements into personArrayList from spliterator first via forEachRemaining
        spliterator.forEachRemaining(personArrayList::add);
        //add elements into personArrayList from spliterator1 via tryAdvance
//        spliterator1.forEachRemaining(personArrayList::add);
        while (spliterator1.tryAdvance(personArrayList::add)){}
        //check that personArrayList is not equal to original arrayList but sizes are the same
        assertAll("asdasd",
                () -> assertNotEquals(arrayList, personArrayList),
                () -> assertEquals(initSize, personArrayList.size())
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
    @DisplayName("Splitting ArrayList into 2 part if the size of ArrayList is odd")
    void splittingArrayListPartTwo() {
        //add one more Person instance to arrayList to make size of arrayList odd
        arrayList.add(new Person());
        //initialize new spliterator which split first spliterator
        Spliterator<Person> spliterator1 = spliterator.trySplit();
        //check that sizes of spliterator and spliterator1 is not the same
//        System.out.println(spliterator1.getExactSizeIfKnown());
//        System.out.println(spliterator.getExactSizeIfKnown());
        assertNotEquals(spliterator1.getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
        //check that sum of sizes of spliterator and spliterator1 is equal to size of arrayList
        assertEquals(arrayList.size(), spliterator1.getExactSizeIfKnown() + spliterator.getExactSizeIfKnown());
        //initialize additional ArrayList instance which will include elements from spliterators
        ArrayList<Person> personArrayList = new ArrayList<>();
        //add elements into personalArrayList from spliterator1 first via tryAdvance
        while (spliterator1.tryAdvance(personArrayList::add)){}
        //add elements into personalArrayList from spliterator via forEachRemaining
        spliterator.forEachRemaining(personArrayList::add);
        //check that personArrayList is equal to original arrayList and sizes are same too
        assertAll("dsfsdf",
                () -> assertEquals(arrayList, personArrayList),
                () -> assertEquals(arrayList.size(), personArrayList.size())
        );
//        System.out.println(spliterator1.getExactSizeIfKnown());
//        System.out.println(spliterator.getExactSizeIfKnown());
        //check that spliterators are empty
        assertAll("sdfsdf",
                () -> assertEquals(0, spliterator1.getExactSizeIfKnown()),
                () -> assertEquals(0, spliterator.getExactSizeIfKnown())
        );
        //try to use tryAdvance on empty spliterator. Expect method will return false
        assertFalse(spliterator.tryAdvance(System.out::println));
    }

    @Test
    @DisplayName("Try to use null as action in tryAdvance and forEachRemaining")
    void spliteratorOfNullLists() {
        //use tryAdvance and forEachRemaining with null action. expect that it will throw NPE
        assertAll("NPE",
                () -> assertThrows(NullPointerException.class, () -> spliterator.tryAdvance(null)),
                () -> assertThrows(NullPointerException.class, () -> spliterator.forEachRemaining(null))
        );
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
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.subList(20, 32));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.subList(0, 20));
        assertThrows(IllegalArgumentException.class, () -> arrayList.subList(5, 0));
    }
}
