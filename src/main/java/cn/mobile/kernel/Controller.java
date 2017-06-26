package cn.mobile.kernel;

/**
 * Created by LucasX on 2016/2/15.
 */
public class Controller {

    public static void process() {
        MultiThreadSpider multiThreadSpider = new MultiThreadSpider();
        MultiThreadSpiderTwo multiThreadSpiderTwo = new MultiThreadSpiderTwo();

        Thread t1 = new Thread(multiThreadSpider);
        Thread t2 = new Thread(multiThreadSpiderTwo);

        t1.start();
        System.out.println("线程t1开始运行...");

        t2.start();
        System.out.println("线程t2开始运行...");
    }

    public static void main(String[] args) {
        Controller.process();
    }
}
