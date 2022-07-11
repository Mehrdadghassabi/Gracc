
package ElectricalGate;

/**
 *
 * @author Dour-Andish
 */
public class Battery extends ElectricalGate {
    private boolean side;
    
    public Battery(float voltage,float resistance,boolean side){
    super.voltage=voltage;
    super.resistance=resistance;
    this.side=side;
    }
    
    public Battery(float voltage,boolean side){
    this.voltage=voltage;
    super.resistance=0;
    this.side=side;
    }

    @Override
    public String toString(){
        String vol=Float.toString(voltage);
    String str="|- volatage:"+vol;
    return str;
    }
}