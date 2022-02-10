public interface DelayTaskScheduler {
    void addTask(Task task);

    Task getTask();
}
