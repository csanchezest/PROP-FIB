CLASES = src/dominio/clases
CONTROLADORES = src/dominio/controladores

default: class

class:
	javac -d bin -cp $(CONTROLADORES)/junit-4.13.2.jar:$(CONTROLADORES)/hamcrest-core-1.2.jar $(CLASES)/*.java $(CONTROLADORES)/*.java
	cp -r src/persistencia bin/persistencia
	
run:
	java -cp $(CONTROLADORES):bin dominio.clases.Main
	
DCG:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverDCG
	
cjt:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverCjt_items
	
collaborative:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverCollaborative

content:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverContentBased

item:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverItem
	
kmeans:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverKMeans
	
slope:
	java -cp $(CONTROLADORES):bin dominio.controladores.DriverSlopeOne
	
test: 
	java -cp $(CONTROLADORES):$(CONTROLADORES)/junit-4.13.2.jar:$(CONTROLADORES)/hamcrest-core-1.2.jar:bin dominio.controladores.MyDistanceTestRunner
	
valoracio:
	java -cp $(CONTROLADORES):$(CONTROLADORES)/junit-4.13.2.jar:$(CONTROLADORES)/hamcrest-core-1.2.jar:bin dominio.controladores.DriverValoracio

clean:
	rm -rf bin/*
