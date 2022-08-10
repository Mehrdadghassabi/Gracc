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

package GraCc;

import ElectricalGate.ElectricalGate;
import Myutil.GeneratorException;
import Graph.Graph;
import Graph.Node;
import Myutil.SingletonException;
import Graph.Gracc;
import ElectricalGate.Battery;
import ElectricalGate.Resistor;
import Myutil.Solver;
import Myutil.StringSeperator;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws GeneratorException, SingletonException {
        mesal();
    }

    private static void mesal() throws GeneratorException, SingletonException {
        Node a=new Node();
        Node b=new Node();
        Node c=new Node();
        Node d=new Node();

        a.AddConnectNodes(c);
        a.AddConnectNodes(c);
        a.AddConnectNodes(b);

        b.AddConnectNodes(d);
        b.AddConnectNodes(d);
        b.AddConnectNodes(a);

        c.AddConnectNodes(a);
        c.AddConnectNodes(a);
        c.AddConnectNodes(d);

        d.AddConnectNodes(b);
        d.AddConnectNodes(b);
        d.AddConnectNodes(c);

        ArrayList<Node> aln=new ArrayList<>();
        aln.add(a);
        aln.add(b);
        aln.add(c);
        aln.add(d);

        Graph g=new Graph(aln);

        ArrayList<ArrayList<ElectricalGate>> in=new ArrayList<>();


        ArrayList<ElectricalGate> b1=new ArrayList<>();
        Resistor rb1=new Resistor(6);
        b1.add(rb1);
        Battery Bb1=new Battery(10f,false);
        b1.add(Bb1);

        ArrayList<ElectricalGate> b2=new ArrayList<>();
        Resistor r1b2=new Resistor(3);
        Resistor r2b2=new Resistor(5);
        b2.add(r1b2);
        b2.add(r2b2);
        Battery Bb2=new Battery(5f,false);
        b2.add(Bb2);

        ArrayList<ElectricalGate> b3=new ArrayList<>();
        Resistor r1b3=new Resistor(2);
        Resistor r2b3=new Resistor(3);
        b3.add(r1b3);
        b3.add(r2b3);

        ArrayList<ElectricalGate> b4=new ArrayList<>();
        Resistor rb4=new Resistor(2);
        b4.add(rb4);
        Battery Bb4=new Battery(6f,false);
        b4.add(Bb4);


        ArrayList<ElectricalGate> b5=new ArrayList<>();
        Resistor rb5=new Resistor(2);
        b5.add(rb5);

        ArrayList<ElectricalGate> b6=new ArrayList<>();
        Resistor rb6=new Resistor(4);
        b6.add(rb6);
        Battery Bb6=new Battery(4f,true);
        b6.add(Bb6);

        in.add(b1);
        in.add(b2);
        in.add(b3);
        in.add(b4);
        in.add(b5);
        in.add(b6);

        Gracc circuit=new Gracc(g,in);
        Solver eq=new Solver(circuit);
        System.out.println(eq.getPassives());
    }

    private static void mesal1() throws GeneratorException, SingletonException {
        Node a=new Node();
        Node b=new Node();

        a.AddConnectNodes(b);
        a.AddConnectNodes(b);
        a.AddConnectNodes(b);

        b.AddConnectNodes(a);
        b.AddConnectNodes(a);
        b.AddConnectNodes(a);

        ArrayList<Node> aln=new ArrayList<>();
        aln.add(a);
        aln.add(b);

        Graph g=new Graph(aln);

        ArrayList<ArrayList<ElectricalGate>> in=new ArrayList<>();

        ArrayList<ElectricalGate> b1=new ArrayList<>();
        Resistor rb1=new Resistor(40);
        b1.add(rb1);


        // ArrayList<ElectricalGate> b2=new ArrayList<>();
        // Resistor r1b2=new Resistor(20);
        // b2.add(r1b2);
        // Battery Bb2=new Battery(20f,false);
        //b2.add(Bb2);
        StringSeperator s2=new StringSeperator("R20,B20");
        ArrayList<ElectricalGate> b2=s2.getBranch();


        ArrayList<ElectricalGate> b3=new ArrayList<>();
        Resistor r1b3=new Resistor(10);
        b3.add(r1b3);
        Battery Bb3=new Battery(10f,false);
        b3.add(Bb3);

        in.add(b1);
        in.add(b2);
        in.add(b3);

        Gracc circuit=new Gracc(g,in);

        Solver eq=new Solver(circuit);
        System.out.println(eq.getPassives());
    }
}
