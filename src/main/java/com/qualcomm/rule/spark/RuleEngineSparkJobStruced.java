package com.qualcomm.rule.spark;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

public class RuleEngineSparkJobStruced {

	public static void main(String args[]) throws TimeoutException, StreamingQueryException, InterruptedException {

//		Logger.getLogger("org.apache").setLevel(Level.WARN);
//		Logger.getLogger("org.apache.spark.storage").setLevel(Level.ERROR);

		SparkSession session = SparkSession.builder().master("local[*]").appName("structuredViewingReport")
				.getOrCreate();

//		session.conf().set("spark.sql.shuffle.partitions", "10");

		Dataset<Row> df = session
				.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092").option("subscribe",
						"mytopic")/* .option("includeHeaders", "true").option("startingOffsets", "latest") */
				.load();

		df.createOrReplaceTempView("viewing_figures");


		System.out.println(df.collectAsList().toString());
//		df.foreach(check -> System.out.println(check));

		Dataset<Row> results = session.sql("select value from viewing_figures");
//
		StreamingQuery query = results.writeStream().format("console").outputMode(OutputMode.Append()).start();
//
		query.awaitTermination();
//		session.aw

//		StreamingQuery query = results.writeStream().format("console")/* .outputMode(OutputMode.Append()) */.start();
//		session.stop();

//		df.selectExpr("CAST value AS STRING");
//
//		Dataset<String> words = df.as(Encoders.STRING()).flatMap(
//				(FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(), Encoders.STRING());
//
//		// Generate running word count
//		Dataset<Row> wordCounts = words.groupBy("value").count();
//
//		StreamingQuery query = wordCounts.writeStream().outputMode("complete").format("console").start();
//
//		query.awaitTermination();

	}

}
