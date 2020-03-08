package com.example.demo;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Test
    public void contextLoads() {
        list.forEach(System.out::println);
        list.forEach((t) -> System.out.println(t));
    }

    private int dataInitSize = 100;
    int threadSize = 10;

    @Test
    public void testSynData() {
        long startTime = System.currentTimeMillis();
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < dataInitSize; i++) {
            datas.add(i);
        }

        int dataSize = datas.size();
        // 每个线程处理的数据长度
        int threadCount = dataSize % threadSize == 0 ? dataSize / threadSize : (dataSize / threadSize) + 1;


        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadCount);
        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> task;
        for (int i = 0; i < threadCount; i++) {
            final List<Integer> listStr = datas.subList(i * threadSize, (i + 1) * threadSize);
            task = () -> {
                handleList(listStr);
                return null;
            };
            tasks.add(task);
        }

        List<Future<Integer>> results = null;
        try {
            results = exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* for (Future<Integer> future : results) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("finish");
        System.out.println("end");
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));

    }


    @Test
    public void testSynData3() {
        long startTime = System.currentTimeMillis();
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < dataInitSize; i++) {
            datas.add(i);
        }

        int dataSize = datas.size();
        // 每个线程处理的数据长度
        int threadCount = dataSize % threadSize == 0 ? dataSize / threadSize : (dataSize / threadSize) + 1;


        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final List<Integer> listStr = datas.subList(i * threadSize, (i + 1) * threadSize);

            exec.submit(() -> {
                try {
                    handleList(listStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("finish");
        System.out.println("end");
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));

    }

    @Test
    public void testSynData2() throws Exception {
        long startTime = System.currentTimeMillis();
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < dataInitSize; i++) {
            datas.add(i);
        }

        handleList(datas);
        System.out.println("finish");
        System.out.println("end");
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));

    }


    private void handleList(List<Integer> datas) throws Exception {
        System.out.println("executing!!!!!!!!!!!!!!!!!");
        for (Integer i : datas) {
            int z = 1 / (50 - i);
            System.out.println("CurrentThreadName:" + Thread.currentThread().getName() + " " + i);
        }
    }
}
