all: out trafficLightInterface trafficLight trafficLightServerClient client

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

trafficLightServerClient:
	cd out && javac -cp trafficLightInterface.jar trafficLightServerClient/*.java
	rm -f out/trafficLightServerClient/*.java

client:
	cd out && javac -cp trafficLightInterface.jar:trafficLight.jar client/*.java
	rm -f out/client/*.java

run-rmiregistry:
	CLASSPATH=out/trafficLightInterface.jar rmiregistry

run-trafficLightServerClient:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    trafficLightServerClient.TrafficLightServerClient ${id} ${n} ${bearer}

run-client:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    client.ConnectingClient ${n}

clean:
	rm -rf out