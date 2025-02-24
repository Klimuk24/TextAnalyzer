package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

/**
 * Класс InactivityTimer используется для отслеживания бездействия пользователя.
 * Если пользователь не взаимодействует с приложением в течение заданного времени,
 * приложение автоматически закрывается.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class InactivityTimer {
    private Timer timer;
    private final JFrame parentFrame; // Окно сообщения о закрытии программы
    private final int timeout; // Время ожидания бездействия
    private AWTEventListener eventListener; // Слушатель событий

    /**
     * Конструктор, инициализирует таймер и слушатели событий.
     *
     * @param parentFrame Окно приложения, в котором будет отображаться сообщение о бездействии
     * @param timeout Время бездействия в миллисекундах, после которого приложение закрывается
     */
    public InactivityTimer(JFrame parentFrame, int timeout) {
        this.parentFrame = parentFrame;
        this.timeout = timeout;
        initTimer(); // Инициализация таймера
        initEventListeners(); // Инициализация слушателей событий
    }

    /**
     * Инициализирует таймер, который будет запускать завершение приложения после заданного времени.
     */
    private void initTimer() {
        timer = new Timer(timeout, _ -> {
            // Сообщение о бездействии и закрытие приложения
            JOptionPane.showMessageDialog(parentFrame,
                    "Приложение закрывается из-за бездействия.",
                    "Бездействие",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0); // Завершаем работу приложения
        });
        timer.setRepeats(false); // Таймер срабатывает только один раз
    }

    /**
     * Инициализирует слушатели событий для отслеживания активности пользователя.
     * Таймер сбрасывается при любом действии пользователя (клик мыши или нажатие клавиши).
     */
    private void initEventListeners() {
        eventListener = event -> {
            if (event instanceof MouseEvent || event instanceof KeyEvent) {
                resetTimer(); // Сброс таймера при активности пользователя
            }
        };

        // Добавляем слушатель событий для мыши и клавиатуры
        Toolkit.getDefaultToolkit().addAWTEventListener(eventListener, AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * Запускает таймер отслеживания бездействия.
     */
    public void startTimer() {
        timer.start();
        System.out.println("Таймер запущен");
    }

    /**
     * Останавливает таймер отслеживания бездействия.
     */
    public void stopTimer() {
        timer.stop();
        System.out.println("Таймер остановлен");
    }

    /**
     * Перезапускает таймер, сбрасывая отсчет времени.
     */
    public void resetTimer() {
        timer.restart();
        System.out.println("Таймер перезапущен");
    }

    /**
     * Удаляет слушатель событий, прекращая отслеживание активности пользователя.
     */
    public void removeEventListener() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(eventListener); // Удаление слушателя событий
        System.out.println("Слушатель удален");
    }

    // TODO -> Таймер работает только при первом запуске приложения и сбрасывается после перехода в другое окно
    // TODO -> В идеале необходимо добавить восстановление таймера при возвращении в стартовое окно
}

