package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Класс StartWindow - стартовое окно приложения с основными кнопками для перехода в другие части программы.
 * Оно также отображает информацию о курсовой работе, а также выполняет обработку бездействия пользователя.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class StartWindow extends JFrame {
    private InactivityTimer inactivityTimer;

    /**
     * Конструктор окна, инициализирует интерфейс и таймер бездействия.
     */
    public StartWindow() {
        UtilsUI.setAppIcon(this);
        initializeTimer();

        setTitle("Анализатор текста  ver. 1.1.2.2024");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        UtilsUI.disableFocusForAllButtons(this);
    }

    /**
     * Инициализирует таймер бездействия, который отслеживает, если пользователь не взаимодействует с приложением.
     */
    private void initializeTimer() {
        inactivityTimer = new InactivityTimer(this, 60 * 1000); // 1 минута бездействия
        inactivityTimer.startTimer(); // Запуск таймера
    }

    /**
     * Создает верхнюю панель с информацией о курсовой работе и учебном заведении.
     *
     * @return Панель с текстовой информацией.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Вертикальное расположение элементов

        JLabel universityLabel = createLabel("Белорусский национальный технический университет",
                Font.PLAIN, 16);
        JLabel facultyLabel = createLabel("Факультет информационных технологий и робототехники",
                Font.PLAIN, 16);
        JLabel departmentLabel = createLabel("Кафедра программного обеспечения информационных систем и технологий",
                Font.PLAIN, 16);
        JLabel courseLabel = createLabel("Курсовая работа", Font.BOLD, 20);
        JLabel disciplineLabel = createLabel("по дисциплине «Программирование на языке Java»",
                Font.PLAIN, 18);
        JLabel taskLabel = createLabel("Анализ текста", Font.BOLD, 24);

        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(universityLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(facultyLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(departmentLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        topPanel.add(courseLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(disciplineLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(taskLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        return topPanel;
    }

    /**
     * Создает центральную панель с изображением и информацией о студенте и преподавателе.
     *
     * @return Центральная панель.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Левая часть - изображение
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 100, 0));
        imagePanel.add(createImageLabel());
        centerPanel.add(imagePanel, BorderLayout.WEST);

        // Правая часть - информация
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel combinedInfoLabel = new JLabel(
                "<html><div style='text-align: left;'>"
                        + "Выполнил: Студент группы 10702322<br>Климук Максим Николаевич</div>"
                        + "<div style='margin-top: 20px; text-align: left;'>Преподаватель: к.ф.-м.н., доц.<br>"
                        + "Сидорик Валерий Владимирович</div></html>",
                SwingConstants.RIGHT
        );
        combinedInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoPanel.add(combinedInfoLabel);

        centerPanel.add(infoPanel, BorderLayout.EAST);

        return centerPanel;
    }

    /**
     * Создает нижнюю панель с годом, кнопками и отступами.
     *
     * @return Нижняя панель.
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel minskYearLabel = createLabel("Минск 2024", Font.PLAIN, 16);
        bottomPanel.add(minskYearLabel, BorderLayout.NORTH);

        // Добавляем промежуточный отступ
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));

        // Кнопка "Начать"
        JButton jbtStart = createButton("Начать", _ -> openMainWindow());
        jbtStart.setFont(new Font("Arial", Font.BOLD, 18));
        jbtStart.setBackground(new Color(117, 255, 189));

        // Кнопка "О программе"
        JButton jbtAboutProgram = createButton("О программе", _ -> openAboutProgramWindow());
        jbtAboutProgram.setFont(new Font("Arial", Font.PLAIN, 18));

        // Кнопка "Об авторе"
        JButton jbtAboutAuthor = createButton("Об авторе", _ -> openAboutAuthorWindow());
        jbtAboutAuthor.setFont(new Font("Arial", Font.PLAIN, 18));

        // Кнопка "Выход"
        JButton jbtExit = createButton("Выход", _ -> System.exit(0));
        jbtExit.setFont(new Font("Arial", Font.BOLD, 18));
        jbtExit.setBackground(new Color(253, 164, 164));

        buttonsPanel.add(jbtStart);
        buttonsPanel.add(jbtAboutProgram);
        buttonsPanel.add(jbtAboutAuthor);
        buttonsPanel.add(Box.createRigidArea(new Dimension(150, 0))); // Отступ между группами кнопок
        buttonsPanel.add(jbtExit);

        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    /**
     * Создает метку с заданным текстом, стилем и размером шрифта.
     *
     * @param text Текст на метке.
     * @param style Стиль шрифта (например, Font.PLAIN или Font.BOLD).
     * @param size Размер шрифта.
     * @return Метка с текстом.
     */
    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", style, size));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Создает кнопку с заданным текстом и обработчиком события.
     *
     * @param text Текст кнопки.
     * @param action Действие, которое будет выполнено при нажатии на кнопку.
     * @return Кнопка.
     */
    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(action);
        return button;
    }

    /**
     * Создает метку с изображением, которое загружается из ресурсов.
     *
     * @return Метка с изображением.
     */
    private JLabel createImageLabel() {
        URL resource = getClass().getResource("/resources/icon.png");
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            Image scaledImage = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
        } else {
            return new JLabel("Изображение не найдено", SwingConstants.CENTER);
        }
    }

    /**
     * Открывает основное окно программы.
     */
    private void openMainWindow() {
        inactivityTimer.stopTimer();
        inactivityTimer.removeEventListener();
        MainWindow mainWindow = new MainWindow(this);
        mainWindow.setVisible(true);
        setVisible(false);
    }

    /**
     * Открывает окно с информацией о программе.
     */
    private void openAboutProgramWindow() {
        inactivityTimer.stopTimer();
        inactivityTimer.removeEventListener();
        AboutProgramWindow aboutProgramWindow = new AboutProgramWindow(this);
        aboutProgramWindow.setVisible(true);
        setVisible(false);
    }

    /**
     * Открывает окно с информацией об авторе программы.
     */
    private void openAboutAuthorWindow() {
        inactivityTimer.stopTimer();
        inactivityTimer.removeEventListener();
        AboutAuthorWindow aboutAuthorWindow = new AboutAuthorWindow(this);
        aboutAuthorWindow.setVisible(true);
        setVisible(false);
    }
}




