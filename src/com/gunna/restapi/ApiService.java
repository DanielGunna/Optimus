package com.gunna.restapi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.gunna.controller.Simplex;
import com.gunna.model.Coeficientes;
import com.gunna.model.FuncaoObjetiva;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

 
@Path("/api/v1")
public class ApiService {
	private Simplex simplexSolver;
	private JSONObject requestBody;
	
	private void initSimplexSolver(){
		simplexSolver = new Simplex();
	}
	
	  @POST
	  @Produces("application/json")
	  @Consumes("application/json")
	  public Response simplexFromInput(InputStream requestStream) throws JSONException {
		 initSimplexSolver(); 
		 parseRequestBody(requestStream);
		 
		 			
		
		 return null;//getResponse();

	  }
	
	private Response getResponse() {
		try {
			JSONArray restrictions = requestBody.getJSONArray("restrictions");
			return Response.status(200).entity(simplexSolver.solve().toString()).build();
		}  catch (Exception e) {
			e.printStackTrace();
			requestBody = new JSONObject("{ \"code\":"+400+", \"error\":\""+e.getLocalizedMessage()+"\"}");
			return Response.status(400).entity(requestBody.toString()).build();
		}
	}

	private String getRequestBodyAsString(InputStream  requestStream){
		StringBuilder requestData = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(requestStream));
			String line = null;
			while ((line = in.readLine()) != null) {
				requestData.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
			e.printStackTrace();
		}
		return requestData.toString();
	}
	
	private void parseRequestBody(InputStream requestStream){
		String bodyAsString = getRequestBodyAsString(requestStream);
		Gson gson = new Gson();	
		List<Coeficientes> coef = gson.fromJson(bodyAsString,new TypeToken<List<Coeficientes>>(){}.getType());
		if(coef != null && coef.size() > 0){
			System.out.println(coef.toString());
		}
		System.out.println("Data Received: " + bodyAsString);
		requestBody = new JSONObject(bodyAsString);

	}


	  
	  
}