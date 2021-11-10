package videogameshop;

public interface UserRepo {
    void save(User user);
    User findByEmail(String email);
}
