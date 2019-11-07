/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import Graph.Graph;
import Graph.Node;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

/**
 *
 * @author Dour-Andish
 */
public class GraphGUI {
    private final Scene scene;
    private final int Knot_Amount;
    private final Graph graph;
    private Group group;
    
    GraphGUI(Scene scene,int KnotAmount){
    this.scene=scene;
    this.Knot_Amount=KnotAmount;
    this.graph=FXMLDocumentController.getGraph();
    }
    
    public Graph maingraph(){
    ArrayList<Node> aln=new ArrayList<>();
    
    for(int i=0;i<this.Knot_Amount;i++){
    Node node=new Node();
    aln.add(node);
    }
    
    for(int j=0;j<aln.size()-1;j++){
        for(int i=0;i<aln.size();i++){
            TextField txt=(TextField) scene.lookup("#"+"text"+i+this.arrnum(i, j));
            
                 for(int k=0;k<Integer.valueOf(txt.getText());k++)
                aln.get(i).AddConnectNodes(aln.get(this.arrnum(i, j)));
              
        }
    }
    Graph g=new Graph(aln);
      return g;
    }

    
    private int arrnum(int i,int j){
     int[][] arr=new int[this.Knot_Amount][this.Knot_Amount-1];
     for(int k=0;k<this.Knot_Amount;k++){
     arr[k]=arrs(k);
     }
     return arr[i][j];
     }
     
    private int[] arrs (int x){
         boolean find=false;
     int[] re=new int[this.Knot_Amount-1];
     for(int i=0;i<re.length;i++){
     if(x==i) find=true;
        re[i]=find?i+1:i;
     }
     return re;
     }

    public Group getGroup() {
        return group;
    }
   
}