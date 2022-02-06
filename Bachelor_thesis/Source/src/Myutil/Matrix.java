package Myutil;

/**
 *
 * @author Mehrdad Ghassabi
 */
public class Matrix{
     
    private final float[][] m;
    private final boolean issquare;
    private final int rowsize;
    private final int cloumnsize;

    public Matrix(int n) {
        m=new float[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                m[i][j]=0;
        
        this.rowsize=n;
        this.cloumnsize=n;
        this.issquare=true;
    }
    
    public Matrix(float [][] m){
        this.m=m;
        this.rowsize=m.length;
        this.cloumnsize=m[0].length;
        this.issquare=m[0].length==m.length;
    }

    public void setMatrix(float[][] m) {
    for(int i=0;i<this.rowsize;i++)
            for(int j=0;j<this.cloumnsize;j++)
                this.m[i][j]=m[i][j];
    }
    
    public int getrowsize(){
    return this.rowsize;
    }
    
    public int getcloumnsize(){
       return this.cloumnsize;
    }
    
    public float[][] getMatrix() {
    return this.m;
    }
    
    public void setCell(int row, int column, int value) {
        this.m[row][column]=value;
    }
    
    public float getCell(int row, int column) {
       
        return this.m[row][column];
    }
    
    public float determinant() throws MatrixException {
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
        if(this.rowsize==1)
            return m[0][0];
        else{
        Matrix mat=new Matrix(this.rowsize-1);
        
        int sum=0;
        for(int n=0;n<this.rowsize;n++){
           float[][] Arr=new float[mat.rowsize][mat.rowsize];
           for(int i=0;i<mat.rowsize;i++){
               for(int j=0;j<mat.rowsize;j++){
                   int hold=0;
                   if(j>=n)
                       hold++;
                   Arr[i][j]=this.m[i+1][j+hold];
           
               }
           }
           mat.setMatrix(Arr);
           
         if(Arr.length>1)
           sum +=Math.pow(-1, n)*mat.determinant()*this.m[0][n];
         
         else{
             //System.out.println(Math.pow(-1, n)/*Arr[0][0]*this.m[1][n]*/);
             //System.out.println(this.m[0][n]);
             sum +=Math.pow(-1, n)*Arr[0][0]*this.m[0][n];
         }
        }
        return sum;
        }
    }
    
    public Matrix transpose() throws MatrixException {
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
        Matrix mat=new Matrix(this.rowsize);
        for(int i=0;i<this.rowsize;i++)
            for(int j=0;j<this.rowsize;j++)
                mat.m[i][j]=this.m[j][i];
        
        return mat;
        
    }
    
    private Matrix getinnermat(int a,int b) throws MatrixException{
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
        int counter1=0;
        int counter2=0;
        float[][] arr=new float[this.rowsize-1][this.cloumnsize-1];
        for(int i=0;i<this.rowsize;i++){
            if(i==a) continue;
            for(int j=0;j<this.cloumnsize;j++){
                if(j==b) continue;
    
                arr[counter1][counter2]=this.getCell(i, j);
         
                counter2++;
            }
            
            counter1++;
            counter2=0;
        }
        return new Matrix(arr);
    }
    
    public Matrix kahad() throws MatrixException{
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
        float[][]arr=new float[this.rowsize][this.cloumnsize];
        for(int i=0;i<this.rowsize;i++)
            for(int j=0;j<this.cloumnsize;j++)
                arr[i][j]=getinnermat(i,j).determinant();
        
        return new Matrix(arr);
    }
    
    public Matrix hamsaze() throws MatrixException{
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
     float[][]arr=new float[this.rowsize][this.cloumnsize];
      for(int i=0;i<this.rowsize;i++)
            for(int j=0;j<this.cloumnsize;j++)
                arr[i][j]=(int)Math.pow(-1, i+j)*kahad().getCell(i, j);
      
      return new Matrix(arr);
    }
    
    public Matrix star() throws MatrixException{
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
    return hamsaze().transpose();
    }
    
    public Matrix reverse() throws MatrixException{
        if(m.length!=m[0].length)
            throw new MatrixException("Not square matrice");
        
           if(determinant()==0)
            throw new MatrixException("Single matrice");
        
         float[][]arr=new float[this.rowsize][this.cloumnsize];
        for(int i=0;i<this.rowsize;i++)
            for(int j=0;j<this.cloumnsize;j++)
                arr[i][j]=(1/determinant())*star().getCell(i, j);
        
        return new Matrix(arr);
    }
    
    public Matrix multiply(Matrix mat) throws MatrixException{
    if(this.cloumnsize!=mat.rowsize)
        throw new MatrixException("multiply is not defined");
    
    
    float[][] arr=new float[this.rowsize][mat.cloumnsize];
    for(int i=0;i<this.rowsize;i++){
        for(int j=0;j<mat.cloumnsize;j++){
            //System.out.println("this len: "+getradif(i).length+"matlen: "+mat.m[j].length);
             arr[i][j]=multiplytwoarr(getradif(i),getsutun(mat,j));
                    }
    }
    
    return new Matrix(arr);
    }
    
    private float[] getradif(int x){
        float[] radif_at_x=new float[m[0].length];
        for(int i=0;i<m[0].length;i++)
           radif_at_x[i]=m[x][i];
        
           return radif_at_x;
    }
    
    private float[] getsutun(Matrix mat,int x){
    float[] f=new float[mat.m.length];
    
    for(int i=0;i<f.length;i++){
    f[i]=mat.m[i][x];
    }
    return f;
    }
    
    private float multiplytwoarr(float[]a,float[] b) throws MatrixException{
        if(a.length!=b.length)
            throw new MatrixException("not allowed for diffrent length");
        float sum=0;
       for(int i=0;i<a.length;i++)
           sum+=a[i]*b[i];
       
       return sum;
    }
    
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder("");
        for(int i=0;i<rowsize;i++ ){
            for(int j=0;j<cloumnsize;j++ ){
                sb.append(Float.toString(m[i][j])+ " ");
            }
            sb.append("\n");
    }
    return sb.toString();
}
}