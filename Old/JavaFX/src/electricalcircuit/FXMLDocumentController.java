/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import Graph.Equations;
import Graph.GeneratorException;
import Graph.Graph;
import Graph.SingletonException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dour-Andish
 */
public class FXMLDocumentController implements Initializable {
    private static int Knot_amount;
    private int Time_go_clicked=0;
    private static Graph graph;
    private static Equations equa;
    
    @FXML
    private TextField text;
    
    @FXML
     private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        System.out.println("Time_go_clicked:"+Time_go_clicked);
        if(this.Time_go_clicked==0){
        Knot_amount=Integer.valueOf(text.getText());
        }
        
        if(this.Time_go_clicked==1){
        GraphGUI gragui=new GraphGUI(ElectricalCircuit.getScene(),Knot_amount);
        FXMLDocumentController.graph=gragui.maingraph();
        }
            if(this.Time_go_clicked==2){
                    //System.out.println(FXMLDocumentController.graph.getUnknowns());
            BranchGUI bg=new BranchGUI(FXMLDocumentController.graph);
            
            EventHandlerImpl1 EhI1=new EventHandlerImpl1(ElectricalCircuit.getScene());
         Button btn=(Button) ElectricalCircuit.getScene().lookup("#Btn_Go1");
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,EhI1 );
            }
            if(this.Time_go_clicked==3){
            TableGUI tgui=new TableGUI(FXMLDocumentController.graph);
            try {
                Equations eq=new Equations(TableGUI.getbran());
                equa=eq;
                System.out.println(eq.getPassives());
            } catch (GeneratorException | SingletonException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
               // EventHandlerImpl2 EhI=new EventHandlerImpl2(ElectricalCircuit.getScene());
               // Button btn=(Button) ElectricalCircuit.getScene().lookup("#Btn_Go2");
                //btn.addEventHandler(MouseEvent.MOUSE_CLICKED,EhI );
            
            }
            synchronized(this){
            if(this.Time_go_clicked==4){
               EquationGUI eqgui=new EquationGUI(FXMLDocumentController.graph);
             EventHandlerImpl2 EhI2=new EventHandlerImpl2(ElectricalCircuit.getScene());
                Button btnE=(Button) ElectricalCircuit.getScene().lookup("#Btn_Go2");
                //System.out.println(btnE==null);
                btnE.addEventHandler(MouseEvent.MOUSE_CLICKED,EhI2 );
   
            }
            }
        
        this.Time_go_clicked++;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    public static int getKnot_amount() {
       return Knot_amount;
    }

    public static Graph getGraph() {
        return graph;
    }   

    public static Equations getEqua() {
        return equa;
    }
    
}
