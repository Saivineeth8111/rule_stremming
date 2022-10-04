package com.qualcomm.rule.spark;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qualcomm.rule.service.RuleEngineService;
import com.qualcomm.rulesImpl.DeviceRuleEngine.RuleInputDetails;

import scala.Tuple2;

@Service
public class RuleEngineSparkJob {

	@Autowired
	SparkSession spark;

	@Autowired
	private RuleEngineService ruleEngineService;

	public void sparkjobs(List<RuleInputDetails> inputDeatils) throws StreamingQueryException, TimeoutException {
	}

	public static void main(String args[]) throws TimeoutException, StreamingQueryException, InterruptedException {

		Logger.getLogger("org.apache").setLevel(Level.WARN);
		Logger.getLogger("org.apache.spark.storage").setLevel(Level.ERROR);
		Logger.getLogger("org.apache").setLevel(Level.OFF);

		SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("viewingFigures");

		JavaStreamingContext sc = new JavaStreamingContext(conf, Durations.seconds(1));

		Collection<String> topics = Arrays.asList("mytopic");

		Map<String, Object> params = new HashMap<>();

		params.put("bootstrap.servers", "localhost:9092");
		params.put("key.deserializer", StringDeserializer.class);
		params.put("value.deserializer", StringDeserializer.class);
		params.put("group.id", "group1");
		params.put("auto.offset.reset", "latest");
		params.put("enable.auto.commit", false);

		JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(sc,
				LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(topics, params));

		JavaPairDStream<Long, String> results = stream.mapToPair(item -> new Tuple2<>(item.value(), 5L))
				.reduceByKeyAndWindow((x, y) -> x + y, Durations.minutes(60), Durations.minutes(1))
				.mapToPair(item -> item.swap()).transformToPair(rdd -> rdd.sortByKey(false));
		
		

		results.print(50);

		sc.start();
		sc.awaitTermination();

//		SparkSession spark = SparkSession.builder().appName("MyStructuredStreamingJob").master("local[*]")
//				.getOrCreate();
//
//		Dataset<Row> df = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
//				.option("subscribe", "mytopic").load();
//
//		df.selectExpr("CAST(value AS STRING)");
//		StructType emp_schema = new StructType().add("word", DataTypes.StringType).add("msg_ts",
//				DataTypes.TimestampType);
//		
//		df = df.select(functions.from_json(functions.col("value"), emp_schema).alias("data"));

//		Dataset<String> words = df.as(Encoders.STRING()).flatMap(
//				(FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(), Encoders.STRING());

		// Generate running word count
//		Dataset<Row> wordCounts = words.groupBy("value").count();
//
//		StreamingQuery query = wordCounts.writeStream().outputMode("complete").format("console").start();
//
//		query.awaitTermination();

//				df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");
//				df.show();
//				
//				writeStream
//			    .format("kafka")
//			    .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
//			    .option("topic", "updates")
//			    .start()

//				StreamingQuery ds = df
//						  .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
//						  .writeStream()
//						  .format("kafka")
//						  .option("kafka.bootstrap.servers", "localhost:9092")
//						  .option("subscribe", "mytopic")
//						  .start();
//				
//				ds.awaitTermination();

//				df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
//				  .write()
//				  .format("kafka")
//				  .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
//				  .option("topic", "topic1")
//				  .save();
//		

//		Dataset<Row> df = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
//				.option("subscribe", "mytopic").option("includeHeaders", "true").option("startingOffsets", "latest")
//				.load();
//
//		df = df.selectExpr("CAST(value AS STRING)");
//		df.show();
//		StructType emp_schema_new = new StructType();
//
//		StructType emp_schema = new StructType().add("word", DataTypes.StringType).add("msg_ts",
//				DataTypes.TimestampType);
//		df = df.select(functions.from_json(functions.col("value"), emp_schema).alias("data"));
//		
//		df = df.select("data.*");
//		
//		df.printSchema();
//		
//		df.createOrReplaceTempView("people");
//
//		Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
//		sqlDF.show();

		// df.select("msg_ts").show();

		// StreamingQuery query = df.writeStream().

		// df = df.groupBy(functions.window(df.col("msg_ts"), "10 seconds", "10
		// seconds"), df.col("word")).count();

//		StreamingQuery query = df.writeStream().format("console").option("truncate", "False")
//				.outputMode(OutputMode.Complete()).option("compression", "none").start();
//
//		query.awaitTermination();

//		Encoder<RuleInputDetails> inputDeatilsEncoder = Encoders.bean(RuleInputDetails.class);
//		
//		Dataset<RuleInputDetails> ds = spark.createDataset(inputDeatils, inputDeatilsEncoder);
//
//		System.out.println("*** here is the schema inferred from the bean");
//		ds.printSchema();
//		ds.show();
//
//		Dataset<RuleInputDetails> ds1 = ds.distinct();
//		ds1.show();
//
//		List<RuleInputDetails> loo = ds1.collectAsList();
//		loo.forEach(System.out::println);
//
//		loo.forEach(input -> ruleEngineService.run(input));
//		
//
//		spark.stop();

	}

}
