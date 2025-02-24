package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Главное окно программы для анализа текста. В этом окне выполняется ввод текста, 
 * его анализ, загрузка и сохранение файлов, а также отображение результатов анализа.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class MainWindow extends JFrame {
    // Объявление полей текстового поля и меток слов и предложений
    private final JTextArea textArea;
    private final JLabel sentenceCountLabel;
    private final JLabel wordCountLabel;
    private final JLabel declarativeCountLabel;
    private final JLabel questionCountLabel;
    private final JLabel exclamatoryCountLabel;

    /**
     * Конструктор главного окна.
     * @param previousWindow окно, с которого было вызвано текущее
     */
    public MainWindow(JFrame previousWindow) {
        // Устанавливаем иконку
        UtilsUI.setAppIcon(this);
        setTitle("Анализ текста");
        setMinimumSize(new Dimension(900, 900));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаём текстовую область для ввода текста
        textArea = new JTextArea(10, 50);

        // Создание и добавление кнопок с обработчиками событий
        JButton jbtAnalyze = new JButton("Анализировать");
        jbtAnalyze.addActionListener(_ -> analyzeText());

        JButton jbtLoadFile = new JButton("Загрузить файл");
        jbtLoadFile.addActionListener(_ -> loadFile());

        JButton jbtSave = new JButton("Сохранить");
        jbtSave.addActionListener(_ -> saveResults());

        JButton jbtClear = new JButton("Очистить");
        jbtClear.addActionListener(_ -> clearText());

        JButton jbtBack = new JButton("Назад");
        jbtBack.setFont(new Font("Arial", Font.BOLD, 16));
        jbtBack.addActionListener(_ -> {
            previousWindow.setVisible(true);
            dispose(); // Закрываем текущее окно
        });

        JButton jbtExit = new JButton("Выход");
        jbtExit.setFont(new Font("Arial", Font.BOLD, 16));
        jbtExit.setBackground(new Color(255, 164, 164));
        jbtExit.addActionListener(_ -> System.exit(0));

        // Метки для отображения результатов анализа
        sentenceCountLabel = new JLabel("Количество предложений: 0");
        wordCountLabel = new JLabel("Количество слов: 0");
        declarativeCountLabel = new JLabel("Повествовательные предложения: 0");
        questionCountLabel = new JLabel("Вопросительные предложения: 0");
        exclamatoryCountLabel = new JLabel("Восклицательные предложения: 0");

        // Панель для текста с рамкой
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Текст для анализа"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Обёртка для панели текста с внешними отступами
        JPanel textPanelWrapper = new JPanel(new BorderLayout());
        textPanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        textPanelWrapper.add(textPanel, BorderLayout.CENTER);

        // Панель для результатов анализа
        JPanel resultPanel = new JPanel(new GridLayout(5, 1, 1, 15));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Результаты анализа"),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        resultPanel.add(sentenceCountLabel);
        resultPanel.add(wordCountLabel);
        resultPanel.add(declarativeCountLabel);
        resultPanel.add(questionCountLabel);
        resultPanel.add(exclamatoryCountLabel);

        // Панель для кнопок
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Действия"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        buttonPanel.add(jbtAnalyze);
        buttonPanel.add(jbtLoadFile);
        buttonPanel.add(jbtSave);
        buttonPanel.add(jbtClear);

        // Панель для кнопки "Назад" с выравниванием по левому краю
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(jbtBack);

        // Обёртка для кнопки "Назад" с отступами
        JPanel backButtonWrapper = new JPanel(new BorderLayout());
        backButtonWrapper.add(backButtonPanel, BorderLayout.WEST);
        backButtonWrapper.setBorder(BorderFactory.createEmptyBorder(2, 10, 10, 10));

        // Панель для кнопки "Выход" с выравниванием по правому краю
        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitButtonPanel.add(jbtExit);

        // Обёртка для кнопки "Выход" с отступами
        JPanel exitButtonWrapper = new JPanel(new BorderLayout());
        exitButtonWrapper.add(exitButtonPanel, BorderLayout.EAST);
        exitButtonWrapper.setBorder(BorderFactory.createEmptyBorder(2, 10, 10, 10));

        // Панель для кнопок навигации ("Назад" и "Выход")
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.add(backButtonWrapper, BorderLayout.WEST);
        navigationPanel.add(exitButtonWrapper, BorderLayout.EAST);

        // Основная панель для нижней части (результаты и кнопки)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 30, 10));
        bottomPanel.add(resultPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Собираем нижнюю часть окна
        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.add(bottomPanel, BorderLayout.CENTER);
        lowerPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Добавляем компоненты в основное окно
        setLayout(new BorderLayout());
        add(textPanelWrapper, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);

        // Создаём и добавляем меню
        JMenuBar menuBar = getjMenuBar();
        setJMenuBar(menuBar);

        // Отключаем фокус на всех кнопках в окне
        UtilsUI.disableFocusForAllButtons(this);
    }

    /**
     * Метод для получения меню.
     * @return JMenuBar с настройками меню.
     */
    private JMenuBar getjMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Вкладка "Информация"
        JMenu infoMenu = new JMenu("Информация");
        JMenuItem aboutProgram = new JMenuItem("О программе");
        JMenuItem aboutAuthor = new JMenuItem("Об авторе");

        aboutProgram.addActionListener(_ -> new AboutProgramWindow(this).setVisible(true));
        aboutAuthor.addActionListener(_ -> new AboutAuthorWindow(this).setVisible(true));

        infoMenu.add(aboutProgram);
        infoMenu.add(aboutAuthor);

        // Вкладка "Об версиях"
        JMenu versionMenu = getjMenu();

        // Вкладка "Помощь"
        JMenu helpMenu = getMenu();

        // Добавляем меню в меню-бар
        menuBar.add(infoMenu);
        menuBar.add(versionMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }

    /**
     * Создаёт вкладку "Помощь" в меню.
     * @return JMenu для помощи.
     */
    private JMenu getMenu() {
        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem howToUse = new JMenuItem("Как пользоваться");

        howToUse.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                """
                        Инструкция:
                        1. Введите текст в поле сверху или загрузите файл.
                        2. Нажмите "Анализировать", чтобы выполнить анализ.
                        3. Сохраните результаты в файл, если нужно.
                        4. Для очистки текста нажмите "Очистить".""",
                "Помощь", JOptionPane.INFORMATION_MESSAGE));

        helpMenu.add(howToUse);
        return helpMenu;
    }

    /**
     * Создаёт вкладку "Об версиях" в меню.
     * @return JMenu с информацией о версиях программы.
     */
    private JMenu getjMenu() {
        JMenu versionMenu = new JMenu("Об версиях");
        JMenuItem version1 = new JMenuItem("Версия 1.0.0");
        JMenuItem version2 = new JMenuItem("Версия 1.1.0");
        JMenuItem version3 = new JMenuItem("Версия 1.1.1");
        JMenuItem version4 = new JMenuItem("Версия 1.1.2");

        version1.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                """
                        Версия 1.0.0:
                        - Созданы все основные окна
                        - Добавлен базовый функционал\
                        
                        - Добавлены переходы между окнами""",
                "Версия 1.0.0", JOptionPane.INFORMATION_MESSAGE));
        version2.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                """
                        Версия 1.1.0:
                        - Добавлен расширенный функционал
                        - Обновлен интерфейс\
                        
                        - Добавлена защита от пустых файлов""",
                "Версия 1.1.0", JOptionPane.INFORMATION_MESSAGE));
        version3.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                """
                        Версия 1.1.1:
                        - Добавлены вкладки "Информация" и "Помощь"\
                        
                        - Добавлена информация об авторе и программе""",
                "Версия 1.1.1", JOptionPane.INFORMATION_MESSAGE));
        version4.addActionListener(_ -> JOptionPane.showMessageDialog(this,
                """
                        Версия 1.1.2:
                        - Добавлена вкладка "Об версиях"
                        - Исправлены баги с анализом текста\
                        
                        - Добавлена защита от пустых файлов""",
                "Версия 1.1.2", JOptionPane.INFORMATION_MESSAGE));

        versionMenu.add(version1);
        versionMenu.add(version2);
        versionMenu.add(version3);
        versionMenu.add(version4);

        return versionMenu;
    }

    // Метод для очистки текста и сброса меток
    private void clearText() {
        textArea.setText(""); // Очищаем текстовое поле
        // Сбрасываем значения меток с результатами анализа
        sentenceCountLabel.setText("Количество предложений: 0");
        wordCountLabel.setText("Количество слов: 0");
        declarativeCountLabel.setText("Повествовательные предложения: 0");
        questionCountLabel.setText("Вопросительные предложения: 0");
        exclamatoryCountLabel.setText("Восклицательные предложения: 0");
    }

    // Метод для сохранения результатов анализа в файл
    private void saveResults() {
        // Проверяем, пустой ли текст или результаты анализа равны нулю
        String text = textArea.getText().trim();
        if (text.isEmpty() ||
                sentenceCountLabel.getText().endsWith("0") &&
                        wordCountLabel.getText().endsWith("0") &&
                        declarativeCountLabel.getText().endsWith("0") &&
                        questionCountLabel.getText().endsWith("0") &&
                        exclamatoryCountLabel.getText().endsWith("0")) {

            // Сообщаем пользователю о невозможности сохранить пустой файл
            JOptionPane.showMessageDialog(this,
                    "Нельзя сохранить пустой файл. Проверьте, что текст заполнен и выполнен анализ.",
                    "Ошибка сохранения",
                    JOptionPane.WARNING_MESSAGE);
            return; // Прерываем выполнение метода
        }

        // Создание диалога для выбора файла для сохранения
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить файл");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files",
                "txt"));

        // Ожидаем выбора файла пользователем
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Если пользователь не указал расширение, добавляем .txt
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }

            // Проверяем, существует ли файл, и спрашиваем пользователя о перезаписи
            if (selectedFile.exists()) {
                int overwrite = JOptionPane.showConfirmDialog(this,
                        "Файл уже существует. Перезаписать?",
                        "Подтверждение",
                        JOptionPane.YES_NO_OPTION);
                if (overwrite != JOptionPane.YES_OPTION) {
                    return; // Отмена сохранения
                }
            }

            // Пытаемся сохранить результаты анализа в выбранный файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write("Текст:\n\n");
                writer.write(text + "\n\n");
                writer.write("\nРезультаты анализа:\n\n");
                writer.write(sentenceCountLabel.getText() + "\n");
                writer.write(wordCountLabel.getText() + "\n");
                writer.write(declarativeCountLabel.getText() + "\n");
                writer.write(questionCountLabel.getText() + "\n");
                writer.write(exclamatoryCountLabel.getText() + "\n");

                // Уведомляем пользователя об успешном сохранении
                JOptionPane.showMessageDialog(this,
                        "Файл успешно сохранён!",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                // Обрабатываем ошибки записи в файл
                JOptionPane.showMessageDialog(this,
                        "Ошибка при сохранении файла: " + e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для загрузки текста из файла
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Фильтр для текстовых файлов
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files",
                "txt"));

        // Ожидаем выбора файла пользователем
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.setText(""); // Очищаем текстовое поле перед загрузкой файла
                String line;
                // Чтение строк из файла и добавление их в текстовое поле
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                // Обрабатываем ошибки чтения файла
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для анализа текста
    private void analyzeText() {
        String text = textArea.getText().trim(); // Удаляем пробелы в начале и конце

        // Проверка, что текст не пустой
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Текстовое поле пусто! Пожалуйста, введите текст для анализа.",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return; // Прерываем выполнение метода
        }

        // Разбиваем текст на предложения, учитывая только те, которые заканчиваются на точку, ! или ?
        String[] sentences = text.split("(?<=[.!?])\\s*");
        int sentenceCount = 0;
        int declarativeCount = 0;
        int questionCount = 0;
        int exclamatoryCount = 0;

        // Процесс анализа каждого предложения
        for (String sentence : sentences) {
            sentence = sentence.trim(); // Убираем лишние пробелы вокруг предложения
            if (sentence.isEmpty()) continue; // Пропускаем пустые строки и предложения без знака точки, ? или !
            if (!sentence.endsWith(".") && !sentence.endsWith("?") && !sentence.endsWith("!")) continue;

            sentenceCount++; // Подсчёт предложений

            // Определяем тип предложения
            if (sentence.endsWith(".")) {
                declarativeCount++;
            } else if (sentence.endsWith("?")) {
                questionCount++;
            } else if (sentence.endsWith("!")) {
                exclamatoryCount++;
            }
        }

        // Считаем слова, используя разбиение по пробелам
        int wordCount = text.split("\\s+").length;

        // Обновляем метки с результатами анализа
        sentenceCountLabel.setText("Количество предложений: " + sentenceCount);
        wordCountLabel.setText("Количество слов: " + wordCount);
        declarativeCountLabel.setText("Повествовательные предложения: " + declarativeCount);
        questionCountLabel.setText("Вопросительные предложения: " + questionCount);
        exclamatoryCountLabel.setText("Восклицательные предложения: " + exclamatoryCount);
    }
}


