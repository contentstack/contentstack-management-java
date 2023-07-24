package com.contentstack.cms.core;

import org.junit.jupiter.api.*;

public class AuthInterceptorTest {

	@Test
	public void AuthInterceptor() {
		AuthInterceptor expected = new AuthInterceptor("abc");
		AuthInterceptor actual = new AuthInterceptor();
		Assertions.assertNull(actual.authtoken);
		Assertions.assertEquals("abc", expected.authtoken);
	}

	@Test
	public void testSetAuthtoken() {
		AuthInterceptor actual = new AuthInterceptor();
		actual.setAuthtoken("abcd");
		Assertions.assertEquals("abcd", actual.authtoken);
	}

	@Test
	public void testBadArgumentException() {
		BadArgumentException exception = new BadArgumentException("Invalid Argument");
		String message = exception.getLocalizedMessage();
		Assertions.assertEquals("Invalid Argument", message.toString());
	}
}
