import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Pacman extends LivingEntity {

    private int lives = 3;
    private int score = 0;
    private double powerUpTimeLeft = 0;
    private int killedGhostsStreak = 0;
    long timeRenderCircle;              //zmienna pomocnicza do animowania ruchu Pacmana
    private final int POWERUP_TIME = 15;
    private int dotsLeft = 200;

    Ghost[] ghosts;
    CollectableEntity[][] dots;
    Fruit[] fruits;

    public Pacman(int x, int y, int size, double speed) {
        this.startX = toPixelsX(x);
        this.startY = toPixelsY(y);
        this.x = toPixelsX(x);
        this.y = toPixelsY(y);
        this.width = size;
        this.height = size;
        this.speed = speed;
        this.direction = Direction.RIGHT;
        this.timeRenderCircle = System.nanoTime();
    }

    // przekazanie położenie ścian do tej klasy
    public void pushGhosts(Ghost[] ghosts) {
        this.ghosts = ghosts;
    }

    // przekazanie położenie kulek do tej klasy
    public void pushDots(CollectableEntity[][] dots) {
        this.dots = dots;
    }

    // przekazanie położenie owoców do tej klasy
    public void pushFruits(Fruit[] fruits) {
        this.fruits = fruits;
    }

    // dodaje punkty
    public void addScore(int value) {
        score += value;
    }

    // porusza w ustalonym kierunku
    public void move() {
        if (teleport()) {

        } else if (super.canMove()) {
            switch (direction) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
        }

    }

    // nastąpiła kolizja z duchem
    public void collision(Ghost g) {
        if (powerUpTimeLeft > 0) {
            addScore(g.points);
            killedGhostsStreak++;
            g.die();
        } else {
            loseLife();
        }
    }

    // zmiana kierunku
    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    // utrata życia
    private void loseLife() {
        killedGhostsStreak = 0;
        lives--;
        if (lives > 0) {
            // TODO: jeśli pacman przed śmiercią szedł w lewo to zmiana kierunku jest ignorowana, warto naprawić
            direction = Direction.RIGHT;
            teleport(startX, startY);
            for(Ghost ghost : ghosts) {
                ghost.direction = Direction.RIGHT; // to chyba będzie można usunąć
                ghost.teleport(ghost.startX, ghost.startY);
            }
        }
        // TODO: else koniec gry
    }

    @Override
    public void tick() {
        move();

        if (powerUpTimeLeft > 0) {
            powerUpTimeLeft -= (double) 1 / 60;
        }

        if (ghosts != null) {
            for (Ghost g : ghosts) {
                if (getBounds().intersects(g.getBounds())) {
                    collision(g);
                }
            }
        }

        if (dots != null) {
            if (dots[toCellsY(y + Maze.cellSize / 2)][toCellsX(x + Maze.cellSize / 2)] != null &&
                    dots[toCellsY(y + Maze.cellSize / 2)][toCellsX(x + Maze.cellSize / 2)].renderable) {
                dots[toCellsY(y + Maze.cellSize / 2)][toCellsX(x + Maze.cellSize / 2)].pickup(this);
                //System.out.println("pozostalo kulek: " + (dotsLeft - 1));
                if(--dotsLeft == 0) {
                    // TODO: zrobić przejście do następnego poziomu
                    System.err.println("brawo");
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

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("Score: " + this.score, 14, 14);
        g.drawString("Lives: ",  280, 14);
        //g.setColor(Color.YELLOW);
        //g.fillOval(x, y, width, height);
        if(lives>=1) {
            g.setColor(Color.YELLOW);
            g.fillOval(330, 2, 13, 13);
        }
        if(lives>=2) {
            g.setColor(Color.YELLOW);
            g.fillOval(350, 2, 13, 13);
        }
        if(lives>=3) {
            g.setColor(Color.YELLOW);
            g.fillOval(370, 2, 13, 13);
        }

        String imgPath = "";
        if(System.nanoTime() - timeRenderCircle >= 0.15e9) {
            if (direction == Direction.UP) {
                imgPath = "Images/pacman_up.png";
                try {
                    g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (direction == Direction.RIGHT) {
                imgPath = "Images/pacman_right.png";
                try {
                    g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (direction == Direction.LEFT) {
                imgPath = "Images/pacman_left.png";
                try {
                    g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (direction == Direction.DOWN) {
                imgPath = "Images/pacman_down.png";
                try {
                    g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(System.nanoTime() - timeRenderCircle >= 0.3e9)
                timeRenderCircle=System.nanoTime();
        }
        else{
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, width, height);
        }
    }


}
