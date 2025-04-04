package br.com.lambda.cep.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lambda.cep.model.Address;

public interface AddressRepositoy extends CrudRepository<Address, String>{

}
