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

/**
 *
 * @author Mehrdad Ghassabi
 */
public class Direction {
    private final Branch branch;
    private boolean IsReverse;
    
      Direction(Branch branch, boolean IsReverse){
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
