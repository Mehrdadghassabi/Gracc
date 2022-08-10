package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dour-Andish
 */
public class Node {
    private final ArrayList<Node> ConnectedNodes;
    private Map<Integer,Node> duplicated;
    private int Number;
    private ArrayList<Nodesbranch> ConnectedBranch=new ArrayList<>();
    
    public Node(ArrayList<Node> ConnectedNodes){
    this.ConnectedNodes=ConnectedNodes;
    this.duplicated=null;
    }
    
    public Node(){
    this.ConnectedNodes=new ArrayList<>();
    this.duplicated=null;
    }
    
    public void AddConnectNodes(Node node){
        ConnectedNodes.add(node);
    }
    
    public int getNumber(){
    return this.Number;
    }
    
    public Map<Integer,Node> getDuplicated(){
        this.duplicated=this.gethashMap(ConnectedNodes);
    return this.duplicated;
    }
    
    public void SetNumber(int x){
    this.Number=x;
    }

    public ArrayList<Nodesbranch> getConnectedBranch() {
        return ConnectedBranch;
    }

    public void AddConnectedBranch(Nodesbranch ConnectedBranch) {
        this.ConnectedBranch.add(ConnectedBranch);
    }
    
    public int getConnectedNodesSize(){
       return this.ConnectedNodes.size();
    }
    
    public ArrayList<Node> getconnectednodes(){
    return this.ConnectedNodes;
    }
    
    private HashMap<Integer,Node> gethashMap(ArrayList<Node> ConnectedNodes){
        Map<Integer,Node> Duplicated=new HashMap<>();
        for(Node node:ConnectedNodes){
        if(CountDuplication(node,ConnectedNodes)>1)
            Duplicated.put(CountDuplication(node,ConnectedNodes), node);

        }
        return (HashMap)Duplicated;
    }
    
    private Integer CountDuplication(Node a,ArrayList<Node> source){
    Integer x=0;
    x = source.stream().filter((node) -> (node.equals(a))).map((_item) -> 1).reduce(x, Integer::sum);
    return x;
    }
    
    @Override
    public String toString(){
    StringBuilder sb=new StringBuilder("");
    sb.append("Node Number:").append(this.Number).append(" ");
    
    return sb.toString();
    }
}