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
public class BranchDirection {
    private final Branch branch;
    private boolean IsReverse;
    
      BranchDirection(Branch branch,boolean IsReverse){
    this.IsReverse=IsReverse;
    this.branch=branch;
    }

      public Branch getBranch() {
        return branch;
    }
    
      public boolean getIsReverse() {
        return IsReverse;
    }

      public void SetIsReverse(boolean IsReverse){
      this.IsReverse=IsReverse;
      }
      
    @Override
      public String toString(){
      StringBuilder sb=new StringBuilder();
      sb.append(this.branch);
      String rev=(this.IsReverse)?"  .R.  ":"  .N.  ";
      sb.append(rev);
      return sb.toString();
      }
}
