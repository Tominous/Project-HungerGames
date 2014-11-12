package fr.skyost.hungergames.utils;

import java.util.LinkedList;

/**
 * A circular list, implemented through a linked list.<br />
 * Next methods
 * @author Skyler
 *
 * @param <o>
 */
public class CircularQueue<o> extends LinkedList<o> {

	/**
	 * Lulz whut evz
	 */
	private static final long serialVersionUID = 806520540639986296L;
	
	/**
	 * The index of the next object returned from the list.
	 */
	private int index;
	
	public CircularQueue() {
		super();
		index = 0;
	}
	
	/**
	 * Returns the next object in the list. This object is the one index is currently pointing to, but the next in the list
	 * as follows from calls to 'next'
	 * @return
	 */
	public o next() {
		o output;
		
		//Error check: Is the list empty?
		if (size() == 0) {
			return null;
		}
		output = get(index);
		
		index++;
		if (index >= size()) {
			index = 0;
		}
		
		return output;
	}
	
	public o prev() {
		
		if (size() == 0) {
			return null;
		}
		
		index--;
		
		if (index < 0) {
			index = this.size() - 1;
		}
		
		return get(index);
		
	}
	
	public int getIndex() {
		return this.index;
	}
}
