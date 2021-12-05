/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricalcircuit;

import ElectricalGate.Battery;
import ElectricalGate.ElectricalGate;
import ElectricalGate.Resistor;
import java.util.ArrayList;

/**
 *
 * @author Dour-Andish
 */
public class StringSeperator {
    private final String input;
    private final ArrayList<String> gates;
    private final ArrayList<ElectricalGate> branch;
    
    public StringSeperator(String input){
    this.input=input;
    this.gates=findgates(input);
    this.branch=findbranch(gates);
    }
    
   private ArrayList<String> findgates(String str){
        ArrayList<String> re=new ArrayList<>();
        StringBuffer sb=new StringBuffer("");
       for(int i=0;i<str.length();i++){
           
       if(str.charAt(i)==','){
       re.add(sb.toString());
       sb=sb.delete(0, sb.length());
       }
       
             if(str.charAt(i)!=',')
       sb.append(str.charAt(i));
             
             if(i==str.length()-1)
                  re.add(sb.toString());
       }
       return re;
    }
   
   private float amount(String str){
   String num=str.substring(1);
   return Float.valueOf(num);
   }
   
   private ArrayList<ElectricalGate> findbranch(ArrayList<String> gat){
       ArrayList<ElectricalGate> bran=new ArrayList<>();
       for(String str:gat){
           if(str.charAt(0)=='B'){
               Battery batery=new Battery(amount(str),false);
               bran.add(batery);
           }
           else{
               Resistor res=new Resistor(amount(str));
               bran.add(res);
           }
       }
   return bran;
   }

    public ArrayList<ElectricalGate> getBranch() {
        return branch;
    }
   
}
