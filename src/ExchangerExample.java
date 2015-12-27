import java.util.concurrent.*;
import java.lang.*;
import java.io.*;

class Sendable2 implements Runnable { //��������� ������
    private Exchanger<String> exch;
    private String str;

    Sendable2(Exchanger<String> exch) {
        this.exch = exch;
        new Thread(this).start();
    }

    @Override
    public void run() {
        str = "Test Exchanger";
        try {
            str = exch.exchange(str); //�������� ������ �������, � ����� ��� �� ���������� (�� �����, � ����� ����� �� ����� ��������� ������� ���� �� �����)
        } catch (Exception e) {
        }
    }
}


class Writeable implements Runnable { //��������� �����
    private Exchanger<String> exch;
    private String str;

    Writeable(Exchanger<String> exch) {
        this.exch = exch;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            FileWriter f = new FileWriter("X://index.txt");
            BufferedWriter out = new BufferedWriter(f);
            str = exch.exchange(new String()); //������ ������ � ������ Sendable - ���� ���� ��������, ���� ��� ���� ���� �����
            out.write(str);
            out.close();
        } catch (Exception e) {

        }
    }
}


public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exch = new Exchanger<String>();
        new Sendable2(exch);
        new Writeable(exch);
    }
}

/*
������ ������ � ����


*/
