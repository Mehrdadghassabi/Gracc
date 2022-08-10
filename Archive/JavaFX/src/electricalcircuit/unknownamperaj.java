/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dour-Andish
 */
public class unknownamperaj {
    private StringProperty branNum;
   private StringProperty amperaj;
   
    unknownamperaj(String branNum,String amperaj){
     setbranNum(branNum);
      setamperaj(amperaj);
    }
    
      public StringProperty branNumProperty(){
      if(branNum == null)
      branNum = new SimpleStringProperty();
      return branNum;
   }
   public StringProperty amperajProperty() {
      if(amperaj == null)
      amperaj = new SimpleStringProperty();
      return amperaj;
   }
   
     public void setbranNum(String name){ 
             branNumProperty().setValue(name);
         }
     
   public String getName(){ 
       return branNumProperty().get(); 
   }
   
   public void setamperaj(String phone){
       amperajProperty().setValue(phone);
   }
   
   public String getPhone(){ 
       return amperajProperty().get();
   }
}
