import java.util.concurrent.*;
import java.lang.*;

class Echoable implements Runnable {
    private CountDownLatch count;

    Echoable(CountDownLatch count) {
        this.count = count;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            count.countDown();//отсчет событий
        }
    }
}

class Massager implements Runnable {
    private CountDownLatch count;

    Massager(CountDownLatch count) {
        this.count = count;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            count.await();//поток ждет пока закончится отчет событий
        } catch (InterruptedException e) {
        }
        System.out.println("End...");
    }
}


public class CountDownLatchExample {
    public static void main(String[] args) throws Exception {
        System.out.println("Start");
        CountDownLatch cdl = new CountDownLatch(5);//количество событий которые должны пройти, чтобы поток Massager активировался
        new Echoable(cdl);
        new Massager(cdl);
    }
}
/*
Вывод программы
Start
0
1
2
3
4
End...
*/