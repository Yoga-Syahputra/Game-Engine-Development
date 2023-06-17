import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import javax.swing.colorchooser.*;
import java.awt.Rectangle;

public class BrinaryEnine {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel objectPanel;
    private JPanel sidebarPanel;
    private JButton rectangleButton;
    private JButton circleButton;
    private JButton saveButton;
    private JButton playButton;
    private JButton deleteButton;
    private JLabel selectedObject;
    private JButton colorButton;

    private int initialX;
    private int initialY;
    private ArrayList<JLabel> objects;

    private JLabel playerCircle;
    private int score;
    private JLabel scoreLabel;
    private JLabel gameOverLabel;
    private JButton restartButton;
    private JButton exitButton;
    private ArrayList<Color> circleColors;
    private JButton squareButton;
    private ArrayList<Color> squareColors;
    private Color selectedSquareColor;

    private Random random;

    private Color selectedColor; // Menambahkan variabel untuk warna terpilih

    public BrinaryEngine() {

         random = new Random();

        frame = new JFrame("Game Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        mainPanel = new JPanel(new BorderLayout());

        startPanel = new JPanel(null);
        JButton newFileButton = new JButton("New Project");
        newFileButton.setBounds(700, 350, 120, 30);
        newFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFileButtonClicked();
            }
        });

        startPanel.add(newFileButton);

        mainPanel.add(startPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void newFileButtonClicked() {
        showMainPanel();
    }

    private void showMainPanel() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();

         startPanel = new JPanel(null) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon imageIcon = new ImageIcon("main.jpg");  // Ganti dengan path file gambar background yang diinginkan
            Image image = imageIcon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };
    startPanel.setLayout(null);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("main.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        gamePanel.setLayout(null);

        objectPanel = new JPanel();
        objectPanel.setBackground(Color.LIGHT_GRAY);
        objectPanel.setPreferredSize(new Dimension(100, 600));
        objectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

        JLabel objectLabel = new JLabel("Objects");
        objectPanel.add(objectLabel);

        rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRectangle(0, 0);
            }
        });
        objectPanel.add(rectangleButton);

        colorButton = new JButton("Select Color");
colorButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        Color selected = JColorChooser.showDialog(frame, "Select Color", selectedColor);
        if (selected != null) {
            selectedColor = selected;
        }
    }
});
objectPanel.add(colorButton);
colorButton.setOpaque(true);


    squareButton = new JButton("Square");
squareButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        createSquare(0, 0);
    }
});
objectPanel.add(squareButton);

squareColors = new ArrayList<>();
squareColors.add(Color.RED);
squareColors.add(Color.GREEN);
squareColors.add(Color.BLUE);
selectedSquareColor = squareColors.get(0);



        circleButton = new JButton("Circle");
        circleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCircle(0, 0);
            }
        });
        objectPanel.add(circleButton);

        mainPanel.add(startPanel, BorderLayout.CENTER);
        mainPanel.add(objectPanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(173, 216, 230));
        sidebarPanel.setPreferredSize(new Dimension(100, 600));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveObjects();
            }
        });
        buttonPanel.add(saveButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedObject();
            }
        });
        buttonPanel.add(deleteButton);

        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });
        buttonPanel.add(playButton);

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(buttonPanel);
        sidebarPanel.add(Box.createVerticalGlue());

        mainPanel.add(sidebarPanel, BorderLayout.EAST);

        objects = new ArrayList<>();
        score = 0;

        scoreLabel = new JLabel("Score: " + score);
        mainPanel.add(scoreLabel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void createRectangle(int x, int y) {
        JLabel rectangle = new JLabel();
        rectangle.setOpaque(true);
       rectangle.setBackground(selectedColor);
 // Menggunakan warna terpilih
        rectangle.setBounds(x, y, 50, 200);
        rectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rectangle.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                selectedObject = rectangle;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rectangle.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        rectangle.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = rectangle.getX() + e.getX() - rectangle.getWidth() / 2;
                int newY = rectangle.getY() + e.getY() - rectangle.getHeight() / 2;
                rectangle.setLocation(newX, newY);
            }
        });
        rectangle.setTransferHandler(new TransferHandler("text"));
        gamePanel.add(rectangle);
        gamePanel.repaint();

        objects.add(rectangle);
    }

    private void createCircle(int x, int y) {
        JLabel circle = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(selectedColor); // Menggunakan warna terpilih
                g2d.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                g2d.dispose();
            }
        };
        
        circle.setBackground(selectedColor);
        circle.setOpaque(true);
        circle.setBounds(x, y, 50, 50);
        circle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                circle.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                selectedObject = circle;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                circle.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        circle.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = circle.getX() + e.getX() - circle.getWidth() / 2;
                int newY = circle.getY() + e.getY() - circle.getHeight() / 2;
                circle.setLocation(newX, newY);
            }
        });
        circle.setTransferHandler(new TransferHandler("text"));
        gamePanel.add(circle);
        gamePanel.repaint();

        objects.add(circle);

        if (playerCircle == null) {
            playerCircle = circle;
        }
    }

    private void createSquare(int x, int y) {
    JLabel square = new JLabel();
    square.setOpaque(true);
    square.setBackground(selectedSquareColor);
    square.setBounds(x, y, 50, 50);
    square.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            square.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            selectedObject = square;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            square.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    });
    square.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            int newX = square.getX() + e.getX() - square.getWidth() / 2;
            int newY = square.getY() + e.getY() - square.getHeight() / 2;
            square.setLocation(newX, newY);
        }
    });
    square.setTransferHandler(new TransferHandler("text"));
    gamePanel.add(square);
    gamePanel.repaint();

    objects.add(square);
}


    private void saveObjects() {
        for (JLabel obj : objects) {
            String type;
            if (obj instanceof JLabel) {
                type = "Rectangle";
            } else {
                type = "Circle";
            }
            System.out.println("Type: " + type + ", X: " + obj.getX() + ", Y: " + obj.getY() + ", Color: " + selectedColor);
        }
    }

    private void deleteSelectedObject() {
        if (selectedObject != null) {
            gamePanel.remove(selectedObject);
            objects.remove(selectedObject);
            gamePanel.repaint();
            selectedObject = null;
        }
    }

  private void playGame() {
    for (JLabel obj : objects) {
        if (obj != playerCircle) {
            animateObject(obj);
        }
    }

    // Tambahkan objek tambahan dengan kecepatan acak
    for (int i = 0; i < 50; i++) {
        int x = random.nextInt(gamePanel.getWidth());
        int y = -random.nextInt(500);
        JLabel additionalObject = new JLabel();
        additionalObject.setOpaque(true);
        additionalObject.setBackground(Color.YELLOW);
        additionalObject.setBounds(x, y, 50, 50);
        gamePanel.add(additionalObject);
        gamePanel.repaint();
        objects.add(additionalObject);
        animateObject(additionalObject);
    }
}


 private void animateObject(JLabel object) {
    int startX = object.getX();
    int startY = object.getY();

    Timer timer = new Timer(10, new ActionListener() {
        int x = startX;
        int y = startY;
        int speed = random.nextInt(15) + 1; // Kecepatan acak antara 1-5

        @Override
        public void actionPerformed(ActionEvent e) {
            y += speed; // Menggunakan kecepatan yang dihasilkan
            object.setLocation(x, y);
            gamePanel.repaint();
            checkCollision(object);
            updateScore(object);
            checkBoundary(object);
            spawnDroid(object);
        }
    });

    timer.start();
}



private void spawnDroid(JLabel object) {
    if (random.nextInt(100) < 1) { // Peluang 5% untuk menghasilkan droid
        JLabel droid = new JLabel();
        droid.setIcon(new ImageIcon("red_character.png")); // Ganti dengan path gambar droid yang sesuai
        droid.setSize(30, 30);
        droid.setLocation(object.getX() + object.getWidth() / 2 - droid.getWidth() / 2,
                object.getY() + object.getHeight() / 2 - droid.getHeight() / 2);
        gamePanel.add(droid);
        gamePanel.repaint();

        Timer timer = new Timer(10, new ActionListener() {
            int y = droid.getY();
            int speed = 3; // Kecepatan droid

            @Override
            public void actionPerformed(ActionEvent e) {
                y += speed;
                droid.setLocation(droid.getX(), y);
                gamePanel.repaint();
                if (droid.getBounds().intersects(playerCircle.getBounds())) {
                    gamePanel.remove(droid);
                    score += 10; // Menambahkan 10 poin ke score
                    scoreLabel.setText("Score: " + score);
                } else if (y > gamePanel.getHeight()) {
                    gamePanel.remove(droid);
                }
            }
        });

        timer.start();
    }
}

private void checkCollision(JLabel object) {
    if (object.getBounds().intersects(playerCircle.getBounds())) {
        gamePanel.setBackground(Color.RED);
        gamePanel.remove(object);
        playerCircle.setBackground(Color.RED);
        showGameOver(score);
    }
}

private void updateScore(JLabel object) {
    if (object.getX() + object.getWidth() == playerCircle.getX()) {
        score++;
        scoreLabel.setText("Score: " + score);
    }
}

private void checkBoundary(JLabel object) {
    if (object.getY() >= gamePanel.getHeight()) {
        gamePanel.remove(object);
    }
}
 private void showGameOver(int finalScore) {
    gamePanel.removeAll();
    gamePanel.repaint();

    gameOverLabel = new JLabel("Game Over");
    gameOverLabel.setFont(new Font("Helvetica", Font.BOLD, 50));
    gameOverLabel.setBounds(500, 200, 400, 200);
    gamePanel.add(gameOverLabel);

    restartButton = new JButton("Play Again");
    restartButton.setBounds(550, 600, 120, 30);
    restartButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            resetGame();
            gamePanel.requestFocusInWindow(); // Fokus ke panel permainan setelah mengklik tombol bermain kembali
        }
    });
    gamePanel.add(restartButton);

    exitButton = new JButton("Exit");
    exitButton.setBounds(700, 600, 80, 30);
    exitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });
    gamePanel.add(exitButton);

    scoreLabel.setText("Final Score: " + finalScore);
    scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
    scoreLabel.setHorizontalAlignment(JLabel.CENTER);
    scoreLabel.setVerticalAlignment(JLabel.CENTER);
    scoreLabel.setBounds(0, 400, gamePanel.getWidth(), 100);
    gamePanel.add(scoreLabel);

    gamePanel.revalidate();
    gamePanel.repaint();
}


    private void resetGame() {
        gamePanel.removeAll();
        gamePanel.setBackground(Color.WHITE);
        gamePanel.repaint();

        playerCircle = null;
        score = 0;
        scoreLabel.setText("Score: " + score);

        for (JLabel obj : objects) {
            gamePanel.add(obj);
            if (obj instanceof JLabel) {
                if (obj.getBounds().intersects(playerCircle.getBounds())) {
                    playerCircle = (JLabel) obj;
                }
            }
        }
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.awt.geom.Ellipse2D;
        import java.util.ArrayList;
        import java.util.Random;
        import java.awt.Color;
        import javax.swing.colorchooser.*;
        import java.awt.Rectangle;
        
        public class BrinaryEngine {
            private JFrame frame;
            private JPanel mainPanel;
            private JPanel startPanel;
            private JPanel gamePanel;
            private JPanel objectPanel;
            private JPanel sidebarPanel;
            private JButton rectangleButton;
            private JButton circleButton;
            private JButton saveButton;
            private JButton playButton;
            private JButton deleteButton;
            private JLabel selectedObject;
            private JButton colorButton;
        
            private int initialX;
            private int initialY;
            private ArrayList<JLabel> objects;
        
            private JLabel playerCircle;
            private int score;
            private JLabel scoreLabel;
            private JLabel gameOverLabel;
            private JButton restartButton;
            private JButton exitButton;
            private ArrayList<Color> circleColors;
            private JButton squareButton;
            private ArrayList<Color> squareColors;
            private Color selectedSquareColor;
        
            private Random random;
        
            private Color selectedColor; // Menambahkan variabel untuk warna terpilih
        
            public BrinaryEngine() {
        
                 random = new Random();
        
                frame = new JFrame("Game Engine");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                mainPanel = new JPanel(new BorderLayout());
        
                startPanel = new JPanel(null);
                JButton newFileButton = new JButton("New Project");
                newFileButton.setBounds(700, 350, 120, 30);
                newFileButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFileButtonClicked();
                    }
                });
        
                startPanel.add(newFileButton);
        
                mainPanel.add(startPanel, BorderLayout.CENTER);
        
                frame.add(mainPanel, BorderLayout.CENTER);
        
                frame.setVisible(true);
            }
        
            private void newFileButtonClicked() {
                showMainPanel();
            }
        
            private void showMainPanel() {
                mainPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();
        
                 startPanel = new JPanel(null) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon imageIcon = new ImageIcon("main.jpg");  // Ganti dengan path file gambar background yang diinginkan
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
            startPanel.setLayout(null);
        
                gamePanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        ImageIcon imageIcon = new ImageIcon("main.jpg");
                        Image image = imageIcon.getImage();
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                    }
                };
                gamePanel.setLayout(null);
        
                objectPanel = new JPanel();
                objectPanel.setBackground(Color.LIGHT_GRAY);
                objectPanel.setPreferredSize(new Dimension(100, 600));
                objectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        
                JLabel objectLabel = new JLabel("Objects");
                objectPanel.add(objectLabel);
        
                rectangleButton = new JButton("Rectangle");
                rectangleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createRectangle(0, 0);
                    }
                });
                objectPanel.add(rectangleButton);
        
                colorButton = new JButton("Select Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(frame, "Select Color", selectedColor);
                if (selected != null) {
                    selectedColor = selected;
                }
            }
        });
        objectPanel.add(colorButton);
        colorButton.setOpaque(true);
        
        
            squareButton = new JButton("Square");
        squareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSquare(0, 0);
            }
        });
        objectPanel.add(squareButton);
        
        squareColors = new ArrayList<>();
        squareColors.add(Color.RED);
        squareColors.add(Color.GREEN);
        squareColors.add(Color.BLUE);
        selectedSquareColor = squareColors.get(0);
        
        
        
                circleButton = new JButton("Circle");
                circleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createCircle(0, 0);
                    }
                });
                objectPanel.add(circleButton);
        
                mainPanel.add(startPanel, BorderLayout.CENTER);
                mainPanel.add(objectPanel, BorderLayout.WEST);
                mainPanel.add(gamePanel, BorderLayout.CENTER);
        
                sidebarPanel = new JPanel();
                sidebarPanel.setBackground(new Color(173, 216, 230));
                sidebarPanel.setPreferredSize(new Dimension(100, 600));
                sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
        
                saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveObjects();
                    }
                });
                buttonPanel.add(saveButton);
        
                deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteSelectedObject();
                    }
                });
                buttonPanel.add(deleteButton);
        
                playButton = new JButton("Play");
                playButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playGame();
                    }
                });
                buttonPanel.add(playButton);
        
                sidebarPanel.add(Box.createVerticalGlue());
                sidebarPanel.add(buttonPanel);
                sidebarPanel.add(Box.createVerticalGlue());
        
                mainPanel.add(sidebarPanel, BorderLayout.EAST);
        
                objects = new ArrayList<>();
                score = 0;
        
                scoreLabel = new JLabel("Score: " + score);
                mainPanel.add(scoreLabel, BorderLayout.SOUTH);
        
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        
            private void createRectangle(int x, int y) {
                JLabel rectangle = new JLabel();
                rectangle.setOpaque(true);
               rectangle.setBackground(selectedColor);
         // Menggunakan warna terpilih
                rectangle.setBounds(x, y, 50, 200);
                rectangle.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        rectangle.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        selectedObject = rectangle;
                    }
        
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        rectangle.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                });
                rectangle.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int newX = rectangle.getX() + e.getX() - rectangle.getWidth() / 2;
                        int newY = rectangle.getY() + e.getY() - rectangle.getHeight() / 2;
                        rectangle.setLocation(newX, newY);
                    }
                });
                rectangle.setTransferHandler(new TransferHandler("text"));
                gamePanel.add(rectangle);
                gamePanel.repaint();
        
                objects.add(rectangle);
            }
        
            private void createCircle(int x, int y) {
                JLabel circle = new JLabel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(selectedColor); // Menggunakan warna terpilih
                        g2d.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                        g2d.dispose();
                    }
                };
                
                circle.setBackground(selectedColor);
                circle.setOpaque(true);
                circle.setBounds(x, y, 50, 50);
                circle.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        circle.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        selectedObject = circle;
                    }
        
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        circle.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                });
                circle.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int newX = circle.getX() + e.getX() - circle.getWidth() / 2;
                        int newY = circle.getY() + e.getY() - circle.getHeight() / 2;
                        circle.setLocation(newX, newY);
                    }
                });
                circle.setTransferHandler(new TransferHandler("text"));
                gamePanel.add(circle);
                gamePanel.repaint();
        
                objects.add(circle);
        
                if (playerCircle == null) {
                    playerCircle = circle;
                }
            }
        
            private void createSquare(int x, int y) {
            JLabel square = new JLabel();
            square.setOpaque(true);
            square.setBackground(selectedSquareColor);
            square.setBounds(x, y, 50, 50);
            square.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    square.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    selectedObject = square;
                }
        
                @Override
                public void mouseReleased(MouseEvent e) {
                    square.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            square.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int newX = square.getX() + e.getX() - square.getWidth() / 2;
                    int newY = square.getY() + e.getY() - square.getHeight() / 2;
                    square.setLocation(newX, newY);
                }
            });
            square.setTransferHandler(new TransferHandler("text"));
            gamePanel.add(square);
            gamePanel.repaint();
        
            objects.add(square);
        }
        
        
            private void saveObjects() {
                for (JLabel obj : objects) {
                    String type;
                    if (obj instanceof JLabel) {
                        type = "Rectangle";
                    } else {
                        type = "Circle";
                    }
                    System.out.println("Type: " + type + ", X: " + obj.getX() + ", Y: " + obj.getY() + ", Color: " + selectedColor);
                }
            }
        
            private void deleteSelectedObject() {
                if (selectedObject != null) {
                    gamePanel.remove(selectedObject);
                    objects.remove(selectedObject);
                    gamePanel.repaint();
                    selectedObject = null;
                }
            }
        
          private void playGame() {
            for (JLabel obj : objects) {
                if (obj != playerCircle) {
                    animateObject(obj);
                }
            }
        
            // Tambahkan objek tambahan dengan kecepatan acak
            for (int i = 0; i < 50; i++) {
                int x = random.nextInt(gamePanel.getWidth());
                int y = -random.nextInt(500);
                JLabel additionalObject = new JLabel();
                additionalObject.setOpaque(true);
                additionalObject.setBackground(Color.YELLOW);
                additionalObject.setBounds(x, y, 50, 50);
                gamePanel.add(additionalObject);
                gamePanel.repaint();
                objects.add(additionalObject);
                animateObject(additionalObject);
            }
        }
        
        
         private void animateObject(JLabel object) {
            int startX = object.getX();
            int startY = object.getY();
        
            Timer timer = new Timer(10, new ActionListener() {
                int x = startX;
                int y = startY;
                int speed = random.nextInt(15) + 1; // Kecepatan acak antara 1-5
        
                @Override
                public void actionPerformed(ActionEvent e) {
                    y += speed; // Menggunakan kecepatan yang dihasilkan
                    object.setLocation(x, y);
                    gamePanel.repaint();
                    checkCollision(object);
                    updateScore(object);
                    checkBoundary(object);
                    spawnDroid(object);
                }
            });
        
            timer.start();
        }
        
        
        
        private void spawnDroid(JLabel object) {
            if (random.nextInt(100) < 1) { // Peluang 5% untuk menghasilkan droid
                JLabel droid = new JLabel();
                droid.setIcon(new ImageIcon("red_character.png")); // Ganti dengan path gambar droid yang sesuai
                droid.setSize(30, 30);
                droid.setLocation(object.getX() + object.getWidth() / 2 - droid.getWidth() / 2,
                        object.getY() + object.getHeight() / 2 - droid.getHeight() / 2);
                gamePanel.add(droid);
                gamePanel.repaint();
        
                Timer timer = new Timer(10, new ActionListener() {
                    int y = droid.getY();
                    int speed = 3; // Kecepatan droid
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        y += speed;
                        droid.setLocation(droid.getX(), y);
                        gamePanel.repaint();
                        if (droid.getBounds().intersects(playerCircle.getBounds())) {
                            gamePanel.remove(droid);
                            score += 10; // Menambahkan 10 poin ke score
                            scoreLabel.setText("Score: " + score);
                        } else if (y > gamePanel.getHeight()) {
                            gamePanel.remove(droid);
                        }
                    }
                });
        
                timer.start();
            }
        }
        
        private void checkCollision(JLabel object) {
            if (object.getBounds().intersects(playerCircle.getBounds())) {
                gamePanel.setBackground(Color.RED);
                gamePanel.remove(object);
                playerCircle.setBackground(Color.RED);
                showGameOver(score);
            }
        }
        
        private void updateScore(JLabel object) {
            if (object.getX() + object.getWidth() == playerCircle.getX()) {
                score++;
                scoreLabel.setText("Score: " + score);
            }
        }
        
        private void checkBoundary(JLabel object) {
            if (object.getY() >= gamePanel.getHeight()) {
                gamePanel.remove(object);
            }
        }
         private void showGameOver(int finalScore) {
            gamePanel.removeAll();
            gamePanel.repaint();
        
            gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setFont(new Font("Helvetica", Font.BOLD, 50));
            gameOverLabel.setBounds(500, 200, 400, 200);
            gamePanel.add(gameOverLabel);
        
            restartButton = new JButton("Play Again");
            restartButton.setBounds(550, 600, 120, 30);
            restartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                    gamePanel.requestFocusInWindow(); // Fokus ke panel permainan setelah mengklik tombol bermain kembali
                }
            });
            gamePanel.add(restartButton);
        
            exitButton = new JButton("Exit");
            exitButton.setBounds(700, 600, 80, 30);
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            gamePanel.add(exitButton);
        
            scoreLabel.setText("Final Score: " + finalScore);
            scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
            scoreLabel.setHorizontalAlignment(JLabel.CENTER);
            scoreLabel.setVerticalAlignment(JLabel.CENTER);
            scoreLabel.setBounds(0, 400, gamePanel.getWidth(), 100);
            gamePanel.add(scoreLabel);
        
            gamePanel.revalidate();
            gamePanel.repaint();
        }
        
        
            private void resetGame() {
                gamePanel.removeAll();
                gamePanel.setBackground(Color.WHITE);
                gamePanel.repaint();
        
                playerCircle = null;
                score = 0;
                scoreLabel.setText("Score: " + score);
        
                for (JLabel obj : objects) {
                    gamePanel.add(obj);
                    if (obj instanceof JLabel) {
                        if (obj.getBounds().intersects(playerCircle.getBounds())) {
                            playerCircle = (JLabel) obj;
                        }
                    }
                }
        
                showMainPanel();
            }
        
            public static void main(String[] args) {
                new BrinaryEngine();
            }
        }
        showMainPanel();
    }

    public static void main(String[] args) {
        new BrinaryEngine();
    }
}