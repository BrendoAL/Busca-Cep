package br.com.lambda.cep.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Voltar no minuto 7 pelo lombok e ide

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
	@Id
	private String zipcode;
	private String street;
	private String district;
	private String city;
	private String state;
	
	public void setZipCode(String zipcode) {
	}
}
