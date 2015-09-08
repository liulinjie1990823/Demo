package cn.com.llj.demo.activity.other;

import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import bolts.CancellationTokenSource;
import bolts.Continuation;
import bolts.Task;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 *
 */
public class BoltsDemo extends DemoActivity {

    @Override
    public int getLayoutId() {
        return R.layout.bolts_demo;
    }


    public void button1(View view) {
        // 直接通过Task.callInBackground返回的task.getResult()来获取结果可能为null，因为如果是异步任务可以result还没有
        // continueWith后面的Continuation会被先添加到一个集合中，等异步执行完毕就接着执行Continuation里面的回掉
        // 如果
        Task<Integer> task = Task.callInBackground(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(100);
                return 5;
            }
        }).continueWith(new Continuation<Integer, Integer>() {

            public Integer then(Task<Integer> task) {
                // 这里的task已经执行完了
                return null;
            }
        });
        // 这里返回的结果可能为null
        task.getResult();
    }

    public void button2(View view) {
        // CancellationTokenSource类是用来控制Callable.call()方法是否执行
        final CancellationTokenSource cts = new CancellationTokenSource();
        cts.cancel();
        Task.callInBackground(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(100);
                return 5;
            }
        }, cts.getToken()).continueWith(new Continuation<Integer, Void>() {
            public Void then(Task<Integer> task) {

                return null;
            }
        });
    }

    public void button3(View view) {
        Task.delay(1000).continueWith(new Continuation<Void, Void>() {
            public Void then(Task<Void> task) {

                return null;
            }
        });
    }

    public void button4(View view) {
        final ArrayList<Task<Integer>> tasks = new ArrayList<Task<Integer>>();
        tasks.add(Task.callInBackground(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(100);
                return 5;
            }
        }));
        tasks.add(Task.callInBackground(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(100);
                return 5;
            }
        }));
        tasks.add(Task.callInBackground(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(100);
                return 5;
            }
        }));
        Task.whenAnyResult(tasks).continueWith(new Continuation<Task<Integer>, Void>() {
            @Override
            public Void then(Task<Task<Integer>> task) throws Exception {

                return null;
            }
        });
    }

    public void button5(View view) {
        //continueWhile中提供一个预言的Callable，如果Callable返回true就会接着调用Continuation
        //最后==5后cts.cancel();结束后调用continueWith中的Continuation
        final AtomicInteger count = new AtomicInteger(0);
        final CancellationTokenSource cts = new CancellationTokenSource();
        Callable<Task<?>> callable = new Callable<Task<?>>() {
            public Task<?> call() throws Exception {
                return Task.forResult(null).continueWhile(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        return count.get() < 10;
                    }
                }, new Continuation<Void, Task<Void>>() {
                    public Task<Void> then(Task<Void> task) throws Exception {
                        if (count.incrementAndGet() == 5) {
                            cts.cancel();
                        }
                        return null;
                    }
                }, Executors.newCachedThreadPool(), cts.getToken()).continueWith(new Continuation<Void, Void>() {
                    public Void then(Task<Void> task) throws Exception {
                        task.isCancelled();
                        count.get();
                        return null;
                    }
                });
            }
        };
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
