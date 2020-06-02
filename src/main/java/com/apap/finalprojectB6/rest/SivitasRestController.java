package com.apap.finalprojectB6.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SivitasRestController {

	@GetMapping(value = "/user-profile/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUser(@PathVariable String uuid) 
	{
		String url = "http://si-sivitas.herokuapp.com/api/employees/{uuid}";
		Map<String, String> params = new HashMap<String, String>();
		//uuid masih hardcode sesuai yang ada di sivitas
	    params.put("uuid", uuid);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(url, String.class, params);	     
	    return result;
	}
	
	@PostMapping(value = "/add-user", consumes = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	private boolean addSubmit(@RequestBody UserModel pengguna) {	
		if (userService.validate(pengguna.getUsername())) {
			pengguna.setId_role(5);
			userService.addUser(pengguna);
			return true;
		} else {
			return false;
		}
	}

}
