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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import Myutil.Generator;
import Myutil.GeneratorException;


/**
 *
 * @author Mehrdad Ghassabi
 */
public class Graph {
   private final int NodesAmount;
   private final ArrayList<Node> Nodes;
   private int UnKnowns;
   
   public Graph(ArrayList<Node> Nodes){
   this.Nodes=Nodes;
   for(int i=0;i<Nodes.size();i++)
   {
   this.Nodes.get(i).SetNumber(i);
   }     
   this.NodesAmount=Nodes.size();
   }
   
   public Graph(int size){
   this.Nodes=new ArrayList<>();
   this.NodesAmount=size;
   }
   
   public void AddNode(Node node){
   this.Nodes.add(node);
   }
   
   public ArrayList<Node> GetNodes(){
   return this.Nodes;
   }
   
   public int getUnknowns(){
       int counter=0;
       
       counter = Nodes.stream().map((node) -> node.getConnectedNodesSize()).reduce(counter, Integer::sum);
       return counter/2;
   }
   
   public ArrayList<int[]> getNloop(int x) throws GeneratorException{
       if(x==2){
       return this.get2loop();
       }
   Generator generator=new Generator();
       List<int[]> list=generator.generate(this.NodesAmount, x);
       List<int[]> list1=generator.CloneBypermute(list);
       ArrayList<int[]> temp=new  ArrayList<>();
       
       for(int i=0;i<list1.size();i++){
        if(IsLoop(list1.get(i)))
            temp.add(list1.get(i));
       }
        ArrayList<int[]> list2=generator.removeDuplicated(temp);
        return (ArrayList)list2;
   }
   
   public int getNodesamount(){
   return this.NodesAmount;
   }
   
   private ArrayList<int[]> get2loop(){
      Generator generator=new Generator();
       List<int[]> list=generator.generate(this.NodesAmount, 2);
        ArrayList<int[]> list1=new ArrayList<>();
        
        for(int[] arr:list){
            int num1=arr[0];
            int num2=arr[1];
            
            int tedad=SearchaNode(this.Nodes.get(num1),this.Nodes.get(num2).getDuplicated());
       if(tedad>1){
       for(int i=0;i<tedad-1;i++)
           list1.add(arr);
       }
        }
        return list1;
   }
   
   public ArrayList<int[]> getWholeLoops() throws GeneratorException{
   int KVL_Equtions_size=this.getUnknowns()-(this.NodesAmount-1);
   ArrayList<int[]> KVL_Equtions=new ArrayList<>();

   int counter=2;
   int ite=0;
   for(int i=0;i<KVL_Equtions_size;i++){
           
           while(findlen(counter)<=i){
              // System.out.println("size "+(getNloop(counter).size())+" ite: "+ite);
               counter++;
               ite=0;
             //System.out.println("counter: "+counter);
           }   
             //System.out.println("ive reched");
            //System.out.println("added+ "+getNloop(counter).get(ite)+" with i= "+ite);
   KVL_Equtions.add(getNloop(counter).get(ite));
   ite++;
   }
      
   return KVL_Equtions;
   
   }
   
   private int findlen(int x) throws GeneratorException{
   int hold=0;
   while(x>1){
      hold+=getNloop(x).size();
   x--;
   }
   return hold;
   }
   
   private Integer SearchaNode(Node a,Map<Integer,Node> source){
       Collection<Node> arr=source.values();
      for(Node node:arr){
        if(node.equals(a))
            return getKeyFromValue(source,node);
      }
      return 0;
   }
   
   private boolean IsLoop(int[] in){
     boolean flag=true;
       for(int i=0;i<in.length-1;i++){
       if(!Isconnected(Nodes.get(in[i]),Nodes.get(in[i+1])))
           flag=false;
       }
       if(!Isconnected(Nodes.get(in[0]),Nodes.get(in[in.length-1])))
           flag=false;
       
       return flag;
   }
   
   private boolean Isconnected(Node a,Node b){
       boolean flag=false;
   ArrayList<Node> Aconnectednodes=a.getconnectednodes();
    for(Node node:Aconnectednodes){
    if(node.equals(b))
        flag=true;
    }
    return flag;
   }
   
   private Integer getKeyFromValue(Map<Integer,Node> hm, Node value) {
            for (Integer o : hm.keySet()) {
              if (hm.get(o).equals(value)) {
                return o;
              }
            }
            return null;
          }
   
   @Override
   public String toString(){
      StringBuilder sb=new StringBuilder("");
   for(Node node:Nodes){
       sb.append(node.toString()+"\n");
   }
   return sb.toString();
   }
    
}