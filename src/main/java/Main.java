import org.rest.Communication.Communication;
        import org.rest.config.Config;
        import org.rest.model.User;
        import org.springframework.context.annotation.AnnotationConfigApplicationContext;
        import org.springframework.stereotype.Component;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Communication communication = context.getBean("communication", Communication.class);
        System.out.println(communication.getAllUsers());
        User user = new User(3L, "James", "Brown", (byte) 30);
        communication.saveUser(user);
        user.changeName("Thomas", "Shelby");
        communication.updateUser(user);
      //  System.out.println(user);
        communication.deleteUser(3L);
    }
}

