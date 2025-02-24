package textanalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Класс AboutAuthorWindow представляет окно "Об авторе", которое отображает фотографию автора,
 * контактную информацию и кнопку для возврата к предыдущему окну.
 *
 * @author Klimuk M.N.
 * @version 1.1.2.2024
 */
public class AboutAuthorWindow extends JFrame {
    /**
     * Конструктор, инициализирует окно с информацией об авторе.
     *
     * @param previousWindow Окно, которое будет отображаться после закрытия текущего окна.
     */
    public AboutAuthorWindow(JFrame previousWindow) {
        UtilsUI.setAppIcon(this);

        setTitle("Об авторе");
        setSize(350, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10)); // Устанавливаем отступы между элементами

        add(createImagePanel(), BorderLayout.NORTH);
        add(createInfoPanel(), BorderLayout.CENTER);
        add(createButtonPanel(previousWindow), BorderLayout.SOUTH);

        UtilsUI.disableFocusForAllButtons(this);
    }

    /**
     * Создает панель с изображением автора.
     *
     * @return Панель с изображением.
     */
    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 2, 20));

        JLabel imageLabel = createImageLabel(); // Получаем метку с изображением
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        return imagePanel;
    }

    /**
     * Создает метку с фотографией автора. Фотография масштабируется, если она слишком большая.
     *
     * @return Метка с изображением автора.
     */
    private JLabel createImageLabel() {
        JLabel imageLabel = new JLabel();
        try {
            URL imageUrl = getClass().getResource("/resources/photoAuthor.jpg"); // Путь к изображению
            if (imageUrl != null) {
                BufferedImage authorImage = ImageIO.read(imageUrl); // Считываем изображение
                Image scaledImage = authorImage.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImage); // Создаем иконку из изображения

                imageLabel.setIcon(imageIcon);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3)); // Добавляем рамку
            } else {
                throw new IOException("Изображение не найдено!"); // Обработка ошибки
            }
        } catch (IOException ex) {
            System.out.println("Ошибка загрузки изображения: " + ex.getMessage());
            imageLabel.setText("Фото не найдено");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return imageLabel;
    }

    /**
     * Создает панель с информацией об авторе, включая его электронную почту.
     *
     * @return Панель с информацией об авторе.
     */
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(2, 20, 10, 20));

        infoPanel.add(Box.createVerticalStrut(10)); // Отступ сверху
        infoPanel.add(createAuthorEmailLabel());
        infoPanel.add(Box.createVerticalStrut(10)); // Отступ снизу

        return infoPanel;
    }

    /**
     * Создает метку с электронной почтой автора. При клике почта копируется в буфер обмена.
     *
     * @return Метка с электронной почтой автора.
     */
    private JLabel createAuthorEmailLabel() {
        JLabel authorEmailLabel = new JLabel(
                "<html>" +
                        "<div style='text-align: center; font-size: 13px; line-height: 1.5;'>" +
                        "Автор:<br>" +
                        "<strong>Климук Максим Николаевич</strong><br>" +
                        "Студент группы 10702322<br>" +
                        "<span style='color: blue; text-decoration: underline; cursor:" +
                        "pointer;'>klimukm24@gmail.com</span>" +
                        "</div>" +
                        "</html>", SwingConstants.CENTER);
        authorEmailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        authorEmailLabel.setToolTipText("Кликните, чтобы скопировать почту"); // Текст подсказки

        // Обработчик кликов на метке для копирования почты в буфер обмена
        authorEmailLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String email = "klimukm24@gmail.com";
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(email), null);
                JOptionPane.showMessageDialog(
                        AboutAuthorWindow.this,
                        "Почта скопирована в буфер обмена",
                        "Скопировано",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                authorEmailLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Смена курсора при наведении
            }
        });
        return authorEmailLabel;
    }

    /**
     * Создает панель с кнопкой "Назад" для возврата к предыдущему окну.
     *
     * @param previousWindow Окно, которое будет отображаться после закрытия текущего.
     * @return Панель с кнопкой "Назад".
     */
    private JPanel createButtonPanel(JFrame previousWindow) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backButton = createBackButton(previousWindow);
        buttonPanel.add(backButton, BorderLayout.CENTER);

        return buttonPanel;
    }

    /**
     * Создает кнопку "Назад", которая возвращает пользователя на предыдущее окно.
     *
     * @param previousWindow Окно, которое будет отображаться после закрытия текущего.
     * @return Кнопка "Назад".
     */
    private JButton createBackButton(JFrame previousWindow) {
        JButton jbtBack = new JButton("Назад");
        jbtBack.setFont(new Font("Arial", Font.BOLD, 16));
        jbtBack.setPreferredSize(new Dimension(100, 40));
        jbtBack.addActionListener(_ -> {
            previousWindow.setVisible(true);
            dispose(); // Закрываем текущее окно
        });
        return jbtBack;
    }
}







