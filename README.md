# parking-lot

Run: requires docker [(download)](https://www.docker.com/products/docker-desktop)  
docker build --no-cache -t parkinglot .  
docker run parkinglot

Build:
requires java 1.8 [(download)](https://openjdk.java.net/install/)
and maven 3 [(download)](https://maven.apache.org/install.html) installed  
In the pom.xml directory execute

mvn clean package  
java -jar target/parking-lot-1.0-SNAPSHOT-jar-with-dependencies.jar (OptionalArgs defaults to input.txt in same dir) -f input-file-path   
