package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Класс UtilsUI содержит вспомогательные методы для работы с интерфейсом пользователя.
 * Включает методы для установки иконки приложения и управления фокусом кнопок.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class UtilsUI {
    /**
     * Иконка приложения, загружается из ресурсов.
     */
    private static final ImageIcon APP_ICON = new ImageIcon(Objects.requireNonNull
            (UtilsUI.class.getResource("/resources/icon.png")));

    /**
     * Устанавливает иконку для окна приложения.
     *
     * @param frame Окно приложения, для которого устанавливается иконка
     */
    public static void setAppIcon(JFrame frame) {
        frame.setIconImage(APP_ICON.getImage());
    }

    /**
     * Отключает фокус для кнопки, чтобы она не могла получить фокус.
     *
     * @param button Кнопка, для которой отключается фокус
     */
    public static void disableButtonFocus(JButton button) {
        button.setFocusable(false);
        button.setFocusPainted(false);
    }

    /**
     * Отключает фокус для всех кнопок в контейнере и его вложенных контейнерах.
     *
     * @param container Контейнер, содержащий кнопки
     */
    public static void disableFocusForAllButtons(Container container) {
        // Если контейнер является кнопкой, отключаем фокус для этой кнопки
        if (container instanceof JButton) {
            disableButtonFocus((JButton) container);
        }
        // Рекурсивно обрабатываем все вложенные компоненты контейнера
        for (Component comp : container.getComponents()) {
            if (comp instanceof Container) {
                disableFocusForAllButtons((Container) comp);
            }
        }
    }
}
