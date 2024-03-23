# sales
Data processing sales data using Apache Spark 

Run below command to start Apache Spark Docker container:
---------------------------------------------------------
mvn clean package
docker-compose up -d
docker cp -L target/sales-1.0-SNAPSHOT.jar spark_master:/opt/bitnami/spark/.
docker exec -it spark_master spark-submit --master spark://spark:7077 --class org.opensky.App sales-1.0-SNAPSHOT.jar

Cleanup commands
----------------
docker-compose down
