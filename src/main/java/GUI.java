import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс, который реализует интерфейс.
 */
public class GUI extends JFrame {
    public GUI() {
        //Вызов конструктора родителя.
        super("Solve!");
        //Задаем размер окна.
        this.setSize(800, 300);
        //Задаем, что при нажатии на крестик в правом верхнем углу окна, окно закроется.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Задаем менеджер компоновки.
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        //Создаем поле ввода выражения. 70 - кол-во символ ввода.
        final TextField inputField = new TextField(70);
        //Создаем поле вывода выражения.
        final TextField outputField = new TextField(70);
        //Создаем управляющие кнопки.
        final Button buttonSolve = new Button("Решить!");
        final Button buttonExit = new Button("Выход.");

        //Создаем панель, на которую помещаем надпись и поле ввода.
        Panel panel = new Panel(new FlowLayout());
        panel.add(new Label("Введите выражение:"));
        panel.add(inputField);
        add(panel);

        panel = new Panel(new FlowLayout());
        panel.add(new Label("Ответ:"));
        panel.add(outputField);
        add(panel);

        //Задаем размеры кнопоки "Решить"
        buttonSolve.setMaximumSize(new Dimension(130, 80));
        add(buttonSolve);

        //Задаем размеры кнопки "Выход"
        buttonExit.setMaximumSize(new Dimension(130, 80));
        add(buttonExit);

        //Задаем действие при нажатии на кнопку "Решить."
        buttonSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /**
                     * Ищем ответ.
                     */
                    Solve solve = new Solve(inputField.getText());
                    String answer = solve.solve();
                    /**
                     * Выводим ответ.
                     */
                    outputField.setText((answer != null) ? answer : "Нет решения");
                } catch (Exception exp) {
                    outputField.setText("Ошибка.");
                }
            }
        });

        final JFrame self = this;
        //Задаем действие при нажатии на кнопку "Выход."
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Закрываем окно.
                 */
                self.dispose();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
