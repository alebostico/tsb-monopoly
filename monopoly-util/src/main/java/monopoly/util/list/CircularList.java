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
	 * Description: Elimina el nodo cabecera de la lista enlazada y retorna
	 * el valor del nodo cabecera.
	 * Precondition: La  lista no debería ser nula.
	 * Postcondition: El nodo cabecera es eliminado.
	 * 
	 * @return T, retorna el valor del nodo eliminado
	 */
	public T pop() {
		if (isEmpty())
			return null;

		T k = head.getKey();

		if (head == head.getNext()) {
			head = null;
		} else {
			Node<T> h = head; 
			Node<T> p = head.getPrev();
			p.setNext(h.getNext());
			head = head.getNext();
			head.setPrev(p);
		}

		return k;
	}

	/*
	 * Description: Elimina el último nodo de la lista enlazada y retorna 
	 * el valor del nodo eliminado
	 * Precondition: La  lista no debería ser nula.
	 * Postcondition: El último nodo es eliminado.
	 * 
	 * @return T, retorna el valor del nodo eliminado.
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

	/**
	 * Description: Inserta un nuevo nodo x al final de la lista enlazada.
	 * Precondition: La  lista no debería ser nula.
	 * Postcondition: El nodo x con valor pasado por parametro k
	 * es insertado al final de la lista enlazada.
	 * 
	 * @param k, valor del nodo que se va a insertar al final de la lista.
	 */
	public void push_back(T k) {
		Node<T> x = new Node<T>(k);
		push_back(x);
	}

	/**
	 * Description: Inserta un nodo x al final de la lista enlazada.
	 * Precondition: El nodo x debería ser insertado.
	 * Postcondition: El nodo x es insertado al final de la lista enlazada.
	 * 
	 * @param x
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

	/**
	 * Description: Inserta un nodo x al principio de la lista enlazada
	 * Precondition: La  lista no debería ser nula.
	 * Postcondition: El nodo x con el valor pasado por parametro k
	 * es insertado al principio de la lista enlazada.
	 * 
	 * @param k
	 */
	public void push_front(T k) {
		Node<T> x = new Node<T>(k);
		push_front(x);
	}

	/**
	 * Description: Inserta un nodo x al principio de la lista enlazada.
	 * Precondition: El nodo x debería ser insertado.
	 * Postcondition: El nodo x es insertado al principio de la lista enlazada.
	 * 
	 * @param x
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

	public T top() {
		if (isEmpty())
			return null;

		return head.getKey();
	}

	public boolean isEmpty() {
		return (head == null);
	}
	
	public Node<T> getHeader(){
		if (isEmpty())
			return null;

		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Node<T> head) {
		this.head = head;
	}
	
}
