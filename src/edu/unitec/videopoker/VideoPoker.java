/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import edu.unitec.adt.*;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EdilsonFernando
 */
public class VideoPoker extends javax.swing.JFrame {

    /**
     * Creates new form VideoPoker
     *
     */
    public VideoPoker() {
        initComponents();

        //centrar la ventana
        this.setLocationRelativeTo(null);
        this.pack();

        //Inicializando parte trasera de un naipe
        this.parteTrasera = new ImageIcon("./Cartas/ParteTrasera.png");
        this.parteTrasera.setImage(this.parteTrasera.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));

        //Inicializando arreglo actual
        this.cartasActuales = new Carta[5];

        inicializarCartas();
        situacionInicial();
        ajustarApuesta(PRIMER_RANGO);
    }

    private void situacionInicial() {
        nuevaBarajar();
        animacionCambio();
        this.btn_draw.setVisible(false);
        this.btn_bet.setVisible(true);
        this.btn_maxbet.setVisible(true);
        this.btn_deal.setVisible(true);
    }

    private void animacionCambio() {
        this.carta1.setIcon(this.parteTrasera);
        this.carta2.setIcon(this.parteTrasera);
        this.carta3.setIcon(this.parteTrasera);
        this.carta4.setIcon(this.parteTrasera);
        this.carta5.setIcon(this.parteTrasera);
        this.desCartas = true;
        ocultarHelds();
    }

    private void ocultarHelds() {
        this.lbl_Carta1.setVisible(false);
        this.lbl_Carta2.setVisible(false);
        this.lbl_Carta3.setVisible(false);
        this.lbl_Carta4.setVisible(false);
        this.lbl_Carta5.setVisible(false);
    }
    
    private void borrarSeleccion() {
        this.carta1.setSelected(false);
        this.carta2.setSelected(false);
        this.carta3.setSelected(false);
        this.carta4.setSelected(false);
        this.carta5.setSelected(false);
    }

    private void inicializarCartas() {
        this.cartas = new SLList();
        String card = Carta.DIAMANTE;

        for (int i = 1; i <= 52; i++) {
            if (i == 13) {
                card = Carta.CORAZONES;
            } else if (i == 26) {
                card = Carta.ESPADA;
            } else if (i == 39) {
                card = Carta.TREBOL;
            }

            ImageIcon n = new ImageIcon("./Cartas/" + card + ((i % 13) + 1) + ".png");
            n.setImage(n.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));

            try {
                this.cartas.insert(new Carta((i % 13) + 1, card, n), this.cartas.getSize());
            } catch (Exception e) {
                System.exit(1);
            }
        }

        try {
            ImageIcon n = new ImageIcon("./Cartas/Joker.png");
            n.setImage(n.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));
            this.cartas.insert(new Carta(0, Carta.JOKER, n), this.cartas.getSize());
        } catch (Exception e) {
            System.exit(1);
        }

    }

    private void nuevaBarajar() {
        SLList cCartas = new SLList();

        for (int i = 0; i < this.cartas.getSize(); i++) {
            cCartas.insert(this.cartas.get(i), cCartas.getSize());
        }


        this.baraja = new SLStack();

        while (!cCartas.isEmpty()) {
            Random r = new Random();
            int num = r.nextInt(cCartas.getSize());
            baraja.push(cCartas.remove(num));
        }
    }

    private void deal() {
        this.desCartas = false;
        final int TIME = 500;

        this.carta1.setSelected(false);
        this.carta2.setSelected(false);
        this.carta3.setSelected(false);
        this.carta4.setSelected(false);
        this.carta5.setSelected(false);

        ocultarHelds();

        if (this.baraja.getSize() < 5) {
            nuevaBarajar();
        }

        this.cartasActuales[0] = ((Carta) this.baraja.pop());
        this.cartasActuales[1] = ((Carta) this.baraja.pop());
        this.cartasActuales[2] = ((Carta) this.baraja.pop());
        this.cartasActuales[3] = ((Carta) this.baraja.pop());
        this.cartasActuales[4] = ((Carta) this.baraja.pop());

        
        this.carta1.setIcon(this.cartasActuales[0].getImage());
        this.carta2.setIcon(this.cartasActuales[1].getImage());
        this.carta3.setIcon(this.cartasActuales[2].getImage());
        this.carta4.setIcon(this.cartasActuales[3].getImage());
        this.carta5.setIcon(this.cartasActuales[4].getImage());
        
        this.btn_draw.setVisible(true);
        this.btn_bet.setVisible(false);
        this.btn_maxbet.setVisible(false);
        this.btn_deal.setVisible(false);
    }
    
    private void draw() {
        if (!this.carta1.isSelected()) {
             this.cartasActuales[0] = ((Carta) this.baraja.pop());
             this.carta1.setIcon(this.cartasActuales[0].getImage());
        }
        
        if (!this.carta2.isSelected()) {
             this.cartasActuales[1] = ((Carta) this.baraja.pop());
             this.carta2.setIcon(this.cartasActuales[1].getImage());
        }
        
        if (!this.carta3.isSelected()) {
             this.cartasActuales[2] = ((Carta) this.baraja.pop());
             this.carta3.setIcon(this.cartasActuales[2].getImage());
        }
        
        if (!this.carta4.isSelected()) {
             this.cartasActuales[3] = ((Carta) this.baraja.pop());
             this.carta4.setIcon(this.cartasActuales[3].getImage());
        }
        
        if (!this.carta5.isSelected()) {
             this.cartasActuales[4] = ((Carta) this.baraja.pop());
             this.carta5.setIcon(this.cartasActuales[4].getImage());
        }
        
        ocultarHelds();
        borrarSeleccion();
    }
    
    
    private void ajustarApuesta(final int RANGO) {
        DefaultTableModel model = (DefaultTableModel) this.tablaApuestas.getModel();
        
        while (model.getRowCount() != 0) {
            model.removeRow(0);
        }
        
        final String[] combinaciones = {
            "Royal Straight Flush",
            "Five of a Kind",
            "Straight Flush",
            "Four of a Kind",
            "Full House",
            "Flush",
            "Straight",
            "Three of a Kind",
            "Two Pair",
            "Jacks or Better"
        };
        
        final int[] puntuaciones = {
            250, 100, 50, 25, 9, 6, 4, 3, 2, 1
        };
        
        if (RANGO == PRIMER_RANGO) {
            for (int i = 0; i < combinaciones.length; i++) {
                Object[] row = {combinaciones[i], puntuaciones[i]};
                model.addRow(row);
            }
        } else if (RANGO == SEGUNDO_RANGO) {
            for (int i = 0; i < combinaciones.length; i++) {
                Object[] row = {combinaciones[i], puntuaciones[i] * 2};
                model.addRow(row);
            }
        } else if (RANGO == TERCER_RANGO) {
            for (int i = 0; i < combinaciones.length; i++) {
                Object[] row = {combinaciones[i], puntuaciones[i] * 3};
                model.addRow(row);
            }
        } else if (RANGO == CUARTO_RANGO){
            for (int i = 0; i < combinaciones.length; i++) {
                Object[] row = {combinaciones[i], puntuaciones[i] * 4};
                model.addRow(row);
            }
        } else {
            for (int i = 0; i < combinaciones.length; i++) {
                Object[] row = {combinaciones[i], puntuaciones[i] * 5};
                if (i == 0) {
                    row[1] = 4000;
                }
                model.addRow(row);
            }
        }
        
        this.tablaApuestas.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        carta2 = new javax.swing.JToggleButton();
        carta3 = new javax.swing.JToggleButton();
        carta4 = new javax.swing.JToggleButton();
        carta1 = new javax.swing.JToggleButton();
        carta5 = new javax.swing.JToggleButton();
        lbl_Carta1 = new javax.swing.JLabel();
        lbl_Carta2 = new javax.swing.JLabel();
        lbl_Carta3 = new javax.swing.JLabel();
        lbl_Carta4 = new javax.swing.JLabel();
        lbl_Carta5 = new javax.swing.JLabel();
        btn_draw = new javax.swing.JButton();
        btn_maxbet = new javax.swing.JButton();
        btn_bet = new javax.swing.JButton();
        btn_deal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaApuestas = new javax.swing.JTable();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Video Poker");
        setMaximumSize(new java.awt.Dimension(756, 540));
        setMinimumSize(new java.awt.Dimension(756, 540));
        setResizable(false);
        getContentPane().setLayout(null);

        carta2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carta2ActionPerformed(evt);
            }
        });
        getContentPane().add(carta2);
        carta2.setBounds(160, 270, 131, 186);

        carta3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carta3ActionPerformed(evt);
            }
        });
        getContentPane().add(carta3);
        carta3.setBounds(310, 270, 131, 186);

        carta4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carta4ActionPerformed(evt);
            }
        });
        getContentPane().add(carta4);
        carta4.setBounds(460, 270, 131, 186);

        carta1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carta1ActionPerformed(evt);
            }
        });
        getContentPane().add(carta1);
        carta1.setBounds(10, 270, 131, 186);

        carta5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carta5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carta5ActionPerformed(evt);
            }
        });
        getContentPane().add(carta5);
        carta5.setBounds(610, 270, 131, 186);

        lbl_Carta1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Carta1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Carta1.setText("Held");
        getContentPane().add(lbl_Carta1);
        lbl_Carta1.setBounds(50, 240, 41, 22);

        lbl_Carta2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Carta2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Carta2.setText("Held");
        getContentPane().add(lbl_Carta2);
        lbl_Carta2.setBounds(210, 240, 41, 22);

        lbl_Carta3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Carta3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Carta3.setText("Held");
        getContentPane().add(lbl_Carta3);
        lbl_Carta3.setBounds(350, 240, 41, 22);

        lbl_Carta4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Carta4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Carta4.setText("Held");
        getContentPane().add(lbl_Carta4);
        lbl_Carta4.setBounds(500, 240, 41, 22);

        lbl_Carta5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_Carta5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Carta5.setText("Held");
        getContentPane().add(lbl_Carta5);
        lbl_Carta5.setBounds(650, 240, 41, 22);

        btn_draw.setText("Draw");
        btn_draw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_drawActionPerformed(evt);
            }
        });
        getContentPane().add(btn_draw);
        btn_draw.setBounds(340, 470, 57, 23);

        btn_maxbet.setText("Max Bet");
        btn_maxbet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_maxbetActionPerformed(evt);
            }
        });
        getContentPane().add(btn_maxbet);
        btn_maxbet.setBounds(340, 470, 71, 23);

        btn_bet.setText("Bet");
        btn_bet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_betActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bet);
        btn_bet.setBounds(280, 470, 49, 23);

        btn_deal.setText("Deal");
        btn_deal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dealActionPerformed(evt);
            }
        });
        getContentPane().add(btn_deal);
        btn_deal.setBounds(420, 470, 60, 23);

        jScrollPane1.setBackground(new java.awt.Color(0, 163, 22));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setColumnHeader(null);
        jScrollPane1.setOpaque(false);

        tablaApuestas.setBackground(new java.awt.Color(0, 163, 22));
        tablaApuestas.setFont(new java.awt.Font("Clarendon Lt BT", 1, 14)); // NOI18N
        tablaApuestas.setForeground(new java.awt.Color(255, 255, 255));
        tablaApuestas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaApuestas.setOpaque(false);
        jScrollPane1.setViewportView(tablaApuestas);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(160, 10, 430, 170);

        fondo.setIcon(new ImageIcon("./Cartas/fondo.png"));
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 750, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta1ActionPerformed
        if (!this.desCartas) {

            if (this.carta1.isSelected()) {
                this.lbl_Carta1.setVisible(true);
            } else {
                this.lbl_Carta1.setVisible(false);
            }
        }
    }//GEN-LAST:event_carta1ActionPerformed

    private void carta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta2ActionPerformed
        if (!this.desCartas) {
            if (this.carta2.isSelected()) {
                this.lbl_Carta2.setVisible(true);
            } else {
                this.lbl_Carta2.setVisible(false);
            }
        }
    }//GEN-LAST:event_carta2ActionPerformed

    private void carta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta3ActionPerformed
        if (!this.desCartas) {
            if (this.carta3.isSelected()) {
                this.lbl_Carta3.setVisible(true);
            } else {
                this.lbl_Carta3.setVisible(false);
            }
        }
    }//GEN-LAST:event_carta3ActionPerformed

    private void carta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta4ActionPerformed
        if (!this.desCartas) {
            if (this.carta4.isSelected()) {
                this.lbl_Carta4.setVisible(true);
            } else {
                this.lbl_Carta4.setVisible(false);
            }
        }
    }//GEN-LAST:event_carta4ActionPerformed

    private void carta5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta5ActionPerformed
        if (!this.desCartas) {
            if (this.carta5.isSelected()) {
                this.lbl_Carta5.setVisible(true);
            } else {
                this.lbl_Carta5.setVisible(false);
            }
        }
    }//GEN-LAST:event_carta5ActionPerformed

    private void btn_drawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_drawActionPerformed
        draw();
    }//GEN-LAST:event_btn_drawActionPerformed

    private void btn_maxbetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_maxbetActionPerformed
        ajustarApuesta(QUINTO_RANGO);
        deal();
    }//GEN-LAST:event_btn_maxbetActionPerformed

    private void btn_betActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_betActionPerformed
        ajustarApuesta((rango % 5) + 1);
        rango++;
    }//GEN-LAST:event_btn_betActionPerformed

    private void btn_dealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dealActionPerformed
        deal();
    }//GEN-LAST:event_btn_dealActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VideoPoker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoPoker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoPoker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoPoker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VideoPoker().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bet;
    private javax.swing.JButton btn_deal;
    private javax.swing.JButton btn_draw;
    private javax.swing.JButton btn_maxbet;
    private javax.swing.JToggleButton carta1;
    private javax.swing.JToggleButton carta2;
    private javax.swing.JToggleButton carta3;
    private javax.swing.JToggleButton carta4;
    private javax.swing.JToggleButton carta5;
    private javax.swing.JLabel fondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Carta1;
    private javax.swing.JLabel lbl_Carta2;
    private javax.swing.JLabel lbl_Carta3;
    private javax.swing.JLabel lbl_Carta4;
    private javax.swing.JLabel lbl_Carta5;
    private javax.swing.JTable tablaApuestas;
    // End of variables declaration//GEN-END:variables
    
    //apuestas 
    private final int PRIMER_RANGO = 1;
    private final int SEGUNDO_RANGO = 2;
    private final int TERCER_RANGO = 3;
    private final int CUARTO_RANGO = 4;
    private final int QUINTO_RANGO = 5;
    private static int rango = 1;
    
    //Estructuras de datos
    private SLList cartas;
    private SLStack baraja;
    private ImageIcon parteTrasera;
    private boolean desCartas;
    private Carta[] cartasActuales;
}
