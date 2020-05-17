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
        for (Ghost ghost : ghosts) {
            ghost.pushPacmanX(pacman.x);
            ghost.pushPacmanY(pacman.y);
            ghost.pushPacmanDirection(pacman.direction);
            ghost.pushPacmanDirectionFuture(pacman.directionFuture);
            ghost.tick();
        }
        pacman.tick();
    }

    //Rysuje jedną całą klatkę gry
    //TODO - usunąć niepotrzebne drawBoard'y jak już skończymy prace nad fullscreenem
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //drawBoard(g);
        //drawBoardCenter(g);
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
                    if ((randX == 9 && randY == 7) || (randX == 8 && randY == 8) || (randX == 9 && randY == 8) || (randX == 10 && randY == 8)) {
                    } else if (grid[randY][randX] && !yellowDots[randY][randX].renderable) {
                        try {
                            fruits.add(new Fruit(randX, randY));
                        } catch (ConcurrentModificationException ignored) {
                        }
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

    //Rysuje planszę na panelu
    public void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 440, 460);

        g.setColor(Color.WHITE);
        g.drawRect(19, 19, 382, 382);

        g.setColor(Color.BLUE);
        g.fillRect(40, 40, 60, 20);
        g.fillRect(120, 40, 60, 20);
        g.fillRect(200, 20, 20, 40);
        g.fillRect(240, 40, 60, 20);
        g.fillRect(320, 40, 60, 20);
        g.fillRect(40, 80, 60, 20);
        g.fillRect(160, 80, 100, 20);
        g.fillRect(200, 80, 20, 60);
        g.fillRect(320, 80, 60, 20);

        g.fillRect(20, 120, 80, 60);
        g.fillRect(320, 120, 80, 60);
        g.fillRect(20, 200, 80, 60);
        g.fillRect(320, 200, 80, 60);

        g.fillRect(160, 160, 40, 20);
        g.fillRect(220, 160, 40, 20);
        g.fillRect(160, 180, 20, 20);
        g.fillRect(160, 200, 100, 20);
        g.fillRect(240, 180, 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect(120, 120, 60, 20);
        g.fillRect(120, 80, 20, 100);
        g.fillRect(280, 80, 20, 100);
        g.fillRect(240, 120, 60, 20);

        g.fillRect(280, 200, 20, 60);
        g.fillRect(120, 200, 20, 60);
        g.fillRect(160, 240, 100, 20);
        g.fillRect(200, 260, 20, 40);

        g.fillRect(120, 280, 60, 20);
        g.fillRect(240, 280, 60, 20);

        g.fillRect(40, 280, 60, 20);
        g.fillRect(80, 280, 20, 60);
        g.fillRect(320, 280, 60, 20);
        g.fillRect(320, 280, 20, 60);

        g.fillRect(20, 320, 40, 20);
        g.fillRect(360, 320, 40, 20);
        g.fillRect(160, 320, 100, 20);
        g.fillRect(200, 320, 20, 60);

        g.fillRect(40, 360, 140, 20);
        g.fillRect(240, 360, 140, 20);
        g.fillRect(280, 320, 20, 40);
        g.fillRect(120, 320, 20, 60);
    }

    public static double scale = 1.5;

    // przydałoby się wydobyć dla poszczególnego komputera jego full screen,
    public static int deltaX = 400; // tak akurat dla 1.5 wygląda dobrze, ale np. dla scale = 2 to 200 i 50
    public static int deltaY = 100;

    public void drawBoardCenter(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0 + deltaX, 0 + deltaY, 440, 460);

        g.setColor(Color.WHITE);
        g.drawRect(19 + deltaX, 19 + deltaY, 382, 382);

        g.setColor(Color.BLUE);
        g.fillRect(40 + deltaX, 40 + deltaY, 60, 20);
        g.fillRect(120 + deltaX, 40 + deltaY, 60, 20);
        g.fillRect(200 + deltaX, 20 + deltaY, 20, 40);
        g.fillRect(240 + deltaX, 40 + deltaY, 60, 20);
        g.fillRect(320 + deltaX, 40 + deltaY, 60, 20);
        g.fillRect(40 + deltaX, 80 + deltaY, 60, 20);
        g.fillRect(160 + deltaX, 80 + deltaY, 100, 20);
        g.fillRect(200 + deltaX, 80 + deltaY, 20, 60);
        g.fillRect(320 + deltaX, 80 + deltaY, 60, 20);

        g.fillRect(20 + deltaX, 120 + deltaY, 80, 60);
        g.fillRect(320 + deltaX, 120 + deltaY, 80, 60);
        g.fillRect(20 + deltaX, 200 + deltaY, 80, 60);
        g.fillRect(320 + deltaX, 200 + deltaY, 80, 60);

        g.fillRect(160 + deltaX, 160 + deltaY, 40, 20);
        g.fillRect(220 + deltaX, 160 + deltaY, 40, 20);
        g.fillRect(160 + deltaX, 180 + deltaY, 20, 20);
        g.fillRect(160 + deltaX, 200 + deltaY, 100, 20);
        g.fillRect(240 + deltaX, 180 + deltaY, 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect(120 + deltaX, 120 + deltaY, 60, 20);
        g.fillRect(120 + deltaX, 80 + deltaY, 20, 100);
        g.fillRect(280 + deltaX, 80 + deltaY, 20, 100);
        g.fillRect(240 + deltaX, 120 + deltaY, 60, 20);

        g.fillRect(280 + deltaX, 200 + deltaY, 20, 60);
        g.fillRect(120 + deltaX, 200 + deltaY, 20, 60);
        g.fillRect(160 + deltaX, 240 + deltaY, 100, 20);
        g.fillRect(200 + deltaX, 260 + deltaY, 20, 40);

        g.fillRect(120 + deltaX, 280 + deltaY, 60, 20);
        g.fillRect(240 + deltaX, 280 + deltaY, 60, 20);

        g.fillRect(40 + deltaX, 280 + deltaY, 60, 20);
        g.fillRect(80 + deltaX, 280 + deltaY, 20, 60);
        g.fillRect(320 + deltaX, 280 + deltaY, 60, 20);
        g.fillRect(320 + deltaX, 280 + deltaY, 20, 60);

        g.fillRect(20 + deltaX, 320 + deltaY, 40, 20);
        g.fillRect(360 + deltaX, 320 + deltaY, 40, 20);
        g.fillRect(160 + deltaX, 320 + deltaY, 100, 20);
        g.fillRect(200 + deltaX, 320 + deltaY, 20, 60);

        g.fillRect(40 + deltaX, 360 + deltaY, 140, 20);
        g.fillRect(240 + deltaX, 360 + deltaY, 140, 20);
        g.fillRect(280 + deltaX, 320 + deltaY, 20, 40);
        g.fillRect(120 + deltaX, 320 + deltaY, 20, 60);
    }

    public void drawBoardCenterAndBigger(Graphics g) {
        // UWAGA: zakładamy, że szerokość okna jest większa niż wysokość
        final double boardScale = 0.75; // procent ekranu, jaki plansza ma zajmować (w pionie)
        final int boardSize = 420; // rozmiar boku planszy bez skalowania w pikselach

        final int screenWidth = mazeFrame.getWidth();
        final int screenHeight = mazeFrame.getHeight();

        scale = screenHeight * boardScale / boardSize;

        deltaX = (int) (((double) screenWidth / scale - boardSize) / 2);
        deltaY = (int) (((double) screenHeight / scale - boardSize) / 2);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.WHITE);
        g.drawRect((int) ((19 + deltaX) * scale), (int) ((19 + deltaY) * scale), (int) (382 * scale), (int) (382 * scale));

        g.setColor(Color.BLUE);
        g.fillRect((int) ((40 + deltaX) * scale), (int) ((40 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((120 + deltaX) * scale), (int) ((40 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((200 + deltaX) * scale), (int) ((20 + deltaY) * scale), (int) (20 * scale), (int) (40 * scale));
        g.fillRect((int) ((240 + deltaX) * scale), (int) ((40 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((40 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((40 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((160 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (100 * scale), (int) (20 * scale));
        g.fillRect((int) ((200 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));

        g.fillRect((int) ((20 + deltaX) * scale), (int) ((120 + deltaY) * scale), (int) (80 * scale), (int) (60 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((120 + deltaY) * scale), (int) (80 * scale), (int) (60 * scale));
        g.fillRect((int) ((20 + deltaX) * scale), (int) ((200 + deltaY) * scale), (int) (80 * scale), (int) (60 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((200 + deltaY) * scale), (int) (80 * scale), (int) (60 * scale));

        g.fillRect((int) ((160 + deltaX) * scale), (int) ((160 + deltaY) * scale), (int) (40 * scale), (int) (20 * scale));
        g.fillRect((int) ((220 + deltaX) * scale), (int) ((160 + deltaY) * scale), (int) (40 * scale), (int) (20 * scale));
        g.fillRect((int) ((160 + deltaX) * scale), (int) ((180 + deltaY) * scale), (int) (20 * scale), (int) (20 * scale));
        g.fillRect((int) ((160 + deltaX) * scale), (int) ((200 + deltaY) * scale), (int) (100 * scale), (int) (20 * scale));
        g.fillRect((int) ((240 + deltaX) * scale), (int) ((180 + deltaY) * scale), (int) (20 * scale), (int) (20 * scale));

        g.setColor(Color.BLUE);
        g.fillRect((int) ((120 + deltaX) * scale), (int) ((120 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((120 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (20 * scale), (int) (100 * scale));
        g.fillRect((int) ((280 + deltaX) * scale), (int) ((80 + deltaY) * scale), (int) (20 * scale), (int) (100 * scale));
        g.fillRect((int) ((240 + deltaX) * scale), (int) ((120 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));

        g.fillRect((int) ((280 + deltaX) * scale), (int) ((200 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));
        g.fillRect((int) ((120 + deltaX) * scale), (int) ((200 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));
        g.fillRect((int) ((160 + deltaX) * scale), (int) ((240 + deltaY) * scale), (int) (100 * scale), (int) (20 * scale));
        g.fillRect((int) ((200 + deltaX) * scale), (int) ((260 + deltaY) * scale), (int) (20 * scale), (int) (40 * scale));

        g.fillRect((int) ((120 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((240 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));

        g.fillRect((int) ((40 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((80 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (60 * scale), (int) (20 * scale));
        g.fillRect((int) ((320 + deltaX) * scale), (int) ((280 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));

        g.fillRect((int) ((20 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (40 * scale), (int) (20 * scale));
        g.fillRect((int) ((360 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (40 * scale), (int) (20 * scale));
        g.fillRect((int) ((160 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (100 * scale), (int) (20 * scale));
        g.fillRect((int) ((200 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));

        g.fillRect((int) ((40 + deltaX) * scale), (int) ((360 + deltaY) * scale), (int) (140 * scale), (int) (20 * scale));
        g.fillRect((int) ((240 + deltaX) * scale), (int) ((360 + deltaY) * scale), (int) (140 * scale), (int) (20 * scale));
        g.fillRect((int) ((280 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (20 * scale), (int) (40 * scale));
        g.fillRect((int) ((120 + deltaX) * scale), (int) ((320 + deltaY) * scale), (int) (20 * scale), (int) (60 * scale));
    }

    public void drawDots(Graphics g) {
        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                if (yellowDots[i][j] != null && activeDots(i, j))
                    yellowDots[i][j].render(g);
            }
        }
    }
}