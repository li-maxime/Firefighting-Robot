Ensimag 2A POO - TP 2022/23
============================

Academic Project

Objective : Develop an object oriented program that controls fire fighting robots in a given environment.






Team : Equipe de 3  
Time Spent : ~50h 
Technologies used:  
- Java



Compilation:
You can start the compilation with make all.  
You can recompile the Main with make main.  
You can run the code on the map carteSujet.map by running make simu.
To run on other map you should use :  
java -classpath bin:bin/gui.jar Main cartes/<selectedMap.map>

you should use JDK17 if you have a problem with X11

If you want to run our tests/ 
First compile the tests:  
javac -d bin -classpath bin/gui.jar -sourcepath src src/<TestName>  
Then execute them:  
java -classpath bin:bin/gui.jar <TestName>  
 

- src: contains the provided classes  
   -> DataReader.java: reads all the elements of a data description file (boxes, fires and robots) and displays them. 
                                    It's up to you to MODIFY this class (or write a new one) to create  

- maps: some examples of data files 

- bin/gui.jar: Java archive containing the graphical interface classes. See a usage example in TestInvader.java 

- doc: the documentation (API) of the GUI classes contained in gui.jar. Entry point: index.html 

- Makefile: some explanations about online compilation, including the concept of classpath and the use of gui.jar 

