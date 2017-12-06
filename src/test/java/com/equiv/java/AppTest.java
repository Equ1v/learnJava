package com.equiv.java;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    @DisplayName("Checking ArrayList")
    void testForArrayList() {
        List<Integer> arrayList = new ArrayList<Integer>();
        for(int i=0; i<3; i++) {
            arrayList.add(i+1);
        }
        List<Integer>additionalArrayList = new ArrayList<Integer>();
        for(int i=0; i<5; i++) {
            additionalArrayList.add(i+1);
        }

        assertThat(arrayList, IsCollectionWithSize.<Integer>hasSize(3));
        assertThat(arrayList, contains(1,2,3));
        assertThat(arrayList, not(hasItem(4)));
        arrayList.add(25);
        assertThat(arrayList, hasItem(25));
        arrayList.add(1,98);
        assertThat(arrayList.get(1), equalTo(98));
        arrayList.addAll(additionalArrayList);
        assertThat(arrayList.containsAll(additionalArrayList), is(true));
        assertThat(arrayList.size(), is(10));
        arrayList.remove(new Integer(98));
        assertThat(arrayList, not(hasItem(98)));
        List<Integer> cutArrayList = arrayList.subList(4,8);
        assertThat(cutArrayList, IsCollectionWithSize.<Integer>hasSize(4));
    }

    @Test
    @DisplayName("Checking LinkedList")
    void testForLinkedList() {
        List<String> linkedList = new LinkedList<String>();
        List<String> additionalLinkedList = new LinkedList<String>();

        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");

        additionalLinkedList.add("d");
        additionalLinkedList.add("e");
        additionalLinkedList.add("f");

        assertThat(linkedList, IsCollectionWithSize.<String>hasSize(3));
        assertThat(linkedList, contains("a", "b", "c"));
        assertThat(linkedList, not(hasItem("d")));
        linkedList.addAll(additionalLinkedList);
        assertThat(linkedList.size(), is(6));
        assertThat(linkedList.containsAll(additionalLinkedList), is(true));
        assertThat(linkedList, hasItem("e"));
        linkedList.remove("f");
        assertThat(linkedList.containsAll(additionalLinkedList), is(false));
        assertThat(linkedList, IsCollectionWithSize.<String>hasSize(5));
        List<String> cutLinkedList = linkedList.subList(2,5);
        assertThat(cutLinkedList.size(), is(3));
    }
}
