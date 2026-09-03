#!/bin/bash

java -jar /tmp/mysql_data_migration-jar-with-dependencies.jar -c

#Phase 0
/home/cloud-user/scripts/Phase0.sh

#Phase 2
/home/cloud-user/scripts/Phase2.sh

#Phase 3
/home/cloud-user/scripts/Phase3.sh

#Phase 4
/home/cloud-user/scripts/Phase4.sh

#Phase 5
/home/cloud-user/scripts/Phase5.sh


