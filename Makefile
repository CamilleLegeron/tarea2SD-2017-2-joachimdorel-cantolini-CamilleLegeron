all: out trafficLightInterface trafficLight trafficLightServerClient client tokenInterface token tokenEngine

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

tokenInterface:
	cd out && javac tokenInterface/*.java
	cd out && jar cvf tokenInterface.jar tokenInterface/*.class
	rm -rf out/tokenInterface

token:
	cd out && javac -cp tokenInterface.jar token/*.java
	cd out && jar cvf token.jar token/*.class
	rm -rf out/token

tokenEngine:
	cd out && javac -cp tokenInterface.jar tokenEngine/*.java
	rm -f out/tokenEngine/*.java

client:
	cd out && javac -cp trafficLightInterface.jar:trafficLight.jar client/*.java
	rm -f out/client/*.java

run-rmiregistry:
	CLASSPATH=out/trafficLightInterface.jar rmiregistry



run-trafficLightServerClient:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    trafficLightServerClient.TrafficLightServerClient ${id} ${n} ${bearer}

run-tokenEngine:
	cd out && java -cp .:tokenInterface.jar:token.jar\
	    -Djava.security.policy=security.policy\
	    tokenEngine.TokenEngine



run-client:
	cd out && java -cp .:trafficLightInterface.jar:trafficLight.jar\
	    -Djava.security.policy=security.policy\
	    client.ConnectingClient ${n}

clean:
	rm -rf out