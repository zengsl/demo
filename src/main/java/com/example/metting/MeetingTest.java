package com.example.metting;

import java.util.*;

/**
 * @author Zengsl
 * @version V1.0
 * @date 2019-10-8 20:34
 */
public class MeetingTest {

   /* public static void main(String[] args) {
        new MeetingTest().start();
    }

    *//**
     * 最大连续计算次数
     *//*
    private final static int MAX_CONSECUTIVE_NUM = 2;

    public void start() {
        List<String> areas = new ArrayList<>();
        // 3A 3B 1C

        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("B");
        areas.add("B");
        areas.add("B");
        areas.add("A");
        areas.add("C");
        areas.add("C");
        Map<String, Integer> frequencyResult = analyzeElementFrequency(areas);
        printMap(frequencyResult);


        // 连续次数
        int consecutiveCount = 0;
        String lastElement = null;
        String avoidElement = null;
        int elementSize = areas.size();
        List<String> sortResult = new ArrayList<>(elementSize);
        for (int i = 0; i < elementSize; i++) {

            if (consecutiveCount >= MAX_CONSECUTIVE_NUM) {
                avoidElement = lastElement;
                consecutiveCount = 0;
            } else {
                avoidElement = null;
            }

            String currentElement = grabElement(frequencyResult, avoidElement, 1);
            if (currentElement == null) {
                continue;
            }
            sortResult.add(currentElement);
            System.out.println("currentElement:" + currentElement);

            // 如果和上次数据相同则次数加1
            if (currentElement.equals(lastElement)) {
                consecutiveCount++;
            } else {
                consecutiveCount = 1;
            }
            lastElement = currentElement;

        }

        Map<String, Integer> frequencyResult2 = analyzeElementFrequency(sortResult);
        printMap(frequencyResult2);
    }

    *//**
     * 分析数组中每个元素出现的次数
     *
     * @param elements 待计算的数组
     * @return Map<String, Integer>,将数组中的元素作为key,元素在数组中出现的次数为value返回。
     * @author zengsl
     * @date 2019-10-8 21:20
     *//*
    private Map<String, Integer> analyzeElementFrequency(List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        Map<String, Integer> frequencyResult = new HashMap<>(elements.size());
        for (String element : elements) {
            Integer currentVal = frequencyResult.get(element);
            frequencyResult.put(element, currentVal == null ? 1 : ++currentVal);
        }
        return frequencyResult;
    }

    *//**
     * 从集合中抓取数量第sortNum多的元素，如果抓取完了之后元素的数量将减少1个
     *
     * @param frequencyResult 存放元素和数量对应关系的集合
     * @param avoidElement    需排除的元素。如果该参数有值，且集合中数量最多的元素是此元素，那么将继续计算获取下一个数量最多的元素 TODO 可能需要兼容多个规避值
     * @param sortNum         排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return
     * @throws
     * @author zengsl
     * @date 2019-10-8 21:22
     *//*
    private String grabElement(Map<String, Integer> frequencyResult, String avoidElement, int sortNum) {
        // 从map集合中,根据value值排序,获取对应sortNum排序位的entry
        Map.Entry<String,Integer> maxValueEntry = getEntryByMaxValueSort(frequencyResult, sortNum);
        if(maxValueEntry == null) {
            return null;
        }

        Integer currentElementValue = maxValueEntry.getValue();
        // 若所有元素的剩余次数都为0，那么直接结束元素抓取逻辑
        if(currentElementValue == null || currentElementValue <= 0) {
            return null;
        }
        String currentElement = maxValueEntry.getKey();
        if (currentElement == null) {
            return null;
        }
        if (!currentElement.equals(avoidElement)) {
            Integer currentVal = frequencyResult.get(currentElement);
            frequencyResult.put(currentElement, --currentVal);
            return currentElement;
        }
        // 出现需要规避值的情况，则继续往下查找下一个较大的值
        String nextMaxElement = this.grabElement(frequencyResult, avoidElement, sortNum + 1);
        return nextMaxElement == null ? currentElement : nextMaxElement;
    }

    *//**
     * 从集合中获取第sortNum大的值
     *
     * @param map     待获取数据的集合
     * @param sortNum 排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return
     * @author zengsl
     * @date 2019-10-8 21:20
     *//*
    public static Object getKeyByMaxValueSort(Map<String, Integer> map, int sortNum) {
        return getEntryByMaxValueSort(map,sortNum).getKey();
    }

    *//**
     * 从集合中获取第sortNum大的值的entry
     *
     * @param map     待获取数据的集合
     * @param sortNum 排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return Map.Entry<String, Integer>
     * @author zengsl
     * @date 2019-10-8 21:20
     *//*
    public static Map.Entry<String, Integer> getEntryByMaxValueSort(Map<String, Integer> map, int sortNum) {
        if (map == null) {
            return null;
        }
        sortNum = sortNum <= 0 ? 1 : sortNum;

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        int size = list.size();
        return list.get(size - 1 - (sortNum - 1));

    }

    *//**
     * 打印map集合

     * @param map 待打印的map集合
     * @return void
     * @author zengsl
     * @date 2019-10-8 23:19
     *//*
    void printMap(Map<String,Integer>map) {
        for(String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }*/
}
