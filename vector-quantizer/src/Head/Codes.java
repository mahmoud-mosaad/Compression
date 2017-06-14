package Head;

import java.util.HashMap;
public class Codes {

    
    // code vars
    //static Vector<String> codes = new Vector<String>();
    static String codes [] = null;
    static int numOfCodes = 0;
    static HashMap<String ,  double[][]> codebook = null;
    static byte codedata[] = null;
    
    public static void init(){
        System.out.println("init Codes");
        numOfCodes = 0;
        codes = new String [main.q.numOfV];
        codebook = new HashMap<String , double[][]>();
        codedata = new byte[((main.width*main.height)/(main.q.vectorHeight*main.q.vectorWidth))*main.q.numOfBits];   
    }
    
    public static void setCodeDataI(int i , byte v){
        codedata[i] = v;
    }
    
    public static void swapStr(int i , int j){
        String tmp = codes[i];
        codes[i] = codes[j];
        codes[j] = tmp;
    }
    
    public static boolean sorted(String v[]){
        for(int i=1;i<numOfCodes;i++){
            if (v[i].compareTo(v[i-1]) < 0)
                return false;
        }
        return true;
    }
    
    public static void sort(){
        while(!sorted(codes)){
            for(int i=1;i<numOfCodes;i++){
                if (codes[i].compareTo(codes[i-1]) < 0){
                    swapStr(i , i-1);
                }
            }
        }
    }
    
    public static void code(int bits , String code){
        if(code.length() == bits){
            // add to codes array
            codes[numOfCodes++] = code;
            // exit
            return ;
        }
        code(bits , "0"+code);
        code(bits , "1"+code);
    }
    
    public static void codeVector(){
        code(main.q.numOfBits , "");
        sort();
        for(int i=0;i<main.q.numOfSteps;i++){
            codebook.put(codes[i], main.q.steps[i]);
        }
    }
    
    public static void convertImage(){
        int u=0;
        for(int i=0;i<main.q.numOfVectors;i++){
            for(int j=0;j<codes[main.q.nearest[i]].length();j++){
                if (codes[main.q.nearest[i]].charAt(j) == '0')
                    codedata[u++] = 0;
                else
                    codedata[u++] = 1;
            }
        }
    }
        
}
