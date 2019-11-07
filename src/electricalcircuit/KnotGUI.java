/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;



import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Dour-Andish
 */
public class KnotGUI {
    private final int Knot_Amount;
    private static Group group;
    private static int location;
    
    KnotGUI(int Knot_Amount){
    this.Knot_Amount=Knot_Amount;
    Group g=group();
    g.getChildren().add(Labelgroup());
    g.getChildren().add(Radiobtngroup());
    KnotGUI.group=g;
    }
    
    private Group Labelgroup(){
    Group gr=new Group();
    for(int i=0;i<this.Knot_Amount;i++){
        Label label=new Label("Node "+i+":");
        label.setStyle("-fx-font: normal bold 20px 'serif' ");
        label.setLayoutX(10);
        label.setLayoutY(73+39*i);
       gr.getChildren().add(label);
    }
    
    return gr;
    }
    
    private Group Radiobtngroup(){
    Group gr=new Group();
    for(int j=0;j<this.Knot_Amount-1;j++){
    for(int i=0;i<this.Knot_Amount;i++){
        TextField btn=new TextField("");
        btn.setMaxWidth(50);
        btn.setId("text"+i+this.arrnum(i, j));
        btn.setLayoutX(100+50*j);
        if(i==this.Knot_Amount-1){
        location=100+38*i;
        }
        btn.setLayoutY(70+38*i);
    gr.getChildren().add(btn);
    }
    }
    
    return gr;
    }
     
    private Group group(){
     return new Group();
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

    public static Group getGroup() {
        return group;
    }

    public static int getLocation() {
        return location;
    }
    
}
