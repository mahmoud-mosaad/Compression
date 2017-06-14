
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class UI extends javax.swing.JFrame {
    ////////////////////////////////////////////////////
    //Globals
    
    static int numOfV = 64;
    static int pixels [][] = new int [9999][9999];
    static int near [][] = new int [9999][9999];
    static int pixels2[][] = new int [9999][9999];
    static int width = 512;
    static int height = 512;
    static double steps [] = new double [1000];
    static int numOfSteps = 1;
    static double reconstruct[] = new double [1000];
    static double range[] = new double [1000];
    static double Q[] = new double [1000];
    static double fullScale = 127.0;
    static String path;
    ////////////////////////////////////////////////////
    // Functions
    
     public static double getAvg(){
        double avg = 0.0;
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    avg += pixels[i][j];
                }
            }
        
        return avg / (width*height);
    }
    
    public static void halfAvg(int idx){
        double sum = 0.0;
        double count = 0.0;
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if (near[i][j] == idx){
                    sum += pixels[i][j];
                }
            }
        }
        steps[idx] = (count!=0.0)?sum/count:0.0;
    }
     
    public static void split1(){
        double tmp[] = new double [1000];
        int k=0;
        System.out.println("steps"+numOfSteps);
        for(int i=0;i<numOfSteps;i++,  k+=2){
            double prev = Math.round(steps[i]) -1.0;
            double next = Math.round(steps[i]) +1.0;
            tmp[k] = prev;
            tmp[k+1] = next;
        }
        
        numOfSteps =k;
        for(int i=0;i<k;i++){
            steps[i] = tmp[i];
        }
        
        for(int i=0;i<k;i++){
            System.out.println("------+"+steps[i]);
        }
        
        
        
        nerestVec();
        
        
        for(int i=0;i<numOfSteps;i++){
            halfAvg(i);
        }
        
    }
    
    public static void split(){
        while(numOfSteps < numOfV){
            split1();
        }
        confirmAvg();
    }
    
    public static double distance(double v1 , int v2){
        return Math.abs(v1-v2);
    }
    
    public static void nerestVec(){
        for(int i=0;i<width ; i++){
            for(int ii=0;ii<height;ii++){
                double min=Double.MAX_VALUE;
                int idx = 0;
                for(int j = 0 ; j<numOfSteps;j++){
                    double mn = distance(steps[j] , pixels[i][ii]);
                    if (mn < min){
                        min = mn;
                        idx = j;
                    }
                }
                near[i][ii] = idx;
            }
        }
    }
    
    public static boolean equal(double a[] , double b[] , int size){
        for(int i=0;i<size;i++){
            if(Math.round(a[i])!=Math.round(b[i]))
                return false;
        }
        return true;
    }
    
    public static void copy(double a[] , double b[] , int size){
        for(int i=0;i<size;i++){
            b[i] = a[i];        
        }
    }
    
    
    public static void confirmAvg(){
        System.out.println("+++++++");
        double tmp[] = new double [1000];
        double count [] = new double [1000];
        double step [] = new double [1000];
        for(int ii=0;ii<width;ii++){
            for(int jj=0;jj<height;jj++){
                count[near[ii][jj]]++;
                tmp[near[ii][jj]]+=pixels[ii][jj];
            }
        }
        for(int i=0 ; i<numOfSteps;i++){
            step[i] =(count[i]!=0)? tmp[i]/count[i]:0.0;
            System.out.print(step[i]+" ");
        }
        if(!equal(step , steps , numOfSteps))
        {
            copy(step , steps , numOfSteps);
            nerestVec();
            confirmAvg();
        }else
            return ;
    }

    public static void makeQ(){
        range [0] = 0.0;
        range[numOfSteps] = fullScale;
        int k = 0;
        for(int i=0;i<numOfSteps-1;i++){
            reconstruct[k] = steps[k];
            range[i+1] = (steps[i]+steps[i+1])/2;
            Q[k] = k;
            k++;
        }
        reconstruct[k] = steps[k];
        Q[k] = k;
        
    }
    
    
    public static void freeQ(){
        for(int i=0;i<numOfSteps;i++){
            System.out.println("const + "+reconstruct[i]);
        }
        
        range [0] = 0.0;
        range[numOfSteps] = fullScale;
        for(int i=0;i<numOfSteps-1;i++){
            range[i+1] = (reconstruct[i]+reconstruct[i+1])/2;
        }
        for(int i=0;i<numOfSteps;i++){
                  Q[i] = i;
        }
    }
    
    public static void reconstruct(){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                /*for(int ii=0;ii<=numOfSteps;ii++){
                    if (pixels[i][j] <= range[ii+1] && pixels[i][j] >= range[ii]){
                        pixels2[i][j] = (int)Q[ii];
                        break;
                    }
                }*/
                pixels[i][j] = (int)Q[near[i][j]];
            }
        }
    }
    
    
    public static void reconstruct2(){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                    pixels2[i][j] = (int)reconstruct[pixels2[i][j]];
            }
        }
    }
    
    public static void quantize(){
        steps[0] = getAvg();
        split();
        makeQ();
        reconstruct();
    }
    
    public static void deQuantize(){
        freeQ();
        reconstruct2();
    }
    
    public  static void Compression() throws IOException{
        quantize();
        System.out.println("Successfully Compression");
    }
    
    public  static void DeCompression(){
        deQuantize();
        System.out.println("Successfully DeCompression");
    }
    
    public static void clear(){
        for(int i=0;i<1000;i++)
            steps [i] = -1.0;
        numOfSteps = 0;
        for(int i=0;i<1000;i++)
            reconstruct[i] = -1.0;
        for(int i=0;i<1000;i++)
            range[i] = -1.0;
        for(int i=0;i<1000;i++)
            Q[i] = -1.0;
        path = "";
    }
    
    // read write image
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
    
    public static boolean getExtension(String h){
        String ex="";
        for(int i = h.length()-1; i>=0;i--)
        {
            if (h.charAt(i) == '.')
                break;
            ex = h.charAt(i) + ex;
        }
        if (ex.equals("compress")||ex.equals("quant"))
            return true;
        else
            return false;
    } 
    
    ////////////////////////////////////////////////////
    // Files
    
    private static void writeArr1D( String path) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(path));
        for(int i=0;i<numOfSteps;i++){
            System.out.println(reconstruct[i]);
            outputWriter.write((int)reconstruct[i]); 
        }
        outputWriter.flush();  
        outputWriter.close(); 
    }
    
    private static void writeArr2D( String path) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(path));
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                   outputWriter.write(pixels2[i][j]); 
            }
        }
        outputWriter.flush();  
        outputWriter.close(); 
    }
   
    private static void readArr1D( String path) throws IOException {
        BufferedReader inputReader = null;
        inputReader = new BufferedReader(new FileReader(path));
        numOfSteps = 0;
        for(int i=0;i<1000;i++){
            int tmp = inputReader.read();
            if(tmp == -1.0){
                break;
            }
            reconstruct[i] = tmp;
            numOfSteps++;
        }
        
        inputReader.close(); 
    }
    
    private static void readArr2D( String path) throws IOException {
        BufferedReader inputReader = null;
        inputReader = new BufferedReader(new FileReader(path));
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                pixels2[i][j] = inputReader.read();
            }
        }  
        inputReader.close(); 
    }
    
    public static void readFromFile(String path) throws IOException, FileNotFoundException, ClassNotFoundException{
        readArr1D(path+"\\reconstruct.quant");
        readArr2D(path+"\\pixels.compress");
    }
    
    public static void writeToFile(String path) throws IOException{
        writeArr1D(path+"\\reconstruct.quant");
        writeArr2D(path+"\\pixels.compress");
    }
    
    //////////////////////////////////////////////////////////////////////
    // GUI
    
    
    public UI(){
        initComponents();
        init();
    }
    
    private void init (){
        setTitle("Scalar Quantization Here");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Browse File To Compress");

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Compress");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("DeCompress");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(61, 61, 61))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser(); 
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal ==  JFileChooser.APPROVE_OPTION) {
           // path1 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".huff" ;
           // path2 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".txt" ;
           // path3 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".map" ;
           
           path = fileChooser.getCurrentDirectory().getAbsolutePath();
           try {
                if (getExtension(fileChooser.getSelectedFile().getName()) == false){
                    pixels = readImage("F:\\testMM\\lena.jpg");
                    System.out.println("Image");
                }else{
                    System.out.println(path);
                    readFromFile(path);
                }
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            jLabel1.setText(fileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Compression();
            writeToFile(path);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DeCompression();
        writeImage(pixels2 , "F:\\testMM\\lena2.jpg" , width , height);
        clear();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        new UI();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
