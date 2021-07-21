"#graphical approach to solve an ElectricalCircuit" 
Algorithm design:
This project solve an electrical circuit
using graph theory approach
you can watch this youtube lecture to get known with this
special approach to solve electrical circuit (https://www.youtube.com/watch?v=F8qiM3o0Jc0)

its simple gui implemented with JavaFX using EventHandler for background threads
the gui needs to be developed in next phases

there is three packages in this project
1.ElectricalCircuit:
in this approach we should assume the electrical circuit as gates
I implemented battery and resistor but the capacitors and ... would be added in the next phases
2.Graph:
as this project solve an electrical circuit with a graph this package created to create
special kind of graph data structure based on Nodes and branches
as we use kirchhoff rules and N equation N unknown there is an equation class too
3:Electrical Circuit:
exept the Matrix class this package is about the gui and background task in the ElectricalCircuit.java
there is mesal() function that run the app on the console if you call it in the main function

Example:lets solve the example in this bolg https://blog.faradars.org/kirchhoffs-circuit-law/
![image](https://user-images.githubusercontent.com/53050138/126564489-9f402c78-f3da-4ee5-9127-8c629a3bbd0d.png)

as you can see by assuming this circuit as a graph weve got 2 Knot so wirte two and press nextstep button
two nodes field would be appear Node1 and Node2 ,
As each Node is connected to three branches so write 3 in the Node fields
the branch fields would automaticly appear so write what does each branch got in the special format so
the code would parse that!
for example R20,B20 means that in this branch we got 20 ohm resistor and 20 volt battery then by clicking next step you get the answer!
![exm](https://user-images.githubusercontent.com/53050138/126566059-9cb1b268-222a-48bb-a8a0-79f59dfc027a.png)

Open the "OPEN ME" folder there i attached a pdf of this example
