# What this repository is about?
this repository is about modeling circuits as graphs and
analyzing them with graph theory algorithms
<a href=http://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf>read this article</a>

# How to run this?
[![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Mehrdadghassabi/Gracc/blob/master/Source/GRacC.ipynb)

# How to give Gracc a circuit as an input?
the goal is to give a picture as an input but since <a href=https://github.com/estineali/Hand-Drawn-Circuits>
hand drawn circuit detection</a> is still underdevelopment we use a text file to do so :-)

# How to give Gracc an input via text?
- well we got to descript a circuit(kirchoff_graph) with a text file to do so
in the first line of the text write two number the first one is nodes_number and the second is edges_number
in the next n lines (n = edges_number) youve got to explain the edges details
such that ( x y z a b c ) means that node x and y are </br>
connected with a z_ohm resistor , a_volt battery , b_farad capacitor & c_henry inductor
- for example consider <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/Source/circuits/circuit1.txt>
circuit1</a> as an input
it is descripting this circuit

![Screenshot from 2022-08-12 04-55-20](https://user-images.githubusercontent.com/53050138/184503563-00484e0f-4007-424e-aec8-2a28b114a8c6.png)
