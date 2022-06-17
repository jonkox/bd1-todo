package tec.bd.Social.authentication;

public interface AuthenticationClient {

    Session validateSession(String session);
}
