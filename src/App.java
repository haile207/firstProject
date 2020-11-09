import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class App {
    private static final Character[] NAME_CHARS = {'f', 'r', 'u', 'i', 't'};
    private static final int MIN_AGE = 20;
    private static final int MAX_AGE = 22;
    private static final int NAME_LENGTH = 3;
    private static final String FILE_PATH = "src/files/users.txt";

    public static void main(String[] args) {
        List<User> users = readFromTextFile();
        System.out.println("The list from the saved file:");
        for (User user : users) {
            System.out.println(user.toString());
        }
        int number = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter number of users you want to create: ");
            number = scanner.nextInt();
            users = randomUserandPrint(number);
            countBySexV1(users);
            Map<String, List<User>> duplicatedUserName = getDuplicatedUserName(users);
            System.out.println("List of duplicated name:");
            for (String name : duplicatedUserName.keySet()) {
                System.out.println(name + ": " + duplicatedUserName.get(name).size());
            }
            Map<NameAndAge, List<User>> duplicatedNameAndAge = getDuplicatedUserNameAndAge(users);
            for (NameAndAge n : duplicatedNameAndAge.keySet()) {
                System.out.println(n.toString() + ": " + duplicatedNameAndAge.get(n).size());
            }
        } while (number > 0);

    }

    public static String randomName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NAME_LENGTH; i++) {
            sb.append(NAME_CHARS[(int) Math.floor(Math.random() * NAME_CHARS.length)]);
        }
        return sb.toString();
    }

    public static int randomAge() {
        return (int) Math.floor(Math.random() * (MAX_AGE - MIN_AGE + 1) + MIN_AGE);
    }

    public static GENDER randomSex() {
        boolean sex = Math.floor(Math.random() * 2) >= 1;
        return sex ? GENDER.MALE : GENDER.FEMALE;
    }

    public static List<User> randomUserandPrint(int number) {
        List<User> users = new ArrayList<>();
        System.out.println(LocalDateTime.now() + " Start creating users.");
        for (int i = 0; i < number; i++) {
            users.add(new User(
                    i + 1,
                    randomName(),
                    randomAge(),
                    randomSex()
            ));
        }
        System.out.println(LocalDateTime.now() + " Finished creating users.");
        Collections.sort(users);
        System.out.println("List of users:");
        for (User user : users) {
            System.out.println(user.toString());
        }
        writeToTextFile(users);
        return users;
    }

    public static void writeToTextFile(List<User> users) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (User user : users) {
                oos.writeObject(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<User> readFromTextFile() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("No user found.");
            return users;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user = null;
            while ((user = (User) ois.readObject()) != null) {
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static List<String> getAllNames(List<User> users) {
        List<String> names = new ArrayList<>();
        for (User user : users) {
            if (!names.contains(user.getName())) {
                names.add(user.getName());
            }
        }
        return names;
    }

    public static void countBySexV1(List<User> users) {
        int male = (int) users.stream()
                .filter(user -> user.getSex() == GENDER.MALE)
                .count();
        int female = (int) users.stream()
                .filter(user -> user.getSex() == GENDER.FEMALE)
                .count();
        System.out.println("Counting result:");
        System.out.println("Male: " + male);
        System.out.println("Female: " + female);
    }

    public static void countBySex(List<User> users) {
        int male = 0, female = 0;
        System.out.println(LocalDateTime.now() + " Start counting by sex.");
        for (User user : users) {
            if (user.getSex().equals("male")) {
                male++;
            } else {
                female++;
            }
        }
        System.out.println(LocalDateTime.now() + " Counting result:");
        System.out.println("Male: " + male);
        System.out.println("Female: " + female);
    }
//    public static Map<String, List<User>> getDuplicatedUserNameV1(List<User> users){
//        Map<String, List<User>> names = new HashMap<>();
//        List<String> nameList = getAllNames(users);
//
//    }

    public static Map<String, List<User>> getDuplicatedUserName(List<User> users) {
        Map<String, List<User>> names = new HashMap<>();
        List<String> nameList = getAllNames(users);
        if (nameList.size() > 0) {
            for (String s : nameList) {
                List<User> userList = new ArrayList<>();
                for (User user : users) {
                    if (user.getName().equals(s)) {
                        userList.add(user);
                    }
                }
                names.put(s, userList);
            }
        }
        return names;
    }


    public static Map<NameAndAge, List<User>> getDuplicatedUserNameAndAge(List<User> users) {
        Map<NameAndAge, List<User>> nameAndAge = new HashMap<>();
        for (User user : users) {
            for (NameAndAge n : nameAndAge.keySet()) {
                if (n.getName().equals(user.getName()) && n.getAge() == user.getAge()) {
                    nameAndAge.get(n).add(user);
                    break;
                }
            }
            List<User> list = new ArrayList<>();
            list.add(user);
            nameAndAge.put(new NameAndAge(user.getName(), user.getAge()), list);
        }
        return nameAndAge;
    }
}
