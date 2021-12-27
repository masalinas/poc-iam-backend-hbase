package io.oferto.backendhbase.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.oferto.backendhbase.service.HbaseService;

@RestController
@RequestMapping("api/hbase")
public class HbaseController {
	Logger log = LoggerFactory.getLogger(HbaseController.class);
	
	private final HbaseService hbaseService;
	
	public HbaseController(HbaseService hbaseService) {
		this.hbaseService = hbaseService;
	}
	
	@RequestMapping(value = "/tables/{tableName}/colfamilies/{colFamily}", method = RequestMethod.POST)
    public ResponseEntity<String> createTable(@PathVariable("tableName") String tableName, @PathVariable("colFamily") String colFamily) throws Exception {
        log.info("Executing createTable");
                    	
        hbaseService.createTable(tableName, colFamily);
        
        return new ResponseEntity<>("Table created", HttpStatus.OK);
    }
	
	@RequestMapping(value = "/tables/{tableName}/colfamilies/{colFamily}/rowkeys/{rowKey}", method = RequestMethod.POST)
    public ResponseEntity<String> putData(@PathVariable("tableName") String tableName, @PathVariable("colFamily") String colFamily, 
    					@PathVariable("rowKey") String rowKey, @RequestBody Map<String, String> data) throws Exception {
        log.info("Executing putData");
                    	
        hbaseService.putData(tableName, colFamily, rowKey, data);
        
        return new ResponseEntity<>("Data register inserted", HttpStatus.OK);
    }
	
	@RequestMapping(value = "/tables/{tableName}/rowkeys/{rowKey}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteData(@PathVariable("tableName") String tableName, @PathVariable("rowKey") String rowKey) throws Exception {
        log.info("Executing putData");
                    	
        hbaseService.deleteData(tableName, rowKey);
        
        return new ResponseEntity<>("Data register deleted", HttpStatus.OK);
    }
	
	@RequestMapping(value = "/tables/{tableName}", method = RequestMethod.GET)
    public Map<String, String> getData(@PathVariable("tableName") String tableName) throws Exception {
        log.info("Executing getData");
                    	
        return hbaseService.getData(tableName);
    }
}
