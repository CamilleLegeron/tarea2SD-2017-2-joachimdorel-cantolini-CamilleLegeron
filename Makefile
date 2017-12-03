all: out tokenInterface token tokenEngine trafficLightInterface trafficLightServerClient client

out:
	mkdir out
	cp -r src/* out/

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

trafficLightInterface:
	cd out && javac token.jar trafficLightInterface/*.java
	cd out && jar cvf trafficLightInterface.jar trafficLightInterface/*.class
	rm -rf out/trafficLightInterface

trafficLightServerClient:
	cd out && javac -cp tokenInterface.jar:token.jar:trafficLightInterface.jar trafficLightServerClient/*.java
	rm -f out/trafficLightServerClient/*.java

client:
	cd out && javac -cp trafficLightInterface.jar client/*.java
	rm -f out/client/*.java

run-rmiregistry:
	CLASSPATH=out/trafficLightInterface.jar rmiregistry



run-trafficLightServerClient:
	cd out && java -cp .:trafficLightInterface.jar\
	    -Djava.security.policy=security.policy\
	    trafficLightServerClient.TrafficLightServerClient ${id} ${n} ${initialDelay} ${bearer}

run-tokenEngine:
	cd out && java -cp .:tokenInterface.jar:token.jar\
	    -Djava.security.policy=security.policy\
	    tokenEngine.TokenEngine


run-client:
	cd out && java -cp .:trafficLightInterface.jar\
	    -Djava.security.policy=security.policy\
	    client.ConnectingClient ${n}

clean:
	rm -rf out