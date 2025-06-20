package br.com.lambda.cep;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lambda.cep.model.Address;
import br.com.lambda.cep.service.CepService;

@MockServerTest({"correios.base.url=http://localhost:${mockServerPort}/ceps.csv"})
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = {
	    "setup.on.startup=false"
	})

class CepApplicationTests {
	
	private MockServerClient mockServer;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CepService service;
	
	@Test
	@Order(1)
	public void testGetzipCodeWhenNotReady() throws Exception {
		mockMvc.perform(get("/zipcode/03358150")).andExpect(status().isServiceUnavailable());
	}
	
	@Test
	@Order(2)
	public void testSetupNotOk() throws Exception {
		mockServer.when(request()
				.withPath("/ceps.csv")
				.withMethod("GET"))
		.respond(response()
				.withStatusCode(500)
				.withBody("ERROR"));
		
		assertThrows (Exception.class, () -> service.setup());
			
	}
	
	@Test
	@Order(3)
	public void testSetupOk() throws Exception {
		String bodyStr = "AM,Manaus,Lago Azul,69018666,Rua Arauna (Res V Melhor - 2 Etapa),,,,,,,,,," ;
		mockServer.when(request()
				.withPath("/ceps.csv")
				.withMethod("GET"))
		.respond(response()
				.withStatusCode(200)
				.withBody(bodyStr));
		
		service.setup();
	}
	
	@Test
	@Order(4)
	public void testGetZipcodeThatDoenstExist() throws Exception{
		mockMvc.perform(get("/zipcode/99999999")).andExpect(status().isNoContent());
	}
	
	@Test
	@Order(5)
	public void testGetZipcodeOk() throws Exception{
		MvcResult result = mockMvc.perform(get("/zipcode/69018666")).andExpect(status().isOk()).andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		String addressToCompareStr = new ObjectMapper().writeValueAsString(
				Address.builder()
					.city("Manaus")
					.district("Lago Azul")
					.state("AM")
					.street("Rua Arauna (Res V Melhor - 2 Etapa)")
					.zipcode("69018666")
					.build());
	
				JSONAssert.assertEquals(addressToCompareStr, resultStr, false);
	}
}
