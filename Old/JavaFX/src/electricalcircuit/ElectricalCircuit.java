/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import ElectricalGate.Battery;
import ElectricalGate.ElectricalGate;
import ElectricalGate.Resistor;
import Graph.Branches;
import Graph.Equations;
import Graph.GeneratorException;
import Graph.Graph;
import Graph.Node;
import Graph.SingletonException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Dour-Andish
 */
public class ElectricalCircuit extends Application {
    private static Scene s;
    
    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Group group = new Group();
        group.getChildren().add(root);
      
         Scene scene=new Scene(group);
         EventHandlerImpl EhI=new EventHandlerImpl(scene);
         
        Button btn=(Button) scene.lookup("#Btn_Go");
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,EhI );
        btn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        
        Label label=(Label) scene.lookup("#label");
        label.setStyle("-fx-font: normal bold 20px 'serif' ");
        
        this.s=scene;
      
   
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Electrical Circuit Simulator");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // try {
            launch(args);
            //long t1=System.currentTimeMillis();
           /* mesal1();
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("Duration:"+(System.currentTimeMillis()-t1)+"ms");
        } catch(GeneratorException|SingletonException ex) {
            Logger.getLogger(ElectricalCircuit.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public static Scene getScene() {
       return s;
    }

    private static void mesal() throws GeneratorException, SingletonException{
            Node a=new Node();
            Node b=new Node();
            Node c=new Node();
            Node d=new Node();
            
            a.AddConnectNodes(c);
            a.AddConnectNodes(c);
            a.AddConnectNodes(b);
            
            b.AddConnectNodes(d);
            b.AddConnectNodes(d);
            b.AddConnectNodes(a);
            
            c.AddConnectNodes(a);
            c.AddConnectNodes(a);
            c.AddConnectNodes(d);
            
            d.AddConnectNodes(b);
            d.AddConnectNodes(b);
            d.AddConnectNodes(c);
            
            ArrayList<Node> aln=new ArrayList<>();
            aln.add(a);
            aln.add(b);
            aln.add(c);
            aln.add(d);
            
            Graph g=new Graph(aln);
             
            ArrayList<ArrayList<ElectricalGate>> in=new ArrayList<>();
     
           
            ArrayList<ElectricalGate> b1=new ArrayList<>();
            Resistor rb1=new Resistor(6);
            b1.add(rb1);
            Battery Bb1=new Battery(10f,false);
            b1.add(Bb1);
            
              ArrayList<ElectricalGate> b2=new ArrayList<>();
            Resistor r1b2=new Resistor(3);
            Resistor r2b2=new Resistor(5);
            b2.add(r1b2);
            b2.add(r2b2);
            Battery Bb2=new Battery(5f,false);
            b2.add(Bb2);
            
                   ArrayList<ElectricalGate> b3=new ArrayList<>();
            Resistor r1b3=new Resistor(2);
            Resistor r2b3=new Resistor(3);
            b3.add(r1b3);
            b3.add(r2b3);
           
   ArrayList<ElectricalGate> b4=new ArrayList<>();
            Resistor rb4=new Resistor(2);
            b4.add(rb4);
            Battery Bb4=new Battery(6f,false);
            b4.add(Bb4);
            
            
              ArrayList<ElectricalGate> b5=new ArrayList<>();
            Resistor rb5=new Resistor(2);
            b5.add(rb5);
   
               ArrayList<ElectricalGate> b6=new ArrayList<>();
            Resistor rb6=new Resistor(4);
            b6.add(rb6);
            Battery Bb6=new Battery(4f,true);
            b6.add(Bb6);
            
            in.add(b1);
            in.add(b2);
            in.add(b3);
            in.add(b4);
            in.add(b5);
            in.add(b6);
            
            Branches circuit=new Branches(g,in);
          Equations eq=new Equations(circuit);
          System.out.println(eq.getPassives());
                }
    
    private static void mesal1() throws GeneratorException, SingletonException{
            Node a=new Node();
            Node b=new Node();
            
            a.AddConnectNodes(b);
            a.AddConnectNodes(b);
            a.AddConnectNodes(b);
            
            b.AddConnectNodes(a);
            b.AddConnectNodes(a);
            b.AddConnectNodes(a);
            
            ArrayList<Node> aln=new ArrayList<>();
            aln.add(a);
            aln.add(b);
            
            Graph g=new Graph(aln);
            
            ArrayList<ArrayList<ElectricalGate>> in=new ArrayList<>();
            
            ArrayList<ElectricalGate> b1=new ArrayList<>();
            Resistor rb1=new Resistor(40);
            b1.add(rb1);
            
         
             // ArrayList<ElectricalGate> b2=new ArrayList<>();
           // Resistor r1b2=new Resistor(20);
           // b2.add(r1b2);
          // Battery Bb2=new Battery(20f,false);
           //b2.add(Bb2);
               StringSeperator s2=new StringSeperator("R20,B20");
           ArrayList<ElectricalGate> b2=s2.getBranch();
            
           
                   ArrayList<ElectricalGate> b3=new ArrayList<>();
            Resistor r1b3=new Resistor(10);
            b3.add(r1b3);
            Battery Bb3=new Battery(10f,false);
            b3.add(Bb3);
            
            in.add(b1);
            in.add(b2);
            in.add(b3);
            
            Branches circuit=new Branches(g,in);
        
           Equations eq=new Equations(circuit);
           System.out.println(eq.getPassives());
    }
}