
package ElectricalGate;

/**
 *
 * @author Dour-Andish
 */
public class Resistor extends ElectricalGate {

    public Resistor(float resistance) {
        super.resistance=resistance;
    }

    @Override
    public String toString(){
       String res= Float.toString(getResistance());
        String str="/\\/\\/ R: "+res;
        return str;
    }
}