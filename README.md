# What this repository is about?
this repository is about modeling circuits as graphs and
analyzing them with graph theory algorithms for more detail
<a href=http://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf>read this article</a>
,or if you know persian see <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/Docs/main/main.pdf>this</a>

# How to solve an Electrical circuit with run Gracc?
- install Gracc with
```
    pip install Gracc
```

[![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Mehrdadghassabi/Gracc/blob/master/Gracc.ipynb)

# How to give Gracc a circuit as an input?
the goal is to give a picture as an input but since <a href=https://github.com/estineali/Hand-Drawn-Circuits>
hand drawn circuit detection</a> is still underdevelopment we use a text file to do so :-)

# How to give Gracc an input via text?
- well, we got to descript a circuit (kirchoff_graph) with a text file to do so: </br>
in the first line of the text write two number, the first one is nodes_number and the second is edges_number </br>
in the next n lines (n = edges_number) you've got to explain the edges details </br>
such that ( x y z a b c ) means that node x and y are </br>
connected with a z_ohm resistor , a_volt battery , b_farad capacitor & c_henry inductor
- for example consider <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/circuits/circuit1.txt>
circuit1</a> as an input,
it is descripting this circuit

![Screenshot from 2022-08-12 04-55-20](https://user-images.githubusercontent.com/53050138/184503563-00484e0f-4007-424e-aec8-2a28b114a8c6.png)

- after descripting your circuit as a text locate it <a href=https://github.com/Mehrdadghassabi/Gracc/tree/master/circuits>
Here</a>
open the jupyter file and download your circuit by wget like that

![image](https://user-images.githubusercontent.com/53050138/184503786-c396c3e7-481c-4a29-bab3-6179c2ced02d.png)

# How to see the solution of our circut?
- run all the cell in the jupyter file
- parse your text file to create the kirchoff graph by
```
kg5 = circuit_parser('circuit5.txt')
```
- plot your input to see your input by
```
plot_kirchoffgraph(kg5)
```
- analyze the input by
```
plot_kirchoffgraph_after_solving(kg5)
```
- then you will see the solution of your circuit
for example for the <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/circuits/circuit5.txt>
circuit5</a> the solution is:

![image](https://user-images.githubusercontent.com/53050138/184504011-aa7d2716-6cae-4d13-bfcf-076be7483a6b.png)

# Notes
- gracc doesn't support RLC circuits yet :)
- solving differential equations which is a difficult task itself is done thanks to <a href=https://github.com/WarrenWeckesser/odeintw>
odeintw</a>
- <a href=https://github.com/Mehrdadghassabi/Gracc/tree/master/Archive>archive</a> folder is just for making an archive, delete it in your own fork
