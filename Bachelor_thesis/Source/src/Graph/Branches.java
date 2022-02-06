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

import ElectricalGate.Battery;
import ElectricalGate.ElectricalGate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Myutil.SingletonException;
import Myutil.GeneratorException;

/**
 *
 * @author Mehrdad Ghassabi
 */
public final class Branches {
    private final Graph graph;
    private final Branch[] blocks;
    Map<Integer,Byte> counter1=new HashMap<>();
    boolean CallForsecond=false;
    public  ArrayList<ArrayList<Direction>> Loops=new ArrayList<>();
    int con=0;
    boolean InstantiatingTwice=false;
    
    public Branches(Graph graph,ArrayList<ArrayList<ElectricalGate>> in) throws GeneratorException, SingletonException{
            
    this.graph=graph;
    this.blocks=getblocks(graph,in);
   
       this.Loops=getLoop();
 
    }
    
    public ArrayList<ArrayList<Direction>> getLoop() throws GeneratorException, SingletonException{
        ArrayList<ArrayList<Direction>> loop=new ArrayList<>();
     for(int i=0;i<this.graph.getUnknowns()-this.graph.getNodesamount()+1;i++){
    
            loop.add(getLoopNumberN(i));
        
    }
     return loop;
    }
    
    private Branch[] getblocks(Graph g,ArrayList<ArrayList<ElectricalGate>> in){
    Branch[] reblocks=new Branch[g.getUnknowns()];
    Node[][] desoriNodes=getbranchNodes(g);
    for(int i=0;i<reblocks.length;i++){
    reblocks[i]=new Branch(desoriNodes[0][i],desoriNodes[1][i],in.get(i));
    desoriNodes[0][i].AddConnectedBranch(new Connection(reblocks[i],true));
    desoriNodes[1][i].AddConnectedBranch(new Connection(reblocks[i],false));
    reblocks[i].setBranchNum(i);
    }
    return reblocks;
    }
    
    private Node[][] getbranchNodes(Graph g){
    Node[][] branchNodes=new Node[2][g.getUnknowns()];
    ArrayList<Node> nodes=g.GetNodes();
    ArrayList<Node> counted=new ArrayList<>();
    int counter=0;
    for(int i=0;i<nodes.size();i++){
        ArrayList<Node> connected=nodes.get(i).getconnectednodes();
        for(int j=0;j<connected.size();j++){
            if(counted.contains(connected.get(j)))
               continue;
                
        branchNodes[0][counter]=nodes.get(i);
        branchNodes[1][counter]=connected.get(j);
        counter++;
        }
        counted.add(nodes.get(i));
    }
    return branchNodes;
    }

    public Branch[] getBlocks() {
        return blocks;
    }
    
    public ArrayList<Direction> getBranchByNode(int origin, int destination){
        ArrayList<Direction> arr=new ArrayList<>();
    for(Branch block:this.blocks){
        if(block.IsRightBranch(origin,destination)){
            arr.add(new Direction(block,false));
        } /////////block.getOrigin(),block.getDestination(),block.getBlocks())
    }
    return arr; 
    }
    
    public ArrayList<Direction> getLoopNumberN(int x) throws GeneratorException, SingletonException{
                if(con==3)
                       throw new SingletonException("cant instanciate");
     
    int[] nums=this.graph.getWholeLoops().get(x);
    ArrayList<Direction> re=new ArrayList<>();
    int First;
    int Last;
    Map<Integer,Byte> counter=new HashMap<>();
        //System.out.println("len"+nums.length);
    for(int i=0;i<nums.length;i++){
     First=nums[i];
     Last=(i+1==nums.length)?nums[0]:nums[i+1];
     
     if(getBranchByNode(First, Last).size()>2){
     re.add(getBranchXmorethan2(First,Last));
      
     continue;
     }
     
     byte hold=(counter.get(JoinTwonum(First,Last))==null)?0:
             ((byte)(counter.get(JoinTwonum(First,Last))+1));
     counter.put(JoinTwonum(First,Last), hold);
     int in=counter.get(JoinTwonum(First,Last));
     
       // System.out.println(in);
        
     Direction bran=getBranchByNode(First, Last).get(in);
     bran.SetIsReverse(First-Last>0);
     re.add(bran);
    }
    this.CallForsecond=true;
    con++;
    return re;
   }
    
    public Direction getBranchXmorethan2(int first, int last){
     byte hold=(counter1.get(JoinTwonum(first,last))==null)?0:
             ((byte)(counter1.get(JoinTwonum(first,last))+1));
     if(this.CallForsecond)
         hold--;
         
     counter1.put(JoinTwonum(first,last), hold);
     int in=counter1.get(JoinTwonum(first,last));
     
       // System.out.println(in);
        
        
    Direction bran=getBranchByNode(first, last).get(in);
    bran.SetIsReverse(first-last>0);
    this.CallForsecond=false;
    return bran;
    }
    
    public int JoinTwonum(int a,int b){
    return (a<b)?(10*a+b):(10*b+a);
    }
    
    public float findVoltageofLoopN(int n) throws GeneratorException{
    ArrayList<Direction> myarr=this.Loops.get(n);
    ArrayList<Battery> batterys;
    int rev;
    float counter=0;
     for(Direction branchDir:myarr){
         rev=branchDir.getIsReverse()?(-1):1;
          batterys= branchDir.getBranch().getBatterys();
          
          for(Battery batery:batterys){
          counter+=rev*(batery.getVoltage());
          }
     }
     return counter;
    }

    public  Graph getGraph() {
        return graph;
    }
   
    @Override
    public String toString(){
    StringBuilder sb=new StringBuilder("");
    for(Branch branch:blocks){
    sb.append(branch.toString()).append("\n");
    }
    return sb.toString();
    }

    public ArrayList<ArrayList<Direction>> getLoops() {
        return Loops;
    }
}
