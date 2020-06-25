package Objects;

public class User {
    private int id ;
    private String username ;
    private String password ;

    public User(int id , String username){
        this.id = id ;
        this.username = username ;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}