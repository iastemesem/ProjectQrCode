package gianfranco.progettomonk;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Gianfranco on 05/04/2017.
 */

public class ResponseBody {

    @SerializedName("status")
    private int status;

    @SerializedName("errorCode")
    private String errorCode;

    @SerializedName("errorMessage")
    private int [] errorMessage;

    @SerializedName("response")
    private ResponseUser response;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(int[] errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResponseUser getResponse() {
        return response;
    }

    public void setResponse(ResponseUser response) {
        this.response = response;
    }
}

class ResponseUser {
    @SerializedName("id")
    private String id;
    @SerializedName("ttl")
    private int ttl;
    @SerializedName("created")
    private Date created;
    @SerializedName("userId")
    private int userId;

    public ResponseUser(String id, int ttl, Date created, int userId) {
        this.id = id;
        this.ttl = ttl;
        this.created = created;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int tll) {
        this.ttl = tll;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userdId) {
        this.userId = userdId;
    }
}
