package com.equiv.java;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

/**
 * Unit test for simple App.
 */
class AppTest
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
        assertThat(arrayList, contains(1,2,3,25));
        assertThat(arrayList.size(), is(4));
        arrayList.add(1,98);
        assertThat(arrayList, contains(1,98,2,3,25));
        assertThat(arrayList.size(), is(5));
        arrayList.addAll(additionalArrayList);
        assertThat(arrayList, contains(1,98,2,3,25,1,2,3,4,5));
        assertThat(arrayList.size(), is(10));
        arrayList.remove(new Integer(98));
        assertThat(arrayList, contains(1,2,3,25,1,2,3,4,5));
        assertThat(arrayList.size(), is(9));
        List<Integer> cutArrayList = arrayList.subList(4,8);
        assertThat(cutArrayList, IsCollectionWithSize.<Integer>hasSize(4));
        assertThat(cutArrayList, contains(1,2,3,4));
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
        assertThat(linkedList, contains("a", "b", "c", "d", "e", "f"));
        linkedList.remove("f");
        assertThat(linkedList, contains("a", "b", "c", "d", "e"));
        assertThat(linkedList, IsCollectionWithSize.<String>hasSize(5));
        List<String> cutLinkedList = linkedList.subList(2,5);
        assertThat(cutLinkedList.size(), is(3));
        assertThat(cutLinkedList, contains("c", "d", "e"));
    }

    @Test
    @DisplayName("Checking HashSet")
    void testForHashSet() {
        Set<String> hashSet = new HashSet<String>();
        Set<String> additionalHashSet = new HashSet<String>();
        hashSet.add("a");
        hashSet.add("b");
        hashSet.add("c");

        additionalHashSet.add("c");
        additionalHashSet.add("b");
        additionalHashSet.add("d");
        additionalHashSet.add("e");

        assertThat(hashSet.size(), is(3));
        assertThat(hashSet, hasItem("b"));
        hashSet.add("b");
        assertThat(hashSet, IsCollectionWithSize.<String>hasSize(3));
        hashSet.addAll(additionalHashSet);
        assertThat(hashSet.size(), is(5));
        assertThat(hashSet, containsInAnyOrder("a", "c", "b", "d", "e"));
        hashSet.remove("c");
        assertThat(hashSet.size(), is(4));
        assertThat(hashSet, not(hasItem("c")));
    }

//    @Disabled
    @Test
    @DisplayName("Checking TreeSet")
    void testForTreeSet() {
        Set<Integer> treeSet = new TreeSet<Integer>();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(21);
        treeSet.add(7);
        treeSet.add(4);

        assertThat(treeSet.size(), is(6));
        assertThat(treeSet, contains(1,2,3,4,7,21));
        treeSet.add(19);
        assertThat(treeSet.size(), is(7));
        assertThat(treeSet, contains(1,2,3,4,7,19,21));
        treeSet.remove(2);
        assertThat(treeSet.size(), is(6));
        assertThat(treeSet, not(hasItem(2)));
        assertThat(treeSet, contains(1,3,4,7,19,21));
        treeSet.add(19);
        assertThat(treeSet.size(), is(6));
    }

    @Test
    @DisplayName("Checking LinkedHashSet")
    void testForLinkedHashSet() {
        Set<Integer> linkedHashSet = new LinkedHashSet<Integer>();
        linkedHashSet.add(5);
        linkedHashSet.add(1);
        linkedHashSet.add(7);
        linkedHashSet.add(-1);
        linkedHashSet.add(0);

        assertThat(linkedHashSet.size(), is(5));
        assertThat(linkedHashSet, contains(5,1,7,-1,0));
        linkedHashSet.add(7);
        assertThat(linkedHashSet.size(), is(5));
        assertThat(linkedHashSet, contains(5,1,7,-1,0));
        linkedHashSet.add(20);
        assertThat(linkedHashSet.size(), is(6));
        assertThat(linkedHashSet, contains(5,1,7,-1,0,20));
        linkedHashSet.remove(7);
        assertThat(linkedHashSet.size(), is(5));
        assertThat(linkedHashSet, contains(5,1,-1,0,20));
    }
}
