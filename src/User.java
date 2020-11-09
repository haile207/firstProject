import java.io.Serializable;

public class User implements Serializable,Comparable<User> {
    private int id;
    private String name;
    private int age;
    private GENDER sex;

    public User(int id, String name, int age, GENDER sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GENDER getSex() {
        return sex;
    }

    public void setSex(GENDER sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() +
                ", Name: " + this.getName() +
                ", Age: " + this.getAge() +
                ", Sex: " + this.getSex();
    }


    @Override
    public int compareTo(User user) {
        return this.getName().compareTo(user.getName());
    }
}
