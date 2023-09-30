# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le Main et de lancer une simulation sur la carte carteSujet.map .
# Faite make main pour compiler le Main puis make simu pour lancer la simulation.

all: testInvader testLecture main

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java
	
main:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/Main.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:bin/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map
	
simu :
	java -classpath bin:bin/gui.jar Main cartes/carteSujet.map

clean:
	rm -rf bin/*.class
