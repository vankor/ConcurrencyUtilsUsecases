import java.util.concurrent.*;
import java.lang.*;

class CB implements Runnable {
    private String str;
    private CyclicBarrier c;

    CB(String str, CyclicBarrier c) {
        this.str = str;
        this.c = c;
        new Thread(this).start();
    }

    public void run() {
        System.out.println(str);
        try {
            c.await();
        } catch (Exception e) {
        }
    }
}

public class CyclicBarrierExample {
    public static void main(String[] args) throws Exception {
        CyclicBarrier demo = new CyclicBarrier(2, new Runnable() {//первый аргумент - количество потоков, которые должны дойти до барьера, второй - поток который запустится после этого
            public void run() {
                System.out.println("End CyclicBarrier");
            }
        });

        new CB("1", demo);
        new CB("2", demo);
        new CB("3", demo);
        new CB("4", demo);
        new CB("5", demo);
        new CB("6", demo);
        new CB("7", demo);
        new CB("8", demo);
    }
}
/*
Вывод:
1
2
End CyclicBarrier
3
4
End CyclicBarrier
5
6
End CyclicBarrier
7
8
End CyclicBarrier

*/
