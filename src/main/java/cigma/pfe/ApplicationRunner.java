package cigma.pfe;

import cigma.pfe.repositories.ClientRepository;
import cigma.pfe.services.ClientService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;

@EnableScheduling
@SpringBootApplication
//@SpringBootConfiguration => @Configuration => @Component
//@EnableAutoConfiguration
//@ComponentScan
public class ApplicationRunner implements CommandLineRunner {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ApplicationRunner.class, args);
        Arrays.stream(ctx.getBeanDefinitionNames())
                .filter(bean -> ctx.getBean(bean).getClass().getPackage().getName().startsWith("cigma.pfe"))
                .map(String::toUpperCase)
                .forEach(System.out::println);

        String testEncode = "Spring Boot";
        String encodedRes = Base64.getEncoder().encodeToString(testEncode.getBytes());
        System.out.println(encodedRes);

        byte[] testDecode = Base64.getDecoder().decode(encodedRes);
        String decodedRes = new String(testDecode);
        System.out.println(decodedRes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
        String dateFormat = formatter.format(LocalDateTime.now());
        System.out.println(dateFormat);

        System.out.println("generating a code of 8 characters of length : " + RandomString.make(8));

    }

    @Autowired
    private ClientRepository client;
    @Autowired
    private ClientService service;

    @Override
    public void run(String... args) throws Exception {

        /*client.save(new Client("henry"));
        client.save(new Client("Olivia"));
        client.save(new Client("william"));*/
        client.findAll().forEach(System.out::println);
        /*service.sendEmail("hamzaezzakri98@gmail.com",
                "Email with attachment",
                "this body has an attachment",
                "./Uploads/cahier de charge - V0.0.docx");*/
    }

}
