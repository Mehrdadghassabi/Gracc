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
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dour-Andish
 */
public class EventHandlerImpl implements EventHandler<MouseEvent> {
       private int counter=0;
    
      private final Scene scene;
        public EventHandlerImpl(Scene scene) {
            this.scene=scene;
        }

        @Override
        public void handle(MouseEvent event) {
            Task<Group> task;
          task = new Task<Group>() {
              @Override
              protected Group call() throws Exception {
                  
                  Group group;
            
                  int kontnum=FXMLDocumentController.getKnot_amount();
                  KnotGUI knotgui=new KnotGUI(kontnum);
                   group=KnotGUI.getGroup();
                  
                    Button btn=(Button) ElectricalCircuit.getScene().lookup("#Btn_Go");
                    btn.setId("Btn_Go1");
                   
                  return group;
                   
              }
          };
          
                task.setOnFailed(new EventHandler<WorkerStateEvent>(){
              @Override
              public void handle(WorkerStateEvent event) {
                  System.out.println("taskfailed89");
                  
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