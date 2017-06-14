package Head;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class RW {
    
    
    public static int[][] readImage(String filePath)
    {
	int width=0;
	int height=0;
        File file=new File(filePath);
        BufferedImage image=null;
        try
        {
            image=ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        width=image.getWidth();
        height=image.getHeight();
        int[][] pixels=new int[height][width];

        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                int rgb=image.getRGB(x, y);
                int alpha=(rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;

                pixels[y][x]=r;
            }
        }

        return pixels;
    }
 
    public static void writeImage(int[][] pixels,String outputFilePath,int width,int height)
    {
        File fileout=new File(outputFilePath);
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );

        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
                image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void writeToFile(String path) throws IOException{
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(path));
            
            out.writeInt(main.getWidth());              // write width
            out.writeInt(main.getHeight());              // write height
            
            out.writeInt(main.q.getVecHeight());
            out.writeInt(main.q.getVecWidth());
            
            out.writeInt(main.q.numOfBits);           // numOfBits
            
            out.writeObject(main.c.codebook);   // write codebook
            
            int end = main.q.numOfBits*((main.getWidth()*main.getHeight())/(main.q.vectorHeight*main.q.vectorWidth));
            for(int i=0;i<end;i++){
                out.writeByte(main.c.codedata[i]);
            }
           
        } catch (IOException e) {
          throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }
    
    public static void readFromFile(String path) throws FileNotFoundException, IOException{
       ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(path));
            
            main.setWidth(in.readInt());
            main.setHeight(in.readInt());
            
            main.init();
            
            main.q.setVecHeight(in.readInt());
            main.q.setVecWidth(in.readInt());
            
            main.q.init();
            
            main.q.numOfBits = in.readInt();
            
            main.c.init();
            
            
            main.c.codebook = (HashMap<String , double[][]>)in.readObject();
            
            int end = main.q.numOfBits*((main.getWidth()*main.getHeight())/(main.q.vectorHeight*main.q.vectorWidth));
            for(int i=0;i<end;i++){
                main.c.setCodeDataI(i, in.readByte());
            }
            
            in.close();
         }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static String readExtension(String h){
        String tmp = "";
        for(int i=h.length()-1;i>0;i--){
            if (h.charAt(i) == '.'){
                break;
            }
            tmp = h.charAt(i)+tmp;
        }
        return tmp;
    }
    
    public static String incExtension(String h){
        String tmp = "";
        for(int i=h.length()-1;i>0;i--){
            if (h.charAt(i) == '.'){
                tmp = h.substring(0, i);
                tmp+="+";
                tmp+= ".jpg";
                break;
            }
        }
        return tmp;
    }

    public static String decExtension(String h){
        String tmp = "";
        for(int i=h.length()-1;i>0;i--){
            if (h.charAt(i) == '.'){
                tmp = h.substring(0, i);
                tmp+="-";
                tmp+= ".quantizer";
                break;
            }
        }
        return tmp;
    }
    
    public static boolean isImage(String h){
        String tmp = readExtension(h);
        if (tmp.equals("jpg")){
            return true;
        }
        return false;
    }
    
    public static boolean isQ(String h){
        String tmp = readExtension(h);
        if (tmp.equals("quantizer")){
            return true;
        }
        return false;
    }
    
    public static boolean isVerified(String h){
        if(isImage(h)||isQ(h)){
            return true;
        }
        return false;
    }
    
    public static String prevPath(String h){
        int idx = h.length()-1;
        for(;idx>0;idx--){
            if (h.charAt(idx) == '/' || h.charAt(idx) == '\\'){
                break;
            }
        }
        return h.substring(0, idx);
    } 
}