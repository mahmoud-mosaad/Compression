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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
public class UI extends javax.swing.JFrame {
    
    static List<Integer> arr = new ArrayList<Integer>();
    static List<Integer> tags = new ArrayList<Integer>();
    static List <Character> arrchar = new ArrayList<Character>();
       
    
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
    
    
    public static void Compression(){
        int k , kk , ll=0 ;
        for( k=0;k< o ;k++)
        {
            if (!exist0(k)){   
                tags.add(0);p++;
                tags.add(0);p++;
                tags.add(arr.get(k));p++;
            }
            else{
                for( kk=k+1;kk< o -1;kk++)
                {
                    if ( (kk < o-2) &&  (arr.get(kk) != arr.get(kk+1)) )
                    {
                      if (!exist(k , kk))
                      {
                            tags.add(k-idx1);p++;
                            tags.add( kk-k);p++;
                            tags.add( arr.get(kk));p++;
                            ll=k;
                            k = kk+1;
                      }
                    }
                }
                if (exist(k , kk-1))
                {
                    if (exist(k , kk-2))
                    {
                            tags.add(k-idx1);p++;
                            tags.add(kk-k-1);p++;
                            tags.add(arr.get(kk-1));p++;
                            ll=k;
                            k = kk+1;
                    }
                }
            }
        }
        if (exist(ll+1 , arr.size()-1)){
            tags.add((ll+1)-idx1);p++;
            tags.add(arr.size()-(ll+1));p++;
            tags.add(0);p++;
        }
        System.out.println(tags);
    }
    
    public static void DeCompression(){
        int j=0,i=0;
        for( i=0;i<p-3;i+=3)
        {
            if (tags.get(i) == 0){
                arr.add(tags.get(i+2));
                j++;
            }
            else 
                break;
        }
        for(;i<p;i+=3)
        {
            for(int u=0;u<tags.get(i+1);u++)
            {
                arr.add(arr.get(j-tags.get(i)));
                j++;
            }
            arr.add(tags.get(i+2));
            j++;
        }
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
        if (ex.equals("lz77")){
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
        for(int kkkk = 0;kkkk<arr.size()-2;kkkk++){
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
            //System.out.println(yy);
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
        setTitle("LZ77 Here");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Browse File First");

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
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(81, 81, 81))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            path1 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".lz77" ;
            path2 =  fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName() +".txt" ; 
            jLabel1.setText(fileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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

    
    public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {
        

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
