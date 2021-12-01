import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        //Variablen
        JFrame obj = new JFrame(); //Erstellt ein Fenster 
        Gameplay gamePlay= new Gameplay(); //Erstellt die Zeichenfläche in dem Fenster

        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setLocationRelativeTo(null); 
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay); //fügt die Zeichenfläche zum Fenster hinzu
    }
}
 