public class SignUpService {
    private PasswordBlackList passwordBlackList;

    public SignUpService(PasswordBlackList passwordBlackList) {
        this.passwordBlackList = passwordBlackList;
    }

    public void signUp(String password) {

        if (passwordBlackList.contains(password)) {
            System.err.println("Пароль не подходит!");
        } else {
            System.out.println("Пароль хороший");
        }
    }

}
