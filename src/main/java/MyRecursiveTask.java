import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Integer> {
    private static int minPartLength;
    private int[] data;

    public MyRecursiveTask(int[] data) {
        this.data = data;
    }

    public static void setMinPartLength(int minPartLength) {
        MyRecursiveTask.minPartLength = minPartLength;
    }

    private Collection<MyRecursiveTask> createChildTasks(){
        Collection<MyRecursiveTask> childTasks = new ArrayList<>();
        childTasks.add(new MyRecursiveTask(Arrays.copyOfRange(data, 0, data.length / 2)));
        childTasks.add(new MyRecursiveTask(Arrays.copyOfRange(data, data.length / 2, data.length)));
        return childTasks;
    }

    @Override
    protected Integer compute() {
        if (data.length > minPartLength){
            return ForkJoinTask.invokeAll(createChildTasks()).stream().mapToInt(x -> x.compute()).sum();
        } else
            return Arrays.stream(data).sum();

    }
}
