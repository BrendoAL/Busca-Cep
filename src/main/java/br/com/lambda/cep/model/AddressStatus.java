package br.com.lambda.cep.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AddressStatus {
	
	public static final int DEFAULT_ID = 1;
	@Id
	private int id;
	private Status status;
	public static AddressStatus builder() {
        return new AddressStatus(); 
    }
	public Object status(Status needSetup) {
		return new Object();
	}
}
