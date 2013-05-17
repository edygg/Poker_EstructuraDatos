/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import edu.unitec.adt.*;
import java.awt.Image;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal del juego Video Poker
 *
 * @author EdilsonFernando
 */
public class VideoPoker extends javax.swing.JFrame {

    /**
     * Crea e inicializa todos los componentes de la ventana de juego. Además
     * establece todo el juego en su situación inicial: pide el monto y prepara
     * para el usuario una nueva mano de cartas.
     *
     * @see VideoPoker#inicializarCartas()
     * @see VideoPoker#situacionInicial()
     * @see VideoPoker#ajustarApuesta(int)
     * @see VideoPoker#pedirMonto()
     */
    public VideoPoker() {
        initComponents();

        //centrar la ventana
        this.setLocationRelativeTo(null);
        this.pack();

        //Inicializando parte trasera de un naipe
        this.parteTrasera = new ImageIcon(this.getClass().getResource("ParteTrasera.png"));
        this.parteTrasera.setImage(this.parteTrasera.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));

        //Inicializando arreglo actual
        this.cartasActuales = new Carta[5];

        //Inicializando las partidas guardadas
        this.partidas = new SLList();
        this.numPartida = 0;

        //Preparando todo el juego
        inicializarCartas();
        situacionInicial();
        ajustarApuesta(PRIMER_RANGO);
        pedirMonto();
    }

    /**
     * Método que despliega InputDialog de JOptionPane para pedir el monto
     * inicial del juego, si el usuario no ingresa ningún monto y cierra la
     * ventana termina la ejecución del programa. Se llamará cada vez que el
     * usuario se quede sin dinero para continuar jugando. Asigna el valor a un
     * label que contiene el monto del dinero actual.
     */
    private void pedirMonto() {
        int val;

        while (true) {
            try {
                String ing = JOptionPane.showInputDialog(
                        this,
                        "Ingrese el monto inicial",
                        "Bienvenido",
                        JOptionPane.INFORMATION_MESSAGE);
                if (ing == null) {
                    // Si el usuario no ingresa ningún valor cierra el programa
                    System.exit(0);
                }

                //Intenta convertir la entrada en número
                val = Integer.parseInt(ing);

                break;
            } catch (Exception e) {
                //Si la entrada no fue válida continúa el ciclo de petición
            }
        }

        //Asigna el valor al monto inicial del juego
        this.creditsVal.setText(Integer.toString(val));
    }

    /**
     * Prepara todos los componentes para la situación inicial del juego.
     * Realiza las siguientes tareas: 1) Reinicia el contador de creditos
     * ganados 2) Deshabilita el modo doubleUp 4) Habilita todos los botones que
     * simulan las cartas 5) Hace una nueva baraja de cartas 6) Asigna las
     * figuras de la parte trasera de las cartas 7) Activa los botones maxbet,
     * bet y deal
     *
     * @see VideoPoker#doubleUp
     */
    private void situacionInicial() {
        this.winVal.setText("0");
        this.doubleUp = false;
        this.carta1.setEnabled(true);
        this.carta2.setEnabled(true);
        this.carta3.setEnabled(true);
        this.carta4.setEnabled(true);
        this.carta5.setEnabled(true);

        nuevaBarajar();
        animacionCambio();
        this.btn_draw.setVisible(false);
        this.btn_bet.setVisible(true);
        this.btn_maxbet.setVisible(true);
        this.btn_deal.setVisible(true);
        this.btn_collect.setVisible(false);
        this.btn_doubleUp.setVisible(false);
    }

    /**
     * Animación que se encarga de asignar a los botones que simulan las cartas
     * la parte trasera de éstas para ocultar las verdaderas imágenes. También
     * se encarga de llamar al método que oculta las etiquetas "Held" de la
     * parte superior de las cartas.
     *
     * @see VideoPoker#ocultarHelds()
     */
    private void animacionCambio() {
        this.carta1.setIcon(this.parteTrasera);
        this.carta2.setIcon(this.parteTrasera);
        this.carta3.setIcon(this.parteTrasera);
        this.carta4.setIcon(this.parteTrasera);
        this.carta5.setIcon(this.parteTrasera);
        this.desCartas = true;
        ocultarHelds();
    }

    /**
     * Oculta las etiquetas "Held" posicionadas en la parte superior de las
     * cartas.
     */
    private void ocultarHelds() {
        this.lbl_Carta1.setVisible(false);
        this.lbl_Carta2.setVisible(false);
        this.lbl_Carta3.setVisible(false);
        this.lbl_Carta4.setVisible(false);
        this.lbl_Carta5.setVisible(false);
    }

    /**
     * Si los botones están seleccionados, borra la selección para que vuelvan a
     * su estado original.
     */
    private void borrarSeleccion() {
        this.carta1.setSelected(false);
        this.carta2.setSelected(false);
        this.carta3.setSelected(false);
        this.carta4.setSelected(false);
        this.carta5.setSelected(false);
    }

    /**
     * Inicializa toda una baraja de cartas de manera ordenada cargando las
     * imágenes con éstas. Si las cartas no logran inicializarse el programa se
     * cierra de manera no exitosa.
     */
    private void inicializarCartas() {
        this.cartas = new SLList();
        String card = Carta.DIAMANTE;

        for (int i = 0; i < 52; i++) {
            if (i == 13) {
                card = Carta.CORAZONES;
            } else if (i == 26) {
                card = Carta.ESPADA;
            } else if (i == 39) {
                card = Carta.TREBOL;
            }

            ImageIcon n = new ImageIcon(this.getClass().getResource(card + ((i % 13) + 1) + ".png"));
            n.setImage(n.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));

            try {
                this.cartas.insert(new Carta((i % 13) + 1, card, n), this.cartas.getSize());
            } catch (Exception e) {
                System.exit(1);
            }
        }
        /*
         try {
         ImageIcon n = new ImageIcon("./Cartas/Joker.png");
         n.setImage(n.getImage().getScaledInstance(131, 186, Image.SCALE_DEFAULT));
         this.cartas.insert(new Carta(0, Carta.JOKER, n), this.cartas.getSize());
         this.cartas.insert(new Carta(0, Carta.JOKER, n), this.cartas.getSize());
         } catch (Exception e) {
         System.exit(1);
         }
         */
    }

    /**
     * Crea una nueva baraja para usar en el programa. Esta se almacena de
     * manera aleatoria.
     *
     * @see VideoPoker#cartas
     * @see VideoPoker#baraja
     */
    private void nuevaBarajar() {
        SLList cCartas = new SLList();

        //Copia de la lista con las cartas ordenadas
        for (int i = 0; i < this.cartas.getSize(); i++) {
            cCartas.insert(this.cartas.get(i), cCartas.getSize());
        }


        this.baraja = new SLStack();

        //Se eliminan las cartas que ya fueron insertadas en la pila.
        //la inserción se hace mediante un Random
        while (!cCartas.isEmpty()) {
            Random r = new Random();
            int num = r.nextInt(cCartas.getSize());
            baraja.push(cCartas.remove(num));
        }
    }

    /**
     * Activa la primer evaluación de las cartas. Luego de este método se
     * permite mantener las cartas para su posterior evaluación. Asigna las
     * cartas actuales sacandolas de la pila para luego activar el botón draw de
     * evaluación de la partida.
     *
     * @see VideoPoker#baraja
     * @see VideoPoker#cartasActuales
     */
    private void deal() {
        this.desCartas = false;

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

    /**
     * Este método sustituye las cartas que no fueron marcadas con "Held" y
     * llama al método evaluador para verificar que combinacion ganó el usuario.
     * Lanza un mensaje de "Ha ganado" si el usuario logró alguna combinación,
     * lanza "Ha perdido" en el caso contrario. Si pierde se encarga de regresar
     * a la situación inicial, mientras que si gana prepara los botones collect
     * y doubleUp para continuar con el flujo del juego.
     *
     * @see VideoPoker#ocultarHelds()
     * @see VideoPoker#borrarSeleccion()
     * @see VideoPoker#validadorCombinaciones()
     * @see VideoPoker#situacionInicial()
     */
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

        int win = validadorCombinaciones();

        if (win == 0) {
            JOptionPane.showMessageDialog(this.tablaApuestas, "¡Has perdido!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            this.creditsVal.setText(
                    Integer.toString(Integer.parseInt(creditsVal.getText()) - Integer.parseInt(wagerVal.getText())));

            if (Integer.parseInt(this.creditsVal.getText()) <= 0) {
                pedirMonto();
            }

            situacionInicial();
        } else {
            JOptionPane.showMessageDialog(this.tablaApuestas, "¡Has GANADO!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            this.winVal.setText(
                    Integer.toString(win));

            this.btn_collect.setVisible(true);
            this.btn_doubleUp.setVisible(true);
            this.btn_draw.setVisible(false);
        }

        if (this.baraja.getSize() < 5) {
            nuevaBarajar();
        }
    }

    /**
     * Ajusta la tabla de apuestas dependiendo del rango designado. El rango se
     * determina a partir de las constantes destinadas para este fin que se
     * encuentran en esta misma clase.
     *
     * @param RANGO Rango de apustas para ajustar la tabla
     * @see VideoPoker#PRIMER_RANGO
     * @see VideoPoker#SEGUNDO_RANGO
     * @see VideoPoker#TERCER_RANGO
     * @see VideoPoker#CUARTO_RANGO
     * @see VideoPoker#QUINTO_RANGO
     */
    private void ajustarApuesta(final int RANGO) {
        DefaultTableModel model = (DefaultTableModel) this.tablaApuestas.getModel();

        //Limpia el modelo de la tabla
        while (model.getRowCount() != 0) {
            model.removeRow(0);
        }

        //Titulos de las combinaciones
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

        //Puntuaciones iniciales por combinación
        final int[] puntuaciones = {
            250, 100, 50, 25, 9, 6, 4, 3, 2, 1
        };

        //Crea todo el modelo a partir de la constante de apuestas dada
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
        } else if (RANGO == CUARTO_RANGO) {
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

        //Establece el modelo de la tabla de apuestas
        this.tablaApuestas.setModel(model);
    }

    /**
     * Se encarga de validar la combinación de cartas que obtuvo el usuario. Se
     * basa en la combinación actual de cartas. Además escribe en el archivo de
     * las partidas por cada partida comprobada por este método.
     *
     * @return retorna el valor de la tabla de apuestas correspondiente a la
     * combinación de cartas realizada, si no se ganó en ningura retorna 0.
     */
    private int validadorCombinaciones() {
        //Ordenar las cartas
        Carta tmp[] = new Carta[5];

        //Copia el arreglo actual de cartas
        for (int i = 0; i < 5; i++) {
            tmp[i] = this.cartasActuales[i];
        }

        //Ordena las cartas mediante un bubble sort
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp.length - 1; j++) {
                if (tmp[j].getNumero() > tmp[j + 1].getNumero()) {
                    Carta aux = tmp[j];
                    tmp[j] = tmp[j + 1];
                    tmp[j + 1] = aux;
                }
            }
        }

        /*
         for (int i = 0; i < 5; i++) {
         System.out.println(tmp[i]);
         }
         */

        //Modelo de la tabla de apuestas
        DefaultTableModel tApuestas = (DefaultTableModel) this.tablaApuestas.getModel();

        //Royal Straight Flush
        if (tmp[0].getPalo().equals(tmp[1].getPalo()) && tmp[0].getPalo().equals(tmp[2].getPalo())
                && tmp[0].getPalo().equals(tmp[3].getPalo()) && tmp[0].getPalo().equals(tmp[4].getPalo())) {
            if (tmp[0].getNumero() == 1 && tmp[1].getNumero() == 10 && tmp[2].getNumero() == 11
                    && tmp[3].getNumero() == 12 && tmp[4].getNumero() == 13) {
                //System.out.println("Royal Straight Flush");
                escribirArchivoPartidas(new Partida(this.cartasActuales, "Royal Straight Flush"));
                return (int) tApuestas.getValueAt(0, 1);
            }
        }

        //Five of a kind

        //Straight Flush
        if (tmp[0].getPalo().equals(tmp[1].getPalo()) && tmp[0].getPalo().equals(tmp[2].getPalo())
                && tmp[0].getPalo().equals(tmp[3].getPalo()) && tmp[0].getPalo().equals(tmp[4].getPalo())) {
            if (tmp[1].getNumero() == tmp[0].getNumero() + 1 && tmp[2].getNumero() == tmp[0].getNumero() + 2
                    && tmp[3].getNumero() == tmp[0].getNumero() + 3 && tmp[4].getNumero() == tmp[0].getNumero() + 4) {
                if (tmp[0].getNumero() != 1) {
                    //System.out.println("Straight Flush");
                    escribirArchivoPartidas(new Partida(this.cartasActuales, "Straight Flush"));
                    return (int) tApuestas.getValueAt(2, 1);
                }
            }
        }

        //Four of a kind
        int four = 0;
        for (int i = 0; i < 5; i++) {

            for (int j = i + 1; j < 5; j++) {
                if (tmp[i].equalsNum(tmp[j])) {
                    four++;
                }
            }

            if (four == 3) {
                //System.out.println("Four of a kind");
                escribirArchivoPartidas(new Partida(this.cartasActuales, "Four of a kind"));
                return (int) tApuestas.getValueAt(3, 1);
            } else {
                four = 0;
            }
        }

        //Full House
        int fullhouse = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (tmp[i].equalsNum(tmp[j])) {
                    fullhouse++;
                }
            }

            if (fullhouse == 4) {
                //System.out.println("Full House");
                escribirArchivoPartidas(new Partida(this.cartasActuales, "Full House"));
                return (int) tApuestas.getValueAt(4, 1);
            }
        }

        //Flush
        if (tmp[0].getPalo().equals(tmp[1].getPalo()) && tmp[0].getPalo().equals(tmp[2].getPalo())
                && tmp[0].getPalo().equals(tmp[3].getPalo()) && tmp[0].getPalo().equals(tmp[4].getPalo())) {
            //System.out.println("Flush");
            escribirArchivoPartidas(new Partida(this.cartasActuales, "Flush"));
            return (int) tApuestas.getValueAt(5, 1);
        }

        //Straight
        if (tmp[1].getNumero() == tmp[0].getNumero() + 1 && tmp[2].getNumero() == tmp[0].getNumero() + 2
                && tmp[3].getNumero() == tmp[0].getNumero() + 3 && tmp[4].getNumero() == tmp[0].getNumero() + 4) {
            //System.out.println("Straight");
            escribirArchivoPartidas(new Partida(this.cartasActuales, "Straight"));
            return (int) tApuestas.getValueAt(6, 1);
        }

        //Three of a kind
        int three = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (tmp[i].equalsNum(tmp[j])) {
                    three++;
                }
            }

            if (three == 2) {
                //System.out.println("Three of a kind");
                escribirArchivoPartidas(new Partida(this.cartasActuales, "Three of a kind"));
                return (int) tApuestas.getValueAt(7, 1);
            } else {
                three = 0;
            }
        }

        //Two pairs
        int pair1 = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (tmp[i].equalsNum(tmp[j])) {
                    pair1++;
                }
            }

            if (pair1 == 2) {
                //System.out.println("Two Pairs");
                escribirArchivoPartidas(new Partida(this.cartasActuales, "Two Pairs"));
                return (int) tApuestas.getValueAt(8, 1);
            }
        }

        //Jacks or better
        for (int i = 0; i < 5; i++) {
            if (tmp[i].getNumero() == 1 || tmp[i].getNumero() >= 11) {
                for (int j = i + 1; j < 5; j++) {
                    if (tmp[i].equalsNum(tmp[j])) {
                        escribirArchivoPartidas(new Partida(this.cartasActuales, "Jacks or Better"));
                        return (int) tApuestas.getValueAt(9, 1);
                    }
                }
            }
        }

        escribirArchivoPartidas(new Partida(this.cartasActuales, "Perdió"));
        return 0;

    }

    /**
     * Método comprobador para el modo Double Up. Compara la carta del Dealer
     * con la carta seleccionada por el usuario, si la carta de Dealer es mayor
     * el usuario pierde y se regresa a la situación inicial, caso contrario el
     * usuario gana y tiene la posibilidad de volver a hacer double Up o de
     * tomar su ganancia. En caso de empate, se recinicia el double up sin
     * perdidas ni ganancias.
     *
     * @see Carta#compareTo(java.lang.Object)
     * @see VideoPoker#situacionInicial()
     * @see VideoPoker#btn_doubleUpActionPerformed(java.awt.event.ActionEvent)
     */
    private void comprobadorDoubleUp() {
        //System.out.println(this.cartaDealer);
        //System.out.println(this.cartaUser);
        if (this.cartasActuales[this.cartaDealer - 1].compareTo(this.cartasActuales[this.cartaUser]) < 0) {
            JOptionPane.showMessageDialog(this.tablaApuestas, "¡Has GANADO!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            this.winVal.setText(Integer.toString(Integer.parseInt(this.winVal.getText()) * 2));
            this.btn_collect.setVisible(true);
            this.btn_doubleUp.setVisible(true);
        } else if (this.cartasActuales[this.cartaDealer - 1].compareTo(this.cartasActuales[this.cartaUser]) == 0) {
            this.btn_doubleUpActionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(this.tablaApuestas, "Has Perdido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            situacionInicial();
        }
    }

    /**
     * Método que se encarga de escribir una partida dentro del historial de
     * partidas, que se conforma de un archivo binario.
     *
     * @param part Partida actual que se guardará en el archivo.
     */
    private void escribirArchivoPartidas(Partida part) {
        SLQueue tmp = new SLQueue();

        File parti = new File("./partidas.bin");

        ObjectInputStream in = null;

        try {
            if (!parti.exists()) {
                parti.createNewFile();
            }

            in = new ObjectInputStream(new FileInputStream("./partidas.bin"));

            while (true) {
                tmp.queue(in.readObject());
            }
        } catch (EOFException e) {
            //Termino la lectura del archivo  
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }

        tmp.queue(part);

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(new File("./partidas.bin")));

            while (!tmp.isEmpty()) {
                out.writeObject(tmp.dequeue());
            }
        } catch (Exception e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Método que se encarga de la lectura del archivo partidas. Este método se
     * encarga de actualizar la lista de partidas para su visualización
     * porterior.
     *
     * @see VideoPoker#partidas
     */
    private void leerArchivoPartidas() {
        this.partidas.clear();

        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream(new File("./partidas.bin")));

            while (true) {
                partidas.insert(in.readObject());
            }
        } catch (EOFException e) {
            //terminó la lectura del archivo;
        } catch (Exception e) {
            System.exit(1);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaPartidas = new javax.swing.JDialog();
        btn_back = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        btn_carta5 = new javax.swing.JButton();
        btn_carta1 = new javax.swing.JButton();
        btn_carta2 = new javax.swing.JButton();
        btn_carta3 = new javax.swing.JButton();
        btn_carta4 = new javax.swing.JButton();
        lbl_descripcion = new javax.swing.JLabel();
        lbl_fondoPartidas = new javax.swing.JLabel();
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
        lbl_credits = new javax.swing.JLabel();
        lbl_paid = new javax.swing.JLabel();
        lbl_win = new javax.swing.JLabel();
        lbl_wager = new javax.swing.JLabel();
        creditsVal = new javax.swing.JLabel();
        paidVal = new javax.swing.JLabel();
        winVal = new javax.swing.JLabel();
        wagerVal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaApuestas = new javax.swing.JTable();
        btn_collect = new javax.swing.JButton();
        btn_doubleUp = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();
        menuPrincipal = new javax.swing.JMenuBar();
        mnu_juego = new javax.swing.JMenu();
        mnu_juego_partidas = new javax.swing.JMenuItem();
        mnu_juego_salir = new javax.swing.JMenuItem();

        ventanaPartidas.setMinimumSize(new java.awt.Dimension(784, 300));
        ventanaPartidas.setResizable(false);
        ventanaPartidas.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                ventanaPartidasWindowActivated(evt);
            }
        });
        ventanaPartidas.getContentPane().setLayout(null);

        btn_back.setText("<");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        ventanaPartidas.getContentPane().add(btn_back);
        btn_back.setBounds(300, 230, 73, 23);

        btn_next.setText(">");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });
        ventanaPartidas.getContentPane().add(btn_next);
        btn_next.setBounds(380, 230, 73, 23);
        ventanaPartidas.getContentPane().add(btn_carta5);
        btn_carta5.setBounds(620, 20, 140, 200);
        ventanaPartidas.getContentPane().add(btn_carta1);
        btn_carta1.setBounds(20, 20, 140, 200);
        ventanaPartidas.getContentPane().add(btn_carta2);
        btn_carta2.setBounds(170, 20, 140, 200);
        ventanaPartidas.getContentPane().add(btn_carta3);
        btn_carta3.setBounds(320, 20, 140, 200);
        ventanaPartidas.getContentPane().add(btn_carta4);
        btn_carta4.setBounds(470, 20, 140, 200);

        lbl_descripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_descripcion.setText("Descripcion");
        ventanaPartidas.getContentPane().add(lbl_descripcion);
        lbl_descripcion.setBounds(660, 230, 100, 20);
        ventanaPartidas.getContentPane().add(lbl_fondoPartidas);
        lbl_fondoPartidas.setBounds(0, 0, 790, 300);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Video Poker");
        setMaximumSize(new java.awt.Dimension(756, 550));
        setMinimumSize(new java.awt.Dimension(756, 550));
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

        lbl_credits.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_credits.setForeground(new java.awt.Color(51, 51, 255));
        lbl_credits.setText("CREDITS");
        getContentPane().add(lbl_credits);
        lbl_credits.setBounds(540, 100, 50, 14);

        lbl_paid.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_paid.setForeground(new java.awt.Color(255, 255, 255));
        lbl_paid.setText("PAID");
        getContentPane().add(lbl_paid);
        lbl_paid.setBounds(560, 80, 30, 14);

        lbl_win.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_win.setForeground(new java.awt.Color(255, 255, 255));
        lbl_win.setText("WIN");
        getContentPane().add(lbl_win);
        lbl_win.setBounds(561, 50, 30, 14);

        lbl_wager.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_wager.setForeground(new java.awt.Color(51, 51, 255));
        lbl_wager.setText("WAGER");
        getContentPane().add(lbl_wager);
        lbl_wager.setBounds(547, 20, 50, 14);

        creditsVal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        creditsVal.setText("0");
        getContentPane().add(creditsVal);
        creditsVal.setBounds(620, 100, 40, 20);

        paidVal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        paidVal.setText("0");
        getContentPane().add(paidVal);
        paidVal.setBounds(620, 80, 40, 20);

        winVal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        winVal.setText("0");
        getContentPane().add(winVal);
        winVal.setBounds(620, 50, 40, 20);

        wagerVal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        wagerVal.setText("1");
        getContentPane().add(wagerVal);
        wagerVal.setBounds(620, 20, 40, 20);

        jScrollPane1.setBackground(new java.awt.Color(0, 163, 22));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
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
        tablaApuestas.setEnabled(false);
        tablaApuestas.setOpaque(false);
        jScrollPane1.setViewportView(tablaApuestas);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 20, 430, 170);

        btn_collect.setText("Collect");
        btn_collect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_collectActionPerformed(evt);
            }
        });
        getContentPane().add(btn_collect);
        btn_collect.setBounds(300, 210, 65, 23);

        btn_doubleUp.setText("Double up");
        btn_doubleUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doubleUpActionPerformed(evt);
            }
        });
        getContentPane().add(btn_doubleUp);
        btn_doubleUp.setBounds(390, 210, 81, 23);

        fondo.setIcon(new ImageIcon(this.getClass().getResource("fondo.png")));
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 750, 540);

        mnu_juego.setText("Juego");

        mnu_juego_partidas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnu_juego_partidas.setText("Partidas");
        mnu_juego_partidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_juego_partidasActionPerformed(evt);
            }
        });
        mnu_juego.add(mnu_juego_partidas);

        mnu_juego_salir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnu_juego_salir.setText("Salir");
        mnu_juego_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_juego_salirActionPerformed(evt);
            }
        });
        mnu_juego.add(mnu_juego_salir);

        menuPrincipal.add(mnu_juego);

        setJMenuBar(menuPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metodo que en modo double up activa el comprobador de cartas para él y
     * en modo normal marca la carta en estado "Held" para su retención luego 
     * de presionar el botón draw
     * @param evt Evento que se dispara al accionar el botón
     */
    private void carta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta1ActionPerformed
        if (this.doubleUp) {
            this.carta1.setIcon(this.cartasActuales[0].getImage());
            this.cartaUser = 0;
            comprobadorDoubleUp();
        } else {
            if (!this.desCartas) {

                if (this.carta1.isSelected()) {
                    this.lbl_Carta1.setVisible(true);
                } else {
                    this.lbl_Carta1.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_carta1ActionPerformed

    /**
     * Metodo que en modo double up activa el comprobador de cartas para él y
     * en modo normal marca la carta en estado "Held" para su retención luego 
     * de presionar el botón draw
     * @param evt Evento que se dispara al accionar el botón
     */
    private void carta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta2ActionPerformed
        if (this.doubleUp) {
            this.carta2.setIcon(this.cartasActuales[1].getImage());
            this.cartaUser = 1;
            comprobadorDoubleUp();
        } else {
            if (!this.desCartas) {
                if (this.carta2.isSelected()) {
                    this.lbl_Carta2.setVisible(true);
                } else {
                    this.lbl_Carta2.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_carta2ActionPerformed

    /**
     * Metodo que en modo double up activa el comprobador de cartas para él y
     * en modo normal marca la carta en estado "Held" para su retención luego 
     * de presionar el botón draw
     * @param evt Evento que se dispara al accionar el botón
     */
    private void carta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta3ActionPerformed
        if (this.doubleUp) {
            this.carta3.setIcon(this.cartasActuales[2].getImage());
            this.cartaUser = 2;
            comprobadorDoubleUp();
        } else {
            if (!this.desCartas) {
                if (this.carta3.isSelected()) {
                    this.lbl_Carta3.setVisible(true);
                } else {
                    this.lbl_Carta3.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_carta3ActionPerformed

    /**
     * Metodo que en modo double up activa el comprobador de cartas para él y
     * en modo normal marca la carta en estado "Held" para su retención luego 
     * de presionar el botón draw
     * @param evt Evento que se dispara al accionar el botón
     */
    private void carta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta4ActionPerformed
        if (this.doubleUp) {
            this.carta4.setIcon(this.cartasActuales[3].getImage());
            this.cartaUser = 3;
            comprobadorDoubleUp();
        } else {
            if (!this.desCartas) {
                if (this.carta4.isSelected()) {
                    this.lbl_Carta4.setVisible(true);
                } else {
                    this.lbl_Carta4.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_carta4ActionPerformed

    /**
     * Metodo que en modo double up activa el comprobador de cartas para él y
     * en modo normal marca la carta en estado "Held" para su retención luego 
     * de presionar el botón draw
     * @param evt Evento que se dispara al accionar el botón
     */
    private void carta5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carta5ActionPerformed
        if (this.doubleUp) {
            this.carta5.setIcon(this.cartasActuales[4].getImage());
            this.cartaUser = 4;
            comprobadorDoubleUp();
        } else {
            if (!this.desCartas) {
                if (this.carta5.isSelected()) {
                    this.lbl_Carta5.setVisible(true);
                } else {
                    this.lbl_Carta5.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_carta5ActionPerformed

    /**
     * Llama al método draw para sustituir las castas no retenidas.
     * @param evt Evento que se dispara al accionar el botón
     * @see VideoPoker#draw() 
     */
    private void btn_drawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_drawActionPerformed
        draw();
    }//GEN-LAST:event_btn_drawActionPerformed

    /**
     * Apuesta máxima. Asigna el rango de apuestas en el mayor de todos y 
     * automáticamente llama al método deal para lanzar la primera mano de
     * cartas.
     * @param evt Evento que se dispara al accionar el botón
     * @see VideoPoker#QUINTO_RANGO
     * @see VideoPoker#deal()
     */
    private void btn_maxbetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_maxbetActionPerformed
        ajustarApuesta(QUINTO_RANGO);
        this.wagerVal.setText("5");
        deal();
    }//GEN-LAST:event_btn_maxbetActionPerformed

    /**
     * Ajusta la apuesta en cada accionar del botón, mantiene un contador de
     * rango que activa los diferentes rangos (1-5) para cambiar la apuesta
     * de la tabla
     * @param evt Evento que se dispara al accionar el botón
     * @see VideoPoker#rango
     * @see VideoPoker#PRIMER_RANGO
     * @see VideoPoker#SEGUNDO_RANGO
     * @see VideoPoker#TERCER_RANGO
     * @see VideoPoker#CUARTO_RANGO
     * @see VideoPoker#QUINTO_RANGO
     */
    private void btn_betActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_betActionPerformed
        ajustarApuesta((rango % 5) + 1);
        if ((rango % 5) + 1 == PRIMER_RANGO) {
            this.wagerVal.setText("1");
        } else if ((rango % 5) + 1 == SEGUNDO_RANGO) {
            this.wagerVal.setText("2");
        } else if ((rango % 5) + 1 == TERCER_RANGO) {
            this.wagerVal.setText("3");
        } else if ((rango % 5) + 1 == CUARTO_RANGO) {
            this.wagerVal.setText("4");
        } else {
            this.wagerVal.setText("5");
        }
        rango++;
    }//GEN-LAST:event_btn_betActionPerformed

    /**
     * Llama al metodo deal para lanzar una mano de cartas por primera vez.
     * @param evt Evento que se dispara al accionar el botón
     * @see VideoPoker#deal() 
     */
    private void btn_dealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dealActionPerformed
        deal();
    }//GEN-LAST:event_btn_dealActionPerformed

    /**
     * Suma lo ganado hasta el momento dentro de los créditos y el pago se 
     * ve reflejado en los labels
     * @param evt Evento que se dispara al accionar el botón
     */
    private void btn_collectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_collectActionPerformed
        this.paidVal.setText(Integer.toString(Integer.parseInt(this.winVal.getText()) + Integer.parseInt(this.paidVal.getText())));
        this.creditsVal.setText(Integer.toString(Integer.parseInt(this.paidVal.getText()) + Integer.parseInt(this.creditsVal.getText())));
        this.winVal.setText("0");

        this.doubleUp = false;
        situacionInicial();
    }//GEN-LAST:event_btn_collectActionPerformed

    /**
     * Configura toda la situación inicial del modo double up
     * @param evt Evento que se dispara al accionar el botón
     */
    private void btn_doubleUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doubleUpActionPerformed
        this.btn_collect.setVisible(false);
        this.btn_doubleUp.setVisible(false);

        this.carta1.setEnabled(true);
        this.carta2.setEnabled(true);
        this.carta3.setEnabled(true);
        this.carta4.setEnabled(true);
        this.carta5.setEnabled(true);

        this.carta1.setSelected(false);
        this.carta2.setSelected(false);
        this.carta3.setSelected(false);
        this.carta4.setSelected(false);
        this.carta5.setSelected(false);


        //Activando el modo doubleUp
        this.doubleUp = true;

        if (this.baraja.getSize() < 5) {
            this.nuevaBarajar();
        }

        //renovando el arreglo de cartas actuales
        this.cartasActuales[0] = ((Carta) this.baraja.pop());
        this.cartasActuales[1] = ((Carta) this.baraja.pop());
        this.cartasActuales[2] = ((Carta) this.baraja.pop());
        this.cartasActuales[3] = ((Carta) this.baraja.pop());
        this.cartasActuales[4] = ((Carta) this.baraja.pop());

        this.carta1.setIcon(this.parteTrasera);
        this.carta2.setIcon(this.parteTrasera);
        this.carta3.setIcon(this.parteTrasera);
        this.carta4.setIcon(this.parteTrasera);
        this.carta5.setIcon(this.parteTrasera);

        //Escogiendo carta del Dealer
        this.cartaDealer = new Random().nextInt(this.cartasActuales.length) + 1;

        switch (this.cartaDealer) {
            case 1:
                this.carta1.setIcon(this.cartasActuales[0].getImage());
                this.carta1.setEnabled(false);
                break;

            case 2:
                this.carta2.setIcon(this.cartasActuales[1].getImage());
                this.carta2.setEnabled(false);
                break;

            case 3:
                this.carta3.setIcon(this.cartasActuales[2].getImage());
                this.carta3.setEnabled(false);
                break;

            case 4:
                this.carta4.setIcon(this.cartasActuales[3].getImage());
                this.carta4.setEnabled(false);
                break;

            case 5:
                this.carta5.setIcon(this.cartasActuales[4].getImage());
                this.carta5.setEnabled(false);
                break;

        }
    }//GEN-LAST:event_btn_doubleUpActionPerformed

    /**
     * Para salir del juego.
     * @param evt Evento que se dispara al accionar el menú.
     */
    private void mnu_juego_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_juego_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnu_juego_salirActionPerformed

    /**
     * Lanza la ventana de partidas para que sean visualizadas por el usuario.
     * @param evt Evento que se dispara al accionar el menú.
     * @see VideoPoker#ventanaPartidas
     */
    private void mnu_juego_partidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_juego_partidasActionPerformed
        this.ventanaPartidas.pack();
        this.ventanaPartidas.setLocationRelativeTo(this);
        this.ventanaPartidas.setVisible(true);
    }//GEN-LAST:event_mnu_juego_partidasActionPerformed

    /**
     * Cada vez que se activa la ventana de partidas, se lee el archivo de
     * partidas para actualizar la lista de partidas.
     * @param evt Evento que se dispara al abrir la ventana
     * @see VideoPoker#partidas
     * @see VideoPoker#leerArchivoPartidas() 
     * @see Partida 
     */
    private void ventanaPartidasWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ventanaPartidasWindowActivated
        this.lbl_fondoPartidas.setIcon(new ImageIcon(this.getClass().getResource("fondo.png")));
        leerArchivoPartidas();
        if (this.partidas.isEmpty()) {
            JOptionPane.showMessageDialog(this.ventanaPartidas, "No hay partidas Guardadas", "Mensaje", JOptionPane.ERROR_MESSAGE);
            this.ventanaPartidas.setVisible(false);
        } else {
            this.btn_carta1.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(0).getImage());
            this.btn_carta2.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(1).getImage());
            this.btn_carta3.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(2).getImage());
            this.btn_carta4.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(3).getImage());
            this.btn_carta5.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(4).getImage());
            this.lbl_descripcion.setText(((Partida) this.partidas.get(numPartida)).getDescripcion());
        }
    }//GEN-LAST:event_ventanaPartidasWindowActivated

    /**
     * Se mueve hacia atrás en la lista de partidas.
     * @param evt Evento que se dispara al accionar el botón.
     * @see VideoPoker#partidas
     */
    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        if (this.partidas.get(this.numPartida - 1) != null) {
            this.numPartida--;
            this.btn_carta1.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(0).getImage());
            this.btn_carta2.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(1).getImage());
            this.btn_carta3.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(2).getImage());
            this.btn_carta4.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(3).getImage());
            this.btn_carta5.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(4).getImage());
            this.lbl_descripcion.setText(((Partida) this.partidas.get(numPartida)).getDescripcion());
        }
    }//GEN-LAST:event_btn_backActionPerformed

    /**
     * Se mueve hacia adelante en la lista de partidas.
     * @param evt Evento que se dispara al accionar el botón.
     */
    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if (this.partidas.get(this.numPartida + 1) != null) {
            this.numPartida++;
            this.btn_carta1.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(0).getImage());
            this.btn_carta2.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(1).getImage());
            this.btn_carta3.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(2).getImage());
            this.btn_carta4.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(3).getImage());
            this.btn_carta5.setIcon(((Partida) this.partidas.get(numPartida)).getCarta(4).getImage());
            this.lbl_descripcion.setText(((Partida) this.partidas.get(numPartida)).getDescripcion());
        }
    }//GEN-LAST:event_btn_nextActionPerformed

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
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_bet;
    private javax.swing.JButton btn_carta1;
    private javax.swing.JButton btn_carta2;
    private javax.swing.JButton btn_carta3;
    private javax.swing.JButton btn_carta4;
    private javax.swing.JButton btn_carta5;
    private javax.swing.JButton btn_collect;
    private javax.swing.JButton btn_deal;
    private javax.swing.JButton btn_doubleUp;
    private javax.swing.JButton btn_draw;
    private javax.swing.JButton btn_maxbet;
    private javax.swing.JButton btn_next;
    private javax.swing.JToggleButton carta1;
    private javax.swing.JToggleButton carta2;
    private javax.swing.JToggleButton carta3;
    private javax.swing.JToggleButton carta4;
    private javax.swing.JToggleButton carta5;
    private javax.swing.JLabel creditsVal;
    private javax.swing.JLabel fondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Carta1;
    private javax.swing.JLabel lbl_Carta2;
    private javax.swing.JLabel lbl_Carta3;
    private javax.swing.JLabel lbl_Carta4;
    private javax.swing.JLabel lbl_Carta5;
    private javax.swing.JLabel lbl_credits;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_fondoPartidas;
    private javax.swing.JLabel lbl_paid;
    private javax.swing.JLabel lbl_wager;
    private javax.swing.JLabel lbl_win;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JMenu mnu_juego;
    private javax.swing.JMenuItem mnu_juego_partidas;
    private javax.swing.JMenuItem mnu_juego_salir;
    private javax.swing.JLabel paidVal;
    private javax.swing.JTable tablaApuestas;
    private javax.swing.JDialog ventanaPartidas;
    private javax.swing.JLabel wagerVal;
    private javax.swing.JLabel winVal;
    // End of variables declaration//GEN-END:variables
    //Ventana de partidas
    /**
     * Partida actual mostrada en la ventana de partidas
     * @see VideoPoker#ventanaPartidas
     */
    private int numPartida;
    //Modo doubleUp
    /**
     * Booleano que identifica si el modo double up está activo.
     * @see VideoPoker#comprobadorDoubleUp() 
     */
    private boolean doubleUp = false;
    
    /**
     * Número de la carta que seleccionó el Dealer en el modo double up.
     */
    private int cartaDealer = -1;
    
    /**
     * Número de la carta que seleccionó el usuario.
     */
    private int cartaUser;
    //apuestas 
    /**
     * Constante que identifica el primer rango de apuestas
     */
    private final int PRIMER_RANGO = 1;
    
    /**
     * Constante que identifica el segundo rango de apuestas
     */
    private final int SEGUNDO_RANGO = 2;
    
    /**
     * Constante que identifica el tercer rango de apuestas
     */
    private final int TERCER_RANGO = 3;
    
    /**
     * Constante que identifica el cuarto rango de apuestas
     */
    private final int CUARTO_RANGO = 4;
    
    /**
     * Constante que identifica el quinto rango de apuestas
     */
    private final int QUINTO_RANGO = 5;
    
    /**
     * Rango actual de apuestas
     */
    private static int rango = 1;
    //Estructuras de datos
    /**
     * Mantiene las partidas cargadas desde el archivo de partidas.
     * @see VideoPoker#leerArchivoPartidas() 
     * @see VideoPoker#escribirArchivoPartidas(edu.unitec.videopoker.Partida) 
     */
    private SLList partidas;
    
    /**
     * Lista de cartas ordenadas.
     */
    private SLList cartas;
    
    /**
     * Baraja de cartas desordenadas.
     */
    private SLStack baraja;
    
    /**
     * Imagen de la parte trasera de las cartas.
     */
    private ImageIcon parteTrasera;
    
    /**
     * Booleano que activa y desactiva el estado "Held" de las cartas.
     */
    private boolean desCartas;
    
    /**
     * Arreglo que mantiene la mano actual del juego.
     */
    private Carta[] cartasActuales;
}
