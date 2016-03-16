#!/usr/bin/env sh

sbt package
${SPARK_HOME}/bin/spark-submit --class "SparkAqlExample" --master local target/scala-2.10/spark-aql_2.10-1.0.jar
