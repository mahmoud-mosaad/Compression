package Head;

import java.io.FileNotFoundException;
import java.io.IOException;

public class main {
    
    // image vars
    static int width = 0;
    static int height = 0;
    static int pixels [][] = null;    // read image here
    static GUI gui;
    static Quantizer q = null;
    static deQuantizer deQ = null;
    static Codes c = null;
    static RW files = null;
    
    
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        gui = new GUI();
        
    }
    
    public static void init(){
        System.out.println("init main");
        pixels = new int [height][width];
        q = new Quantizer();
        deQ = new deQuantizer();
        c = new Codes();
        files = new RW();
    }
    
    public static void Compression() throws IOException{
        System.out.println("readImage Begin");

        pixels = files.readImage(gui.getPath());
        System.out.println("readImage Done");

        q.quantize();
        gui.updateAllP(10);
        System.out.println("writeToFile Begin");
        files.writeToFile(gui.getPath3());
        System.out.println("WriteToFile Done");
        
        /////
        //files.writeImage(pixels , gui.getPath2() , width , height);   // for test
        //gui.updateAllP(10);
        //System.out.println("WriteImage Done");
        gui.setNews("Done");
    }
    
    public static void DeCompression() throws IOException{
        System.out.println("readFromFile Begin");

        files.readFromFile(main.gui.getPath());
        System.out.println("readFromFile Done");
        System.out.println("deQuantize Begin");

        deQ.deQuantize();
        System.out.println("deQuantize Done");

        main.gui.updateAllP(50);
        gui.getPath2();
        System.out.println("writeImage Begin");

        files.writeImage(pixels , gui.getPath2() , width , height); 
        System.out.println("writeImage Done");

        main.gui.updateAllP(50);
        gui.setNews("Done");
    }
    
    public static void setPixels(int i, int j , int v){
        pixels[i][j] = (int) v;
    }
    
    public static int getPixels(int i , int j){
        return pixels[i][j];
    }
    
    public static void setWidth(int w){
        main.width = w;
    }
    
    public static int getWidth(){
        return main.width;
    }
    
    public static void setHeight(int h){
        main.height = h;
    }
    
    public static int getHeight(){
        return main.height;
    }
}
