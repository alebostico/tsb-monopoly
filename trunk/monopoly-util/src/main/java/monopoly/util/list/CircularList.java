/**
 * 
 */
package monopoly.util.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CircularList<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Node<T> head;

	public CircularList() {
		head = null;
	}

	/*
	 * Description: Deletes the head node from the linked list and returns the
	 * head node's key. Running Time: O(1) Precondition: Linked list must not be
	 * null Postcondition: The head node is deleted and its key is returned.
	 */
	public T pop() {
		if (isEmpty())
			return null;

		T k = head.getKey();

		if (head == head.getNext()) {
			head = null;
		} else {
			Node<T> f = head, b = head.getPrev();
			b.setNext(f.getNext());
			head = head.getNext();
			head.setPrev(b);
		}

		return k;
	}

	/*
	 * Description: Deletes the tail node from the linked list and returns its
	 * node's key. Running Time: O(1) Precondition: Linked list must not be null
	 * Postcondition: The tail node is deleted and its key is returned.
	 */
	public T pop_back() {
		if (isEmpty())
			return null;

		T k = head.getPrev().getKey();

		if (head == head.getPrev()) {
			head = null;
		} else {
			Node<T> b = head.getPrev();
			b.getPrev().setNext(head);
			head.setPrev(b.getPrev());
		}

		return k;
	}

	public void print() {
		Node<T> p = head;

		if (isEmpty())
			return;
		else if (p == p.getNext()) {
			System.out.println(p.getKey());
		} else {
			do {
				System.out.println(p.getKey());
				p = p.getNext();
			} while (p != head);
		}
	}

	/*
	 * Description: Allocates a new node x and inserts the node into the back of
	 * the linked list. Running Time: O(1) Precondition: Linked list must be
	 * instantiated. Postcondition: A node x with the key passed in through k is
	 * inserted into the back of the linked list.
	 */
	public void push_back(T k) {
		Node<T> x = new Node<T>(k);
		push_back(x);
	}

	/*
	 * Description: Inserts node x into the back of the linked list. Running
	 * Time: O(1) Precondition: Node x must be allocated Postcondition: A node x
	 * is inserted into the back of the linked list.
	 */
	private void push_back(Node<T> x) {
		if (isEmpty()) {
			head = x;
			head.setNext(head);
			head.setPrev(head);
		} else {
			Node<T> p = head.getPrev();
			p.setNext(x);
			x.setPrev(p);
			x.setNext(head);
			head.setPrev(x);
		}
	}

	/*
	 * Description: Allocates a new node x and inserts the node into the front
	 * of the linked list. Running Time: O(1) Precondition: Linked list must be
	 * instantiated. Postcondition: A node x with the key passed in through k is
	 * inserted into the front of the linked list.
	 */
	public void push_front(T k) {
		Node<T> x = new Node<T>(k);
		push_front(x);
	}

	/*
	 * Description: Inserts node x into the front of the linked list. Running
	 * Time: O(1) Precondition: Node x must be allocated Postcondition: A node x
	 * is inserted into the front of the linked list.
	 */
	private void push_front(Node<T> x) {
		if (isEmpty()) {
			head = x;
			head.setNext(head);
			head.setPrev(head);
		} else {
			Node<T> b = head.getPrev();
			b.setNext(x);
			head.setPrev(x);
			x.setNext(head);
			x.setPrev(b);
			head = x;
		}
	}

	public int size() {
		int out = 0;
		Node<T> tmp;
		if (!isEmpty()) {
			if (head == head.getNext()) {
				out = 1;
			} else {
				tmp = head;
				do {
					tmp = tmp.getNext();
					out++;
				} while (tmp != head);
			}
		}
		return out;
	}

	public List<T> toList() {

		// pay attention to the initialization of the array
		List<T> out = new ArrayList<T>();

		Node<T> tmp = head;
		for (int i = 0; i < size(); i++) {
			out.add(tmp.getKey());
			tmp = tmp.getNext();
		}
		return out;
	}

	public Object[] toArray() {
		return toList().toArray();
	}

	public T top() {
		if (isEmpty())
			return null;

		return head.getKey();
	}

	public boolean isEmpty() {
		return (head == null);
	}

}
