package textanalyzer;

import javax.swing.*;

/**
 * Класс RunApp содержит точку входа в приложение "Анализ текста".
 * Этот класс инициализирует и отображает окно StartWindow с использованием Swing.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class RunApp {
    public static void main(String[] args) {
        // Запуск программы и инициализация стартового окна
        SwingUtilities.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });
    }
}



