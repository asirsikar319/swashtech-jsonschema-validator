package com.swashtech.utils;

import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

@Service
public class JSchemaUtility {

	public ResponseEntity<String> validateSchema(JSONObject jsonSchema, JSONObject inputRequest) {
		JSONObject response = new JSONObject();
		try {
			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(inputRequest);
			response.put("status", "Success");
			response.put("message", "Schema Validated");
			return new ResponseEntity<String>(response.toString(), HttpStatus.ACCEPTED);
		} catch (ValidationException e) {
			response.put("status", "Error");
			response.put("message", e.getAllMessages());
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<String> transformJolt(JSONArray jsonSchema, JSONObject inputRequest) {
		JSONObject response = new JSONObject();
		try {
			List<?> chainrConfig = JsonUtils.jsonToList(jsonSchema.toString());
			Chainr chainr = Chainr.fromSpec(chainrConfig);
			Object input = JsonUtils.jsonToObject(inputRequest.toString());
			Object jsonOutput = chainr.transform(input);
			response.put("status", "Success");
			response.put("response", jsonOutput);
			return new ResponseEntity<String>(response.toString(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			response.put("status", "Error");
			response.put("message", e.getMessage());
			return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
		}
	}

}
