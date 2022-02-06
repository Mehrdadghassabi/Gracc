/*
Copyleft (ALL WRONG ARE RESERVED) 2019  Mehrdad Ghassabi <mehrdad.gv@gmail.com>
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/

package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Mehrdad Ghassabi
 */
public class Node {
    private final ArrayList<Node> ConnectedNodes;
    private Map<Integer,Node> duplicated;
    private int Number;
    private ArrayList<Connection> ConnectedBranch=new ArrayList<>();
    
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

    public ArrayList<Connection> getConnectedBranch() {
        return ConnectedBranch;
    }

    public void AddConnectedBranch(Connection ConnectedBranch) {
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