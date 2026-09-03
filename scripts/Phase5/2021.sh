#!/bin/bash
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-06.json" -f "2021-06.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-07.json" -f "2021-07.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-08.json" -f "2021-08.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-09.json" -f "2021-09.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-10.json" -f "2021-10.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-11.json" -f "2021-11.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2021-12.json" -f "2021-12.json"