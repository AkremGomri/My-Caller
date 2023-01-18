package issat.akrem.myapplication.models;

public class User {
    public String email, password, salt;
    public Integer id;

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", id=" + id +
                '}';
    }

    public User(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String salt) {
        this.salt = salt;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
