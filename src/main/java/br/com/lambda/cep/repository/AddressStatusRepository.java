package br.com.lambda.cep.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lambda.cep.model.AddressStatus;

public interface AddressStatusRepository extends CrudRepository<AddressStatus, Integer> {

}
