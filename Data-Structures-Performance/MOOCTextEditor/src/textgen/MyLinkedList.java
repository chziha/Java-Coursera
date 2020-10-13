package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);;
		tail = new LLNode<E>(null);;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) throws NullPointerException {
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("The element cannot be null.");
		}
		LLNode<E> node = new LLNode<E>(element);
		node.prev = tail.prev;
		node.next = tail;
		tail.prev.next = node;
		tail.prev = node;
		
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) throws IndexOutOfBoundsException {
		// TODO: Implement this method.
		if (index >= size || index < 0 || size == 0) {
			throw new IndexOutOfBoundsException("The index is out of bounds.");
		}
		LLNode<E> curr = head;
		for (int i = 0; i <= index; i++) {
			curr = curr.next;
		}
		return curr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) {
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("The element cannot be null.");
		}
		if ((index >= size && size > 0) || (index > size && size == 0) || index < 0) {
			throw new IndexOutOfBoundsException("The index is out of bounds.");
		}
		
		LLNode<E> node = new LLNode<E>(element);
		// Find the node to add the new element after
		LLNode<E> curr = head;
		for (int i = 0; i <= index; i++) {
			curr = curr.next;
		}
		node.next = curr;
		node.prev = curr.prev;
		curr.prev.next = node;
		curr.prev = node;
		
		size++;
	}

	/** Return the size of the list */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// TODO: Implement this method
		if (index >= size || index < 0 || size == 0) {
			throw new IndexOutOfBoundsException("The index is out of bounds.");
		}
		LLNode<E> curr = head;
		for (int i = 0; i <= index; i++) {
			curr = curr.next;
		}
		curr.prev.next = curr.next;
		curr.next.prev = curr.prev;
		curr.prev = null;
		curr.next = null;
		
		size--;
		
		return curr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// TODO: Implement this method
		if (index >= size || index < 0 || size == 0) {
			throw new IndexOutOfBoundsException("The index is out of bounds.");
		}
		if (element == null) {
			throw new NullPointerException("The element cannot be null.");
		}
		
		LLNode<E> curr = head;
		for (int i = 0; i <= index; i++) {
			curr = curr.next;
		}
		curr.data = element;
		return curr.data;
	}   
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}
		
	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
		this.data = e;
		this.prev = prev;
		this.next = next;
	}
}
