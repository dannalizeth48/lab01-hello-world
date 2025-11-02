# Lab 04 – Graphical User Interfaces  
CSC 210 – Computer Programming 2  
Student: Danna Gomez  

## Overview  
This lab demonstrates Java Swing programming using four small applications that introduce buttons, labels, lists, and file-based data management.

## Programs  

**1. HelloGUI.java**  
Shows a button that displays a random greeting when clicked.  

**2. SingleCreatureGUI.java**  
Lets the user view and edit one `Creature` object using text fields and buttons.  

**3. MultipleCreatureGUI.java**  
Displays a list of several `Creature` objects, allows selecting, editing, and triggering actions (`eat()`).  

**4. CreaturesFromFileGUI.java**  
Loads and saves creatures from `creature-data.csv` using `ProcessCreatureFile`. Supports adding, editing, and deleting records through a GUI.  

## How to Compile and Run  
Run from inside the `lab-04` folder:

```bash
javac *.java
java HelloGUI
java SingleCreatureGUI
java MultipleCreatureGUI
java CreaturesFromFileGUI
