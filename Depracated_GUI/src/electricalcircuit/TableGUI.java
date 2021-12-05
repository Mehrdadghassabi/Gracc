/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import ElectricalGate.ElectricalGate;
import Graph.Branches;
import Graph.Equations;
import Graph.GeneratorException;
import Graph.Graph;
import Graph.SingletonException;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.control.TextField;

/**
 *
 * @author Dour-Andish
 */
public class TableGUI {
   private static Graph graph;
   private static ArrayList<ArrayList<ElectricalGate>> in;
    private static Branches bran;
    private static Group group;
    private static Equations eq;
   
    TableGUI(Graph g){
    this.graph=g;
    this.in=getgates();
    //group=tablegroup();
    }
    
    public ArrayList<ArrayList<ElectricalGate>> getgates(){
      int branamount=this.graph.getUnknowns();
      ArrayList<ArrayList<ElectricalGate>> re=new ArrayList<>();
      for(int i=0;i<branamount;i++){
      TextField txt=(TextField) ElectricalCircuit.getScene().lookup("#"+"branch"+i);
      String str=txt.getText();
      StringSeperator strsp=new StringSeperator(str);
      ArrayList<ElectricalGate> get=strsp.getBranch();
      re.add(get);
      }
      return re;
    }
    
    public static Branches getbran() throws GeneratorException, SingletonException{
    return new Branches(graph,in);
    }

    public static Group getGroup() {
        return group;
    }

    public static Equations getEq() {
        return eq;
    }
   
}
