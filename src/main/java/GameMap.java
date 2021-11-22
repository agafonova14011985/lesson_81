
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameMap extends JPanel {
    public static final int MODE_VS_AI = 0;
    public static final int MODE_VS_HUMAN = 1;
    public static final Random RANDOM = new Random();
    public static final int MODE_VS_HUMAN2 = 3;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;
    private static final int DOT_HUMAN2 = 3;
    private static final int DOT_EMPTY = 0;
    private static final int DOT_PADDING = 7;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_WIN_HUMAN2 = 3;

    private int stateGameOver;
    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int cellWidth;
    private int cellHeight;
    private boolean isGameOver;
    private boolean isInitialized;
    private int gameMode;
    private int playerNumTurn;


    public GameMap() {
        isInitialized = false;
        //адаптер реагирующий на отпускания мыши
        addMouseListener(new MouseAdapter() {
            @Override//
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                update(e);
            }
        });
    }
    //метод реагирующий на клик мышкой, сдделать
    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) {
            return; //если гамовер или не инициализированна игра то ни чего не делаем
        }//реакция на клик мыши
        //еесли
        if (!playerTurn(e)) {
            return;
        }//проверка на победу
        if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) {
            return;//если победа то прерывается игра
        }
        aiTurn();
        repaint();
        //проверка статуса игры
        if (gameCheck(DOT_AI, STATE_WIN_AI)) {
            return;
        }

        /////////////////////////1
        if (isGameOver || !isInitialized) {
            return; //если гамовер или не инициализированна игра то ни чего не делаем
        }//реакция на клик мыши

        if (!playerTurn2(e)) {
            return;
        }//проверка на победу
        if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) {
            return;//если победа то прерывается игра
        }
        //humanTurn();
        repaint();
        //проверка статуса игры
        if (gameCheck(DOT_HUMAN2, STATE_WIN_HUMAN2)) {
            return;
        }

    }
///////////////////////////
private boolean playerTurn2(MouseEvent event) {
    int cellX = event.getX() / cellWidth;
    int cellY = event.getY() / cellHeight;
    if (!isCellValid(cellY, cellX) || !isCellEmpty(cellY, cellX)) {
        return false;//если пустая то мы ничего не делаем
    }
    field[cellY][cellX] = DOT_HUMAN2;repaint();//вызываем метод для перересовки поля
    return true;
}


    //ход игрока
    private boolean playerTurn(MouseEvent event) {
        int cellX = event.getX() / cellWidth;
        int cellY = event.getY() / cellHeight;
        if (!isCellValid(cellY, cellX) || !isCellEmpty(cellY, cellX)) {
            return false;//если пустая то мы ничего не делаем
        }
        field[cellY][cellX] = DOT_HUMAN;//жестко присваиваем  хуману ьакак намереваемся играть с компом
        repaint();//вызываем метод для перересовки поля
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //рисуем метод рисующий линии
        super.paintComponent(g);
        render(g);
    }
    //рисование
    private void render(Graphics g) {
        if (!isInitialized) {
            return;
        }
        int width = getWidth();//ширина поля
        int height = getHeight();//высота поля
        cellWidth = width / fieldSizeX;//делим ширину на размер поля
        cellHeight = height / fieldSizeY;
        g.setColor(Color.BLACK);
//рисуем горизонтальные линии
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }
        //рисуем  вертикальные линии
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellHeight;
            g.drawLine(x, 0, x, height);
        }//если ячейка пустая то мы ни чего не далаем
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) {
                    continue;
                }//если лежит фишка игрока то будем рисовать кружок или что угодно
                if (field[y][x] == DOT_HUMAN) {
                    g.setColor(Color.GREEN);
                    //координаты и размеры овала
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                    //в противном случае красный прямоугольник
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }
            }
        }//проверка
        if (isGameOver) {
            showGameOverMessage(g);
        }
    }
    //метод старта игры
    public void startNewGame(int gameMode, int fieldSize, int winLength) {
        this.gameMode = gameMode;
        fieldSizeX = fieldSize;
        fieldSizeY = fieldSize;
        this.winLength = winLength;
        playerNumTurn = 1;//начало хода
        field = new int[fieldSizeY][fieldSizeX];
        isInitialized = true;
        isGameOver = false;
        repaint();//обновилось что бы
    }
    //метоод выводящий сообщение о победе
    private void showGameOverMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        //прямоугольнок
        g.fillRect(0, getHeight() / 2 - 60, getWidth(), 120);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        switch (stateGameOver)
        {
            //если ничья то пишем ничья и тд
            case STATE_DRAW -> g.drawString("DRAW", getWidth() / 4, getHeight() / 2);
            case STATE_WIN_HUMAN -> g.drawString("HUMAN WIN!", getWidth() / 4, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString("AI WIN", getWidth() / 4, getHeight() / 2);
        }
    }


    private  boolean gameCheck(int dot, int stateGameOver) {

        if (checkWin(dot, winLength)) {
            this.stateGameOver = stateGameOver;
            isGameOver = true;
            repaint();
            return true;
        }
        if (checkDraw()) {
            this.stateGameOver = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) return false;
            }
        }
        return true;
    }



    private void aiTurn() {
        if (scanField(DOT_AI, winLength)) return;        // проверка выигрыша компа
        if (scanField(DOT_HUMAN, winLength)) return;    // проверка выигрыша игрока на след ходу
        if (scanField(DOT_AI, winLength - 1)) return;
        if (scanField(DOT_HUMAN, winLength - 1)) return;
        if (scanField(DOT_AI, winLength - 2)) return;
        if (scanField(DOT_HUMAN, winLength - 2)) return;
        aiTurnEasy();
    }

    private void aiTurnEasy() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    private boolean scanField(int dot, int length) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) {                // поставим фишку в каждую клетку поля по очереди
                    field[y][x] = dot;
                    if (checkWin(dot, length)) {
                        if (dot == DOT_AI) return true;    // если комп выигрывает, то оставляем
                        if (dot == DOT_HUMAN) {
                            field[y][x] = DOT_AI;            // Если выигрывает игрок ставим туда 0
                            return true;
                        }
                    }
                    field[y][x] = DOT_EMPTY;            // если никто ничего, то возвращаем как было
                }
            }
        }
        return false;
    }




    private boolean checkWin(int dot, int length) {
        for (int y = 0; y < fieldSizeY; y++) {            // проверяем всё поле
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkLine(x, y, 1, 0, length, dot)) return true;    // проверка  по +х
                if (checkLine(x, y, 1, 1, length, dot)) return true;    // проверка по диагонали +х +у
                if (checkLine(x, y, 0, 1, length, dot)) return true;    // проверка линию по +у
                if (checkLine(x, y, 1, -1, length, dot)) return true;    // проверка по диагонали +х -у
            }
        }
        return false;
    }

    // проверка линии
    private boolean checkLine(int x, int y, int incrementX, int incrementY, int len, int dot) {
        int endXLine = x + (len - 1) * incrementX;            // конец линии по Х
        int endYLine = y + (len - 1) * incrementY;            // конец по У
        if (!isCellValid(endYLine, endXLine)) return false;    // Выход линии за пределы
        for (int i = 0; i < len; i++) {                    // идем по линии
            if (field[y + i * incrementY][x + i * incrementX] != dot) return false;    // символы одинаковые?
        }
        return true;
    }

    private boolean isCellValid(int y, int x) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private boolean isCellEmpty(int y, int x) {
        return field[y][x] == DOT_EMPTY;
    }

    //void twoPlayersMode(){
      //  if()

}