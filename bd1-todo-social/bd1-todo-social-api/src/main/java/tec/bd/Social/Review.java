package tec.bd.Social;

import java.util.Date;

public class Review {
    private int id;
    private String todoid;
    private Date createdat;
    private String comentario;
    private String clientid;

    public Review() {
    }

    public Review(int id, String todoid, Date createdat, String comentario, String clientid) {
        this.id = id;
        this.todoid = todoid;
        this.createdat = createdat;
        this.comentario = comentario;
        this.clientid = clientid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoid() {
        return todoid;
    }

    public void setTodoid(String todoid) {
        this.todoid = todoid;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
}
