
package arithmetic.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArithmeticTest {

    
    //static String data="bbccccccccccccccccccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    static String data ="acba";
    static double value = 0.773;
    static int chnum = 0;
    
    static HashMap<Character , Double> prob = new HashMap<Character , Double>();
    static Character chcomm[] = new Character[30];
    static Double comm[] = new Double[30];
    static Double ocomm[] = new Double[30];
    
    static Double upcomm[] = new Double[30];
    
    public static int search (Character c){
        for(int i = 1 ; i<=chnum;i++)
        {
            if (chcomm[i] == c){
                return i;
            }
        }
        return -1;
    }
    
    public static void putProbs(){
        int idx = 1;
        for(int i=0;i<data.length();i++){
            if ( prob.containsKey(data.charAt(i)) ){
               prob.put(data.charAt(i), prob.get(data.charAt(i)) + 1.0);
            }else{
                chnum++;
                prob.put(data.charAt(i), 1.0);
            }
        }
        Set< Map.Entry< Character,Double> > st = prob.entrySet();    
        for(Map.Entry< Character,Double> me:st)
        {
            chcomm[idx] = me.getKey();
            double d = me.getValue()/data.length();
            
            me.setValue(d);
            
            comm[idx++] = d;
        }
        // make commulative
        comm[0] = 0.0;
        for(int i=1;i<=chnum;i++){
            comm[i] += comm[i-1];
        }
        for(int i = 0 ;i<=chnum;i++)
            ocomm[i] = comm[i];
    }
    
    public static double avg(double x , double y){
        return (x+y)/2.0;
    }
    
    public static int find(double v){
        int i;
        for( i = 0 ; i <= chnum; i++){
            if (ocomm[i] > v)
                return i;
        }
        return i-1;
    }
    
    public static void copyUp(){
       for(int i =0 ; i <= chnum ; i ++){
            comm[i] = upcomm[i];
        }
    }
   
     
    public static void copyUp2(){
       for(int i =0 ; i <= chnum ; i ++){
            comm[i] = upcomm[i];
        }
    }
   
    
    public static void updateComm(double low , double high){
        upcomm[0] = low;
        upcomm[chnum] = high;
        for(int i=1;i<chnum;i++){
            upcomm[i] = (((comm[i] - comm[i-1])/(comm[chnum]- comm[0]))*(high- low))+upcomm[i-1];
            low = upcomm[i];
        }
    }
    
    
    public static void updateComm2(double low , double high){
        upcomm[0] = low;
        upcomm[chnum] = high;
        double loww = low;
        
        for(int i=1;i<chnum;i++){
            upcomm[i] = (((comm[i] - comm[i-1])/(comm[chnum]- comm[0]))*(high- low))+loww;    
            loww = upcomm[i];
        }
    }
    
    
    public static void Compression (){
        double low = 0.0, high = 1.0 ,range = 1.0;
        for(int i = 0;i<data.length();i++){
            int idx = search(data.charAt(i));
            low = comm[idx-1];
            high = comm[idx];
            /*high = low + (range * comm[idx]); 
            low = low + (range * comm[idx-1]); 
            range = high - low;*/
            
            updateComm(low , high);
            copyUp();
        } 
        value = avg(low,high);
    }

    public static void DeCompression (){
        double low = 0.0, high = 1.0 ,range = 1.0; 
        double v = value;
        while (true){
            
            int idx = find (v);
            
            System.out.print(chcomm[idx]);  
            
            if (v < low)
                break;
            
            low = comm[idx-1]; 
            high = comm[idx];

            
            
            
            v = (value - low) / (high-low);
            
            
            updateComm2(low , high);
            copyUp2();
            
        }
    }
    
    public static void resetComm(){
        for(int i=0 ; i<=chnum;i++)
            comm[i] = ocomm[i];
    }
    
    public static void main(String[] args) {
        putProbs();
        Compression();
        
        System.out.println(value);
        
        resetComm();

        DeCompression();
    }
}
