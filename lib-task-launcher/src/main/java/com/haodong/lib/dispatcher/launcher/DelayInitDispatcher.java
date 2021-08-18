package com.haodong.lib.dispatcher.launcher;

import android.os.Looper;
import android.os.MessageQueue;

import com.haodong.lib.dispatcher.launcher.task.DispatchRunnable;
import com.haodong.lib.dispatcher.launcher.task.Task;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: tangyuan
 * Time : 2021/7/16
 * Description:
 */
public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
