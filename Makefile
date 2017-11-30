all: out trafficLightInterface trafficLight engine client

out:
	mkdir out
	cp -r src/* out/

trafficLightInterface:
	cd out && javac trafficLightInterface/*.java
	cd out && jar cvf trafficLightInterface.jar trafficLightInterface/*.class
	rm -rf out/trafficLightInterface

trafficLight:
	cd out && javac -cp trafficLightInterface.jar trafficLight/*.java
	cd out && jar cvf trafficLight.jar trafficLight/*.class
	rm -rf out/trafficLight

engine:
	cd out && javac -cp trafficLightInterface.jar engine/*.java
	rm -f out/engine/*.java

client:
	cd out && javac -cp trafficLightInterface.jar:trafficLight.jar client/*.java
	rm -f out/client/*.java

run-rmiregistry:
	CLASSPATH=out/trafficLightInterface.jar rmiregistry

run-engine:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    engine.TrafficLightEngine

run-client:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    client.ComputeTrafficLight

clean:
	rm -rf out