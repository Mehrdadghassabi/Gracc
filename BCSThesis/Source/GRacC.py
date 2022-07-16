import networkx as nx
import numpy as np
import pandas as pd

# this function get the example circuit information
# from .txt file by parsing it
# 
# returns an array which contains
#   [nodesnumber ,
#    edgesnumber ,
#    array containing resistors of each edge ,
#    array containing batterys vlotage of each edge ,
#    array containing capacitors of each edge ,
#    array containing selves of each edge &
#    Kirchoff graph adjacency matrix]
def parsing_cicuit(circ):
     nodesnumber = 0
     edgesnumber = 0
     kgatt = {}
     i = 0
     # i is the loop variable
     with open(circ) as f:
         for line in f:
            # for each line in the .txt file get the line number i
            omitnewlines = line.replace('\n', '')
            # remove newlines
            arrstr = omitnewlines.split(" ")
            arrint = [int(numeric_string) for numeric_string in arrstr]
            # in each lines there are some numbers
            # convert the line which is a string to array of numbers
            if i == 0 :
              # if its the first line it determines 
              # number of nodes & edges
              nodesnumber = arrint[0]
              edgesnumber = arrint[1]
              adjacencymatrix = np.zeros([nodesnumber,nodesnumber], dtype=int)
            else :
              # else it means that this line is an information about an edge
              # the origin and the destanation node
              # & information that an edge contains
              xmat = arrint[0]
              ymat = arrint[1]

              Coord = (xmat,ymat)
              adjacencymatrix[xmat][ymat] = 1
   
              att_of_edge = {}
              att_of_edge['resistor'] = arrint[2]
              att_of_edge['battery'] = arrint[3]
              att_of_edge['capacitor'] = arrint[4]
              att_of_edge['self'] = arrint[5]

              kgatt[Coord] = att_of_edge

    
            i = i + 1
     # return them as an array ! !!
     circ = [nodesnumber,edgesnumber,adjacencymatrix,kgatt]
     return circ

# this function get kirchoff graph adjacency matrix
# and simply return the kirchoff graph
def get_kg(kgam,kgatt):
    df = pd.DataFrame(kgam)
    kg = nx.from_pandas_adjacency(df)
    nx.set_edge_attributes(kg, kgatt)
    return kg

# this function takes kirchoff graph & minimum spaning tree
# and returns edges that eliminated 
# from kirchoff graph to build its minimum spaning tree
#
# kirchoff graph minimum spaning tree + eliminated edges = kirchoff graph 
def Eliminated_Edges(kgam,kgmstam):
    nodesnumber = len(kgam)
    l = []
    for i in range(nodesnumber):
       for j in range(nodesnumber):
           if kgam[i][j] == 1 and kgmstam[i][j]== 0 :
              if [j,i] not in l :
                 l.append([i,j])
    return np.array(l)

# by removing some edges from kirchoff graph
# we calculated minimum spaning tree
# for finding fundemental cycles of kirchoff graph
# we need to restore eliminated edges one by one
# and create list of graph (for more description read the doc)
# 
# this function takes minimum spaning tree (kgmst)
#  & eliminated edges as input
# and return the list that mentioned above
def graph_list(kgmstam,eled):
    i = 0
    glam = np.zeros([len(eled),len(kgmstam),len(kgmstam)], dtype=int)
    gl = []
    for edge in eled :
        glam[i] = kgmstam
        glam[i][edge[0]][edge[1]] = 1
        glam[i][edge[1]][edge[0]] = 1
        i = i + 1
    for am in glam :
        df = pd.DataFrame(am)
        g = nx.from_pandas_adjacency(df)
        gl.append(g)
    return gl

# this function get graph list as input
# and returns kirchoff graph fundamental cycles as list
def find_fundamental_cycles(gl) :
    fcl = []
    for g in gl :
        try:
           fcl.append(nx.find_cycle(g, orientation="ignore"))
        except nx.exception.NetworkXNoCycle:
           continue
    return fcl

circuit1 = parsing_cicuit('citcuit2.txt')
nodesnumber =  circuit1[0]
edgesnumber =  circuit1[1]
kgam = circuit1[2]
kgat = circuit1[3]
kg = get_kg(kgam,kgat)
kgmst = nx.minimum_spanning_tree(kg)
kgmstam = nx.to_numpy_array(kgmst).astype(int)
eled = Eliminated_Edges(kgam,kgmstam)
gl = graph_list(kgmstam,eled)
fcl = find_fundamental_cycles(gl)
