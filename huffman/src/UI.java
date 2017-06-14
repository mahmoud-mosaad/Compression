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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

class Node {
    String key;
    int prob;
    String code;

    Node leftChild;
    Node rightChild;
    Node(String key, int prob) {
            this.key = key;
            this.prob = prob;
            this.code = "";
    }
    Node(){
            this.key = "";
            this.prob = 0;
            this.code = "";
    }
}

class BinaryTree {
    Node root;
}

public class UI extends javax.swing.JFrame {

    static BinaryTree tree = new BinaryTree();
    static List<Integer> arr = new ArrayList<Integer>();
    static String path1;
    static String path2;
    static String path3;
    static String datacode="";
    static HashMap< String,Integer> freq = new HashMap< String,Integer>();
    static int size = 0;
    static int a[] = new int [1000001];
    static String c[] = new String [1000001];
    static HashMap< String,Integer> cidx = new HashMap< String,Integer>();
    static HashMap< Integer,String> idxc = new HashMap<Integer,String>();
    static HashMap<String,Boolean> took = new HashMap< String,Boolean>();
    static HashMap<String , Boolean> isNode = new HashMap<String , Boolean>();
    static HashMap<String , Node> getNode = new HashMap<String , Node>();
    static HashMap<String , String> codeword = new HashMap<String , String>();
    static HashMap<String , String> wordcode = new HashMap<String , String>();
    
    public static void clear(){
        tree = new BinaryTree();
        arr.clear();
        datacode="";
        freq.clear();
        size = 0;
        a = new int[1000001];
        c =  new String [1000001];
        cidx.clear();
        idxc.clear();
        took.clear();
        isNode.clear();
        getNode.clear();
        codeword.clear();
        wordcode.clear();
    }
    
    ////////////////////////////////
    // Algorithm functions
    public static String sch(char a){ String h="";h+=a;return h; }
    public static char ich(int x){return (char)x;}
    public static void count(){
        for(int i=0;i<arr.size()-1;i++){
            if (!freq.containsKey(sch(ich(arr.get(i)))))
                freq.put(sch(ich(arr.get(i))), 1);
            else{
                freq.replace(sch(ich(arr.get(i))), freq.get(sch(ich(arr.get(i)))), freq.get(sch(ich(arr.get(i))))+1) ;
            }   
        }
        Set< Map.Entry< String,Integer> > st = freq.entrySet();    
        for(Map.Entry< String,Integer> me:st)
        {
            cidx.put(me.getKey(), size);
            idxc.put(size,me.getKey());   
            a[size] = me.getValue();
            c[size++] = me.getKey();
        }
    }
    
    public static int getMin(){
        int mn = Integer.MAX_VALUE;
        int idx = -1;
        for(int i=0;i<size;i++)
            if (!took.containsKey(idxc.get(i)) && a[i] < mn){
                mn = a[i];
                idx = i;
            }
        took.put(idxc.get(idx), true);
        return idx;
    }
    
    public static void dfs(Node t , String curcode){
        if (t == null)
            return ;
        if (t.key.length() == 1){
            codeword.put(t.key, curcode);
            wordcode.put(curcode , t.key);
        }
        dfs(t.leftChild , curcode+"1");
        dfs(t.rightChild , curcode+"0");   
    }
    
    public static void convert(){
        for(int i=0;i<arr.size();i++)
            datacode+= ( codeword.get(sch(ich(arr.get(i)))) );
    }
    
    static boolean end = false;
    
    public static void Compression(){
        count();
        while(end != true)
           addNode(); 
        end = false;
        dfs(tree.root , "");
        convert();
        // display codeword;
        Set< Map.Entry< String,String> > st = codeword.entrySet();    
        for(Map.Entry< String,String> me:st)
        {
            System.out.print("   "+me.getKey()+" : "+me.getValue()+"\n");
        }
         // end of display    
    }
    
    
    public static void convert2(){
        for(int i=0;i<come.length();i++)
            arr.add((int)come.charAt(i));
    }
    
    static String come = "";
    
    public static void DeCompression(){
        String tmpstr = datacode;        
        Set< Map.Entry< String,String> > st = codeword.entrySet();    
        for(Map.Entry< String,String> me:st)
            addNodeNormal(me.getValue());
        
        while(true){
            int idx = search(tmpstr);
            if (idx > 0)
                tmpstr = tmpstr.substring(idx, tmpstr.length());
            else
                break;
        }
        System.out.println(come);
        convert2();
    }
   
    ///////////////////////////////////////////////////
    // files functions

    
    public static String removeExtension(String h){
        int end=h.length();
        for(int i = h.length()-1; i>=0;i--)
        {
            if (h.charAt(i) == '.'){
                end = i;
                break;
            }
        }
        return h.substring(0, end);
    } 
    

    public static boolean getExtension(String h){
        String ex="";
        for(int i = h.length()-1; i>=0;i--)
        {
            if (h.charAt(i) == '.')
                break;
            ex = h.charAt(i) + ex;
        }
        if (ex.equals("huff")||ex.equals("map")){
            return true;
        }
        else{return false;}
    } 
    
    private static void writeMap(HashMap<String , String> ints , String path) {
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
    
    private static void writeCode( String path) throws IOException {
        FileWriter writer = new FileWriter(new File(path));
        writer.write(datacode);
        writer.close();
    }
        
    private static void writeArr( String path) throws IOException {
        FileWriter writer = new FileWriter(new File(path)); 
        for(int kkkk = 0;kkkk<arr.size()-1;kkkk++){
            if (kkkk != 0){
                if (arr.get(kkkk) == 10)
                       writer.append(System.getProperty("line.separator"));
                else{
                    int one = arr.get(kkkk);
                    writer.append((char)one);
                }
            }
            else
            {
                if (arr.get(kkkk) == 10)
                       writer.write(System.getProperty("line.separator"));
                else{
                    int one = arr.get(kkkk);
                    writer.write((char)one);
                }
            }
        }
        writer.close();
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
    
    private static void readMap( String path) throws ClassNotFoundException {
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(path));
            codeword = (HashMap<String , String>)in.readObject();
            in.close();
         }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        copyMap();
    }
    
    public static void copyMap(){
                
        Set< Map.Entry< String,String> > st = codeword.entrySet();    
        for(Map.Entry< String,String> me:st)
        {
            wordcode.put(me.getValue(), me.getKey());
        }
    }
    
    public static void readArr(String path) throws FileNotFoundException, IOException, ClassNotFoundException{    
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
        br.close();
    }
    
    public static void readCode(String path) throws FileNotFoundException, IOException, ClassNotFoundException{    
        BufferedReader br;  
        br = new BufferedReader(new FileReader(path));
        datacode = br.readLine();
        datacode = datacode.substring(0, datacode.length()-4);
        br.close();
    }
    
    public static void readFromFile(String path) throws IOException, FileNotFoundException, ClassNotFoundException{
        if (getExtension(path)==true){
            readCode(path1);
            readMap(path3);
        }
        else
        {
            readArr(path2);
        }
    }
    ////////////////////////////////////////////////
    // Tree converted functions
    
    static boolean chk , roott;        

    public static Node makeNode(){
        int one = getMin();
        if (one == -1){
            return null;
        }
        makeRootChk(one);
        if (isNode.containsKey(idxc.get(one))){
            return getNode.get(idxc.get(one));
        }
        Node newNode = new Node(idxc.get(one), freq.get(idxc.get(one)));
        took.put(idxc.get(one), true);
        return newNode;
    }
    
        
    public static void makeRootChk(int idx){
        if (isNode.containsKey(idxc.get(idx))){
            chk = true;
            if (getNode.get(idxc.get(idx)).key == tree.root.key && getNode.get(idxc.get(idx)).prob == tree.root.prob){
                roott =true;
            }
        }
        else{
            chk = false;
            roott = false;
        }
    }
    
    public static void resetChk(){
        chk = false;
        roott = false;
    }
    
    
    public static Node makeRoot0(Node aa, Node b){
        Node root2;
        String newew = aa.key+b.key;
        int freqeq = freq.get(aa.key)+freq.get(b.key);
        root2 = new Node(newew, freqeq);
        root2.rightChild = b;
        root2.leftChild = aa;
        
        freq.put(newew, freqeq);
        cidx.put(newew, size);
        idxc.put(size,newew);      

        isNode.put(newew, true);
        getNode.put(newew , root2);
        
        a[size] = freqeq;
        c[size++] = newew;
        return root2;
    }
    
    public static Node makeRoot(Node aa , Node b){
        Node root2;
        String newew = aa.key+b.key;
        int freqeq = freq.get(aa.key)+freq.get(b.key);
        root2 = new Node(newew, freqeq);
        root2.rightChild = b;
        root2.leftChild =aa;
        
        
        freq.put(newew, freqeq);
        cidx.put(newew, size);
        idxc.put(size,newew);      

        isNode.put(newew, true);
        getNode.put(newew , root2);
        
        a[size] = freqeq;
        c[size++] = newew;
        return root2;
    }   
    public static void noneMakeRoot(Node aa , Node b){
        Node root2;
        String newew = aa.key+b.key;
        
        int freqeq = freq.get(aa.key)+freq.get(b.key);
        root2 = new Node(newew, freqeq);
        root2.rightChild = b;
        root2.leftChild = aa;
        
        freq.put(newew, freqeq);
        cidx.put(newew, size);
        idxc.put(size,newew);

        isNode.put(newew, true);

        getNode.put(newew , root2);

        a[size] = freqeq;
        c[size++] = newew;
    }
    
    public static void addNode() {
        if (tree.root == null) {
            Node an = makeNode();
            Node bn = makeNode();
            tree.root = makeRoot0(an , bn);
        } 
        else {
            boolean a,aa,b,bb;
            Node one = makeNode();
            a = chk;
            aa = roott;
            resetChk();
            Node two = makeNode();
            b = chk;
            bb = roott;
            resetChk();
            if (two == null || one == null){
                end = true;
                return ;
            }
            if (((a == true) && b == true) || (a == true && b == false)||(a == false && b == true)){
                if (aa == true){
                    tree.root = makeRoot(one , two);
                }
                else if (b == true){
                    tree.root = makeRoot(two , one);
                }
                else{
                    noneMakeRoot(one , two);
                }
            }
            else if (a == false && b == false){
                noneMakeRoot(one , two);
            }
        }
    }    

    public static  void addNodeNormal(String h){
        int beg = 0;
        int sz= h.length();
        if (tree.root == null){
            tree.root = new Node();
        }
        Node parent = tree.root;
        while(beg < sz){
            if (h.charAt(beg) == '1'){
                if (parent.leftChild != null){
                    parent = parent.leftChild;
                }else{
                    Node u = new Node();
                    parent.leftChild = u;
                    parent = parent.leftChild;
                }
            }
            else {
                if (parent.rightChild != null){
                    parent = parent.rightChild;
                }else{
                    Node u = new Node();
                    parent.rightChild = u;
                    parent = parent.rightChild;
                }
            }
            if (beg == sz-1 ){
                parent.key = wordcode.get(h);
            }
            beg++;
        }
    }
    
    public static int search(String h){
        int beg = 0;
        int sz= h.length();
        Node parent = tree.root;
        while(beg < sz){
            if (h.charAt(beg) == '1'){
                if (parent.leftChild == null){
                    come+=parent.key;
                    return beg;
                }else{
                    parent = parent.leftChild;
                }
            }
            else{
                if (parent.rightChild == null){
                    come+=parent.key;
                    return beg;
                }else{
                    parent = parent.rightChild;
                }
            }
            if (beg == h.length()-1)
                come+=parent.key;
            beg++;
        }
        return h.length()-1;
    }
    
    
    ////////////////////////////////////////////////
    
    public UI(){
        initComponents();
        init();
    }
    
    private void init (){
        setTitle("Huffman Here");
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

        jLabel1.setText("Browse File First");

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
                        .addContainerGap()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Compression();
        try {
            writeCode(path1);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        writeMap(codeword,path3);
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser(); 
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal ==  JFileChooser.APPROVE_OPTION) {
            path1 =  removeExtension(fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName()) +".huff" ;
            path2 =  removeExtension(fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName()) +".txt" ;
            path3 =  removeExtension(fileChooser.getCurrentDirectory().getAbsolutePath() + "\\"+ fileChooser.getSelectedFile().getName()) +".map" ;

            try {
                readFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            jLabel1.setText(fileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DeCompression();
        try {
            writeArr(path2);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
