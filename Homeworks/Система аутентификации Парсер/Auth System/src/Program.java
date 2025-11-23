import java.util.HashMap;
import java.util.Map;

public class Program {
    static Map<String, String> data = new HashMap<>();
    static int MAX_ATTEMPTS = 3;
    public Map<String, Integer> attempts_count = new HashMap<>();

    public static void main(String[] args) {
    User Maxim_user = new User();
    Maxim_user.username = "Maxim";
    Maxim_user.password = "123456";
    data.put(Maxim_user.username, Maxim_user.password);

    try {
        Login(Maxim_user, "1111"); // неверный пароль
    } catch (UserNotFoundException | InvalidCredentialsException | AccountLockedException e) {
        System.out.println("Ошибка: " + e.getMessage());
    }

    try {
        Login(Maxim_user, "123456"); // правильный пароль
    } catch (UserNotFoundException | InvalidCredentialsException | AccountLockedException e) {
        System.out.println("Ошибка: " + e.getMessage());
    }
}


    public static void Login(User user, String password)
        throws UserNotFoundException, InvalidCredentialsException, AccountLockedException {

    if (!data.containsKey(user.username)) {
        throw new UserNotFoundException("Пользователь " + user.username + " не найден.");
    }

    if (user.login_attempts >= user.MAX_ATTEMPTS) {
        throw new AccountLockedException("Количество попыток входа превышено. Попробуйте позже.");
    }
    
    if (!data.get(user.username).equals(password)) {
        user.login_attempts++; 
        throw new InvalidCredentialsException("Неверный пароль. Попыток осталось: " 
                + (user.MAX_ATTEMPTS - user.login_attempts));
    }

    user.login_attempts = 0;
    System.out.println("Успешный вход для пользователя: " + user.username);
    }
}

class User {
    public String username;
    public String password;

    public int login_attempts = 0;
    public int MAX_ATTEMPTS = 3;
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

class AccountLockedException extends Exception {
    public AccountLockedException(String message) {
        super(message);
    }
}
