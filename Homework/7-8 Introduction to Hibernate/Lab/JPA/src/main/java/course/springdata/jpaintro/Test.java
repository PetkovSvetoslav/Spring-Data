package course.springdata.jpaintro;

import course.springdata.jpaintro.entity.User;

public class Test {
    public static void main(String[] args) {
        User e = new User();
        User user = new User("Angel", "Velkov", "acho007", "1234");
        System.out.println(user);
        System.out.println(e);
    }
}
