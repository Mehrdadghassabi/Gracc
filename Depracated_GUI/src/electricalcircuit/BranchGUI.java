/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import Graph.Graph;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Dour-Andish
 */
public class BranchGUI {
    private final Graph graph;
    private static Group group;
    
    BranchGUI(Graph gra){
            this.graph=gra;
    Group g=group();
    g.getChildren().add(Labelgroup());
    g.getChildren().add(Textfieldgroup());
   // g.getChildren().add(new Button("k"));
    BranchGUI.group=g;
    }
    
    private Group Labelgroup(){
    Group grup=new Group();
    int firstlocate=KnotGUI.getLocation();
    for(int i=0;i<this.graph.getUnknowns();i++){
     Label label=new Label("Branch "+i+":");
     label.setStyle("-fx-font: normal bold 20px 'serif' ");
     label.setLayoutX(10);
     label.setLayoutY(firstlocate+45+(38*i));
     grup.getChildren().add(label);
    }
    
    return grup;
    }
    
    private Group Textfieldgroup(){
    Group grup=new Group();
    int firstlocate=KnotGUI.getLocation();
    for(int i=0;i<this.graph.getUnknowns();i++){
        TextField txt=new TextField("");
        txt.setId("branch"+i);
        txt.setLayoutX(100);
        txt.setLayoutY((firstlocate)+45+(38*i));
        grup.getChildren().add(txt);
    }
    
    return grup;
    }
     
    private Group group(){
     return new Group();
     }

    public Graph getGraph() {
        return graph;
    }

    public static Group getGroup() {
        return group;
    }
  
}
