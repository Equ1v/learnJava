package com.equiv.java.lists.stack;

import com.equiv.java.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class StackTestSuite {

    private Stack<Person> stack = new Stack<>();

    private int initSize = stack.size();

    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < new SecureRandom().nextInt(20); i++) {
            stack.add(new Person());
        }
        initSize = stack.size();
    }

    @Test
    @DisplayName("Add new element into Stack")
    void addNewElementIntoStack() {
        //initialize new instance of Person class
        Person person = new Person();
        //add person into stack
        assertEquals(person, stack.push(person));
        //check that size of stack was changed
        assertEquals(initSize, stack.size() - 1);
        initSize = stack.size();
        //add null into stack
        assertNull(stack.push(null));
        //check that size of stack was changed
    }

    @Test
    @DisplayName("Get element from Stack")
    void getElementFromStack() {
        //initialize empty instance of Stack class
        Stack<Person> personStack = new Stack<>();
        //try to get element from empty stack. Expected EmptyStackException
        assertThrows(EmptyStackException.class, () -> personStack.peek());
        assertThrows(EmptyStackException.class, () -> personStack.pop());
        //get element from stack via peek()
        assertEquals(stack.lastElement(), stack.peek());
        //check that size of stack was not changed
        assertEquals(initSize, stack.size());
        //save last element from stack
        Person person = stack.peek();
        //get element from stack via pop()
        assertEquals(person, stack.pop());
        //check that size of stack was changed and last element is not equal to person
        assertEquals(initSize, stack.size() + 1);
        assertNotEquals(person, stack.peek());
    }

    @Test
    @DisplayName("search in Stack")
    void searchElementInStack() {
        //initialize new instance of Person class
        Person person = new Person();
        //search element which does not exist in stack
        assertEquals(-1, stack.search(person));
        //add element into stack and check position of it in stack
        stack.push(person);
        assertEquals(1, stack.search(person));
        //add more random elements into stack
        for (int i = 0; i < 5; i++) {
            stack.push(new Person());
        }
        //check position of person in stack
        assertEquals(6, stack.search(person));
    }
}
