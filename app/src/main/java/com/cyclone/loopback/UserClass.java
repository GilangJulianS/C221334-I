package com.cyclone.loopback;

/**
 * Created by solusi247 on 16/12/15.
 */
public class UserClass {
    public static class User extends com.strongloop.android.loopback.User{
        private String username;
        private String id;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class UserRepository extends com.strongloop.android.loopback.UserRepository<User> {
        public interface LoginCallback extends com.strongloop.android.loopback.UserRepository.LoginCallback<User> {

        }
        public UserRepository() {
            super("account", null, User.class);
        }

    }
}
