package Graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dour-Andish
 */
public class Generator {

    /**
     *
     * @param n
     * @param r
     * @return
     */
    public List<int[]> generate(int n,int r){
    List<int[]> combinations=new ArrayList<>();
    int[] combination=new int[r];
    
    
    for(int i=0;i<r;i++)
        combination[i]=i;
    
    while(combination[r-1]<n){
        combinations.add(combination.clone());
        
        int t=r-1;
        while(t!=0 && combination[t]==n-r+t)
            t--;
        
        combination[t]++;
        for(int i=t+1;i<r;i++)
            combination[i]=combination[i-1]+1;
        
        
    }
    return combinations;
    }
    
    public ArrayList<int[]> permute(int[] num) {
	ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
 
	//start from an empty list
	result.add(new ArrayList<Integer>());
 
	for (int i = 0; i < num.length; i++) {
		//list of list in current iteration of the array num
		ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
 
		for (ArrayList<Integer> l : result) {
			// # of locations to insert is largest index + 1
			for (int j = 0; j < l.size()+1; j++) {
				// + add num[i] to different locations
				l.add(j, num[i]);
 
				ArrayList<Integer> temp = new ArrayList<Integer>(l);
				current.add(temp);
 
				//System.out.println(temp);
 
				// - remove num[i] add
				l.remove(j);
			}
		}
 
		result = new ArrayList<ArrayList<Integer>>(current);
	}
 
	return getchange(result);
}
    
    protected ArrayList<int[]> CloneBypermute(List<int[]> input){
    ArrayList<int[]> output=new ArrayList<>();
    for(int[] i:input){
    output.addAll(permute(i));
    }
    return output;
    }
    
    private ArrayList<int[]> getchange(ArrayList<ArrayList<Integer>> input){
    ArrayList<int[]> myresult=new ArrayList<>();
        for(ArrayList<Integer> r:input){
        Integer[]arr=new Integer[r.size()];
        for(int j=0;j<r.size();j++){
        arr[j]=r.get(j);
        }
        
        int[]brr=new int[arr.length];
        for(int i=0;i<brr.length;i++)
            brr[i]=arr[i].intValue();
        
        myresult.add(brr);
        }
        return myresult;
    }
    
    public  ArrayList<int[]> removeDuplicated(List<int[]> input) throws GeneratorException{
     ArrayList<int[]> output=new ArrayList<>();
     for(int[] in:input){
      if(!SearchTheElement(output,in))
          output.add(in);
     }
     return output;
    }
    
    private boolean SearchTheElement(ArrayList<int[]> source,int[]x) throws GeneratorException{
        boolean flag=false;
        for(int[] i:source){
        if(IsEqual(i,x)){
            flag=true;
            break;
        }
        }
        return flag;
    }
    
    private boolean IsEqual(int[]arr,int[]brr) throws GeneratorException{
    if(arr.length!=brr.length)
        throw new GeneratorException("Can not compare with diffrenet len");
        
         boolean flag=true;
         for(int i:arr){
         if(!SearchTheElement(brr,i)){
             flag=false;
             break;
         }
         }
         return flag;
    }
    
    private boolean SearchTheElement(int[] source,int x){
    boolean flag=false;
    for(int inside:source){
    if(inside==x)
        flag=true;
    }
    return flag;
    }
}