import java.awt.Graphics;
import java.awt.Color;

public class Pacman extends LivingEntity {

    private int lives = 3;
    private int score = 0;
    private double powerupTimeLeft = 0;
    private int killedGhostsStreak = 0;

    private final int POWERUP_TIME = 15;

    Ghost[] ghosts;
    CollectableEntity[][] dots;
    Fruit[] fruits;

    public Pacman(int x, int y, int size, double speed) {
        this.x = toPixels(x) + BOARD_START_X;
        this.y = toPixels(y) + BOARD_START_Y;
        this.width = size;
        this.height = size;
        this.speed = speed;
        this.direction = Direction.RIGHT;
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
        if (canMove()) {
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
        if (powerupTimeLeft > 0) {
            addScore(g.points);
            killedGhostsStreak++;
            g.die();
        } else {
            loseLife();
        }
    }

    // zmiana kierunku
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // utrata życia
    private void loseLife() {
        killedGhostsStreak = 0;
        lives--;
        if (lives > 0) {
            // TODO: aktualnie respawn jest na środku mapy, ewentualnie zmienić
            teleport((420 - width) / 2, (460 - height) / 2);
            // TODO: forEach Ghost teleport na start
        }
        // TODO: else koniec gry
    }

    @Override
    public void tick() {
        move();

        if (powerupTimeLeft > 0) {
            powerupTimeLeft -= (double) 1 / 60;
        }

        if(ghosts != null) {
            for (Ghost g : ghosts) {
                if (getBounds().intersects(g.getBounds())) {
                    collision(g);
                }
            }
        }

        if(dots != null) {
            if (dots[toCells(y - BOARD_START_Y)][toCells(x - BOARD_START_X)] != null) {
                dots[toCells(y - BOARD_START_Y)][toCells(x - BOARD_START_X)].pickup(this);
            }
        }

        if(fruits != null) {
            for (Fruit f : fruits) {
                if (getBounds().intersects(f.getBounds())) {
                    f.pickup(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width + 1, height + 1);
    }

}
