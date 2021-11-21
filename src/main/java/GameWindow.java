import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 640;
    private GameMap gameMap;

    public GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);//по центру
        setTitle("TicTacToe");
        setResizable(false);
        JButton btnStart = new JButton("<html><body><b>START</b></body></html>");
        JButton btnExit = new JButton("EXIT");
        JPanel buttonPanel = new JPanel();//панель с кнопками
        buttonPanel.setLayout(new GridLayout(1, 2));//1 строка 2 столбца
        buttonPanel.add(btnStart);
        buttonPanel.add(btnExit);
        add(buttonPanel, BorderLayout.SOUTH);//добавление панели низ
        gameMap = new GameMap();//игровое поле
        SettingWindow settings = new SettingWindow(this);//окно настроек
        add(gameMap, BorderLayout.CENTER);
        setVisible(true);
        btnStart.addActionListener(e -> settings.setVisible(true));//кнопака старта
        btnExit.addActionListener(e -> System.exit(0));//кнопка выхода из игры
    }

    public void startGame(int gameMode, int fieldSize, int winLength) {
        gameMap.startNewGame(gameMode, fieldSize, winLength);
        System.out.printf("Mode: %d, Size: %d length: %d\n", gameMode, fieldSize, winLength);
    }
}