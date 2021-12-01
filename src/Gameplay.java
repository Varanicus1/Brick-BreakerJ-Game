import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;

//Datei für alles Zeichnet, Erstellt, Kollision,Keylistener,Variablen, Ball, Paddle
public class Gameplay extends JPanel implements KeyListener, ActionListener{

    //Variablen
     boolean play=false;
     int score= 0;
     int totalBricks = 21;

     Timer timer;
     int delay = 8;
    
     int playerX = 310;

     int ballposX = 120, ballposY = 350;
     int ballXdir = -2, ballYdir = -3;

     MapGenerator map;
    
     //Konstruktor für erstellen des Arrays, Keylistener aufruf, erstellt ein timer/clock
    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    } 
   
     //Zeichnen der Gegenstände
    public void paint(Graphics g){

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing bricks
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+score,590, 30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        //Siegesanimation
        if(totalBricks <= 0){
            play= false;
            ballXdir = 0;
            ballYdir =0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You Won" ,260, 300);

            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart" ,230, 350); 
        }

        //Verlierer Animation
        if(ballposY > 570){
            play= false;
            ballXdir = 0;
            ballYdir =0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game over, Scores: " ,190, 300);

            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart" ,230, 350);
        }

        g.dispose();
    }
    
    
    


    //Ballbewegung und Kollisionsprüfung
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        //Kollisionspürfung mit dem paddle
        if(play){
        if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
            ballYdir = -ballYdir;
        }
        
        //Kollisionsprüfung mit den Bricks
        A: for(int i =0; i<map.map.length; i++){ //Initiale Array aus MapGenerator
            for(int j =0; j<map.map[0].length; j++){
                if(map.map[i][j]>0){//solange noch Bricks übrig

                    //Kordinaten und Größe jedes Bricks
                    int brickX = j*map.brickWidth + 80;
                    int brickY = i*map.brickHeight + 50;
                    int brickWidth = map.brickWidth ;
                    int brickHeight = map.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY,brickWidth, brickHeight);//Bricks werden als rechtecke betrachtet
                    Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);//Der runde Ball wird als rechteck betrachtet
                    Rectangle brickRect=rect; //Arbeitsvariable für rect

                    //Eigentlich Kolliosions abfrage mit den Bricks
                    if(ballRect.intersects(brickRect)){
                        //Ein Brick wird gelöscht
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        //Punkte werden erhöhht
                        score +=5;
                        
                        if(ballposX +19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                            ballXdir = -ballXdir;
                        }else{
                            ballYdir = -ballYdir;
                        }
                        break A;
                    }


                }
            
            }
        }   
            //Ballbewegung
            ballposX += ballXdir;
            ballposY += ballYdir;

            //Kollisions Prüfung mit der Wand
            if(ballposX < 0){
                ballXdir =-ballXdir;
            }
            if(ballposY < 0){
                ballYdir =-ballYdir;
            }
            if(ballposX > 670){
                ballXdir =-ballXdir;
            }
        }
        repaint();
        
    }
    
    //KeyListener
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    
    @Override
    public void keyPressed(KeyEvent e) {

        //Nach Rechts
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else {
                moveRight();
            }

        }

        //Nach Links
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else {
                moveLeft();
            } 
        }

        //Enter Reset von allem Spiel beginnt erneut
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play= true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -2;
                ballYdir = -3;
                playerX= 310;
                score = 0;
                totalBricks = 28;
                map = new MapGenerator(4, 7);
                repaint();
            }
        }
    }
    
    //Rechts Bewegung
    public void moveRight(){
        play = true;
        playerX +=20;
    }

    //Links Bewegung
    public void moveLeft(){
        play = true;
        playerX -=20;
    }  
}
