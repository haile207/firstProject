import java.io.Serializable;

public class NameAndAge implements Serializable {
    private String name;
    private int age;

    public NameAndAge(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public NameAndAge() {
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

    @Override
    public String toString() {
        return "{Name: " + this.getName() +
                ", Age: " + this.getAge() + "}";
    }
}
