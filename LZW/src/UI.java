import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class UI extends javax.swing.JFrame {

     
    static List<Integer> arr = new ArrayList<Integer>();
    static List<Integer> tags = new ArrayList<Integer>();
    static List <Character> arrchar = new ArrayList<Character>();
       
    static HashMap< Integer, ArrayList<Integer> > idxmap = new HashMap< Integer ,ArrayList<Integer> >();
    static HashMap< ArrayList<Integer>,Integer> mapidx = new HashMap< ArrayList<Integer>,Integer>();
    

    static String spw;
    static String scw;
    static int pw;
    static int cw;
    
    
    static int o=0 , p=0;
    static String path1;
    static String path2;
    
    
    public static boolean match(int beg , int end , int beg2){
        for(int j=0;j<=end-beg;j++){
            if (arr.get(beg+j) != arr.get(beg2+j))
                return false;
        }
        return true;
    }
    
    static int idx1 , idx2;
    
    public static boolean exist(int beg , int end){
        int gg = beg;
        for(int i=gg-(end-beg+1);i>=0;i--)
        {
            if (arr.get(i) == arr.get(beg)){
                if (match(beg , end , i)){
                    idx1 = i;
                    idx2 = i+(end-beg);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean exist0(int beg){
        int gg = beg;
        for(int i=gg-1;i>=0;i--)
        {
            if (arr.get(i) == arr.get(beg)){
                return true;
            }
        }
        return false;
    }
    
    
    public static int end(int pos , int en , int ch){
        int uuu = pos;
        for(int i=pos ; i<en ; i++)
        {
            if (arr.get(i) == ch){
                uuu = i;
            }
        }
        return uuu;
    }
    
    public static ArrayList<Integer>list(int beg , int end){
        ArrayList<Integer> a = new ArrayList<Integer>();
        for(int i=beg;i<=end;i++){
            a.add(arr.get(i));
        }
        return a;
    }
    
    public static ArrayList<Integer>list2(int beg){
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(beg);
        return a;
    }
    
    
    
    public static void Compression(){
        int last = 128;
        for(int i=0;i<arr.size();i++){
            if (!mapidx.containsKey(list(i,i))){
                mapidx.put(list(i,i), arr.get(i));
                idxmap.put(arr.get(i), list(i,i));
                System.out.print(list(i,i)+" : ");
                System.out.println(
                    list(i,i)
                );
            }
        }
        int ll=0;
        for(int i=0;i<arr.size();i++){
            for(int j = i;j<arr.size();j++){
                ArrayList<Integer> a = list(i , j);
                if (!mapidx.containsKey(a)){
                    ll = j;
                    mapidx.put(a, last);
                    idxmap.put(last, a);
                                    System.out.print(mapidx.get(list(i,j))+" : ");
                System.out.print(
                    list(i,j)
                );
                 
                    tags.add(mapidx.get(list(i , j-1)));
                    last++;
                    i=j-1;
                    break;
                }
            }
        }
        
        System.out.println(tags);
    }
   
    public static void DeCompression(){
        System.out.println(tags);

        int last = 128;
        for(int i=0;i<=127;i++){        
            mapidx.put(list2(i), i);
            idxmap.put(i, list2(i));                  
        }
        
        arr.add(tags.get(0));
        for(int i=1;i<tags.size();i++)
        {
            System.out.println(i);
            if (tags.get(i) < 128){
                arr.add(tags.get(i));
                
                ArrayList <Integer>aa = new ArrayList<Integer>();
                
                aa.add(tags.get(i-1));
                aa.add(tags.get(i));
                
                System.out.println(aa);
                
                if (!mapidx.containsKey(aa))
                {
                    mapidx.put(aa , last);
                    idxmap.put(last, aa);
                    last++;
                }
                
                
            }
            else
            {
            
                ArrayList<Integer> a = idxmap.get(tags.get(i));

                if (a!= null && mapidx.containsKey(a)){

                    for(int o=0;o<a.size();o++){
                        arr.add(a.get(o));
                    }

                    ArrayList<Integer>aa = idxmap.get(tags.get(i-1));
                    aa.add(a.get(0));

                    System.out.println(aa);

                    if (!mapidx.containsKey(aa))
                    {
                        mapidx.put(aa , last);
                        idxmap.put(last, aa);
                        
                        System.out.println(idxmap.get(last));
                        
                        last++;
                    }
                }else{
                    
                    ArrayList<Integer> b = idxmap.get(tags.get(i-1));
                    ArrayList<Integer>aa = idxmap.get(tags.get(i-1));
                    aa.add(b.get(0));

                    for(int o=0;o<aa.size();o++){
                        arr.add(aa.get(o));
                    }
                    
                    

                        mapidx.put(aa , last);
                        idxmap.put(last, aa);
                        last++;
                    
                    
                }
            }
        }
        System.out.println(arr);
                
    }
    
    public static void displayList(List<Character> o){
        for(int i=0;i<o.size();i++)
        {
            System.out.print(o.get(i)+" ");
        }
        System.out.println();
    }
    
    public static boolean getExtension(String h){
        String ex="";
        for(int i = h.length()-1; i>=0;i--)
        {
            if (h.charAt(i) == '.')
                break;
            ex = h.charAt(i) + ex;
        }
        if (ex.equals("lzw")){
            return true;
        }
        else{return false;}
    }
    
    public static void readFromFile(String path) throws FileNotFoundException, IOException, ClassNotFoundException{    
        boolean  bbb = getExtension(path);
        if (bbb == false){
                BufferedReader br;  
                String read;
                br = new BufferedReader(new FileReader(path));
                while ((read = br.readLine()) != null) {
                    for(int i = 0 ; i<read.length();i++)
                    {
                        arr.add( (int) read.charAt(i));
                    }
                    arr.add((int) '\n');                    
                }
                o = arr.size();
                br.close();
        }
        else if (bbb == true){
                read( path);
                p=tags.size();
        }
    }
    
    private static void store(List<Integer> ints , String path) {
      ObjectOutputStream out = null;
      try {
        out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(ints);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        safeClose(out);
      }
    }
    
    private static void storech( String path) throws IOException {
        FileWriter writer = new FileWriter(new File(path)); 
        for(int kkkk = 0;kkkk<arr.size()-1;kkkk++){
            if (kkkk != 0){
                if (arr.get(kkkk) == 10)
                       writer.append(System.getProperty("line.separator"));
                else{
                    int one = arr.get(kkkk);
                    char two = (char)one;
                    writer.append(two);
                }
            }
            else
            {
                if (arr.get(kkkk) == 10)
                       writer.write(System.getProperty("line.separator"));
                else{
                    int one = arr.get(kkkk);
                    char two = (char)one;
                    writer.write(two);
                }
            }
        }
        writer.close();
    }
    
    private static void read( String path) throws ClassNotFoundException {
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(path));
            List<Integer> l = (ArrayList<Integer>)in.readObject();
            for(int i=0;i<l.size();i++)
            {
                tags.add(l.get(i));
            }
            in.close();
         }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void safeClose(OutputStream out) {
       try {
        if (out != null) {
            out.close();
        }
        } catch (IOException e) {
         // do nothing
        }
    }
    
    public static void Decode(){
        for(int i=0;i<arr.size();i++)
        {
            int y = arr.get(i);
            char yy = (char)y;
            arrchar.add(yy);
        }
    }
    
    public static void writeToFile(String path) throws IOException{
            if (getExtension(path) == true){
                store(tags , path);
            }
            else{
                Decode();
                storech( path);
            }
    }
    
    
    public UI(){
        initComponents();
        init();
    }
    
    private void init (){
        setTitle("LZW Here");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel1.setText("Browse File First");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addGap(73, 73, 73))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DeCompression();
        try {
            writeToFile(path2);
            arr.clear();
            tags.clear();
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
               Compression();
        try {
            writeToFile(path1);
            tags.clear();
            arr.clear();
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser(); 
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal ==  JFileChooser.APPROVE_OPTION) {
            try {
                readFromFile(fileChooser.getSelectedFile().getAbsolutePath().toString());
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            path1 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".lzw" ;
            path2 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".txt" ; 
            jLabel1.setText(fileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
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
        new UI();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
