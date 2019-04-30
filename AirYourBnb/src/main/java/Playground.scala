import java.io.File

import scala.io.Source._

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._


/*

https://stackoverflow.com/questions/40800920/how-do-i-convert-arrayrow-to-dataframe
 */

object Playground {
  def main(args: Array[String]) {



    val spark = SparkSession
      .builder
      .appName("DFS Read Write Test")
      .getOrCreate()

    val sparkContext = spark.sparkContext;

    val sqlContext = new org.apache.spark.sql.SQLContext(sparkContext)
    import sqlContext.implicits._
    import org.apache.spark.sql.functions._


    val df = spark.read.format("org.apache.spark.csv").option("header", true).option("delimiter", ";").csv("/airbnb/out.csv")

    val amen = df.select("Amenities", "Price", "Country")

    val amenSample = amen.sample(.001)

    //val stuff = amenSample.rdd.collect

    val CP = df.select("Country","Price","Property Type", "Accommodates", "Amenities", "City")

    val noNull1 = CP.where(df.col("Price").isNotNull)

    val noNull2 = noNull1.where(df.col("Country").isNotNull)

    val noNull3 = noNull2.where(df.col("Property Type").isNotNull)

    val noNull4 = noNull3.where(df.col("Accommodates").isNotNull)

    val noNull5 = noNull4.where(df.col("Amenities").isNotNull)

    val noNull6 = noNull5.where(df.col("City").isNotNull)

    //mapreduce to get total listings per country and total price per country
    //val hmm = noNull4.rdd.map(row => ( (row.getString(0),  row.getString(2)), (row.getString(1).toInt/row.getString(3).toInt, 1) ) ).reduceByKey((x:(Int, Int), y:(Int, Int)) => (x._1+y._1, x._2+y._2))

    val eylmao = noNull5.rdd.flatMap(row =>  row.getString(4).split(",").map(am => ( (row.getString(0),  row.getString(2), am), (row.getString(1).toInt/row.getString(3).toInt, 1) ) ) )
    val lmao = eylmao.reduceByKey((x:(Int, Int), y:(Int, Int)) => (x._1+y._1, x._2+y._2))
    //make 5 columns in dataset
    //country, property type, average per night per person, number of listings, total combined price/people
    val hmmst = lmao.map(row => (row._1._1, row._1._2, row._1._3, (row._2._1/row._2._2), row._2._2, row._2._1) )

    val countryRankingsMap = noNull5.rdd.flatMap(row =>  row.getString(4).split(",").map(am => ( (row.getString(0)), (row.getString(1).toInt/row.getString(3).toInt, 1) ) ) )
    val countryRankingRed = countryRankingsMap.reduceByKey((x:(Int, Int), y:(Int, Int)) => (x._1+y._1, x._2+y._2))
    val countryRankingSplit = countryRankingRed.map(row => (row._1, (row._2._1/row._2._2) )
    val countryRanking = countryRankingSplit.toDF.orderBy(desc("_2"))

    val countryAmenitiesMap


    val perEverything = hmmst.toDF

    val sorted = perEverything.orderBy(desc("_5"))


    //val sorted = perEverything.orderBy(asc("_1"), asc("_3"), desc("_2"),  desc("_5"))

    //val fileRDD = spark.sparkContext.parallelize(amens.sample(.01), 1);
    //fileRDD.saveAsTextFile("/debug3/whaat2");

    val fileRdd = spark.sparkContext.parallelize(sorted.collect(), 1)

    fileRdd.saveAsTextFile("/debug3/me5thanks8")

    spark.stop();
  }
}

