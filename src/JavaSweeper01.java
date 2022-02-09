import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper01 extends JFrame{
    private Game game;

    private JPanel panel;
    private JLabel label;

    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper01();
    }
    private JavaSweeper01 (){                                  //Приватный конструктор
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel(){
        label = new JLabel("Здорова, помни - сапер ошибается однажды!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel(){
        panel = new JPanel(){                                       // Создаем панель

           @Override
           protected void paintComponent (Graphics g){
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()){

                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
           }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE; // Получаем координаты куда мышка ткнулась
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y); // Переменная с координатами, где мышка была нажата
                if (e.getButton() == MouseEvent.BUTTON1) // Если нажата левая кнопка мышки
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3) // Если нажата правая кнопка мышки
                    game.pressRihtButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2) // Если нажата средняя кнопка мышки
                    game.start();
                label.setText(getMessage());
                panel.repaint(); // Важно перерисовать форму
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));  //Устанавливаем ее размеры
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()){
            case PLAYED: return "Соберись с мыслями!";
            case BOMBED: return "Непруха - большой БАДА-БУМ!";
            case WINNER: return "ПОБЕДА!!! Счастливчик! В этот раз тебе свезло.";
            default    : return "Приветствуем идущего путём сапёра!";
        }
    }

    private void initFrame (){                                 //Приватная функция
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Закрытие программы при закрытии окна
        setTitle("Jawa Sweeper");                                   //Заголовок окна
        setResizable(false);                                        // Запрет изменения размера окна
        setVisible(true);                                           // ТРУ чтоб форму было видно
        pack();                                                     //Изменяет размер формы так чтобы все поместилось
        setLocationRelativeTo(null);                                //Заголовок (окно) по центру должна быть после pack()
        setIconImage(getImage("icon"));                       //Отображение иконки в статусбаре
    }

    private void setImages () {                    //Установка всех картинок
        for (Box box: Box.values())               // Цикл который переберет картинки
            box.image = getImage(box.name().toLowerCase());
    }




    private Image getImage (String name){
        String filename = "img/" + name + ".png"; // определение имени файла для картинок ПАПКА + имя + РАсширение
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); // Создаем объект картинок с указанием папки ресрсов
        return icon.getImage(); // возвращаем картинку


    }
}
