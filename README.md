# Description

PoC Olive Backend HBase

## Start HBase environment

```shell
docker run -d --name hbase -p 2181:2181 -p 16010:16010 -p 16020:16020 -p 16030:16030 harisekhon/hbase
```

## Start HBase WebUI 

```shell
http://localhost:16010

```

![HBase WebUI](captures/hbase_webui.png "HBase WebUI")


## Springboot configurations

 Although it is simple to build hbase environment through docker, there is a problem that its host mapping directly uses the container ID. therefore, if our external java program needs to connect to hbase, we need to add the container ID to the host list of our machine
 
## Create a table

From endpoint createTable pass these attributes:

- tableName: table name
- colFamily: Column Family name

![HBase Create Table](captures/hbase_create_table.png "HBase Create Table")

## Insert a register in a table

From endpoint createTable pass these attributes:

- tableName: table name
- colFamily: Column Family name
- rowkeys: Unique key row
- In the body: set all columns/values to be inserted

![HBase Insert register](captures/hbase_put_data.png "HBase Insert register")

## Remove a register

From endpoint putData pass these attributes:

- tableName: table name
- rowkeys: Unique key row

![HBase Remove register](captures/hbase_delete_data.png "HBase Remove register")

## Get all registers from a table

From endpoint getData pass these attributes:

- tableName: table name

![HBase Get data from a Table](captures/hbase_get_data.png "HBase Get data from a Table")