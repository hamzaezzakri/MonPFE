package cigma.pfe.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

import cigma.pfe.exception.ClientNotFoundException;
import javassist.NotFoundException;
import org.springframework.data.domain.Sort;

import cigma.pfe.models.Client;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface ClientService {

	Client save(Client clt);
	Client modify(Client clt) throws ClientNotFoundException;
	void remove(long idClt) throws ClientNotFoundException;
	Client getOne(long idClt) throws ClientNotFoundException;
	List<Client> getAll();
	List<Client> getByName(String name);
	List<Client> getAllClients(Sort sort);
	Client getClientByNameIndexedParams(String name);
	Client getClientByNameNamedParams(String name);
	List<Client> getByNameLike(String name);
	void removeByName(String name);
	void uploadFile(String fileName, MultipartFile multipartFile) throws IOException;
	void sendEmail(String to, String subject, String body,String attachment) throws MessagingException, UnsupportedEncodingException;
	void sendEmail(String to, String subject, String body,MultipartFile multipartFile) throws MessagingException, UnsupportedEncodingException;
	void sendSms(String body);
	void launch();

}
