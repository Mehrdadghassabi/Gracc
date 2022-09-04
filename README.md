# What this repository is about?
this repository is about modeling circuits as graphs and
analyzing them with graph theory algorithms for more detail
<a href=http://diposit.ub.edu/dspace/bitstream/2445/170548/1/170548.pdf>read this article</a>,
or if you know persian its better to take look at <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/Docs/main/main.pdf>this</a>.

## How to solve an Electrical circuit with Gracc?
- install Gracc with
```
    pip install Gracc
```
- descripe your circuit as a text! to do so see <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/circuits/README.md>this</a>.
after descripting your circuit as a text locate it <a href=https://github.com/Mehrdadghassabi/Gracc/tree/master/circuits>
Here</a>

- run and plot it with:
```
    import Gracc as grc
    kg6 = grc.circuit_parser('yourdescriptedcircuit.txt')
    grc.plot_kirchoffgraph(kg6)
    grc.plot_kirchoffgraph_after_solving(kg6)
```
for example for the <a href=https://github.com/Mehrdadghassabi/Gracc/blob/master/circuits/circuit5.txt>
circuit5</a> the solution is: </br>
</br>
![image](https://user-images.githubusercontent.com/53050138/184504011-aa7d2716-6cae-4d13-bfcf-076be7483a6b.png)

- its wise to run it in google colab instead of your local machine to do so click in the icon </br>
[![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/Mehrdadghassabi/Gracc/blob/master/Gracc.ipynb)

# Comming soon...
the next goal is to give a picture as an input see <a href=https://github.com/estineali/Hand-Drawn-Circuits>
this</a> then you can use Gracc in your online exam :-)

# Notes
- gracc doesn't support RLC circuits yet :)
- solving differential equations which is a difficult task itself is done thanks to <a href=https://github.com/WarrenWeckesser/odeintw>
odeintw</a>
- <a href=https://github.com/Mehrdadghassabi/Gracc/tree/master/Archive>archive</a> folder is just for making an archive, delete it in your own fork
