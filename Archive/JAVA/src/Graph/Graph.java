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
   private final int Nodes_Number;
   private final ArrayList<Node> Nodes;
   
   public Graph(ArrayList<Node> Nodes){
   this.Nodes=Nodes;
   for(int i=0;i<Nodes.size();i++)
   {
   this.Nodes.get(i).SetNumber(i);
   }     
   this.Nodes_Number=Nodes.size();
   }
   
   public Graph(int size){
   this.Nodes=new ArrayList<>();
   this.Nodes_Number=size;
   }
   
   public void AddNode(Node node){
   this.Nodes.add(node);
   }
   
   public ArrayList<Node> GetNodes(){
   return this.Nodes;
   }
   
   public int Get_Unknown_Number(){
       int counter=0;
       
       counter = Nodes.stream().map((node) -> node.getConnectedNodesSize()).reduce(counter, Integer::sum);
       return counter/2;
   }
   
   public ArrayList<int[]> Get_Xth_Loop(int x) throws GeneratorException{
       if(x==2){
       return this.Get_Two_Nodes_Loop();
       }
   Generator generator=new Generator();
       List<int[]> list=generator.generate(this.Nodes_Number, x);
       List<int[]> list1=generator.CloneBypermute(list);
       ArrayList<int[]> temp=new  ArrayList<>();
       
       for(int i=0;i<list1.size();i++){
        if(Is_Loop(list1.get(i)))
            temp.add(list1.get(i));
       }
        ArrayList<int[]> list2=generator.removeDuplicated(temp);
        return (ArrayList)list2;
   }
   
   public int Get_Nodes_Number(){
   return this.Nodes_Number;
   }
   
   private ArrayList<int[]> Get_Two_Nodes_Loop(){
      Generator generator=new Generator();
       List<int[]> list=generator.generate(this.Nodes_Number, 2);
        ArrayList<int[]> list1=new ArrayList<>();
        
        for(int[] arr:list){
            int num1=arr[0];
            int num2=arr[1];
            
            int tedad=Search_Node(this.Nodes.get(num1),this.Nodes.get(num2).getDuplicated());
       if(tedad>1){
       for(int i=0;i<tedad-1;i++)
           list1.add(arr);
       }
        }
        return list1;
   }
   
   public ArrayList<int[]> DFT_Get_Loops() throws GeneratorException{
   int KVL_Equtions_size=this.Get_Unknown_Number()-(this.Nodes_Number-1);
   ArrayList<int[]> KVL_Equtions=new ArrayList<>();

   int counter=2;
   int ite=0;
   for(int i=0;i<KVL_Equtions_size;i++){
           
           while(Xth_Loop_Length(counter)<=i){

               counter++;
               ite=0;
           }

   KVL_Equtions.add(Get_Xth_Loop(counter).get(ite));
   ite++;
   }
      
   return KVL_Equtions;
   
   }
   
   private int Xth_Loop_Length(int x) throws GeneratorException{
   int hold=0;
   while(x>1){
      hold+=Get_Xth_Loop(x).size();
   x--;
   }
   return hold;
   }
   
   private Integer Search_Node(Node a,Map<Integer,Node> source){
       Collection<Node> arr=source.values();
      for(Node node:arr){
        if(node.equals(a))
            return Search_For_Key(source,node);
      }
      return 0;
   }
   
   private boolean Is_Loop(int[] in){
     boolean flag=true;
       for(int i=0;i<in.length-1;i++){
       if(!Is_Connected(Nodes.get(in[i]),Nodes.get(in[i+1])))
           flag=false;
       }
       if(!Is_Connected(Nodes.get(in[0]),Nodes.get(in[in.length-1])))
           flag=false;
       
       return flag;
   }
   
   private boolean Is_Connected(Node a,Node b){
       boolean flag=false;
   ArrayList<Node> Aconnectednodes=a.getconnectednodes();
    for(Node node:Aconnectednodes){
    if(node.equals(b))
        flag=true;
    }
    return flag;
   }
   
   private Integer Search_For_Key(Map<Integer,Node> hm, Node value) {
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