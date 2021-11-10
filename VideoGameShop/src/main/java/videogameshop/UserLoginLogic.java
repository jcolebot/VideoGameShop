package videogameshop;
import at.favre.lib.crypto.bcrypt.BCrypt;


public class UserLoginLogic implements UserLogin {
    private final UserRepo userRepo;

    public UserLoginLogic(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void register(User user) {
        // ref: https://github.com/patrickfav/bcrypt
        // convert password to hash
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(bcryptHashString);
        // save user in database
        userRepo.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepo.findByEmail(email);
        if(user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(result.verified) {
                return user;
            }
            else {
                throw new InvalidCredentialException("Invalid email or password");
            }
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }
}
