package cigma.pfe.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cigma.pfe.exception.ClientNotFoundException;
import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageResponse;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cigma.pfe.models.Client;
import cigma.pfe.repositories.ClientRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Override
    @Transactional
    public Client save(Client clt) {

        return clientRepository.save(clt);
    }

    @Override
    @Transactional
    public Client modify(Client newclt) throws ClientNotFoundException {

        clientRepository.findById(newclt.getId())
                .orElseThrow(()->
                        new ClientNotFoundException("the client with id : " + newclt.getId() + " doesn't exist"));
        return clientRepository.save(newclt);
    }

    @Override
    @Transactional
    public void remove(long idClt) throws ClientNotFoundException {

        clientRepository.findById(idClt)
                .orElseThrow(() ->
                        new ClientNotFoundException("the client with id : " + idClt + " doesn't exist"));
        clientRepository.deleteById(idClt);
    }

    @Override
    public Client getOne(long idClt) throws ClientNotFoundException {

        return clientRepository.findById(idClt)
                .orElseThrow(() ->
                        new ClientNotFoundException("the client with id : " + idClt + " doesn't exist"));
    }

    @Override
    public List<Client> getAll() {

        return clientRepository.findAll();
    }

    @Override
    public List<Client> getByName(String name) {

        return clientRepository.findByName(name);
    }

    @Override
    public List<Client> getAllClients(Sort sort) {

        return clientRepository.findAllClients(sort);
    }

    @Override
    public Client getClientByNameIndexedParams(String name) {
        return clientRepository.findClientByNameIndexedParam(name);
    }

    @Override
    public Client getClientByNameNamedParams(String name) {

        return clientRepository.findClientByNameNamedParam(name);
    }

    @Override
    public List<Client> getByNameLike(String name) {

        return clientRepository.findByNameLike(name);
    }

    @Override
    @Transactional
    public void removeByName(String name) {

        clientRepository.deleteByName(name);
    }

    @Override
    public void uploadFile(String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadDirectory = Paths.get("Uploads");

        try(InputStream inputStream = multipartFile.getInputStream()){

            Path filePath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new IOException("Error while uploading the following file : " + fileName, e);
        }
    }

    @Scheduled(cron = "0 40 18 * * ?")
    public void scheduleTaskUsingCronExpression() {

        System.out.println("scheduled task using cron jobs");
    }

    @Override
    public void sendEmail(String to,
                          String subject,
                          String body,
                          String attachment) throws MessagingException, UnsupportedEncodingException {

        //Sending email with simple message

        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zakri7740@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        sender.send(message);*/

        //Sending email with attachment

        /*MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom("zakri7740@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        FileSystemResource systemResource = new FileSystemResource(new File(attachment));

        helper.addAttachment(systemResource.getFilename(),systemResource);

        sender.send(message);*/

        //Sending email with HTML

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        String content = "<p><b>Sender Name :</b> Hamza Ezzakri</p>" +
                         "<p><b>Sender Email :</b> zakri7740@gmail.com</p>" +
                         "<p><b>Subject :</b> " + subject + "</p>" +
                         "<p><b>Body :</b> " + body + "</p>" +
                         "<hr><img src='cid:logo'/>";
        helper.setFrom("zakri7740@gmail.com","Hamza Ezzakri");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        ClassPathResource resource = new ClassPathResource("/static/images/books.png");
        helper.addInline("logo",resource);

        FileSystemResource systemResource = new FileSystemResource(new File(attachment));
        helper.addAttachment(systemResource.getFilename(),systemResource);

        sender.send(message);
    }

    @Override
    public void sendEmail(String to, String subject, String body, MultipartFile multipartFile) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        String content = "<p><b>Sender Name :</b> Hamza Ezzakri</p>" +
                "<p><b>Sender Email :</b> zakri7740@gmail.com</p>" +
                "<p><b>Subject :</b> " + subject + "</p>" +
                "<p><b>Body :</b> " + body + "</p>" +
                "<hr><img src='cid:logo'/>";
        helper.setFrom("zakri7740@gmail.com","Hamza Ezzakri");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        ClassPathResource resource = new ClassPathResource("/static/images/books.png");
        helper.addInline("logo",resource);

        if(!multipartFile.isEmpty()){

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            InputStreamSource source = new InputStreamSource() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return multipartFile.getInputStream();
                }
            };

            helper.addAttachment(fileName,source);
        }

        sender.send(message);
    }

    @Override
    public void sendSms(String  body){

        // Create a MessageBirdService
        final MessageBirdService messageBirdService = new MessageBirdServiceImpl("wTEwkgS0oxX8AigcL2THdMkp0");
        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        // convert String number into acceptable format
        BigInteger phoneNumber = new BigInteger("+212648057213");
        final List<BigInteger> phones = new ArrayList<>();
        phones.add(phoneNumber);

        try {
            final MessageResponse response = messageBirdClient.sendMessage("+212648057213",
                   body , phones);
        } catch (UnauthorizedException | GeneralException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void launch() {

        JobParameters params = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job,params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
    }

}
