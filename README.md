# What this repository is about?
this repository is about modeling circuits as graphs and
analyzing them with graph theory algorithms

<a href=http://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf>read this article</a>

# How to run this?
[![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Mehrdadghassabi/Gracc/blob/master/Source/GRacC.ipynb)

# How to give Gracc a circuit as an input?
- the goal is to give a picture as an input but since <a href=https://github.com/estineali/Hand-Drawn-Circuits>
hand drawn circuit detection</a> is still underdevelopment we use a text file to do so :-)
- consider <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/Source/circuits/circuit1.txt>
circuit1</a> as an input
the first line tell us that this circuit have 4 node and 5 edge
the next 5 lines explain the edges detail for us...
- ( x y z m n k ) means that node x and y are 
connected with a z_ohm resistor , m_volt battery , n_farad capacitor & k_henry inductor
