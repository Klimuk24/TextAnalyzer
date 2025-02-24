package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Класс AboutProgramWindow представляет окно "О программе".
 * Включает описание функций программы, а также кнопки выхода и возврата к предыдущему окну.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class AboutProgramWindow extends JFrame {
    /**
     * Конструктор, инициализирует окно с описанием программы.
     *
     * @param previousWindow Окно, которое будет отображаться после закрытия текущего окна
     */
    public AboutProgramWindow(JFrame previousWindow) {
        UtilsUI.setAppIcon(this);

        setTitle("О программе");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Главная панель с элементами
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(previousWindow), BorderLayout.SOUTH);

        add(mainPanel);
        UtilsUI.disableFocusForAllButtons(this);
    }

    /**
     * Создает верхнюю панель с заголовком программы.
     *
     * @return Панель с заголовком
     */
    private JPanel createHeaderPanel() {
        JLabel headerLabel = new JLabel("Анализ текста", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER); // Добавляем метку с заголовком в панель
        return headerPanel;
    }

    /**
     * Создает центральную панель с описанием функций программы и изображением.
     *
     * @return Центральная панель с описанием и изображением
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Панель с описанием функций программы
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Функции программы"));

        JLabel descriptionLabel = getjLabel(); // Получаем описание функций программы
        descriptionPanel.add(descriptionLabel);

        centerPanel.add(descriptionPanel, BorderLayout.CENTER);

        // Панель с изображением
        JLabel imageLabel = createImageLabel(); // Создаем метку с изображением
        centerPanel.add(imageLabel, BorderLayout.WEST);

        return centerPanel;
    }

    /**
     * Создает метку с описанием функций программы.
     *
     * @return Метка с HTML-форматированным списком функций программы
     */
    private static JLabel getjLabel() {
        JLabel descriptionLabel = new JLabel("<html><ol>" +
                "<li>Анализировать текстовые данные.</li>" +
                "<li>Подсчитывать количество слов, предложений, типов предложений.</li>" +
                "<li>Загружать текст из файлов.</li>" +
                "<li>Очищать текстовое окно.</li>" +
                "<li>Выполнять анализ с выводом результатов.</li>" +
                "<li>Сохранять результаты анализа в отдельный текстовый файл.</li>" +
                "</ol></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Увеличенный шрифт
        return descriptionLabel;
    }

    /**
     * Создает метку с изображением.
     * Изображение масштабируется, если оно слишком большое.
     *
     * @return Метка с изображением или текстом, если изображение не найдено
     */
    private JLabel createImageLabel() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/icon.png")));
        if (icon.getIconWidth() > 0) {
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
        } else {
            return new JLabel("Изображение не найдено", SwingConstants.CENTER);
        }
    }

    /**
     * Создает нижнюю панель с кнопками и информацией о версии программы.
     *
     * @param previousWindow Окно, которое будет отображаться после закрытия текущего окна
     * @return Нижняя панель с кнопками и текстом о версии
     */
    private JPanel createBottomPanel(JFrame previousWindow) {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Кнопка "Назад"
        JButton jbtBack = new JButton("Назад");
        jbtBack.addActionListener(_ -> {
            previousWindow.setVisible(true); // Показываем предыдущее окно
            dispose(); // Закрываем текущее окно
        });

        // Текст о версии программы
        JLabel versionLabel = new JLabel("Версия программы: ver. 1.1.2.2024", SwingConstants.CENTER);
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // Кнопка "Выход"
        JButton jbtExit = new JButton("Выход");
        jbtExit.setBackground(new Color(255, 164, 164));
        jbtExit.addActionListener(_ -> System.exit(0)); // Закрытие приложения

        // Сборка панели
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(jbtBack);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(jbtExit);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(versionLabel, BorderLayout.CENTER);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        return bottomPanel;
    }
}







