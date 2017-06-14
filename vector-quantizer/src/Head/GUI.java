
package Head;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class GUI extends javax.swing.JFrame {

    javax.swing.JFrame frame = new javax.swing.JFrame( "FileDrop" );
    boolean visibleDrop = false;
    
    static boolean compression = false;
    static boolean valid = false;
    
    static String pathD;
    static String path;
    
    public void addResponseItems(int width , int height){
        if (width%2==0 && height%2 ==0){
            for(int i=2;i<=width/2;i*=2){
                if(width%i==0){
                    vecWidth.addItem(Integer.toString(i));
                }
            }
            for(int i=2;i<=height/2;i*=2){
                if(height%i==0){
                    vecHeight.addItem(Integer.toString(i));
                }
            }
        }else{
            setNews("Dimensions is odd!!");
        }
    }
    
    public String getPath(){
        return path;
    }

    public String getPath2(){
        return main.files.incExtension(path);
    }
 
    public String getPath3(){
        return main.files.decExtension(path);
    }
    
    public String getPathD(){
        return pathD;
    }
    
    public void setNews(String h){
        news.setText(h);
    }
    
    public void updateAllP(int x){
        allP.setValue(allP.getValue()+x);
    }
    
    public GUI() {
        initComponents();
        init();
    }
    
    private void init (){
        setTitle("VectorQuantization Here");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        allP.setVisible(false);
        bits_label.setText(bits_slider.getValue()+" Bits");
        vecHeight.removeAllItems();
        vecWidth.removeAllItems();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jComboBox1 = new javax.swing.JComboBox<>();
        news = new javax.swing.JLabel();
        browse = new javax.swing.JButton();
        allP = new javax.swing.JProgressBar();
        bits_slider = new javax.swing.JSlider();
        bits_label = new javax.swing.JLabel();
        drop = new javax.swing.JButton();
        vecWidth = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        vecHeight = new javax.swing.JComboBox<>();
        begin = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        news.setText("Browse File or Drop Files");

        browse.setText("Browse");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        allP.setStringPainted(true);

        bits_slider.setMaximum(20);
        bits_slider.setMinimum(1);
        bits_slider.setValue(2);
        bits_slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bits_sliderStateChanged(evt);
            }
        });

        bits_label.setText("# Bits");

        drop.setText("Drop");
        drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropActionPerformed(evt);
            }
        });

        vecWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vecWidthActionPerformed(evt);
            }
        });

        jLabel1.setText("vecWidth");

        jLabel2.setText("vecHeight");

        vecHeight.setSelectedIndex(-1);
        vecHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vecHeightActionPerformed(evt);
            }
        });

        begin.setText("Begin");
        begin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(allP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(news, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(vecWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(bits_label)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(drop))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bits_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browse))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(begin)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(vecHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(news)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(allP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vecHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vecWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(begin)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bits_label, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drop))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bits_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(browse)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bits_sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bits_sliderStateChanged
        main.q.numOfBits = bits_slider.getValue(); 
        bits_label.setText(Integer.toString(main.q.numOfBits)+" Bits");
        main.q.numOfV = (int) Math.pow(2, main.q.numOfBits);
    }//GEN-LAST:event_bits_sliderStateChanged

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        JFileChooser fileChooser = new JFileChooser(); 
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal ==  JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
  
            setNews("Processing : "+ path);
            pathD = main.files.prevPath(path);
            if (main.files.isVerified(path)){
// new main
                if (main.files.isImage(path)){
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(fileChooser.getSelectedFile());
                        
                        
                        main.setHeight(image.getHeight());
                        main.setWidth(image.getWidth());
                        
                        addResponseItems(main.width , main.height);
                        
                        
                        System.out.println("Height = "+main.height);
                        System.out.println("Width = "+main.width);

                        
                        
                        compression = true;
                        valid = true;
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    compression = false;
                    valid = true;
                }
            }else{
                setNews("Wrong Extension");
                valid = false;
            }
        }
    }//GEN-LAST:event_browseActionPerformed

    private void dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropActionPerformed
        if(visibleDrop == false){
            final javax.swing.JTextArea text = new javax.swing.JTextArea();
                frame.getContentPane().add( 
                     new javax.swing.JScrollPane( text ), 
                java.awt.BorderLayout.CENTER );
        
            new FileDrop( System.out, text, /*dragBorder,*/ new FileDrop.Listener()
            {
                public void filesDropped( java.io.File[] files )
                {   for( int i = 0; i < files.length; i++ )
                    {   try
                        {   
                            text.append( files[i].getCanonicalPath() + "\n" );
                            path = files[i].getAbsolutePath();
                            
                            setNews("Processing : "+path);     
                            pathD = main.files.prevPath(path);
                            if (main.files.isVerified(path)){
                                if (main.files.isImage(path)){                                    
                                    BufferedImage image = null;
                                    try {
                                        image = ImageIO.read(files[i]);
                                        main.setHeight(image.getHeight());
                                        main.setWidth(image.getWidth());
                                        
                                        System.out.println("->>> DROP");
                                        
                                        addResponseItems(main.width , main.height);
                                        
                                        compression = true;
                                        valid = true;
                                    } catch (IOException ex) {
                                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }else{
                                    compression = false;
                                    valid = true;
                                }
                            }else{
                                setNews("Wrong Extension");
                                valid = false;
                            }
                        }   // end try
                        catch( java.io.IOException e ) {}
                    }   // end for: through each dropped file
                }   // end filesDropped
            }); // end FileDrop.Listener

            frame.setBounds( 200, 150, 300, 400 );
            frame.setVisible(true);
            visibleDrop = true;
        }else{
            frame.setVisible(false);
            visibleDrop = false;
        }
    }//GEN-LAST:event_dropActionPerformed

    private void vecHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vecHeightActionPerformed
        main.q.setVecHeight(Integer.parseInt(vecHeight.getSelectedItem().toString()));
    }//GEN-LAST:event_vecHeightActionPerformed

    private void vecWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vecWidthActionPerformed
        main.q.setVecWidth(Integer.parseInt(vecWidth.getSelectedItem().toString()));
    }//GEN-LAST:event_vecWidthActionPerformed

    private void beginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginActionPerformed
       
        try {
            if (compression == true && valid == true){
                main.init();
                main.q.numOfBits = bits_slider.getValue(); 
                main.q.numOfV = (int) Math.pow(2, main.q.numOfBits);
                main.q.setVecHeight(Integer.parseInt(vecHeight.getSelectedItem().toString()));
                main.q.setVecWidth(Integer.parseInt(vecWidth.getSelectedItem().toString()));
                main.q.init();
                main.Compression();
            }else if(compression == false && valid == true){
                main.DeCompression();
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        valid = false;
    }//GEN-LAST:event_beginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar allP;
    private javax.swing.JButton begin;
    private javax.swing.JLabel bits_label;
    private javax.swing.JSlider bits_slider;
    private javax.swing.JButton browse;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton drop;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel news;
    private javax.swing.JComboBox<String> vecHeight;
    private javax.swing.JComboBox<String> vecWidth;
    // End of variables declaration//GEN-END:variables
}
