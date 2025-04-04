package br.com.lambda.cep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lambda.cep.exception.NoContentException;
import br.com.lambda.cep.model.Address;
import br.com.lambda.cep.model.AddressStatus;
import br.com.lambda.cep.model.Status;
import br.com.lambda.cep.repository.AddressRepositoy;
import br.com.lambda.cep.repository.AddressStatusRepository;

@Service
public class CepService {
	
	@Autowired
	private AddressRepositoy addressRepository;
	
	@Autowired
	private AddressStatusRepository addressStatusRepository;
	
	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
				.getStatus();
	}
	
	public Address getAddressByZipcode(String zipcode) throws NoContentException {
		
		return	addressRepository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	public void setup() {
		
	}
}
