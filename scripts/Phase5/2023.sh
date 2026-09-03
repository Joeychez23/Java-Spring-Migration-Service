#!/bin/bash
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-01.json" -f "2023-01.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-02.json" -f "2023-02.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-03.json" -f "2023-03.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-04.json" -f "2023-04.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-05.json" -f "2023-05.json"
java -Xmx -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -p5 "/home/cloud-user/storage/2023-06.json" -f "2023-06.json"