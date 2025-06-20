package br.com.lambda.cep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason ="Serviço em instalação. Por favor aguarde")
public class NotReadyException extends Exception {

}
