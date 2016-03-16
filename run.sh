#!/usr/bin/env sh

source settings.sh
SPARK_HOME=${1:-"$HOME/work/spark-1.6.1-bin-hadoop2.6"}
EXAMPLE_CLASS="SparkAqlExample"
SCALA_VERSION="2.11"
PROJECT_VERSION="0.1.0"

# set sbt repositories to China mirrors if necessary
if ! cmp ~/.sbt/repositories ./sbt_repositories >/dev/null 2>&1
then
    echo "Replace sbt repositories with mirrors in China"
    cp ./sbt_repositories ~/.sbt/repositories
fi

echo "sbt package"
sbt package

${SPARK_HOME}/bin/spark-submit\
    --jars aql/target/scala-${SCALA_VERSION}/spark-aql_${SCALA_VERSION}-${PROJECT_VERSION}.jar\
    --class ${EXAMPLE_CLASS}\
    --master local\
    examples/target/scala-${SCALA_VERSION}/spark-aql-examples_${SCALA_VERSION}-${PROJECT_VERSION}.jar
    # parameters here