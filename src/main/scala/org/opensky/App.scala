package org.opensky

import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.sql.SparkSession

/**
 * @author ${user.name}
 */
object App {

  lazy val logger: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val spark = org.apache.spark.sql.SparkSession.builder
                .master("local")
                .appName("Spark CSV Sales Reader")
                .getOrCreate;

    spark.read
      .option("header",true)
      .option("inferSchema",true)
      .csv("/opt/sales_data_sample.csv")
      .createOrReplaceTempView("sales")

    val salesAvgDf = spark.sql("select YEAR_ID, PRODUCTLINE, round(avg(sales),2) AVERAGE_SALES_AMT from sales where status='Shipped' group by YEAR_ID, PRODUCTLINE order by YEAR_ID, PRODUCTLINE")

    salesAvgDf.write
      .format("csv")
      .option("header", "true")
      .mode("overwrite")
      .save("/opt/output.csv")

    //Stop the SparkSession
    spark.stop()
  }
}
