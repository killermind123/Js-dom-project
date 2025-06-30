import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JFrame {
    CardLayout cardLayout;
    JPanel container;
    GamePanel gamePanel;

    public SnakeGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setPreferredSize(new Dimension(600, 600));

        MenuPanel menuPanel = new MenuPanel(this);
        container.add(menuPanel, "Menu");

        add(container);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame(int delay) {
        gamePanel = new GamePanel(delay, this);
        container.add(gamePanel, "Game");
        cardLayout.show(container, "Game");
        gamePanel.requestFocusInWindow();
    }

    public void returnToMenu() {
        cardLayout.show(container, "Menu");
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}

class MenuPanel extends JPanel {
    public MenuPanel(SnakeGame frame) {
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setFocusable(true);

        JLabel title = new JLabel("Snake Game", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setForeground(Color.GREEN);
        title.setBounds(100, 80, 400, 60);
        add(title);

        JButton easy = createButton("Easy", 200);
        JButton medium = createButton("Medium", 280);
        JButton hard = createButton("Hard", 360);

        add(easy); add(medium); add(hard);

        easy.addActionListener(e -> frame.startGame(150));
        medium.addActionListener(e -> frame.startGame(75));
        hard.addActionListener(e -> frame.startGame(40));
    }

    private JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setBounds(200, y, 200, 50);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 150, 50));
        button.setBorder(BorderFactory.createEmptyBorder());

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 200, 70));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 150, 50));
            }
        });
        return button;
    }
}

class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;

    char direction;
    boolean running = false;
    boolean gameOver = false;
    Timer timer;
    Random random;
    int delay;
    SnakeGame parentFrame;

    GamePanel(int delay, SnakeGame parentFrame) {
        this.delay = delay;
        this.parentFrame = parentFrame;
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        gameOver = false;
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        newApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        draw(g2);
    }

    private void drawBackground(Graphics2D g2) {
        GradientPaint gp = new GradientPaint(0, 0, new Color(30, 30, 30), 600, 600, new Color(10, 10, 10));
        g2.setPaint(gp);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void draw(Graphics2D g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                g.setColor(i == 0 ? Color.GREEN : new Color(34, 139, 34));
                g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 12, 12);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Segoe UI", Font.BOLD, 28));
            g.drawString("Score: " + applesEaten, 20, 40);
        } else {
            drawGameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
            case 'L': x[0] -= UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                gameOver = true;
            }
        }

        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
            gameOver = true;
        }

        if (!running) {
            timer.stop();
        }
    }

    private void drawGameOver(Graphics2D g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Segoe UI", Font.BOLD, 60));
        g.drawString("Game Over", 150, SCREEN_HEIGHT / 2 - 30);

        g.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Score: " + applesEaten, 240, SCREEN_HEIGHT / 2 + 20);
        g.drawString("Press R to Retry", 190, SCREEN_HEIGHT / 2 + 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (running) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') direction = 'D';
                        break;
                }
            } else if (gameOver) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    startGame();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    parentFrame.returnToMenu();
                }
            }
        }
    }
}
