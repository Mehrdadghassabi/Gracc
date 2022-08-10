/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import Graph.Equations;
import Graph.Graph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Dour-Andish
 */
public class EquationGUI {
    private  final Graph graph;
    private static Group grup;
    
    EquationGUI(Graph graph){
     this.graph=graph;
     grup=new Group();
     }

    
   public static Group getGrup(Graph graph) {
        Group gr=new Group();
         Equations eq=FXMLDocumentController.getEqua();
        TableView<unknownamperaj> table = new TableView<>();
  
         table.setPrefWidth(165);
         table.setLayoutX(480);
         table.setLayoutY(480);
        
         TableColumn<unknownamperaj, String> numberCol =
         new TableColumn<>("Branches");
         numberCol.setCellValueFactory(
         new PropertyValueFactory<>("Branches"));
         
         TableColumn<unknownamperaj, String> amperjCol =
         new TableColumn<>("amperj");
         amperjCol.setCellValueFactory(
         new PropertyValueFactory<>("amperj"));
  
            table.getColumns().addAll(numberCol,amperjCol);
    
           numberCol.setCellValueFactory(cellData -> cellData.getValue().branNumProperty());
            amperjCol.setCellValueFactory(cellData -> cellData.getValue().amperajProperty());
           
            table.setItems(amperaj());
   
        gr.getChildren().add(table);
        return gr;
    }
    
    private static Equations getEquation(){
    return FXMLDocumentController.getEqua();
    }
    
    private static String[] getamperaj(){
    Equations equa=getEquation();
    String[] am=new String[FXMLDocumentController.getGraph().getUnknowns()];
    Matrix mat=equa.getPassives();
    for(int i=0;i<am.length;i++){
    am[i]=Float.toString(mat.getCell(i, 0));
    }
    return am;
    }
    
   private static ObservableList<unknownamperaj> amperaj() {
      ObservableList<unknownamperaj> amperajs =
         FXCollections.observableArrayList();
    
      String[] amper=getamperaj();
     
      for(int i=0;i<FXMLDocumentController.getGraph().getUnknowns();i++){
      amperajs.add(new unknownamperaj(String.valueOf(i),amper[i]));
      }
      return amperajs;
   }
   
}
