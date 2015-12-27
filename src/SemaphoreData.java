
import java.util.concurrent.*;

	class SemaphoreData {
		int count;
		Semaphore s1 = new Semaphore(0);//0 чтобы не пустил первым того кто берет
		Semaphore s2 = new Semaphore(1);//1 чтобы первым пошел тот кто дает

		synchronized void get(){
			try{
				s1.acquire(); //если занято ждет, если свободно заходит, берет освобожлает семафор для send (s2)
				System.out.println("Get: " + count);
				s2.release();
			}
			catch (Exception e){
				System.out.println("IE");
			}
		}

		synchronized void send(int tmp){
			try{
				s2.acquire();//если занято ждет, если свободно заходит, берет освобожлает семафор для get (s1)
				count = tmp;
				System.out.println("Send: " + count);
				s1.release();
			}
			catch (Exception e){
				System.out.println("IE");
			}

		}
	}

	class Getable1 implements Runnable{
		private Data d;
		Getable1(Data d){
			this.d =d;
			new Thread(this).start();
		}
		public void run(){
			while(true){
				d.get();
			}
		}
	}
	
	class Sendable1 implements Runnable{
		private Data d;
		Sendable1(Data d){
			this.d =d;
			new Thread(this).start();
		}
		public void run(){
			int i = 0;
			while(true){
				d.send(++i);
			}
		}
	}

	class Test1{
		public static void main(String [] args){
			Data dt = new Data();
			new Sendable(dt);
			new Getable(dt);
		}
	}
