package Head;

public class deQuantizer {
        
    deQuantizer(){}
    
    public static void deQuantize(){
        getPixels();
    }
    public static void getPixels(){

        int kw = 0 , kh=0 ;
        int end = main.q.numOfBits*((main.getWidth()*main.getHeight())/(main.q.vectorHeight*main.q.vectorWidth));
        for(int i = 0;i<end;i+=main.q.numOfBits){
            
            if (kw == main.width){
                kw = 0;
                kh+=main.q.vectorHeight;
            }
            
            String h = "";
            for(int j = i ; j<i+main.q.numOfBits;j++){
                if (main.c.codedata[j] == 1){
                    h+="1";
                }else{
                    h+="0";
                }
            }
            
            double d[][] = new double [main.q.vectorHeight][main.q.vectorWidth];

            d = main.c.codebook.get(h);
            
            for(int ii=0;ii<main.q.vectorHeight;ii++){
                for(int jj=0;jj<main.q.vectorWidth;jj++){
                    main.setPixels(kh+ii , kw+jj , (int) d[ii][jj]);
                }
            }
            
            kw+=main.q.vectorWidth;
        }
    }
}
