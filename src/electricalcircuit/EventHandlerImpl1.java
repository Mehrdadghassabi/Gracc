/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dour-Andish
 */
public class EventHandlerImpl1 implements EventHandler<MouseEvent> {
  private final Scene scene;
        public EventHandlerImpl1(Scene scene) {
            this.scene=scene;
        }
    
    
    @Override
    public void handle(MouseEvent event) {
          Task<Group> task;
          task = new Task<Group>() {
              @Override
              protected Group call() throws Exception {
                        Group group;
                      int knotnum=FXMLDocumentController.getKnot_amount();
                 Scene sc=ElectricalCircuit.getScene();
                 
                  group=BranchGUI.getGroup();
                    Button btn=(Button) ElectricalCircuit.getScene().lookup("#Btn_Go1");
                    btn.setId("Btn_Go2");
              
                    
                   return group;
                   
              }
          };
          task.setOnFailed(new EventHandler<WorkerStateEvent>(){
              @Override
              public void handle(WorkerStateEvent event) {
                  System.out.println("taskfailed88");
                  
              }
          });
          
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ((Group)scene.getRoot()).getChildren().add(task.getValue());
                }
               
            });
            Thread t=new Thread(task);
            t.start();
            
    }
    
}
