import networkx as nx
import numpy as np
import pandas as pd
from scipy import linalg
from scipy.integrate import odeint
import matplotlib.pyplot as plt
import random

def str_on_edge(a,b,c,d):
    s = ''
    if not a == 0 :
       s = s + str(a) + 'Ohms '
    if not b == 0 :
       s = s + str(b) + 'Volts '
    if not c == 0 :
       s = s + str(c) + 'Farad '
    if not d == 0 :
       s = s + str(d) + 'Henry '
    return s

def print_sug_dir(vals):
    for val in vals :
        s = '[' + str(val[0]) + ']' + '---->' + '[' + str(val[1]) + ']'
        print(s)

# this function get the example circuit information
# from .txt file by parsing it
# 
# returns kirchoff graph as the result
def circuit_parser(circ):
     nodesnumber = 0
     edgesnumber = 0
     kgedgatt = {}
     kgnodatt = {}
     i = 0
     # i is the loop variable
     with open(circ) as f:
         for line in f:
            # for each line in the .txt file get the line number i
            omitnewlines = line.replace('\n', '')
            # remove newlines
            arrstr = omitnewlines.split(" ")
            arrnum = [float(numeric_string) for numeric_string in arrstr]
            # in each lines there are some numbers
            # convert the line which is a string to array of numbers
            if i == 0 :
              # if its the first line it determines 
              # number of nodes & edges
              nodesnumber = int(arrnum[0])
              edgesnumber = int(arrnum[1])
              kgam = np.zeros([nodesnumber,nodesnumber], dtype=float)
            else :
              # else it means that this line is an information about an edge
              # the origin and the destanation node
              # & information that an edge contains
              xmat = int(arrnum[0])
              ymat = int(arrnum[1])

              Coord = (xmat,ymat)
              kgam[xmat][ymat] = 1
   
              att_of_edge = {}
              att_of_edge['resistor'] = arrnum[2]
              att_of_edge['battery'] = arrnum[3]
              att_of_edge['capacitor'] = arrnum[4]
              att_of_edge['self'] = arrnum[5]
              att_of_edge['suggested_dir'] = Coord
              att_of_edge['whole'] = str_on_edge(arrnum[2],arrnum[3],
                                                 arrnum[4],arrnum[5])

              kgedgatt[Coord] = att_of_edge
            i = i + 1
     df = pd.DataFrame(kgam)
     kg = nx.from_pandas_adjacency(df)
     nx.set_edge_attributes(kg, kgedgatt)
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

def isstraight(org,dst,suggested_dir):
    return (suggested_dir[0] == org) and (suggested_dir[1] == dst)

def get_A_coord(kg,org,dst):
    edges = kg.edges()
    i = 0 
    for edge in kg.edges():
        c1 = edge[0] == org and edge[1] == dst
        c2 = edge[1] == org and edge[0] == dst
        if c1 or c2 :
           return i
        i = i + 1

def find_kg_edges_weights(kg):
    kgmst = nx.minimum_spanning_tree(kg)
    kgmstam = nx.to_numpy_array(kgmst).astype(int)
    kgam = nx.to_numpy_array(kg).astype(int)
    eled = Eliminated_Edges(kgam,kgmstam)
    gl = graph_list(kgmstam,eled)
    fcl = find_fundamental_cycles(gl)
    edge_number = kg.number_of_edges()
    B = np.zeros([edge_number,1])
    A = np.zeros([edge_number,edge_number])
    i = 0
    for cycle in fcl :
        volsum = 0
        res = 0
        for edge in cycle :
            org = edge[0]
            dst = edge[1]
            sugdir = kg.get_edge_data(org,dst)['suggested_dir']
            if isstraight(org,dst,sugdir) :
               volsum = volsum - kg.get_edge_data(org,dst)['battery']
               res = -kg.get_edge_data(org,dst)['resistor']
               cor = get_A_coord(kg,org,dst)
               A[i][cor] = res
            else :
               volsum = volsum + kg.get_edge_data(org,dst)['battery']
               res = +kg.get_edge_data(org,dst)['resistor']
               cor = get_A_coord(kg,org,dst)
               A[i][cor] = res
        B[i][0]= volsum
        i = i + 1
    for node in kg.nodes :
        for nei in kg.neighbors(node):
            sugdir = kg.get_edge_data(node,nei)['suggested_dir']
            inr = isstraight(node,nei,sugdir)
            cor = get_A_coord(kg,node,nei)
            if isstraight(node,nei,sugdir) :
               A[i][cor] = -1
            else :
               A[i][cor] = 1
        i = i + 1
        if i == kg.number_of_edges():
           break
    return linalg.solve(A, B)

def round_kg_edges_weights(edw):
    i = 0
    for ele in edw:
        edw[i][0] = round(edw[i][0],3)
        i = i + 1
    return edw

def plot_kirchoffgraph(kg):
    pos = nx.spring_layout(kg)
    nx.draw(kg, pos, with_labels=True, node_color='#FF0000')
    edge_labels = nx.get_edge_attributes(kg,'whole')
    nx.draw_networkx_edge_labels(kg, pos, edge_labels)
    plt.show()
    print('suggested_dir: ')
    print_sug_dir(nx.get_edge_attributes(kg,'suggested_dir').values())

def plot_kirchoffgraph_after_solving(kg):
    edw = find_kg_edges_weights(kg)
    edw = round_kg_edges_weights(edw)
    sugdir = nx.get_edge_attributes(kg,'suggested_dir').values()
    dkg = nx.DiGraph()
    i = 0
    for element in sugdir:
        if edw[i][0] > 0 :
           dkg.add_weighted_edges_from([(element[0], element[1], edw[i][0])])
        else :
           dkg.add_weighted_edges_from([(element[1], element[0], -edw[i][0])])
        i = i + 1
    pos = nx.spring_layout(dkg)
    nx.draw(dkg, pos, with_labels=True, node_color='#00FF00')
    edge_labels = nx.get_edge_attributes(dkg,'weight')
    nx.draw_networkx_edge_labels(dkg, pos, edge_labels)
    plt.show()

kg1 = circuit_parser('circuit2.txt')
plot_kirchoffgraph(kg1)
plot_kirchoffgraph_after_solving(kg1)
