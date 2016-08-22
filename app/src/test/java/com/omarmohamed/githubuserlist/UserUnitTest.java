package com.omarmohamed.githubuserlist;

import com.omarmohamed.githubuserlist.models.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit Tests for User class
 */
public class UserUnitTest {

    @Test
    public void createNewUserWithEmptyConstructor() throws Exception {
        //If we pass null, the method should raise an exception
        User user = new User();

        assertNull(user.getAvatarUrl());
        assertNull(user.getEventsUrl());
        assertNull(user.getFollowersUrl());
        assertNull(user.getFollowingUrl());
        assertNull(user.getGistsUrl());
        assertNull(user.getGravatarId());
        assertNull(user.getHtmlUrl());
        assertNull(user.getLogin());
        assertNull(user.getOrganizationsUrl());
        assertNull(user.getReceivedEventsUrl());
        assertNull(user.getReposUrl());
        assertNull(user.getStarredUrl());
        assertNull(user.getSubscriptionsUrl());
        assertNull(user.getType());
        assertNull(user.getUrl());
        assertNull(user.getId());
        assertNull(user.getSiteAdmin());
    }

    @Test
    public void createNewUserWithNotEmptyConstructor() throws Exception {
        //If we pass null, the method should raise an exception
        User user = new User("login", new Integer(0), "avatarUrl", "gravatarId", "url", "htmlUrl", "followersUrl",
                "followingUrl", "gistsUrl", "starredUrl", "subscriptionsUrl", "organizationsUrl", "reposUrl", "eventsUrl",
                "receivedEventsUrl", "type", true);

        assertEquals("avatarUrl", user.getAvatarUrl());
        assertEquals("eventsUrl", user.getEventsUrl());
        assertEquals("followersUrl", user.getFollowersUrl());
        assertEquals("followingUrl", user.getFollowingUrl());
        assertEquals("gistsUrl", user.getGistsUrl());
        assertEquals("gravatarId", user.getGravatarId());
        assertEquals("htmlUrl", user.getHtmlUrl());
        assertEquals("login", user.getLogin());
        assertEquals("organizationsUrl", user.getOrganizationsUrl());
        assertEquals("receivedEventsUrl", user.getReceivedEventsUrl());
        assertEquals("reposUrl", user.getReposUrl());
        assertEquals("starredUrl", user.getStarredUrl());
        assertEquals("subscriptionsUrl", user.getSubscriptionsUrl());
        assertEquals("type", user.getType());
        assertEquals("url", user.getUrl());
        assertEquals(new Integer(0), user.getId());
        assertEquals(true, user.getSiteAdmin());
        
    }

    @Test
    public void checkSetMethods() throws Exception {
        //If we pass null, the method should raise an exception
        User user = new User();

        user.setAvatarUrl("test");
        user.setEventsUrl("test");
        user.setFollowersUrl("test");
        user.setFollowingUrl("test");
        user.setGistsUrl("test");
        user.setGravatarId("test");
        user.setHtmlUrl("test");
        user.setLogin("test");
        user.setOrganizationsUrl("test");
        user.setReceivedEventsUrl("test");
        user.setReposUrl("test");
        user.setStarredUrl("test");
        user.setSubscriptionsUrl("test");
        user.setType("test");
        user.setUrl("test");
        user.setId(0);
        user.setSiteAdmin(true);

        assertEquals("test", user.getAvatarUrl());
        assertEquals("test", user.getEventsUrl());
        assertEquals("test", user.getFollowersUrl());
        assertEquals("test", user.getFollowingUrl());
        assertEquals("test", user.getGistsUrl());
        assertEquals("test", user.getGravatarId());
        assertEquals("test", user.getHtmlUrl());
        assertEquals("test", user.getLogin());
        assertEquals("test", user.getOrganizationsUrl());
        assertEquals("test", user.getReceivedEventsUrl());
        assertEquals("test", user.getReposUrl());
        assertEquals("test", user.getStarredUrl());
        assertEquals("test", user.getSubscriptionsUrl());
        assertEquals("test", user.getType());
        assertEquals("test", user.getUrl());
        assertEquals(new Integer(0), user.getId());
        assertEquals(true, user.getSiteAdmin());
    }


}