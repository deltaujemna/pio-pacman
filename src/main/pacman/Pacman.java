package pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Pacman extends LivingEntity {
    private int lives = 3;
    private int score = 0;
    private double powerUpTimeLeft = 0;
    private int killedGhostsStreak = 0;
    long timeRenderCircle; //zmienna pomocnicza do animowania ruchu Pacmana
    private final int POWERUP_TIME = 15;
    private int dotsLeft = 199;
    protected boolean automaticTest = false;

    Ghost[] ghosts;
    CollectableEntity[][] dots;
    ArrayList<Fruit> fruits = new ArrayList<>();

    MazeFrame mazeFrame;
    private final MovePacman movePacman;

    public Pacman(int x, int y, int size, double speed, MazeFrame mazeframe) {
        this.startX = toPixels(x);
        this.startY = toPixels(y);
        this.x = toPixels(x);
        this.y = toPixels(y);
        this.width = size;
        this.height = size;
        this.speed = speed;
        this.direction = Direction.RIGHT;
        this.timeRenderCircle = System.nanoTime();
        this.mazeFrame = mazeframe;
        alive = true;
        movePacman = new MovePacman(this);
    }

    //przekazanie informacji o duchach do tej klasy
    public void pushGhosts(Ghost[] ghosts) {
        this.ghosts = ghosts;
    }

    // przekazanie położenie kulek do tej klasy
    public void pushDots(CollectableEntity[][] dots) {
        this.dots = dots;
    }

    // przekazanie położenie owoców do tej klasy
    public void pushFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
    }

    public void addScore(int value) {
        score += value;
    }

    // nastąpiła kolizja z duchem
    public void collision(Ghost g) {
        if (g.alive) {
            if (g.isFrightened()) {
                addScore(g.points * (++killedGhostsStreak));
                g.die();
            } else {
                loseLife();
            }
        }
    }

    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    private void loseLife() {
        killedGhostsStreak = 0;
        lives--;
        if (lives > 0) {
            direction = Direction.RIGHT;
            directionFuture = Direction.RIGHT;
            teleport(startX, startY);
            for (Ghost ghost : ghosts)
                ghost.teleport(ghost.startX, ghost.startY);
            mazeFrame.maze.pause();
        } else if (lives == 0) {
            alive = false;
            endGame();
        }
    }

    public void endGame() {
        mazeFrame.running = false;
        mazeFrame.maze.timer.cancel();
        if (!automaticTest) {
            mazeFrame.dispose();
            Game.menu.setVisible(true);
            JOptionPane.showMessageDialog(Game.menu, "KONIEC GRY!\nZdobyte punkty: " + score,
                    "Koniec gry", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // uruchomienie powerupa
    public void activatePowerup() {
        powerUpTimeLeft = POWERUP_TIME;
        for (Ghost ghost : ghosts) {
            ghost.setFearTimeLeft();
        }
    }

    @Override
    public void tick() {
        movePacman.move();
        if (powerUpTimeLeft > 0) {
            powerUpTimeLeft -= (double) 1 / 60;
        }

        if (ghosts != null && !Debug.disableGhostCollision) {
            for (Ghost g : ghosts) {
                if (getBounds().intersects(g.getBounds())) {
                    collision(g);
                }
            }
        }

        if (dots != null) {
            if (dots[toCells(y + Maze.cellSize / 2)][toCells(x + Maze.cellSize / 2)] != null &&
                    dots[toCells(y + Maze.cellSize / 2)][toCells(x + Maze.cellSize / 2)].renderable) {
                dots[toCells(y + Maze.cellSize / 2)][toCells(x + Maze.cellSize / 2)].pickup(this);
                if (--dotsLeft == 0) {
                    mazeFrame.maze.levelUp();
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.RIGHT;
                    dotsLeft = 199;
                }
            }
        }

        if (fruits != null) {
            for (Fruit f : fruits) {
                if (getBounds().intersects(f.getBounds())) {
                    f.pickup(this);
                }
            }
        }
    }


    private String decidePacmanImageForRender() {
        String imgPath = "";
        if (direction == Direction.UP) {
            imgPath = "Images/pacman_up.png";
        }
        if (direction == Direction.RIGHT) {
            imgPath = "Images/pacman_right.png";
        }
        if (direction == Direction.LEFT) {
            imgPath = "Images/pacman_left.png";
        }
        if (direction == Direction.DOWN) {
            imgPath = "Images/pacman_down.png";
        }
        return imgPath;
    }

    private void renderLives(Graphics g) {
        g.setColor(Color.YELLOW);
        for (int i = 0; i < lives; i++) {
            g.fillOval((int) ((340 + i * 20 + Maze.deltaX) * Maze.scale), (int) ((2 + Maze.deltaY) * Maze.scale), (int) (13 * Maze.scale), (int) (13 * Maze.scale));
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("Score: " + this.score, (int) ((14 + Maze.deltaX) * Maze.scale), (int) ((14 + Maze.deltaY) * Maze.scale));
        g.drawString("Lives: ", (int) ((340 + Maze.deltaX) * Maze.scale - g.getFontMetrics().getStringBounds("Lives: ", g).getWidth()), (int) ((14 + Maze.deltaY) * Maze.scale));
        g.drawString("Level: " + mazeFrame.maze.getLevel(), (int) ((170 + Maze.deltaX) * Maze.scale), (int) ((14 + Maze.deltaY) * Maze.scale));

        renderLives(g);

        String imgPath;
        if (System.nanoTime() - timeRenderCircle >= 0.15e9) {

            imgPath = decidePacmanImageForRender();
            try {
                g.drawImage(ImageIO.read(new File(imgPath)), (int) ((this.x + Maze.deltaX) * Maze.scale), (int) ((this.y + Maze.deltaY) * Maze.scale),
                        (int) (this.width * Maze.scale), (int) (this.height * Maze.scale), null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (System.nanoTime() - timeRenderCircle >= 0.3e9)
                timeRenderCircle = System.nanoTime();
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval((int) ((x + Maze.deltaX) * Maze.scale), (int) ((y + Maze.deltaY) * Maze.scale), (int) (width * Maze.scale), (int) (height * Maze.scale));
        }
    }

    public int getScore() {
        return score;
    }
}