/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import ElectricalGate.Battery;
import ElectricalGate.Resistor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import  electricalcircuit.Matrix;
import electricalcircuit.MatrixException;

/**
 *
 * @author Dour-Andish
 */
public final class Equations {
    private Branches circuit;
    private  Matrix Mainmatrix;
    private  Matrix passivematrix;
    private Matrix Leftsidematrix;
    
    
    public Equations(Branches circuit){
    this.circuit=circuit;
    //this.Mainmatrix=main;
   // this.Leftsidematrix=left;
        try {
            this.Mainmatrix=getMainMatrix(circuit);
            this.Leftsidematrix=getLeftsidematrix(circuit);
             this.passivematrix=getpassive(Mainmatrix,Leftsidematrix);
        } catch (MatrixException|GeneratorException ex) {
            Logger.getLogger(Equations.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
         
    private  Matrix getpassive(Matrix Mainmatrix,Matrix Leftsidematrix) throws MatrixException{
    Matrix mainreverse=Mainmatrix.reverse();
    return mainreverse.multiply(Leftsidematrix);
    }
    
    public Matrix getLeftsidematrix(Branches circuit) throws GeneratorException{
    Graph graph=circuit.getGraph();
    float[] arr=new float[graph.getUnknowns()];
    float[] firstpart=getLeftsidematrixFirstpart(circuit);
    float[] secondpart=getLeftsidematrixSecondpart(circuit);
    arr=concatArr(firstpart,secondpart);
    
    float[][] cons=new float[graph.getUnknowns()][1];
    for(int i=0;i<arr.length;i++){
    cons[i][0]=arr[i];
    }
    
    return new Matrix(cons);
    }
    
    public float[] getLeftsidematrixFirstpart(Branches circuit) throws GeneratorException{
     Graph graph=circuit.getGraph();
    int nodesamount=graph.getNodesamount();
    float[] arr=new float[nodesamount-1];
    for(int i=0;i<arr.length;i++){
          arr[i]=0;
    }
 return arr;
}
    
    public float[] getLeftsidematrixSecondpart(Branches circuit) throws GeneratorException{
     Graph graph=circuit.getGraph();
    int nodesamount=graph.getNodesamount();
    float[] arr=new float[graph.getUnknowns()-nodesamount+1];
     ArrayList<Battery> batterys;
    ArrayList<Branch> myarr;
        //System.out.println("arrrlen: "+arr.length);
    for(int i=0;i<arr.length;i++){
        //System.out.println(arr.length);
          arr[i]=-1*circuit.findVoltageofLoopN(i);
    }
 return arr;
}
    
    public  float[] concatArr(float[] first,float[] second){
    int len=first.length+second.length;
    float[] re=new float[len];
    int counter=0;
    for(int i=0;i<first.length;i++){
    re[i]=first[i];
    counter++;
    }
    for(int j=counter;j<len;j++){
    re[j]=second[j-counter];
    }
    return re;
    }
    
    public  Matrix getMainMatrix(Branches circuit) throws GeneratorException{
     Graph graph=circuit.getGraph();
    float[][] arr=new float[graph.getUnknowns()][graph.getUnknowns()];
    float[][] firstpart=getMainMatrixFirstpart(circuit);
    float[][] secondpart=getMainMatrixSecondpart(circuit);
    arr=concatArrs(firstpart,secondpart);
    
    return new Matrix(arr); 
    }
    
    public  float[][] concatArrs(float[][] first,float[][] second){
        Graph graph=circuit.getGraph();
        int nodesamount=graph.getNodesamount();
    float[][] re=new float[graph.getUnknowns()][graph.getUnknowns()];
     for(int i=0;i<nodesamount-1;i++)
        for(int j=0;j<graph.getUnknowns();j++)
            re[i][j]=first[i][j];
    
    for(int i=nodesamount-1;i<graph.getUnknowns();i++){
        for(int j=0;j<graph.getUnknowns();j++){
            re[i][j]=second[i-nodesamount+1][j];
        }}
    
    return re;
    }
    
    public  float[][] getMainMatrixSecondpart(Branches circuit) throws GeneratorException{
    Graph graph=circuit.getGraph();
        int nodesamount=graph.getNodesamount();

    float[][] re=new float[graph.getUnknowns()-(nodesamount-1)][graph.getUnknowns()];
           ArrayList<BranchDirection> loop;
           for(int i=0;i<re.length;i++){
               loop=circuit.Loops.get(i);
       
               for(int j=0;j<loop.size();j++){
               re[i][loop.get(j).getBranch().getBranchNum()]=(loop.get(j).getIsReverse())?
                   sumofresistors(loop.get(j).getBranch().getResistors()):-sumofresistors(loop.get(j).getBranch().getResistors());  
              // if(i==1)    System.out.println("j:  "+ re[i][loop.get(j).getBranchNum()]);
               }
           }
           return re;
    }
    
    private float sumofresistors(ArrayList<Resistor> res){
        float counter=0;
        for(Resistor resistor:res){
        counter+=resistor.getResistance();
        }
        return counter;
    }
    
    public float[][] getMainMatrixFirstpart(Branches circuit){
        Graph graph=circuit.getGraph();
        int nodesamount=graph.getNodesamount();
        ArrayList<Node> nodes=graph.GetNodes();
    float[][] re=new float[nodesamount-1][graph.getUnknowns()];
     
        for(int i=0;i<re.length;i++){
            for(int j=0;j<nodes.get(i).getConnectedBranch().size();j++){
            re[i][nodes.get(i).getConnectedBranch().get(j).getBranch().getBranchNum()]
                    =findBranchInnode(nodes.get(i),nodes.get(i).getConnectedBranch().get(j));
            }
    }
    
    return re;
    }
    
    private  float findBranchInnode(Node node,Nodesbranch branch){
    ArrayList<Nodesbranch> ConnectedBranch=node.getConnectedBranch();
    float re=0;
    for(Nodesbranch nb:ConnectedBranch){
    if(nb.equals(branch))
        re=(nb.getIsDes())?-1:1;
    }
    return re;
    }

    public  Matrix getPassives() {
        return passivematrix;
    }

    public Matrix getMainmat() {
        return Mainmatrix;
    }

    public  Matrix getLeftMat() {
        return Leftsidematrix;
    }

}
