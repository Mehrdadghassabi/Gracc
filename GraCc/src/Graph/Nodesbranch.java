/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

/**
 *
 * @author Dour-Andish
 */
public class Nodesbranch {
   private  Branch branch;
    private  boolean IsDes;

    public Nodesbranch(Branch branch,boolean IsDes){
    this.IsDes=IsDes;
    this.branch=branch;
    }

    public Branch getBranch() {
        return branch;
    }
    
    public void setBanch(Branch b){
    this.branch=b;
    }
    
    public boolean getIsDes() {
        return IsDes;
    }

    public void setIsDes(boolean IsDes) {
        this.IsDes = IsDes;
    }
}