import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static Random rand = new Random(System.currentTimeMillis());
    private final static int MIN_PART_LENGTH = 10000000;
    private final static int ARRAY_LENGTH =    100000000;

    public static void main(String[] args) {
        int[] ar = generate(ARRAY_LENGTH);
        System.out.println("generated");

        long a1 = System.currentTimeMillis();
        Integer s1 = sumSimpleStreamCalc(ar);
        long a2 = System.currentTimeMillis();
        long d1 = a2 - a1;
        System.out.println("sum = " + s1);
        System.out.println("time stream: " + d1);

        long a3 = System.currentTimeMillis();
        Integer s2 = sumSimpleCalc(ar);
        long a4 = System.currentTimeMillis();
        long d2 = a4 - a3;
        System.out.println("sum = " + s2);
        System.out.println("time simple: " + d2);

        long a5 = System.currentTimeMillis();
        Integer s3 = sumForkCalc(ar);
        long a6 = System.currentTimeMillis();
        long d3 = a6 - a5;
        System.out.println("sum = " + s3);
        System.out.println("time fork: " + d3);
    }

    private static int[] generate(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(9);
        }
        return data;
    }

    private static Integer sumSimpleStreamCalc(int[] data){
        return Arrays.stream(data).sum();
    }

    private static Integer sumSimpleCalc(int[] data){
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum;
    }

    private static Integer sumForkCalc(int[] data){
        MyRecursiveTask.setMinPartLength(MIN_PART_LENGTH);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        MyRecursiveTask task = new MyRecursiveTask(data);
        pool.execute(task);
        return task.join();
    }

}
