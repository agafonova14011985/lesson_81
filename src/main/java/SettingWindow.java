
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingWindow extends JFrame {
    private static final int WINDOW_WIDTH = 350;//ширина
    private static final int WINDOW_HEIGHT = 300;//высота окна
    private static final int MIN_WIN_LENGTH = 3;//размер поля мин
    private static final int MIN_FIELD_SIZE = 3;//длинна выиграшной последовательности
    private static final int MAX_FIELD_SIZE = 10;
    private static final String FIELD_SIZE_PREFIX = "Field size: ";
    private static final String WIN_LENGTH_PREFIX = "Win length: ";
 //выбор режимов игры
    private JRadioButton humanVsAi; //человек против компа
    private JRadioButton humanVsHuman;// человек против человека
    private JSlider sliderWinLength;
    private JSlider sliderFieldSize;
    private GameWindow gameWindow;

    public SettingWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        //размеры ширина высота
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //расположение
        setLocationRelativeTo(gameWindow);
        setResizable(false);
        setTitle("New game settings");
        setLayout(new GridLayout(10, 1));// таблица 10 строк и 1 столбец


        addGameMode();

        addFieldSize();

        //Добавляем кнопку старта
        JButton btnStart = new JButton("Start new game");
        btnStart.addActionListener(e -> submitSettings(gameWindow));//слушатель
        add(btnStart);
    }

    //выбор размера поля
    private void addFieldSize() {
        JLabel labelFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel labelWinLength = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);
        sliderFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        sliderWinLength = new JSlider(MIN_WIN_LENGTH, MAX_FIELD_SIZE, MIN_WIN_LENGTH);
        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = sliderFieldSize.getValue();
                labelFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
                sliderWinLength.setMaximum(currentValue);//ограничение длинны победы
            }
        });

        //слайдер длины победы
        sliderWinLength.addChangeListener(
                e -> labelWinLength.setText(WIN_LENGTH_PREFIX + sliderWinLength.getValue())
        );
        //добавляем
        add(new JLabel("Choose field size"));
        add(labelFieldSize);
        add(sliderFieldSize);
        add(new JLabel("Choose win length"));
        add(labelWinLength);
        add(sliderWinLength);
    }
    //выбор игрового режима
    private void addGameMode() {
        add(new JLabel("Choose game mode:"));
        humanVsAi = new JRadioButton("Human versus AI", true);
        humanVsHuman = new JRadioButton("Human versus human");
        ButtonGroup gameMode = new ButtonGroup();//что бы выбрать один режим и выбралась одна кнопка
        gameMode.add(humanVsAi);
        gameMode.add(humanVsHuman);
        add(humanVsAi);//добавление
        add(humanVsHuman);
    }

    private void submitSettings(GameWindow gameWindow) {
        int gameMode;//условие при выборе режима игры
        if (humanVsAi.isSelected()) {
            gameMode = GameMap.MODE_VS_AI;
        } else {
            gameMode = GameMap.MODE_VS_HUMAN;
        }
        int fieldSize = sliderFieldSize.getValue();
        int winLength = sliderWinLength.getValue();
        //обращаемся к методу и вызываем запуск игры
        gameWindow.startGame(gameMode, fieldSize, winLength);
        setVisible(false);//скроем окно
    }

}