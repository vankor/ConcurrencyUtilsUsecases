import java.util.concurrent.*;


class MyThread implements Runnable {
    private String str;
    private Phaser p;

    MyThread(String str, Phaser p) {
        this.str = str;
        this.p = p;
        this.p.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        String tmp;
        System.out.println("Создание переменной");
        p.arriveAndAwaitAdvance(); //закончили фазу и ждем всех остальных потоков (включая главный). Они должны вызвать тот же метод для перехода в новую фазу
        tmp = str;
        System.out.println("Присваивание переменной значение");
        p.arriveAndAwaitAdvance();
        System.out.println("tmp = " + tmp);
        p.arriveAndDeregister(); //отмена регистрации потока
    }
}

public class PhaserExample {
    public static void main(String[] args) {
        Phaser p = new Phaser(1);
        new MyThread("A", p);
        new MyThread("B", p);
        new MyThread("C", p);
        int num = p.getPhase();
        p.arriveAndAwaitAdvance(); // переключаем все потоки на следующую фазу
        System.out.println("Конец фазы#" + num);
        num = p.getPhase();
        p.arriveAndAwaitAdvance(); // переключаем все потоки на следующую фазу
        System.out.println("Конец фазы#" + num);
        num = p.getPhase();
        p.arriveAndAwaitAdvance(); // переключаем все потоки на следующую фазу
        System.out.println("Конец фазы#" + num);
        p.arriveAndDeregister(); //отмена регистрации потока
    }
}
/*
Создание переменной
Создание переменной
Создание переменной
Конец фазы#0
Присваивание переменной значение
Присваивание переменной значение
Присваивание переменной значение
Конец фазы#1
tmp = B
tmp = A
tmp = C
Конец фазы#2
*/