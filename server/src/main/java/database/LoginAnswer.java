package database;

public enum LoginAnswer {
    SUCCESS("auth successful, welcome!"),
    DUPLICATE("there is user with the same username in database, choose another one"),
    WRONG_PASSWORD("incorrect password for username"),
    USER_NOT_FOUND("there is no user with this username in system, try registering"),
    DB_ERROR("the error with database occurred, sorry! no offence, peace \u262E");
    private final String message;
    LoginAnswer(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
