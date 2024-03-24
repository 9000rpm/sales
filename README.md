# Apache Spark Sales Processing Application
-------------------------------------------
Processing sales data using Apache Spark 

Run below commands:
-------------------
This will clean, compile and package the Scala application into sales-1.0-SNAPSHOT.jar:  
mvn clean package

This will start the Spark container:    
docker-compose up -d

This will copy the required file files to the container:    
docker cp target/sales-1.0-SNAPSHOT.jar spark_master:/opt/bitnami/spark/.
docker cp pre_file_operations.sh spark_master:/opt/.
docker cp post_file_operations.sh spark_master:/opt/.

This will unzip the provided file:
docker exec -it spark_master /bin/sh -c /opt/pre_file_operations.sh

This will submit the job to Apache Spark:  
docker exec -it spark_master spark-submit --master spark://spark:7077 --class org.opensky.App sales-1.0-SNAPSHOT.jar

This will rename to output.csv and cleanup:  
docker exec -it spark_master /bin/sh -c /opt/post_file_operations.sh

This will stop the Spark container:  
docker-compose down
