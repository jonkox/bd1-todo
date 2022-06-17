package tec.bd.Social.authentication;

import java.util.Objects;

public class Session {

    public String sessionId;

    public SessionStatus status;

    public String clientId;

    public Session() {

    }



    public Session(String sessionId, SessionStatus status, String clientId) {
        Objects.requireNonNull(sessionId);
        Objects.requireNonNull(status);
        Objects.requireNonNull(clientId);
        this.sessionId = sessionId;
        this.status = status;
        this.clientId = clientId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public String getClientid() {
        return clientId;
    }

    public void setClientid(String clientId) {
        this.clientId = clientId;
    }


}