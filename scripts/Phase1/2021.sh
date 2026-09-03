#!/bin/bash
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-06.json" -sd "2021-06-01" -ed "2021-06-30" -f "2021-06.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-07.json" -sd "2021-07-01" -ed "2021-07-31" -f "2021-07.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-08.json" -sd "2021-08-01" -ed "2021-08-31" -f "2021-08.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-09.json" -sd "2021-09-01" -ed "2021-09-30" -f "2021-09.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-10.json" -sd "2021-10-01" -ed "2021-10-31" -f "2021-10.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-11.json" -sd "2021-11-01" -ed "2021-11-30" -f "2021-11.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2021-12.json" -sd "2021-12-01" -ed "2021-12-31" -f "2021-12.json"