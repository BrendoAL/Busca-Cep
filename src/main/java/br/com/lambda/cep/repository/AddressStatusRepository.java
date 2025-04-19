package br.com.lambda.cep.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.lambda.cep.model.Address;
import br.com.lambda.cep.model.AddressStatus;

public interface AddressStatusRepository extends CrudRepository<AddressStatus, Integer> {

}
