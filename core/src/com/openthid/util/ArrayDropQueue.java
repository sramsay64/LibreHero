package com.openthid.util;

import java.util.function.Consumer;

import com.badlogic.gdx.utils.Predicate;

public class ArrayDropQueue<E> {

	private E[] array;
	private int nextIndex;

	public ArrayDropQueue(E[] array) {
		this.array = array;
	}

	public int size() {
		return array.length-nextIndex;
	}

	public boolean isEmpty() {
		return array.length == nextIndex;
	}

	public E[] getArray() {
		return array;
	}

	public E next() {
		return array[nextIndex];
	}

	public void forEachRemaining(Consumer<E> consumer) {
		for (int i = nextIndex; i < array.length; i++) {
			consumer.accept(array[i]);
		}
	}

	public void predicatedDrop(Predicate<E> predicate) {
		while (!isEmpty() && predicate.evaluate(array[nextIndex])) {
			drop();
		}
	}

	public E getNext() {
		return array[nextIndex];
	}

	public E drop() {
		nextIndex++;
		return array[nextIndex-1];
	}
}