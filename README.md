# Programación Distribuida: Diseño e Implementación de un Servicio Distribuido de Semáforos utilizando Suzuki-Kasami.

Project as part of the course of Distribuited Systems at the "Universidad Tecnica Federico Santa Maria", based in Valparaiso, Chile.
___

Grupo : Antolini Charlotte, Dorel Joachim & Legeron Camille

___

## 1. Launch of the project

### 1. Launch of the RMI-Registry

```
make run-rmiregistry
```

### 2. Launch of the Token Engine
```
make run-tokenEngine
```

### 3. Launch of a Semaphore[^2]
Each semaphore takes 4 arguments :
- **id** an unique id for the semaphore
- **n** the total number of semaphores
- **initialDelay** the initial delay for a semaphore to enter into his critic section
- **bearer** if the semaphore is holding the token originally or not

```
make run-trafficLightServerClient id={id} n={n} initialDelay={initialDelay} bearer={bearer}
```

### 4. Connection of all the semaphores
Once all the semaphores are created, we must connect them all to each other.
This command takes one argument :
- **n** the total number of semaphores

```
make run-client n={n}
```

## 2. Architecture of the project

The project is centered around the use of RMI. In order to implement the Suzuki-Kasami
algorithm, we used the way RMI interacts between clients and servers. Each semaphore
corresponds in our code as both a Client and a Server, and the interaction between
the semaphores is done through the interface the all share. Locally, they only manage their
local RN queue, while they exchange between themselves the token and then update his
list LN, list who correspond to the next semaphore entering into a critic session.

[^2]: In our project, a semaphore corresponds to a TrafficLightServerClient, or more simply to a traffic light. This change was made to have a better understanding of the logic of a semaphore.
