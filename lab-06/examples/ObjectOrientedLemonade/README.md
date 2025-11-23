Lab 06 — Java Packages, Compilation, Testing, Documentation, and Maven
Overview

This lab demonstrates full proficiency in managing Java programs across multiple stages: multi-class structure, packages, manual compilation, testing, documentation, and Maven project management.
The work is divided into six sections (A–F), all completed.

A. Understanding a Multi-Class Java Project
Tasks Completed

Analyzed the Object-Oriented Tic-Tac-Toe project.

Reviewed all classes (game logic, listeners, UI, opponents).

Compiled manually:

javac *.java


Ran successfully:

java Main --cli
java Main --swing

B. Manual Compilation of Multi-File Java Projects
Tasks Completed

Created a sources.txt listing all .java files.

Compiled using:

javac -d ../bin -cp ../bin; @sources.txt


Executed successfully:

java -cp ../bin Main

C. Splitting Tic-Tac-Toe Into Packages
Tasks Completed

Created structured folders:

PackagedTicTacToe/
    gamelogic/
    userinterface/
    main/


Added package declarations to every file.

Updated all imports.

Compiled using:

javac -d . gamelogic/*.java userinterface/*.java main/Main.java


Ran successfully:

java -cp . main.Main --cli
java -cp . main.Main --swing

D. Studying Maven Project Organization
1. simple-maven-project

Read README.md

Ran all commands:

mvn compile
mvn exec:java -Dexec.mainClass="com.example.App"

2. multi-file-maven

Explored project layout.

Ran:

mvn compile
mvn exec:java -Dexec.mainClass="com.example.App"

3. library-catalog (with tests)

Ran tests:

mvn test


Ran program:

mvn exec:java -Dexec.mainClass="com.example.library.App"

4. doc-demo (Documentation + Maven Site)

Generated documentation:

mvn javadoc:javadoc
mvn site
mvn site:run


Viewed at http://localhost:8080

E. Converting Procedural Code Into OOP
Tasks Completed

Analyzed the original LemonadeStand.java.

Designed a clean object-oriented model.

Created:

ObjectOrientedLemonade/
    LemonadeGame/
        src/main/java/com/example/lemonade/LemonadeGame.java
        src/main/java/com/example/lemonade/Main.java


Verified functionality:

javac Main.java LemonadeGame.java
java Main

F. Adding Maven, Javadoc, and Tests to the OOP Lemonade Game
Tasks Completed

Generated Maven project:

mvn -q archetype:generate -DgroupId=com.example.lemonade -DartifactId=LemonadeGame -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false


Integrated project files into proper Maven structure:

src/main/java/com/example/lemonade/Main.java
src/main/java/com/example/lemonade/LemonadeGame.java
src/test/java/com/example/lemonade/AppTest.java


Added Javadoc to public classes and methods.

Ran:

mvn clean verify
mvn test
mvn compile
mvn exec:java -Dexec.mainClass="com.example.lemonade.Main"
mvn javadoc:javadoc

Technologies Used

Java 17+

Maven 3.9

Swing

CLI Java

JUnit

Manual compilation workflows

Maven site + documentation

How to Run Everything
Packaged Tic-Tac-Toe
cd PackagedTicTacToe
javac -d . gamelogic/*.java userinterface/*.java main/Main.java
java -cp . main.Main --cli
java -cp . main.Main --swing

OOP Lemonade Game (Maven)
cd ObjectOrientedLemonade/LemonadeGame
mvn compile
mvn exec:java -Dexec.mainClass="com.example.lemonade.Main"
