
class Data {
    int count;
    boolean flagSet = false;

    synchronized int get() {
        while (!flagSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
        System.out.println("Get: " + count);
        flagSet = false;
        notify();
        return count;
    }

    synchronized void send(int tmp) {
        while (flagSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
        count = tmp;
        System.out.println("Send: " + count);
        flagSet = true;
        notify();
    }
}

class Getable implements Runnable {
    private Data d;

    Getable(Data d) {
        this.d = d;
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            d.get();
        }
    }
}


class Sendable implements Runnable {
    private Data d;

    Sendable(Data d) {
        this.d = d;
        new Thread(this).start();
    }

    public void run() {
        int i = 0;
        while (true) {
            d.send(++i);
        }
    }
}

class Test {
    public static void main(String[] args) {
        Data dt = new Data();
        Sendable s = new Sendable(dt);
        Getable g = new Getable(dt);
    }
}

/*
Вывод программы:

Send: 1
Get: 1
Send: 2
Get: 2
Send: 3
Get: 3
....(до бесконечности)
*/
