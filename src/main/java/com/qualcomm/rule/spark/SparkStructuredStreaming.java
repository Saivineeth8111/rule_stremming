package com.qualcomm.rule.spark;

import java.util.concurrent.TimeoutException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class SparkStructuredStreaming {

	public static void main(String[] args) throws StreamingQueryException, TimeoutException {

		SparkSession spark = SparkSession.builder().appName("MyStructuredStreamingJob").master("local[*]")
				.getOrCreate();
		Dataset<Row> df = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "127.0.0.1:9092")
				.option("subscribe", "mytopic").option("includeHeaders", "true").option("startingOffsets", "latest")
				.load();
		df = df.selectExpr("CAST(value AS STRING)");

		StructType emp_schema = new StructType().add("word", DataTypes.StringType).add("msg_ts",
				DataTypes.TimestampType);
		df = df.select(functions.from_json(functions.col("value"), emp_schema).alias("data"));
		df = df.select("data.*");

		df = df.groupBy(functions.window(df.col("msg_ts"), "10 seconds", "10 seconds"), df.col("word")).count();

		StreamingQuery query = df.writeStream().format("console").option("truncate", "False")
				.outputMode(OutputMode.Complete()).option("compression", "none").start();
		query.awaitTermination();

	}
}
