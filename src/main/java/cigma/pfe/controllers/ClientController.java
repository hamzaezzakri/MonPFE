package cigma.pfe.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDateTime;

import cigma.pfe.exception.ClientNotFoundException;
import cigma.pfe.exception.ErrorResponse;
import cigma.pfe.exception.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import cigma.pfe.models.Client;
import cigma.pfe.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Slf4j
@RestController //@RestController which is combination of @Controller + @ResponseBody.
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;


	@PostMapping("/create")
	public ResponseEntity<Client> save(@Valid @RequestBody Client clt){

		Client newClient = clientService.save(clt);
		log.info(String.format("client with id : %d has been added successfully",newClient.getId()));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location).body(newClient);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> modify(@RequestBody Client clt){

		try {

			clientService.modify(clt);
			log.info(String.format("client with id : %d has been updated successfully",clt.getId()));
			return ResponseEntity.ok().body(clt);

		} catch (ClientNotFoundException e) {

			log.error(String.format("there is no client with id : %d",clt.getId()),e);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase(),e.getMessage(), LocalDateTime.now())
					,HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") Long idClt) {

		try {

			clientService.remove(idClt);
			log.info(String.format("client with id : %d has been removed successfully",idClt));
			return ResponseEntity.ok().body(
					new SuccessResponse(HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),
							String.format("the client with id : %d has been deleted successfully",idClt),LocalDateTime.now()));

		} catch (ClientNotFoundException e) {

			log.error(String.format("there is no client with id : %d",idClt),e);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase(),e.getMessage(),LocalDateTime.now())
					,HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") long idClt){

		try {

			Client client = clientService.getOne(idClt);
			log.info(String.format("client with id : %d is fetched",idClt));
			return ResponseEntity.ok().body(client);

		} catch (ClientNotFoundException e) {


			/*return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase(),e.getMessage(),LocalDateTime.now())
					,HttpStatus.NOT_FOUND);*/
			log.error(String.format("there is no client with id : %d",idClt),e);
			JSONObject details = new JSONObject();
			details.put("status",HttpStatus.NOT_FOUND);
			details.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
			details.put("message",e.getMessage());
			details.put("timeStamp",LocalDateTime.now());
			return new ResponseEntity<>(details.toString(),HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAll(){

		if(clientService.getAll().isEmpty()){

			log.info("the list is empty");
			return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),
					"the list of clients is currently empty",LocalDateTime.now()));
		}

		log.info("the list contains clients");
		return ResponseEntity.ok().body(clientService.getAll());
	}

	@PostMapping("/uplaodFile")
	public ResponseEntity<?>uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		clientService.uploadFile(fileName,multipartFile);

		return new ResponseEntity<>(String.format("you have successfully uploaded the file : %s with size %.1f Ko"
				,fileName, (float)multipartFile.getSize() / 1024), HttpStatus.OK);
	}

	@PostMapping("/send")
	public ResponseEntity<Object>send(@RequestParam String to,
									  @RequestParam String subject,
									  @RequestParam String body,
									  @RequestParam("attachment") MultipartFile multipart) throws MessagingException, UnsupportedEncodingException {

		clientService.sendEmail(to,subject,body,multipart);
		log.info(String.format("email sent to %s successfully",to));
		return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),
				"email sent successfully",LocalDateTime.now()));
	}

	@PostMapping("/send-SMS")
	public ResponseEntity<Object>send(@RequestParam String body){
		clientService.sendSms(body);
		log.info("SMS sent successfully");
		return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),
				"SMS sent successfully",LocalDateTime.now()));
	}

	@PostMapping("/launch")
	public ResponseEntity<Object>launch(){

		clientService.launch();
		log.info("Job launched successfully");
		return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),
				"Job launched successfully",LocalDateTime.now()));
	}

}
