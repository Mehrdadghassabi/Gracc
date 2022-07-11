/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import ElectricalGate.Battery;
import ElectricalGate.ElectricalGate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dour-Andish
 */
public final class Branches {
    private final Graph graph;
    private final Branch[] blocks;
    Map<Integer,Byte> counter1=new HashMap<>();
    boolean CallForsecond=false;
    public  ArrayList<ArrayList<BranchDirection>> Loops=new ArrayList<>();
    int con=0;
    boolean InstantiatingTwice=false;
    
    public Branches(Graph graph,ArrayList<ArrayList<ElectricalGate>> in) throws GeneratorException, SingletonException{
            
    this.graph=graph;
    this.blocks=getblocks(graph,in);
   
       this.Loops=getLoop();
 
    }
    
    public ArrayList<ArrayList<BranchDirection>> getLoop() throws GeneratorException, SingletonException{
        ArrayList<ArrayList<BranchDirection>> loop=new ArrayList<>();
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
    desoriNodes[0][i].AddConnectedBranch(new Nodesbranch(reblocks[i],true));
    desoriNodes[1][i].AddConnectedBranch(new Nodesbranch(reblocks[i],false));
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
    
    public ArrayList<BranchDirection> getBranchByNode(int origin,int destination){
        ArrayList<BranchDirection> arr=new ArrayList<>();
    for(Branch block:this.blocks){
        if(block.IsRightBranch(origin,destination)){
            arr.add(new BranchDirection(block,false));
        } /////////block.getOrigin(),block.getDestination(),block.getBlocks())
    }
    return arr; 
    }
    
    public ArrayList<BranchDirection> getLoopNumberN(int x) throws GeneratorException, SingletonException{
                if(con==3)
                       throw new SingletonException("cant instanciate");
     
    int[] nums=this.graph.getWholeLoops().get(x);
    ArrayList<BranchDirection> re=new ArrayList<>();
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
        
     BranchDirection bran=getBranchByNode(First, Last).get(in);
     bran.SetIsReverse(First-Last>0);
     re.add(bran);
    }
    this.CallForsecond=true;
    con++;
    return re;
   }
    
    public BranchDirection getBranchXmorethan2(int first,int last){
     byte hold=(counter1.get(JoinTwonum(first,last))==null)?0:
             ((byte)(counter1.get(JoinTwonum(first,last))+1));
     if(this.CallForsecond)
         hold--;
         
     counter1.put(JoinTwonum(first,last), hold);
     int in=counter1.get(JoinTwonum(first,last));
     
       // System.out.println(in);
        
        
    BranchDirection bran=getBranchByNode(first, last).get(in);
    bran.SetIsReverse(first-last>0);
    this.CallForsecond=false;
    return bran;
    }
    
    public int JoinTwonum(int a,int b){
    return (a<b)?(10*a+b):(10*b+a);
    }
    
    public float findVoltageofLoopN(int n) throws GeneratorException{
    ArrayList<BranchDirection> myarr=this.Loops.get(n);
    ArrayList<Battery> batterys;
    int rev;
    float counter=0;
     for(BranchDirection branchDir:myarr){
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

    public ArrayList<ArrayList<BranchDirection>> getLoops() {
        return Loops;
    }
}
