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
        System.out.println("�������� ����������");
        p.arriveAndAwaitAdvance(); //��������� ���� � ���� ���� ��������� ������� (������� �������). ��� ������ ������� ��� �� ����� ��� �������� � ����� ����
        tmp = str;
        System.out.println("������������ ���������� ��������");
        p.arriveAndAwaitAdvance();
        System.out.println("tmp = " + tmp);
        p.arriveAndDeregister(); //������ ����������� ������
    }
}

public class PhaserExample {
    public static void main(String[] args) {
        Phaser p = new Phaser(1);
        new MyThread("A", p);
        new MyThread("B", p);
        new MyThread("C", p);
        int num = p.getPhase();
        p.arriveAndAwaitAdvance(); // ����������� ��� ������ �� ��������� ����
        System.out.println("����� ����#" + num);
        num = p.getPhase();
        p.arriveAndAwaitAdvance(); // ����������� ��� ������ �� ��������� ����
        System.out.println("����� ����#" + num);
        num = p.getPhase();
        p.arriveAndAwaitAdvance(); // ����������� ��� ������ �� ��������� ����
        System.out.println("����� ����#" + num);
        p.arriveAndDeregister(); //������ ����������� ������
    }
}
/*
�������� ����������
�������� ����������
�������� ����������
����� ����#0
������������ ���������� ��������
������������ ���������� ��������
������������ ���������� ��������
����� ����#1
tmp = B
tmp = A
tmp = C
����� ����#2
*/