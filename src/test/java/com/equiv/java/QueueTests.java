package com.equiv.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueTests {

    @Test
    @DisplayName("Checking LinkedList as Queue")
    void linkedListAsQueue() {
        Queue<String> linkedList = new LinkedList<String>(Arrays.asList("a","b","c","d","e"));
        assertThat(linkedList.size(), is(5));
        assertThat(linkedList, contains("a","b","c","d","e"));
        linkedList.add("f");
        assertThat(linkedList.size(), is(6));
        assertThat(linkedList, contains("a","b","c","d","e","f"));
        linkedList.offer("d");
        assertThat(linkedList.size(), is(7));
        assertThat(linkedList, contains("a","b","c","d","e","f","d"));
        assertThat(linkedList.remove(), is("a"));
        assertThat(linkedList.size(), is(6));
        assertThat(linkedList, contains("b","c","d","e","f","d"));
        assertThat(linkedList.poll(), is("b"));
        assertThat(linkedList.size(), is(5));
        assertThat(linkedList, contains("c","d","e","f","d"));
        assertThat(linkedList.peek(), is("c"));
        assertThat(linkedList.size(), is(5));
        assertThat(linkedList, contains("c","d","e","f","d"));
        assertThat(linkedList.element(), is("c"));
        assertThat(linkedList.size(), is(5));
        assertThat(linkedList, contains("c","d","e","f","d"));
        linkedList.clear();
        assertThrows(NoSuchElementException.class, linkedList::remove);
        assertThat(linkedList.poll(), is(nullValue()));
    }

    @Test
    @DisplayName("Checking PriorityQueue")
    void priorityQueue() {

        Queue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> (int) o));

        priorityQueue.offer(20);
        priorityQueue.offer(1);
        priorityQueue.offer(-21);
        priorityQueue.offer(0);
        priorityQueue.offer(11);
        priorityQueue.offer(37);
        assertThat(priorityQueue.size(), is(6));
        assertThat(priorityQueue.peek(), is(-21));
        assertThat(priorityQueue.size(), is(6));
        priorityQueue.add(5);
        assertThat(priorityQueue.size(), is(7));
        priorityQueue.offer(-25);
        assertThat(priorityQueue.size(), is(8));
        assertThat(priorityQueue.poll(), is(-25));
        assertThat(priorityQueue.size(), is(7));
        assertThat(priorityQueue.remove(), is(-21));
        assertThat(priorityQueue.size(), is(6));
        assertThat(priorityQueue.element(), is(0));
        assertThat(priorityQueue.size(), is(6));
        priorityQueue.clear();
        assertThat(priorityQueue.size(), is(0));
        assertThrows(NoSuchElementException.class, priorityQueue::remove);
        assertThat(priorityQueue.poll(), is(nullValue()));
    }
}
