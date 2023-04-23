package database;

public interface Authorizer {
    LoginAnswer login(String name, byte[] hashedPassword);
    LoginAnswer register(String name, byte[] hashedPassword);
}
