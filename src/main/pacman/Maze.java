package pacman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

public class Maze extends JPanel {
    static int cellSize = 20; //rozmiar pojedynczej komórki na planszy
    boolean[][] grid = new boolean[19][19];  //[y][x]
    boolean[][] dots = new boolean[19][19]; // zamiast tego można by stosować yellowDots[i][j].renderable, ale
    // plus tego jest taki, że dots[][] nie wymaga istnienia obiektów klasy Dots
    Ghost[] ghosts;
    private final ArrayList<Fruit> fruits = new ArrayList<>();
    Pacman pacman;
    private CollectableEntity[][] yellowDots;
    private int level;
    Timer timer = new Timer();
    protected int pauseLeft; // ilość pozostałych klatek pauzy

    private final int[][] powerDotPos = {{2, 0}, {16, 0}, {2, 18}, {16, 18}};

    MazeFrame mazeFrame;

    public Maze(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
        level = 1;
        resetBoard();
    }

    public boolean activeDots(int i, int j) {
        return dots[i][j];
    }

    public void update() {
        if (pauseLeft == 0) {
            for (Ghost ghost : ghosts) {
                ghost.pushPacmanX(pacman.x);
                ghost.pushPacmanY(pacman.y);
                ghost.pushPacmanDirection(pacman.direction);
                ghost.pushPacmanDirectionFuture(pacman.directionFuture);
                ghost.tick();
            }
            pacman.tick();
        } else {
            pauseLeft--;
        }
    }

    //Rysuje jedną całą klatkę gry
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardCenterAndBigger(g);

        pacman.render(g);
        drawDots(g);
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
        try {
            for (Fruit fruit : fruits)
                fruit.render(g);
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void deleteDotsFromCage() {
        dots[7][9] = false;
        dots[8][8] = false;
        dots[8][9] = false;
        dots[8][10] = false;
    }

    public void levelUp() {
        level++;
        resetBoard();
    }

    public void pause() {
        pauseLeft = 120;
    }

    public void resetBoard() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                grid[i][j] = true; //przed ustawieniem ścian można się poruszać po wszystkich komórkach
                dots[i][j] = true;
            }
        }

        fruits.clear();
        deleteDotsFromCage();
        updateEntireMap();

        ghosts = new Ghost[4];
        ghosts[0] = new Ghost(8, 8, 1); //komórka 8,8
        ghosts[1] = new Ghost(8, 8, 2); //komórka 8,8
        ghosts[2] = new Ghost(8, 8, 3); //komórka 8,8
        ghosts[3] = new Ghost(8, 8, 4); //komórka 8,8

        if (level == 1)
            pacman = new Pacman(9, 10, 20, 1, mazeFrame);
        else {
            pacman.x = pacman.startX;
            pacman.y = pacman.startY;
        }

        yellowDots = new CollectableEntity[19][19];

        if (level == 1) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int randX = (int) (Math.random() * 18);
                    int randY = (int) (Math.random() * 18);
                    try {
                        if (grid[randY][randX] && !yellowDots[randY][randX].renderable && !(randX >= 8 && randX <= 10 && (randY == 7 || randY == 8))) {
                            fruits.add(new Fruit(randX, randY));
                        }
                    } catch (ConcurrentModificationException | NullPointerException ignored) {
                    }
                }
            }, 5000, 5000);
        }

        for (int[] singlePowerDotPos : powerDotPos) {
            yellowDots[singlePowerDotPos[1]][singlePowerDotPos[0]] = new PowerDot(singlePowerDotPos[0], singlePowerDotPos[1]);
        }

        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                if (activeDots(i, j) && yellowDots[i][j] == null)
                    yellowDots[i][j] = new Dot(j, i);
            }
        }

        ghosts[0].grid = grid;
        ghosts[1].grid = grid;
        ghosts[2].grid = grid;
        ghosts[3].grid = grid;
        pacman.grid = grid;
        pacman.pushGhosts(ghosts);
        pacman.pushDots(yellowDots);
        pacman.pushFruits(fruits);

        pause();
    }

    /*Aktualizuje fragment mapy - dla prostokąta przekazanego w argumencie (x, y - współrzędne
    górnego lewego wierzchołka, width, height - wymiary na prawo i do dołu) ustawia wartość
    elementu na false (ściana) - czyli po danej komórce nie można się poruszać ani ustawić w niej kropki*/
    public void updateMap(int x, int y, int width, int height) {
        for (int i = x / cellSize; i < x / cellSize + width / cellSize; i++) {
            for (int j = y / cellSize; j < y / cellSize + height / cellSize; j++) {
                grid[j - 1][i - 1] = false;
                dots[j - 1][i - 1] = false;
            }
        }
    }

    //Aktualizuje całą mapę ("generuje ściany")
    public void updateEntireMap() {
        updateMap(40, 40, 60, 20);
        updateMap(120, 40, 60, 20);
        updateMap(200, 20, 20, 40);
        updateMap(240, 40, 60, 20);
        updateMap(320, 40, 60, 20);
        updateMap(40, 80, 60, 20);
        updateMap(160, 80, 100, 20);
        updateMap(200, 80, 20, 60);
        updateMap(320, 80, 60, 20);
        updateMap(20, 120, 80, 60);
        updateMap(320, 120, 80, 60);
        updateMap(20, 200, 80, 60);
        updateMap(320, 200, 80, 60);
        updateMap(160, 160, 40, 20);
        updateMap(220, 160, 40, 20);
        updateMap(160, 180, 20, 20);
        updateMap(160, 200, 100, 20);
        updateMap(240, 180, 20, 20);
        updateMap(120, 120, 60, 20);
        updateMap(120, 80, 20, 100);
        updateMap(280, 80, 20, 100);
        updateMap(240, 120, 60, 20);
        updateMap(280, 200, 20, 60);
        updateMap(120, 200, 20, 60);
        updateMap(160, 240, 100, 20);
        updateMap(200, 260, 20, 40);
        updateMap(120, 280, 60, 20);
        updateMap(240, 280, 60, 20);
        updateMap(40, 280, 60, 20);
        updateMap(80, 280, 20, 60);
        updateMap(320, 280, 60, 20);
        updateMap(320, 280, 20, 60);
        updateMap(20, 320, 40, 20);
        updateMap(360, 320, 40, 20);
        updateMap(160, 320, 100, 20);
        updateMap(200, 320, 20, 60);
        updateMap(40, 360, 140, 20);
        updateMap(240, 360, 140, 20);
        updateMap(280, 320, 20, 60);
        updateMap(120, 320, 20, 60);
    }

    public static double scale;

    public static int deltaX;
    public static int deltaY;

    // zwraca 1, jeśli po ścianie po początku w a i długości b powstaje szczelina
    public int fixWalls(double a, double b) {
        a *= scale;
        b *= scale;
        return (int) (Math.round(a + b) - (Math.round(a) + Math.round(b)));
    }

    public void drawWall(Graphics g, int x, int y, int w, int h) {
        g.fillRect((int) ((x + deltaX) * scale), (int) Math.round((y + deltaY) * scale), (int) (w * scale), (int) Math.round(h * scale) + fixWalls(y + deltaY, h));
    }

    public void drawBoardCenterAndBigger(Graphics g) {
        final double boardScale = 0.9; // procent ekranu, jaki plansza ma zajmować (w mniejszym rozmiarze okna)
        final int boardSize = 420; // rozmiar boku planszy bez skalowania w pikselach

        final int screenWidth = mazeFrame.getWidth();
        final int screenHeight = mazeFrame.getHeight();

        scale = Math.min(screenWidth, screenHeight) * boardScale / boardSize;

        deltaX = (int) (((double) screenWidth / scale - boardSize) / 2);
        deltaY = (int) (((double) screenHeight / scale - boardSize) / 2);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.WHITE);
        g.drawRect((int) ((19 + deltaX) * scale), (int) Math.round((19 + deltaY) * scale), (int) (382 * scale), (int) Math.round(382 * scale));

        g.setColor(Color.BLUE);
        drawWall(g, 40, 40, 60, 20);
        drawWall(g, 120, 40, 60, 20);
        drawWall(g, 200, 20, 20, 40);
        drawWall(g, 240, 40, 60, 20);
        drawWall(g, 320, 40, 60, 20);
        drawWall(g, 40, 80, 60, 20);
        drawWall(g, 160, 80, 100, 20);
        drawWall(g, 200, 80, 20, 60);
        drawWall(g, 320, 80, 60, 20);

        drawWall(g, 20, 120, 80, 60);
        drawWall(g, 320, 120, 80, 60);
        drawWall(g, 20, 200, 80, 60);
        drawWall(g, 320, 200, 80, 60);

        drawWall(g, 160, 160, 40, 20);
        drawWall(g, 160, 180, 20, 20);
        drawWall(g, 220, 160, 40, 20);
        drawWall(g, 160, 200, 100, 20);
        drawWall(g, 240, 180, 20, 20);

        drawWall(g, 120, 120, 60, 20);
        drawWall(g, 120, 80, 20, 100);
        drawWall(g, 280, 80, 20, 100);
        drawWall(g, 240, 120, 60, 20);

        drawWall(g, 280, 200, 20, 60);
        drawWall(g, 120, 200, 20, 60);
        drawWall(g, 160, 240, 100, 20);
        drawWall(g, 200, 260, 20, 40);

        drawWall(g, 120, 280, 60, 20);
        drawWall(g, 240, 280, 60, 20);

        drawWall(g, 40, 280, 60, 20);
        drawWall(g, 80, 280, 20, 60);
        drawWall(g, 320, 280, 60, 20);
        drawWall(g, 320, 280, 20, 60);

        drawWall(g, 20, 320, 40, 20);
        drawWall(g, 360, 320, 40, 20);
        drawWall(g, 160, 320, 100, 20);
        drawWall(g, 200, 320, 20, 60);

        drawWall(g, 40, 360, 140, 20);
        drawWall(g, 240, 360, 140, 20);
        drawWall(g, 280, 320, 20, 40);
        drawWall(g, 120, 320, 20, 60);
    }

    public void drawDots(Graphics g) {
        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                if (yellowDots[i][j] != null && activeDots(i, j))
                    yellowDots[i][j].render(g);
            }
        }
    }

    public int getLevel() {
        return level;
    }
}