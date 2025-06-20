package br.com.lambda.cep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.lambda.cep.exception.NoContentException;
import br.com.lambda.cep.exception.NotReadyException;
import br.com.lambda.cep.model.Address;
import br.com.lambda.cep.service.CepService;
import jakarta.websocket.server.PathParam;

@RestController
public class CepController {
	
	@Autowired 
	private CepService service;

	//vai retornar o status e o cep
	@GetMapping("/status")
	public String getStatus(){
		return "Service status: " + this.service.getStatus();
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public Address getAddressByZipcode(@PathVariable("zipcode") String zipcode) throws NoContentException, NotReadyException {
		return this.service.getAddressByZipcode(zipcode);
	}
	
	@GetMapping("/")
    public String home() {
        return "API no ar";
    }
}