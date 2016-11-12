package com.openthid.util;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * A class with utility functions to assist functional programming
 */
public class FunctionalUtils {
	/**
	 * Creates a {@link ChangeListener} from a function
	 */
	public static ChangeListener makeChangeListener(BiConsumer<ChangeEvent, Actor> biConsumer) {
		return new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				biConsumer.accept(event, actor);
			}
		};
	}

	/**
	 * A {@link Comparator} for {@link Vector2}. Vectors Form a
	 * <a href="https://en.wikipedia.org/wiki/Partially_ordered_set">Poset</a>
	 * so there is no possible mathematically sound implementation.
	 * This is an implementation which follows all the laws defined in {@link Comparator}
	 * while being fast
	 */
	public static final Comparator<Vector2> VECTOR2COMPARATOR = new Comparator<Vector2>() {
		public int compare(Vector2 a, Vector2 b) {
			if (a.x == b.x && a.y == b.y) {
				return 0;
			}
			if (a.x != b.x) {
				if (a.x > b.x) {
					return 1;
				}
				return -1;
			} else {
				if (a.y > b.y) {
					return 1;
				}
				return -1;
			}
		};
	};

	/**
	 * Takes two arrays and creates a third by applying each element from the two arrays to the given function
	 * Same as Haskell's zipWith function.
	 */
	public static <A, B> B[] map(A[] as, Function<A, B> function, IntFunction<B[]> newArrayFunction) {
		B[] cs = newArrayFunction.apply(as.length);
		for (int i = 0; i < as.length; i++) {
			cs[i] = function.apply(as[i]);
		}
		return cs;
	}

	/**
	 * Takes two arrays and creates a third by applying each element from the two arrays to the given function
	 * Same as Haskell's zipWith function.
	 * throws {@link ArrayIndexOutOfBoundsException} If bs.length < as.length
	 */
	public static <A, B, C> C[] zipWith(A[] as, B[] bs, BiFunction<A, B, C> function, IntFunction<C[]> newArrayFunction) {
		C[] cs = newArrayFunction.apply(as.length);
		for (int i = 0; i < as.length; i++) {
			cs[i] = function.apply(as[i], bs[i]);
		}
		return cs;
	}

	@SafeVarargs
	public static <A> A applyMany(A a, Consumer<A>... consumers) {
		for (int i = 0; i < consumers.length; i++) {
			consumers[i].accept(a);
		}
		return a;
	}

	public static <A, B> A applyManyWith(A a, B[] bs, BiConsumer<A, B> biConsumer) {
		for (int i = 0; i < bs.length; i++) {
			biConsumer.accept(a, bs[i]);
		}
		return a;
	}

	/**
	 * 
	 * @param a
	 * @param bs
	 * @param cs
	 * @param biConsumer
	 * @return a
	 * throws {@link ArrayIndexOutOfBoundsException} If cs.length < bs.length
	 */
	public static <A, B, C> A applyManyZip(A a, B[] bs, C[] cs, TriConsumer<A, B, C> triConsumer) {
		for (int i = 0; i < bs.length; i++) {
			triConsumer.accept(a, bs[i], cs[i]);
		}
		return a;
	}
}