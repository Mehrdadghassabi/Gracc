/*
Copyleft (ALL WRONG ARE RESERVED) 2019  Mehrdad Ghassabi <mehrdad.gv@gmail.com>
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
*/

package Graph;

import ElectricalGate.Battery;
import ElectricalGate.ElectricalGate;
import ElectricalGate.Resistor;
import java.util.ArrayList;

/**
 *
 * @author Mehrdad Ghassabi
 */
public class Branch {
    private final Node origin;
    private final Node destination;
    private final ArrayList<ElectricalGate> blocks;
    private int branchNum;

    public Branch(Node origin,Node destination,ArrayList<ElectricalGate> blocks){
    this.destination=destination;
    this.origin=origin;
    this.blocks=blocks;
    }

    public ArrayList<ElectricalGate> getBlocks() {
        return blocks;
    }

    public Node getDestination() {
        return destination;
    }

 
    
    public int getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(int branchNum) {
        this.branchNum = branchNum;
    }


    public Node getOrigin() {
        return origin;
    
    
}
    
    public boolean IsRightBranch(int a,int b){  
    return b==this.origin.getNumber()&&a==this.destination.getNumber()||
            a==this.origin.getNumber()&&b==this.destination.getNumber();
    }
    
    public ArrayList<Resistor> getResistors(){
    ArrayList<Resistor> resistors=new ArrayList<>();
    
    this.blocks.stream().filter((eg) -> (eg instanceof Resistor)).forEachOrdered((eg) -> {
        resistors.add((Resistor)eg);
        });
    return resistors;
    }
    
    public ArrayList<Battery> getBatterys(){
    ArrayList<Battery> batterys=new ArrayList<>();
    
    this.blocks.stream().filter((eg) -> (eg instanceof Battery)).forEachOrdered((eg) -> {
        batterys.add((Battery)eg);
        });
    return batterys;
    }
    
    @Override
    public String toString(){
    String str="I"+this.branchNum+"_"+" O: "+
            this.origin.toString()+" D: "+this.destination.toString();
    StringBuilder sb=new StringBuilder(str);
    blocks.forEach((gate) -> {
        sb.append(" ").append(gate.toString());
        });
    
    return sb.toString();
    }
}