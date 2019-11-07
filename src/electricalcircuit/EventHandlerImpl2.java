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
public class EventHandlerImpl2 implements EventHandler<MouseEvent> {
  private final Scene scene;
        public EventHandlerImpl2(Scene scene) {
            this.scene=scene;
        }
    
    
    @Override
    public void handle(MouseEvent event) {
          Task<Group> task;
          task = new Task<Group>() {
              @Override
              protected Group call() throws Exception {
                        Group group;
              
                  
                  group=EquationGUI.getGrup(FXMLDocumentController.getGraph());
                     // group=new Group();
                  //group.getChildren().add(new Button("k"));
               
                  
                   return group;
                   
              }
          };
          task.setOnFailed(new EventHandler<WorkerStateEvent>(){
              @Override
              public void handle(WorkerStateEvent event) {
                  System.out.println("taskfailed90");
                  
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
