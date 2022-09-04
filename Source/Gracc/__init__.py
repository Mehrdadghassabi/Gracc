import networkx as nx
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import random as rm
import odeintw as ow

# for drawing the circuit
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

def circuit_type(kg):
    contain_self  = False
    contain_capacitor = False
    svals = nx.get_edge_attributes(kg,'self').values()
    cvals = nx.get_edge_attributes(kg,'capacitor').values()
    for sval in svals:
        if not sval == 0 :
           contain_self = True
    for cval in cvals:
        if not cval == 0 :
           contain_capacitor = True
    if not contain_self and not contain_capacitor :
        return 'Ordinary'
    if not contain_self and contain_capacitor :
        return 'RC'
    if contain_self and not contain_capacitor :
        return 'RL'
    if contain_self and contain_capacitor :
        return 'RLC'

# this function takes kirchoff graph & minimum spaning tree
# and returns edges that eliminated 
# from kirchoff graph to build its minimum spaning tree
#
# kirchoff graph minimum spaning tree + eliminated edges = kirchoff graph 
def eliminated_edges(kgam,kgmstam):
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
def find_fundamental_cut_set_graph(kgmstam,eled):
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

# checking that suggested direction is proper or not
def sugdir_is_same_with_given_dir(org,dst,suggested_dir):
    return (suggested_dir[0] == org) and (suggested_dir[1] == dst)

# by having 2 connected node
# find the index of the edge connecting them
def get_edge_index(kg,org,dst):
    edges = kg.edges()
    i = 0 
    for edge in kg.edges():
        c1 = edge[0] == org and edge[1] == dst
        c2 = edge[1] == org and edge[0] == dst
        if c1 or c2 :
           return i
        i = i + 1

# finding the current through each edge
# by having the kirchoff graph
#
# for circuits which only contains resistors and batterys
def find_kg_edges_weights_ord(kg):
    kgmst = nx.minimum_spanning_tree(kg)
    kgmstam = nx.to_numpy_array(kgmst).astype(int)
    kgam = nx.to_numpy_array(kg).astype(int)
    eled = eliminated_edges(kgam,kgmstam)
    gl = find_fundamental_cut_set_graph(kgmstam,eled)
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
            if sugdir_is_same_with_given_dir(org,dst,sugdir) :
               volsum = volsum - kg.get_edge_data(org,dst)['battery']
               res = -kg.get_edge_data(org,dst)['resistor']
               cor = get_edge_index(kg,org,dst)
               A[i][cor] = res
            else :
               volsum = volsum + kg.get_edge_data(org,dst)['battery']
               res = +kg.get_edge_data(org,dst)['resistor']
               cor = get_edge_index(kg,org,dst)
               A[i][cor] = res
        B[i][0]= volsum
        i = i + 1
    for node in kg.nodes :
        for nei in kg.neighbors(node):
            sugdir = kg.get_edge_data(node,nei)['suggested_dir']
            inr = sugdir_is_same_with_given_dir(node,nei,sugdir)
            cor = get_edge_index(kg,node,nei)
            if sugdir_is_same_with_given_dir(node,nei,sugdir) :
               A[i][cor] = -1
            else :
               A[i][cor] = 1
        i = i + 1
        if i == kg.number_of_edges():
           break
    return np.linalg.solve(A, B)

# finding the current through each edge
# by having the kirchoff graph
#
# for RC and RL circuits (first order systems)
def find_kg_edges_weights_RC_and_RL(kg):
    kgmst = nx.minimum_spanning_tree(kg)
    kgmstam = nx.to_numpy_array(kgmst).astype(int)
    kgam = nx.to_numpy_array(kg).astype(int)
    eled = eliminated_edges(kgam,kgmstam)
    gl = find_fundamental_cut_set_graph(kgmstam,eled)
    fcl = find_fundamental_cycles(gl)
    edge_number = kg.number_of_edges()
    B1 = np.zeros([edge_number,1])
    A1 = np.zeros([edge_number,edge_number])

    variable_weight_edge = []
    const_weight_edge = []
    const_weight_cycle = []
    variable_weight_cycle = []
    variable_weight_cycle_index = []

    circtyp = circuit_type(kg)

    i = 0
    for cycle in fcl :
        volsum = 0
        res = 0
        varicyc = False
        for edge in cycle :
            org = edge[0]
            dst = edge[1]
            sugdir = kg.get_edge_data(org,dst)['suggested_dir']
            cap = kg.get_edge_data(org,dst)['capacitor']
            sel = kg.get_edge_data(org,dst)['self']
            capsel = cap if circtyp == 'RC' else sel
            istrt = sugdir_is_same_with_given_dir(org,dst,sugdir)
            t =  (org,dst) if (istrt) else (dst,org)
            if capsel == 0 :
                  if not t in const_weight_edge:
                     const_weight_edge.append(t)
            else :
                  varicyc = True
                  if not t in variable_weight_edge :
                     variable_weight_edge.append(t)
        if varicyc :
            variable_weight_cycle.append(cycle)
            variable_weight_cycle_index.append(i)
        else :
            const_weight_cycle.append(cycle)
        i = i + 1

    a,b,c,d,e = variable_weight_edge,const_weight_edge,const_weight_cycle,variable_weight_cycle,variable_weight_cycle_index


    i = 0
    for cycle in fcl :
        volsum = 0
        res = 0
        for edge in cycle :
            org = edge[0]
            dst = edge[1]
            sugdir = kg.get_edge_data(org,dst)['suggested_dir']
            if sugdir_is_same_with_given_dir(org,dst,sugdir) :
               volsum = volsum - kg.get_edge_data(org,dst)['battery']
               res = -kg.get_edge_data(org,dst)['resistor']
               cor = get_edge_index(kg,org,dst)
               A1[i][cor] = res
            else :
               volsum = volsum + kg.get_edge_data(org,dst)['battery']
               res = +kg.get_edge_data(org,dst)['resistor']
               cor = get_edge_index(kg,org,dst)
               A1[i][cor] = res
        B1[i][0]= volsum
        i = i + 1
    for node in kg.nodes :
        for nei in kg.neighbors(node):
            sugdir = kg.get_edge_data(node,nei)['suggested_dir']
            inr = sugdir_is_same_with_given_dir(node,nei,sugdir)
            cor = get_edge_index(kg,node,nei)
            if sugdir_is_same_with_given_dir(node,nei,sugdir) :
               A1[i][cor] = -1
            else :
               A1[i][cor] = 1
        i = i + 1
        if i == kg.number_of_edges():
           break

    for index1 in e :
        A1 = np.delete(A1, index1, 0)
        B1 = np.delete(B1, index1, 0)
    kwnv = []
    for n in range(len(a)):
        edin = get_edge_index(kg,a[n][0],a[n][1])
        row_to_append = A1[:, edin].tolist()
        kwnv.append(row_to_append)
        A1 = np.delete(A1,edin,1)
    kwnvT = np.array(kwnv).T
    B1 = np.concatenate((B1, kwnvT), axis=1)

    ans = np.linalg.solve(A1, B1)
    for x in range(len(a)):
        index1 = get_edge_index(kg,a[x][0],a[x][1])
        l = []
        for y in range(len(e)):
            l.append(None)
        ans = np.insert(ans, index1 , l, axis = 0)

    A = np.zeros([len(a),len(a)])
    B = np.zeros([len(a),len(a)])
    C = np.zeros([len(a),len(a)])
    i = 0
    dic = dict()
    for loop in d :
        j = 0
        for edge in loop :
            cap = kg.get_edge_data(edge[0],edge[1])['capacitor']
            sel = kg.get_edge_data(org,dst)['self']
            index = get_edge_index(kg,edge[0],edge[1])
            caprsnt = not cap == 0 
            slprsnt = not sel == 0 
            slcaprsnt = caprsnt if circtyp == 'RC' else slprsnt
            if slcaprsnt :
               dic[j] = index
               cof = - 1 / cap if circtyp == 'RC' else -sel
               A[i][j] = cof
               j = j + 1
        i = i + 1
    for loop in d :
        i = 0
        for edge in loop :
            res = kg.get_edge_data(edge[0],edge[1])['resistor']
            index = get_edge_index(kg,edge[0],edge[1])
            resprsnt = not res == 0 

            if resprsnt :
               count = 0
               arr = ans[index]
               k = 0
               for el in arr :
                   if k == 0:
                      C[i][count] = C[i][count] + (el*res)
                   else:
                      B[i][count] = B[i][count] + (el*res)
                   k = k + 1
               count = count + 1
                   
            i = i + 1
    if circtyp == 'RC':
       return np.linalg.inv(np.linalg.solve(A, B)),ans
    elif circtyp == 'RL':
       return np.linalg.solve(A, B),ans
    else :
       # should not get here
       return None

# rounding edges weight
#
# for drawing only
def round_kg_edges_weights(edw):
    i = 0
    for ele in edw:
        edw[i][0] = round(edw[i][0],3)
        i = i + 1
    return edw

# related to 
# solving matrix differential equation
def asys(a, t, c):
    return c.dot(a)

# finding edges that its weight obeys a 
# first order differential equation
def find_variable_edge_index(answ):
    l = []
    count = 0
    for t in answ :
        if np.isnan(t[0]) :
           l.append(count)
        count = count + 1
    return l

# by founding the edges weight that obeys 
# differential equation we find other edges weight
def find_other_edge_weight(l,ans):
    wt = []
    i = 0
    for a in ans:
        j = 0
        if not np.isnan(a[0]):
           st = 'i' + str(i) + '= '
           for element in a:
               if j == 0 :
                  st = st  + str(element)
               else :
                  st = st + ' + ' + str(element) + 'i' + str(l[j-1])
               j = j + 1
           wt.append(st)
        i = i + 1
            
    return wt

# get suggestion direction
#
# for printing
def sug_dir_for_printing(vals):
    s = ''
    for val in vals :
        s = s + '[' + str(val[0]) + ']' + '---->' + '[' + str(val[1]) + ']' + '\n'
    return s

# finding prepare position
#
# for printing something
def pos_for_printing_sth(nodespos):
    re = []
    vls = list(nodespos.values())
    minx = 0
    miny = 0
    for vl in vls:
        re.append(list(vl))
    for t in re :
        if t[0]<minx :
           minx = t[0]
        if t[1]<minx :
           miny = t[1] 
    return (minx,miny)

# ploting the unsolved circuit
#
# just for seeing the input
def plot_kirchoffgraph(kg):
    pos = nx.spring_layout(kg)
    nx.draw(kg, pos, with_labels=True, node_color='#FF0000')
    edge_labels = nx.get_edge_attributes(kg,'whole')
    for e in edge_labels :
        edge_labels[e] = edge_labels[e] + ' I' + str(get_edge_index(kg,e[0],e[1]))
    nx.draw_networkx_edge_labels(kg, pos, edge_labels)
    st = 'suggested_dir: ' + '\n' + sug_dir_for_printing(nx.get_edge_attributes(kg,'suggested_dir').values())
    textpos = pos_for_printing_sth(pos)
    plt.text(textpos[0]-0.5, textpos[1]-0.5, st)
    plt.show()

# analyze the ordinary cicuits
#
# by ploting
def plot_solution_of_ord_kirchoffgraph(kg):
    edw = find_kg_edges_weights_ord(kg)
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

# analyze the linear cicuits
#
# by ploting
def plot_solution_of_lin_kirchoffgraph(kg):
    a,answ = find_kg_edges_weights_RC_and_RL(kg)
    l = find_variable_edge_index(answ)
    x0 = [[1]]
    # x0 is the initial condition
    n = len(a)
    t = np.linspace(0, 10, 201)
    sol = ow.odeintw(asys, x0, t, args=(a,))
    
    plt.figure(1)
    plt.clf()
    colorlist = []
    st = find_other_edge_weight(l,answ)
    s = ''
    for elm in st :
        s = s + elm + '\n'
    plt.text(0, -1.5, s)
    for i in range(n):
        c = (rm.uniform(0, 1),rm.uniform(0, 1),rm.uniform(0, 1))
        l = 'i[' + str(l[i]) + ']'
        plt.plot(t, sol[:, 0, i], color = c, label=l)

    plt.legend(loc='best')
    plt.grid(True)
    plt.show()

# ploting the analysis :)
def plot_kirchoffgraph_after_solving(kg):
    cirtyp = circuit_type(kg)
    if cirtyp == 'RC' or cirtyp == 'RL' :
       plot_solution_of_lin_kirchoffgraph(kg)
    elif cirtyp == 'Ordinary' : 
         plot_solution_of_ord_kirchoffgraph(kg)
    else :
      # it should be RLC doesnt developed yet
        print('RLC circuit analysis still is underdevelopment')
