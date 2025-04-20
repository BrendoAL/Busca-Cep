package br.com.lambda.cep.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import br.com.lambda.cep.CepApplication;
import br.com.lambda.cep.exception.NoContentException;
import br.com.lambda.cep.exception.NotReadyException;
import br.com.lambda.cep.model.Address;
import br.com.lambda.cep.model.AddressStatus;
import br.com.lambda.cep.model.Status;
import br.com.lambda.cep.repository.AddressRepositoy;
import br.com.lambda.cep.repository.AddressStatusRepository;
import br.com.lambda.cep.repository.SetupRepository;

@Service
public class CepService {
	
	private static Logger logger = LoggerFactory.getLogger(CepService.class); 
	
	@Autowired
	private AddressRepositoy addressRepository;
	
	@Autowired
	private AddressStatusRepository addressStatusRepository;
	
	@Autowired
	private SetupRepository setupRepository;
	
	@Value("${setup.on.startup}")
	private boolean setupOnStartup;
	
	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)

				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
				.getStatus(); 
	}	
	
	public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if(!this.getStatus().equals(Status.READY))
			throw new NotReadyException();
		
		return addressRepository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	private void saveStatus(Status status) {
		this.addressStatusRepository.save(AddressStatus.builder()
			.id(AddressStatus.DEFAULT_ID) 
			.status(status)
			.build());
	}
	
	@EventListener(ApplicationStartedEvent.class)
	protected void setupOnStartup() {
		if (!setupOnStartup)
			return;
		
		try {
			this.setup();
		} catch(Exception exc) {
				CepApplication.close(999);
				logger.error(".setupOnStartup() - Exception", exc);
		}
	}
	
	public void setup() throws Exception {
		logger.info("-----");
		logger.info("-----");
		logger.info("----- SETUP RUNNING");
		logger.info("-----");
		logger.info("-----");
		
		if(this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);
			
			try {
			this.addressRepository.saveAll(
					this.setupRepository.getFromOrigin());
			} catch(Exception exc) {
				this.saveStatus(Status.NEED_SETUP);
				throw exc;
			}
			
			this.saveStatus(Status.READY);		
		} 		
		
		logger.info("-----");
		logger.info("-----");
		logger.info("----- SERVICE READY");
		logger.info("-----");
		logger.info("-----");
	}
}
