package org.cryptomator.cryptofs.common;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.function.Predicate;

public class CustomMatchers {

	public static <T> BaseMatcher<T> matching(Class<T> clazz, Predicate<T> predicate, String description) {
		return new BaseMatcher<>() {
			@Override
			public boolean matches(Object actual) {
				if (clazz.isInstance(actual)) {
					return predicate.test((T) actual);
				} else {
					return false;
				}
			}

			@Override
			public void describeTo(Description descr) {
				descr.appendText(description);
			}
		};
	}

}
