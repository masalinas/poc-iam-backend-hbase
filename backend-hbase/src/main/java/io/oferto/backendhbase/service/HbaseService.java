package io.oferto.backendhbase.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

@Service
public class HbaseService {
	@Autowired
	private HBaseAdmin admin;
	
	@Autowired
	private Connection connection;
	
	public void createTable(String name, String colFamily) throws IOException {
		TableName table = TableName.valueOf(name);
		
		if(admin.tableExists(table)) {
			System.out.println("table ["+name+"] exist.");
		}
		
		ColumnFamilyDescriptor cfd = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(colFamily))
				.setMaxVersions(1).build();
		
		TableDescriptor tableDes = TableDescriptorBuilder.newBuilder(table).setColumnFamily(cfd).build();
		admin.createTable(tableDes);
		
	}
	
	public void putData(String name, String colFamily, String rowKey, Map<String, String> data) throws IOException {
		TableName table = TableName.valueOf(name);
		
		if(admin.tableExists(table)) {
			Table t = connection.getTable(table);
			
			Put put = new Put(Bytes.toBytes(rowKey));
			for(Map.Entry<String, String> entry:data.entrySet()) {
				put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));	
			}
			t.put(put);
		}else {
			System.out.println("table ["+name+"] does not exist.");
		}
	}
	
	public void deleteData(String name, String rowKey) throws IOException {
		TableName table = TableName.valueOf(name);
		
		if(admin.tableExists(table)) {
			Table t = connection.getTable(table);
			Delete delete = new Delete( Bytes.toBytes(rowKey));
			
			t.delete(delete);
		}else {
			System.out.println("table ["+name+"] does not exist.");
		}
	}
	
	public Map<String, String> getData(String name) throws IOException{
		Map<String, String> data = new HashMap<String, String>();
		
		TableName table = TableName.valueOf(name);
		Table t = connection.getTable(table);
		ResultScanner rs = t.getScanner(new Scan());
		
		for(Result r:rs) {
			System.out.println("row:"+new String(r.getRow()));
			
			String colFamily;
			String qualifier;
			String value;
			
			for(Cell cell:r.rawCells()) {
				colFamily = Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
				qualifier = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
				value = Bytes.toString(cell.getValueArray(),cell.getValueOffset(),cell.getValueLength());
				
				data.put(qualifier, value);						 
				
				System.out.println("colFamily: " + colFamily + ",qualifier: " + qualifier + ",value: " + value);
			}
		}
		
		return data;
	}	
}
