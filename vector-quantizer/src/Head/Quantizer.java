
package Head;

public class Quantizer {
    
    static int numOfBits = 0;
    static int numOfV = 0;
    //static int vectorSize = 2; // may be diff avg and height but later
    
    static int vectorWidth = 0;
    static int vectorHeight = 0;
    
    
    static int vectors [][][] = null;
    static int numOfVectors=0;
    static double steps [][][] = null;
    static double oldsteps [][][] = null;
    static int numOfSteps = 1;
    static int nearest [] = null;
    static int avg [] = null;
    static int oldavg [] = null;
    
    
    public static void init(){
        System.out.println("inti Quantizer");
        numOfV = (int) Math.pow(2, numOfBits);
        numOfVectors = 0;
        numOfSteps = 1;
        int allVecs = ((main.width*main.height)/(main.q.vectorHeight*main.q.vectorWidth));
        vectors = new int [allVecs][vectorHeight][vectorWidth];
        steps = new double [numOfV][vectorHeight][vectorWidth];
        oldsteps = new double [numOfV][vectorHeight][vectorWidth];
        nearest = new int [allVecs];
        avg = new int[numOfV];
        oldavg = new int[numOfV];
    }
    
    public static void setVecWidth(int u){
        vectorWidth = u;
    }
    public static void setVecHeight(int u){
        vectorHeight = u;
    }
    public static int getVecWidth(){
        return vectorWidth ;
    }
    public static int getVecHeight(){
        return vectorHeight;
    }
    
    
    public static void quantize(){

        System.out.println("getAvg Begin");
        
        steps[0] = getAvg();
        main.gui.updateAllP(5);
        
        System.out.println("getAvg Done");
        System.out.println("genVecs Begin");

        genVecs();
        main.gui.updateAllP(10);
        
        System.out.println("genVec Done");
        
        
        System.out.println("split Begin");

        split();
        main.gui.updateAllP(20);
        
        System.out.println("split Done");
        System.out.println("confirmAvg Begin");

        confirmAvg();
        main.gui.updateAllP(25);
        
        System.out.println("confirmAvg Done");
        System.out.println("setVecs Begin");

        setVecs();  // set new records fo new image before write  s
        main.gui.updateAllP(10);
        
        System.out.println("setVecs Done");
        
        main.c.init();
        
        System.out.println("codeVector Begin");

        Codes.codeVector();
        main.gui.updateAllP(10);
        
        System.out.println("codeVector Done");
        System.out.println("convertImage Begin");

        Codes.convertImage();
        
        System.out.println("convertImage Done");
        
    }
    
   
    
    
    public static double round(double x , int d){
        int tmp;  x*=Math.pow(10, d);  tmp = (int)x;  x-= tmp;
        if (x >= 0.5){ tmp++; } x=0.0; x+= tmp;  x/=Math.pow(10, d);
        return x;
    }

    
    public static double[][] getAvg(){
        double  x [][] = new double [vectorHeight][vectorWidth];        
        for(int ii=0;ii<vectorHeight;ii++){
            for(int jj = 0;jj<vectorWidth;jj++){
                double sum =0;
                for(int i=ii;i<main.height;i+=vectorHeight){
                    for(int j=jj;j<main.width;j+=vectorWidth){
                        sum += main.pixels[i][j];
                    }
                }
                x[ii][jj] = round(sum/((main.width*main.height)/(vectorHeight*vectorWidth)) , 1);
            }
        }
        return x;
    }
    
    public static void reCalcAvg(){    
        for(int i=0;i<numOfSteps;i++){
            double d [][] = new double [vectorHeight][vectorWidth];
            int count = 0;
            for(int j=0;j<numOfVectors;j++){
                if (nearest[j] == i){
                    count++;
                    for(int ii=0;ii<vectorHeight;ii++){
                        for(int jj=0;jj<vectorWidth;jj++){
                            d[ii][jj]+=vectors[j][ii][jj];
                        }
                    }
                }
            }
            // divide
            for(int ij=0; ij<vectorHeight; ij++){
                for(int j=0;j<vectorWidth;j++){
                    d[ij][j]/= count;
                    steps[i][ij][j]=round(d[ij][j],1);
                }
            }   
        }
    }
    
    public static void copyAvg(){
        for(int i=0;i<numOfSteps;i++){
            oldavg[i] = avg[i];
        }
    }
    
    public static boolean equalAvg(){
        for(int i=0;i<numOfSteps;i++){
            if (avg[i] != oldavg[i]){
                return false;
            }
        }
        return true;
    }
    
    public static void copySteps(){
        for(int i=0;i<numOfSteps;i++){
            for(int ii=0;ii<vectorHeight;ii++){
                for(int jj=0;jj<vectorWidth;jj++){
                    oldsteps[i][ii][jj] = steps[i][ii][jj];
                }
            }
        }
    }
    
    public static boolean eq(int k){
        for(int i=0;i<vectorHeight;i++){
            for(int j=0;j<vectorWidth;j++){
                if (steps[k][i][j] != oldsteps[k][i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean equals(){
        for(int i=0;i<numOfSteps;i++){
            if (!eq(i)){
                return false;
            }
        }
        return true;
    }
    
    public static void confirmAvg(){
        do{
            copySteps();
            nearestVec();
            reCalcAvg();
        }while(!equals());
    }
    
    public static void genVecs(){
        for(int i=0;i<main.height;i+=vectorHeight){
            for(int j=0;j<main.width;j+=vectorWidth){
                for(int ii=0;ii<vectorHeight;ii++){
                    for(int jj=0;jj<vectorWidth;jj++){                        
                        vectors[numOfVectors][ii][jj] = main.pixels[ii+i][jj+j];
                    }
                }
                numOfVectors++;
            }
        }
    }
    
    public static void split1(){
        double tmp[][][] = new double [9999][vectorHeight][vectorWidth];
        int k=0;
        for(int i=0;i<numOfSteps;i++){
            for(int ii=0;ii<vectorHeight;ii++){
                for(int jj=0;jj<vectorWidth;jj++){
                    double prev = Math.floor(steps[i][ii][jj]); // floor
                    double next = Math.ceil(steps[i][ii][jj]); // ceil
                    tmp[k][ii][jj] = prev;
                    tmp[k+1][ii][jj] = next;
                }
            }
            k+=2;
        }
        numOfSteps *= 2;
        for(int i=0;i<numOfSteps;i++){
            for(int ii=0;ii<vectorHeight;ii++){
                for(int jj=0;jj<vectorWidth;jj++){
                    steps[i][ii][jj] = tmp[i][ii][jj];
                }
            }
        }
        nearestVec();
        reCalcAvg();
    }
    
    public static void split(){
        while(numOfSteps < numOfV){
            split1();
        }
    }
    
    public static double distance(double v1[][] , int v2[][]){
        double all = 0.0;
        for(int i=0;i<vectorHeight;i++){
            for(int j=0;j<vectorWidth;j++){
                all+=Math.pow( Math.abs( v2[i][j] - v1[i][j] ) , 2);
            }
        }
        return Math.sqrt(all);
    }
    
    public static void nearestVec(){
        for(int i=0;i<numOfVectors ; i++){
            double min=Double.MAX_VALUE;
            int idx = 0;
            for(int j = 0 ; j<numOfSteps;j++){
                double mn = distance(steps[j] , vectors[i]);
                if (mn < min){
                    min = mn;
                    idx = j;
                }
            }
            nearest[i] = idx;            
        }
    }
    
    public static void setVecs(){
        int k = 0;
        for(int i=0;i<main.height;i+=vectorHeight){
            for(int j=0;j<main.width;j+=vectorWidth){
                for(int ii=0;ii<vectorHeight;ii++){
                    for(int jj=0;jj<vectorWidth;jj++){
                        main.pixels[ii+i][jj+j] =  (int)steps[nearest[k]][ii][jj];
                    }
                }
                k++;
            }
        }
    }
    

    public static boolean checkVectorSize(int width , int height){
        double tmp = ((main.width*main.height)/(vectorHeight*vectorWidth));
        int tmp2 = (int) tmp;
        if (tmp == tmp2){
            return true;
        }
        return false;
    }
}