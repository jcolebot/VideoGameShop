package videogameshop;
import videogameshop.User;

public interface UserLogin {

    void register(User user);

    User login(String email, String password);
}


