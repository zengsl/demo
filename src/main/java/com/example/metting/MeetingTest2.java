package com.example.metting;

import java.util.*;

/**
 * @author Zengsl
 * @version V1.0
 * @date 2019-10-8 20:34
 */
public class MeetingTest2 {

    public static void main(String[] args) {
        new MeetingTest2().start();
    }

    /**
     * 最大连续计算次数
     */
    private final static int MAX_CONSECUTIVE_NUM = 2;

    public void start() {
        List<String> areas = new ArrayList<>();
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("A");
        areas.add("B");
        areas.add("B");
        areas.add("B");
        areas.add("C");
        areas.add("C");

        List<String> areas2 = new ArrayList<>();
        areas2.add("A");
        areas2.add("B");
        areas2.add("B");
        areas2.add("A");
        areas2.add("C");
        areas2.add("A");
        areas2.add("A");

        List<String> areas3 = new ArrayList<>();
        areas3.add("A");
        areas3.add("B");
        areas3.add("A");
        areas3.add("C");
        areas3.add("A");
        areas3.add("A");

        List<List<String>> meetingRooms = new ArrayList<>();
        meetingRooms.add(areas);
        meetingRooms.add(areas2);
        meetingRooms.add(areas3);

        List<List<String>> sortResults = new ArrayList<>();
        for (List<String> meetingRoom : meetingRooms) {
            int currentRoomSize = meetingRoom.size();
            Map<String, Integer> frequencyResult = analyzeElementFrequency(meetingRoom);
            List<String> tmpList = sortMeetingRoom(currentRoomSize, frequencyResult, sortResults);
            sortResults.add(tmpList);
            System.out.println("-------------------------------");
        }

    }

    /**
     * 计算单一会场中答辩地点排列结果
     *
     * @param elementSize 会场项目数
     * @param frequencyResult 会场中 各个答辩地点的数量分布情况
     * @param sortResults 已经计算好的会场答辩地点排列结果
     * @return List<String>单一会场排列结果
     * @author zsl
     * @date 2019/10/10 11:59
     */
    private List<String> sortMeetingRoom(int elementSize, Map<String, Integer> frequencyResult, List<List<String>> sortResults) {
        int consecutiveCount = 0;
        String lastElement = null;
        String avoidElement, currentElement = null;
        List<String> sortResult = new ArrayList<>(elementSize);
        for (int i = 0; i < elementSize; i++) {

            if (consecutiveCount >= MAX_CONSECUTIVE_NUM) {
                avoidElement = lastElement;
                consecutiveCount = 0;
            } else {
                avoidElement = null;
            }

            boolean isConflict = false;
            int sortNum = 0;

            while (true) {
                ++sortNum;
                // 当前待排列的数组中的任意一个元素与与之前排好序的数组，相同坐标位置的元素都有冲突，则结束本次循环。
                if(sortNum > frequencyResult.size()) {
                    isConflict = true;
                    break;
                }
                currentElement = grabElement(frequencyResult, avoidElement, sortNum);
                if (currentElement == null) {
                    break;
                }
                int sortResultsSize = sortResults.size();
                int loopCount = 0;
                for (int j = 0; j < sortResultsSize; j++) {
                    loopCount++;
                    List<String> oldSortResult = sortResults.get(j);
                    // 若出现和已排序数组相同坐标的值相同的情况
                    isConflict = (i + 1) <= oldSortResult.size() && currentElement.equals(oldSortResult.get(i));
                    if (isConflict) {
                        break;
                    }
                }
                // 诺与之前排好序的数组，相同坐标上都没有冲突则表示获取到的该当前元素可以使用
                if (loopCount == sortResultsSize && !isConflict) {
                    break;
                }
            }

            if(isConflict) {
                currentElement = "X";
                elementSize++;
            } else {
                decreaseFrequency(frequencyResult, currentElement);
            }
            sortResult.add(currentElement);
            System.out.println("currentElement:" + currentElement);

            // 如果和上次数据相同则次数加1
            if (currentElement != null && currentElement.equals(lastElement)) {
                consecutiveCount++;
            } else {
                consecutiveCount = 1;
            }
            lastElement = currentElement;
        }
        return sortResult;
    }


    /**
     * 从集合中抓取数量第sortNum多的元素，如果抓取完了之后元素的数量将减少1个
     *
     * @param frequencyResult 存放元素和数量对应关系的集合
     * @param avoidElement    需排除的元素。如果该参数有值，且集合中数量最多的元素是此元素，那么将继续计算获取下一个数量最多的元素 TODO 可能需要兼容多个规避值
     * @param sortNum         排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return String 返回抓取到的数据
     * @author zengsl
     * @date 2019-10-8 21:22
     */
    private String grabElement(Map<String, Integer> frequencyResult, String avoidElement, int sortNum) {
        // 从map集合中,根据value值排序,获取对应sortNum排序位的entry
        Map.Entry<String, Integer> maxValueEntry = getEntryByMaxValueSort(frequencyResult, sortNum);
        if (maxValueEntry == null) {
            return null;
        }

        Integer currentElementValue = maxValueEntry.getValue();
        // 若所有元素的剩余次数都为0，那么直接结束元素抓取逻辑
        if (currentElementValue == null || currentElementValue <= 0) {
            return null;
        }
        String currentElement = maxValueEntry.getKey();
        if (currentElement == null) {
            return null;
        }

        if (!currentElement.equals(avoidElement)) {
            return currentElement;
        }
        // 出现需要规避值的情况，则继续往下查找下一个较大的值
        String nextMaxElement = this.grabElement(frequencyResult, avoidElement, sortNum + 1);
        return nextMaxElement == null ? currentElement : nextMaxElement;
    }

    /**
     * 减少Map某个key对应值的数量
     *
     * @param map 待递减的map集合
     * @param key 待递减的map集合中对应key值
     * @return  void
     * @author zsl
     * @date 2019/10/10 12:03
     */
    private void decreaseFrequency(Map<String, Integer> map, String key) {
        Integer currentVal = map.get(key);
        map.put(key, --currentVal);
    }


    /**
     * 从集合中获取第sortNum大的值
     *
     * @param map     待获取数据的集合
     * @param sortNum 排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return
     * @author zengsl
     * @date 2019-10-8 21:20
     */
    public static Object getKeyByMaxValueSort(Map<String, Integer> map, int sortNum) {
        return getEntryByMaxValueSort(map, sortNum).getKey();
    }

    /**
     * 从集合中获取第sortNum大的值的entry
     *
     * @param map     待获取数据的集合
     * @param sortNum 排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return Map.Entry<String, Integer>
     * @author zengsl
     * @date 2019-10-8 21:20
     */
    public static Map.Entry<String, Integer> getEntryByMaxValueSort(Map<String, Integer> map, int sortNum) {
        if (map == null) {
            return null;
        }
        sortNum = sortNum <= 0 ? 1 : sortNum;
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        int size = list.size();
        return list.get(size - 1 - (sortNum - 1));

    }

    /**
     * 分析数组中每个元素出现的次数
     *
     * @param elements 待计算的数组
     * @return Map<String, Integer>,将数组中的元素作为key,元素在数组中出现的次数为value返回。
     * @author zengsl
     * @date 2019-10-8 21:20
     */
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

    /**
     * 打印map集合
     *
     * @param map 待打印的map集合
     * @return void
     * @author zengsl
     * @date 2019-10-8 23:19
     */
    void printMap(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }
}
