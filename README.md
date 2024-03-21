# sales
Data processing sales data using Apache Spark 

Run below command to start Apache Spark Docker container:
---------------------------------------------------------
docker-compose up -d
mvn clean package
docker cp -L target/sales-1.0-SNAPSHOT.jar spark_master:/opt/bitnami/spark/.
docker cp -L data/sales_data_sample.csv spark_master:/opt/.
docker exec -it spark_master spark-submit --master spark://spark:7077 --class org.opensky.App sales-1.0-SNAPSHOT.jar

Cleanup commands
----------------
docker-compose down
