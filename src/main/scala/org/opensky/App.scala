package org.opensky

import org.apache.logging.log4j.{LogManager, Logger}

/**
 * @author Kamil Mahabir
 */
object App {

  lazy val logger: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    logger.info("creating spark session..")
    val spark = org.apache.spark.sql.SparkSession.builder
                .master("local")
                .appName("Spark CSV Sales")
                .getOrCreate

    logger.info("reading sales csv file..")
    spark.read
      .option("compression", "gzip")
      .option("header","true")
      .option("inferSchema","true")
      .csv("/data/input/sales_data_sample.csv.gz")
      .createOrReplaceTempView("sales")

    logger.info("executing spark sql.. ")
    val salesAvgDf = spark.sql(
      """SELECT YEAR_ID, PRODUCTLINE, round(avg(sales),2) AVERAGE_SALES_AMT
                   FROM sales
                  WHERE status='Shipped'
                  GROUP BY YEAR_ID, PRODUCTLINE
                  ORDER BY YEAR_ID, PRODUCTLINE""")

    logger.info("writing average sales to csv file.. ")
    salesAvgDf.coalesce(1)
      .write
      .format("csv")
      .option("header", "true")
      .mode("overwrite")
      .save("/data/output")

    logger.info("stopping spark session..")
    spark.stop()

    logger.info("Successfully Completed Spark Sales Application.")
  }
}
