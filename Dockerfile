FROM openjdk:8-jre-alpine

WORKDIR /

ADD target/parking-lot-1.0-SNAPSHOT-jar-with-dependencies.jar parking-lot-1.0-SNAPSHOT-jar-with-dependencies.jar

ADD input.txt input.txt
ADD hibernate.cgf.xml hibernate.cgf.xml

CMD java -jar parking-lot-1.0-SNAPSHOT-jar-with-dependencies.jar