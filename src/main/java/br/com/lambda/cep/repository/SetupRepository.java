package br.com.lambda.cep.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import org.apache.commons.lang3.StringUtils;

import br.com.lambda.cep.model.Address;

@Repository
public class SetupRepository {
	
	@Value("${correios.base.url}")
	private String url;
	
	public List<Address> getFromOrigin() throws Exception {
		List<Address> resultList = new ArrayList<>();
		String resultStr = "";
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault(); 
				CloseableHttpResponse response = httpClient.execute(new HttpGet(this.url))) {
			
			HttpEntity entity = response.getEntity();
			resultStr = EntityUtils.toString(entity); 
		}
		
		String[] resultStrSplited = resultStr.split("\\r?\\n"); //adicionei o \\r
		
		for (String currentLine : resultStrSplited) {
			String[] currentLineSplited = currentLine.split(",");
					
			//retorno dos resultados 
			resultList.add(Address.builder() 
					.zipcode(StringUtils.leftPad(currentLineSplited[3].trim(), 8, "0")) 
					.state(currentLineSplited[0])
					.city(currentLineSplited[1])
					.district(currentLineSplited[2]) 
					.street(currentLineSplited.length > 4 ? currentLineSplited[4].trim()  : null).build());
			; 
		}
		return resultList;
	}
}
