# Programación Distribuida: Diseño e Implementación de un Servicio Distribuido de Semáforos utilizando Suzuki-Kasami.

Project as part of the course of Distribuited Systems at the "Universidad Tecnica Federico Santa Maria", based in Valparaiso, Chile.
___

Grupo : Antolini Charlotte, Dorel Joachim & Legeron Camille

___

## 1. Launch of the RMI-Registry

```
make run-rmiregistry
```

## 2. Launch of the Token Engine
```
make run-tokenEngine
```
## 3. Launch of a Semaphore
Each semaphore takes 4 arguments :
- **id** an unique id for the semaphore
- **n** the total number of semaphores
- **initialDelay** the initial delay for a semaphore to enter into his critic section
- **bearer** if the semaphore is holding the token originally or not
```
make run-trafficLightServerClient id={id} n={n} initialDelay={initialDelay} bearer={bearer}
```
## 4. Connection of all the semaphores
Once all the semaphores are created, we must connect them all to each other.
This command takes one argument :
- **n** the total number of semaphoresx
```
make run-client n={n}
```
