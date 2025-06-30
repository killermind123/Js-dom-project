import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int birdY = 250, birdVelocity = 0, score = 0;
    boolean gameOver = false;
    Timer timer;
    ArrayList<Rectangle> pipes;
    Random rand;

    public FlappyBird() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(400, 600);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setVisible(true);

        pipes = new ArrayList<>();
        rand = new Random();
        timer = new Timer(20, this);
        timer.start();

        addPipe();
        addPipe();
        addPipe();
        addPipe();
    }

    public void addPipe() {
        int space = 150;
        int width = 60;
        int height = 50 + rand.nextInt(300);

        if (pipes.size() > 0) {
            Rectangle lastPipe = pipes.get(pipes.size() - 1);
            int x = lastPipe.x + 200;
            pipes.add(new Rectangle(x, 0, width, height)); // top pipe
            pipes.add(new Rectangle(x, height + space, width, 600 - height - space)); // bottom pipe
        } else {
            pipes.add(new Rectangle(400, 0, width, height));
            pipes.add(new Rectangle(400, height + space, width, 600 - height - space));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 400, 600);

        // Ground
        g.setColor(Color.orange);
        g.fillRect(0, 500, 400, 100);

        // Grass
        g.setColor(Color.green);
        g.fillRect(0, 500, 400, 20);

        // Bird
        g.setColor(Color.red);
        g.fillOval(100, birdY, 20, 20);

        // Pipes
        g.setColor(Color.green.darker());
        for (Rectangle pipe : pipes) {
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        // Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 20, 40);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", 100, 300);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Bird gravity
            birdVelocity += 1;
            birdY += birdVelocity;

            // Move pipes
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 5;
            }

            // Remove pipes out of screen
            if (pipes.get(0).x + pipes.get(0).width < 0) {
                pipes.remove(0);
                pipes.remove(0);
                addPipe();
                score++;
            }

            // Check collision
            for (Rectangle pipe : pipes) {
                if (pipe.intersects(new Rectangle(100, birdY, 20, 20))) {
                    gameOver = true;
                }
            }

            // Check boundaries
            if (birdY > 500 || birdY < 0) {
                gameOver = true;
            }
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            birdVelocity = -10;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            // Restart game
            birdY = 250;
            birdVelocity = 0;
            score = 0;
            pipes.clear();
            addPipe(); addPipe(); addPipe(); addPipe();
            gameOver = false;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new FlappyBird();
    }
}
