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
 * @author Mehrdad ghassabi
 */
public class Connection {
   private  Branch branch;
   private  boolean Is_Destination;

    public Connection(Branch branch, boolean IsDes){
    this.Is_Destination=IsDes;
    this.branch=branch;
    }

    public Branch getBranch() {
        return branch;
    }
    
    public void setBanch(Branch b){
    this.branch=b;
    }
    
    public boolean getIsDes() {
        return Is_Destination;
    }

    public void setIsDes(boolean IsDes) {
        this.Is_Destination = IsDes;
    }
}