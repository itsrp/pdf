package com.rp.cache.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserCacheAuthenticatorTest {

    public static final String USERNAME = "username";
    public static final String VALID_PASSWORD = "valid-password";
    public static final String INVALID_PASSWORD = "invalid-password";
    private CacheAuthenticator cacheAuthenticator;
    private Authenticator mockedAuthenticator;

    @Before
    public void setUp() {
        mockedAuthenticator = Mockito.mock(Authenticator.class);
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 10);
    }

    @Test
    public void shouldCacheDataAndReturnTrueWhenCredentialsAreValidAndCacheIsEmpty() throws ExecutionException {
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(true);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, VALID_PASSWORD);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        Object cachedObject = cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, VALID_PASSWORD);

        //((UsernamePasswordCachedObject)cacheAuthenticator.getCache().get(key)).setPassword("OK");
    }

    @Test
    public void shouldReturnTrueWhenCredentialsAreValidAndCredentialsPresentInCache() throws ExecutionException {
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(true);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, VALID_PASSWORD);
        cacheAuthenticator.getCache().put(key, new UsernamePasswordCachedObject(VALID_PASSWORD));
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        Object cachedObject = cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);

        verify(mockedAuthenticator, times(0)).authenticate(USERNAME, VALID_PASSWORD);
    }

    @Test
    public void shouldCacheDataAndReturnFalseWhenCredentialsAreNotValidAndCacheIsEmpty() throws ExecutionException {
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(false);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, INVALID_PASSWORD);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertFalse(isAuthenticated);

        UsernamePasswordCachedObject cachedObject = (UsernamePasswordCachedObject) cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);
        assertEquals(1, cachedObject.getFailedAttempts());

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, INVALID_PASSWORD);
    }

    @Test
    public void shouldCacheDataAndReturnFalseWhenCredentialsAreNotValidAndCredentialsPresentInCache() throws ExecutionException {
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(false);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, INVALID_PASSWORD);
        UsernamePasswordCachedObject cachedObject1 = new UsernamePasswordCachedObject("");
        cachedObject1.setFailedAttempts(1);
        cacheAuthenticator.getCache().put(key, cachedObject1);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertFalse(isAuthenticated);

        UsernamePasswordCachedObject cachedObject = (UsernamePasswordCachedObject) cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);
        assertEquals(2, cachedObject.getFailedAttempts());

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, INVALID_PASSWORD);
    }

    @Test
    public void shouldTryAuthenticatingFromAuthenticatorWhenWrongCredentialsAndIfNotCrossedMaxTryLimit() throws ExecutionException {
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 5);
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(false);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, INVALID_PASSWORD);
        UsernamePasswordCachedObject cachedObject1 = new UsernamePasswordCachedObject("");
        cachedObject1.setFailedAttempts(4);
        cacheAuthenticator.getCache().put(key, cachedObject1);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertFalse(isAuthenticated);

        UsernamePasswordCachedObject cachedObject = (UsernamePasswordCachedObject) cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);
        assertEquals(5, cachedObject.getFailedAttempts());

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, INVALID_PASSWORD);
    }

    @Test
    public void shouldNotTryAuthenticatingFromAuthenticatorWhenWrongCredentialsAndIfCrossedMaxTryLimit() throws ExecutionException {
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 5);
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(false);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, INVALID_PASSWORD);
        UsernamePasswordCachedObject cachedObject1 = new UsernamePasswordCachedObject("");
        cachedObject1.setFailedAttempts(5);
        cacheAuthenticator.getCache().put(key, cachedObject1);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertFalse(isAuthenticated);

        UsernamePasswordCachedObject cachedObject = (UsernamePasswordCachedObject) cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);
        assertEquals(5, cachedObject.getFailedAttempts());

        verify(mockedAuthenticator, times(0)).authenticate(USERNAME, INVALID_PASSWORD);
    }

    @Test
    public void shouldReturnTrueWhenCorrectCredentialsAndIfReachedMaxTryLimit() throws ExecutionException {
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 5);
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(true);
        CacheKey key = new UsernamePasswordCacheKey(USERNAME, VALID_PASSWORD);
        UsernamePasswordCachedObject cachedObject1 = new UsernamePasswordCachedObject(VALID_PASSWORD);
        cachedObject1.setFailedAttempts(5);
        cacheAuthenticator.getCache().put(key, cachedObject1);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        UsernamePasswordCachedObject cachedObject = (UsernamePasswordCachedObject) cacheAuthenticator.getCache().getIfPresent(key);
        assertNotNull(cachedObject);
        assertEquals(0, cachedObject.getFailedAttempts());

        verify(mockedAuthenticator, times(0)).authenticate(USERNAME, VALID_PASSWORD);
    }

    @Test
    public void shouldCacheTheDataForFirstTimeIfValidCredentialsAndCheckSubSequentCallsFromCache() throws ExecutionException {
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 5);
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(true);

        CacheKey key = new UsernamePasswordCacheKey(USERNAME, VALID_PASSWORD);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, VALID_PASSWORD);
    }

    @Test
    public void shouldIncrementFailedAttemptWhenAlreadyValidCachedObjAndProvidedWrongCredentials() throws ExecutionException {
        cacheAuthenticator = new UserCacheAuthenticator(mockedAuthenticator, 5);
        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(true);

        CacheKey key = new UsernamePasswordCacheKey(USERNAME, VALID_PASSWORD);
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        assertTrue(isAuthenticated);

        when(mockedAuthenticator.authenticate(anyString(), anyString())).thenReturn(false);
        key = new UsernamePasswordCacheKey(USERNAME, INVALID_PASSWORD);
        isAuthenticated = cacheAuthenticator.authenticate(key);
        assertFalse(isAuthenticated);

        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, VALID_PASSWORD);
        verify(mockedAuthenticator, times(1)).authenticate(USERNAME, INVALID_PASSWORD);
    }
}