package com.swashtech.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swashtech.utils.JSchemaUtility;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/swashtech/jschema")
public class JSchemaController {

	@Autowired
	JSchemaUtility JSchemaUtility;

	@ApiOperation(value = "Validate Schema by Inputs", response = Iterable.class)
	@RequestMapping(value = "/validateSchema", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> validateSchema(@RequestBody String input) {
		ResponseEntity<String> response = null;
		JSONObject jsonObject = new JSONObject(input);
		response = JSchemaUtility.validateSchema(jsonObject.getJSONObject("schema"), jsonObject.getJSONObject("input"));
		return response;
	}

	@ApiOperation(value = "Transform Jolt by Inputs", response = Iterable.class)
	@RequestMapping(value = "/transformJolt", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> transformJolt(@RequestBody String input) {
		ResponseEntity<String> response = null;
		JSONObject jsonObject = new JSONObject(input);
		response = JSchemaUtility.transformJolt(jsonObject.getJSONArray("schema"), jsonObject.getJSONObject("input"));
		return response;
	}

}
