#!/bin/bash
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-01.json" -sd "2023-01-01" -ed "2023-01-31" -f "2023-01.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-02.json" -sd "2023-02-01" -ed "2023-02-28" -f "2023-02.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-03.json" -sd "2023-03-01" -ed "2023-03-31" -f "2023-03.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-04.json" -sd "2023-04-01" -ed "2023-04-30" -f "2023-04.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-05.json" -sd "2023-05-01" -ed "2023-05-31" -f "2023-05.json"
java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p1 "/home/cloud-user/storage/2023-06.json" -sd "2023-06-01" -ed "2023-06-19" -f "2023-06.json"