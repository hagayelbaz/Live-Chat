package hac;


import hac.Models.User.User;
import hac.Services.AuthenticationService;
import hac.Services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

import static hac.Models.User.Role.ADMIN;


/**
 * <h1>Ex5TemplateApplication</h1>
 * <p>
 *     This class is the main class of the application.
 * </p>
 */
@SpringBootApplication
public class Ex5TemplateApplication {
    private static final Logger logger = Logger.getLogger(Ex5TemplateApplication.class.getName());

    @Resource(name = "userService")
    private hac.Services.UserService userService;

    private hac.Models.User.User admin;

    public static void main(String[] args) {
        SpringApplication.run(Ex5TemplateApplication.class, args);
    }

    /**
     * This method is used to create an admin user at the start of the application.
     * @param service The authentication service.
     * @return A command line runner.
     */
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            admin = User.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("admin-pw")
                    .role(ADMIN)
                    .build();

            if(userService.getUserByEmail(admin.getEmail()) == null){
                service.register(admin);
            }

            logger.info("Admin user created with email: " + admin.getEmail() + " and password: admin-pw");
        };
    }

    /**
     * This method is used to delete the admin user at the end of the application.
     * <b>for now this function deactivated, you can uncomment the @PreDestroy annotation to auto-delete the admin when the app exit</b>
     */
    //@PreDestroy
    public void destroy() {
        try {
            userService.deleteAdmin(admin.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
