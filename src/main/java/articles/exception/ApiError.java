package articles.exception;

import java.util.Date;

// TODO: Complete this
public class ApiError{
    private int statusCode;
    private String message;
    private String field;
    private Date timestamp;

    public ApiError(int statusCode, String message, Date timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiError(int statusCode, String message, String field, Date timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.field = field;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
