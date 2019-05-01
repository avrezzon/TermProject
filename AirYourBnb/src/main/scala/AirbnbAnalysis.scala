import java.io.File

import scala.io.Source._

import org.apache.spark.sql.SparkSession

/*

https://stackoverflow.com/questions/40800920/how-do-i-convert-arrayrow-to-dataframe
 */

object AirbnbAnalysis {
  def main(args: Array[String]) {


    /*
    val spark = SparkSession
      .builder
      .appName("AirYourBnb")
      .getOrCreate()

    val sparkContext = spark.sparkContext;

    val anthony_hdfs = "hdfs:///cs455/termproject/airbnb-listings.csv"
    val daniel_hdfs = "hdfs:///cs455/TERM/airbnb-listings.csv"
    val bruce_hdfs = "hdfs:///airbnb/out.csv"

    val user = args(0)
    val filename = ""

    user match {
      case 1  => filename = anthony_hdfs
      case 2  => filename = bruce_hdfs
      case 3  => filename = daniel_hdfs
    }

    val df = spark.read.format("org.apache.spark.csv").option("header", true).option("delimiter", ";").csv("/airbnb/out.csv")
    val table = df.select("ID", "Country", "Property Type", "Amenities", "Price", "Accommodates")


    val nn = table.where(df.col("ID").isNotNull).where(df.col("Country").isNotNull).where(df.col("Property Type").isNotNull).where(df.col("Accommodates").isNotNull).where(df.col("Amenities").isNotNull)

    val amenity_list = nn.rdd.map(row => ((row.getString(1),row.getString(2)), row.getString(3)))

    val amenity_flattend = amenity_list.flatMap{ case (key, list) => list.split(",").map(nr => (key+":"+nr, 1))}

    val amenity_count = amenity_flattend.reduceByKey(  (x:(Int), y:( Int)) => ( x+y ) )
    val num_of_listings = amenity_list.map(key => (key, 1)).reduceByKey(_+_).toDF()

    //This is an attempt to try to filter out amenities that are present in at least 50% of the listings
    val avg_amenity = amenity_count.filter(x => num_of_listings.select("_2").where(num_of_listings.col("_2").contains(((x.split(":")(1)+":"+x.split(":")(2))))) / x._2 >.5)
    */
  }
}
