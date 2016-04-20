/**
 * 
 */
package monopoly.util.list;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class Node<T> implements Serializable {

	private static final long serialVersionUID = 7920968233073455193L;
	private T key;
	private Node<T> next;
	private Node<T> prev;

	public Node() {
		this.setNext(null);
		this.setPrev(null);
	}

	public Node(T k) {
		this.setNext(null);
		this.setPrev(null);
		this.setKey(k);
	}

	public Node(T k, Node<T> n) {
		setKey(k);
		setNext(n);
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public void setNext(Node<T> n) {
		next = n;
	}

	public Node<T> getNext() {
		return next;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	public String toString() {
		return key.toString();
	}
}
