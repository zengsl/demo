package com.example.sort.core;

import com.example.sort.constants.SpaceSortConstants;
import com.example.sort.model.MeetingRoom;
import com.example.sort.utils.DateUtils;

import java.util.*;

/**
 * 评审会场以矩阵间隔排列时间策略
 * <p>
 * 每行进行排序<br/>
 * 1.每行中最大间隔数不能超过MAX_CONSECUTIVE_NUM的值<br/>
 * 2.每列中的元素不能重复，如果无法避免重复的话则使用占位符
 * 3.计算出的同一个会场时间不能有交集
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 15:29
 */
public class PeriodSortStrategy implements MatrixSortStrategy {

    private Date startTime;
    private Date endTime;
    private Date mornStartTime;
    private Date mornEndTime;
    private Date afterStartTime;
    private Date afterEndTime;

    private int period;
    private int mornPeriod;
    private int afterPeriod;
    private boolean isMorning;

    /**
     * 排序操作
     *
     * @param elements 待排序的矩阵
     * @return Map<String ,   List < MeetingRoom>> 排好序之后的矩阵
     */
    @Override
    public Map<String, List<MeetingRoom>> doOperate(Map<String, List<String>> elements) {

        Map<String, List<MeetingRoom>> results = new HashMap<>();
        List<List<MeetingRoom>> sortResults = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : elements.entrySet()) {
            List<String> element = entry.getValue();
            String currentKey = entry.getKey();

            // 计算会场时间等参数
            String[] params = currentKey.split("_");
            String roomKey = params[0] + "_" + params[1];
            Date startDate = DateUtils.toDate(params[2]);
            String[] mornStartTimeParams = params[3].split(":");
            Date mSth = DateUtils.setHour(startDate, Integer.parseInt(mornStartTimeParams[0]));
            Date mStartTime = DateUtils.setMinute(mSth, Integer.parseInt(mornStartTimeParams[1]));

            String[] mornEndTimeParams = params[4].split(":");
            Date mEth = DateUtils.setHour(startDate, Integer.parseInt(mornEndTimeParams[0]));
            Date mEndTime = DateUtils.setMinute(mEth, Integer.parseInt(mornEndTimeParams[1]));

            String[] afterStartTimeParams = params[6].split(":");
            Date aSth = DateUtils.setHour(startDate, Integer.parseInt(afterStartTimeParams[0]));
            Date aStartTime = DateUtils.setMinute(aSth, Integer.parseInt(afterStartTimeParams[1]));

            String[] afterEndTimeParams = params[7].split(":");
            Date aEth = DateUtils.setHour(startDate, Integer.parseInt(afterEndTimeParams[0]));
            Date aEndTime = DateUtils.setMinute(aEth, Integer.parseInt(afterEndTimeParams[1]));

            int mornPeriod = Integer.parseInt(params[5]);
            int afterPeriod = Integer.parseInt(params[8]);


            int currentRoomSize = element.size();
            // 初始化时刻表参数
            initScheduleConfig(mStartTime, mEndTime, aStartTime, aEndTime, mornPeriod, afterPeriod);
            Map<String, Integer> frequencyResult = analyzeElementFrequency(element);
            List<MeetingRoom> tmpList = sortMatrixRow(currentRoomSize, frequencyResult, sortResults);
            sortResults.add(tmpList);
            results.put(roomKey, tmpList);
            System.out.println("-------------------------------");
        }
        return results;
    }

    /**
     * 初始化时刻表参数
     *
     * @param mornStartTime   上午开始时间
     * @param mornEndTime     上午结束时间
     * @param afterStartTime  下午开始时间
     * @param afterEndTime    下午开始时间
     * @param mornPeriod      上午每场评审时间
     * @param afterPeriod     下午每场评审时间
     */
    private void initScheduleConfig(Date mornStartTime, Date mornEndTime, Date afterStartTime, Date afterEndTime, int mornPeriod, int afterPeriod) {
        this.mornStartTime = mornStartTime;
        this.mornEndTime = mornEndTime;
        this.afterStartTime = afterStartTime;
        this.afterEndTime = afterEndTime;
        this.mornPeriod = mornPeriod;
        this.afterPeriod = afterPeriod;
        this.isMorning = true;
        this.period = mornPeriod;
        this.startTime = null;
        this.endTime = null;
    }

    /**
     *  计算下一个时间计划<br/>
     *
     *  切换上午和下午的时间段,计算下一个可用的时间段的开始时间startTime和结束时间endTime
     */
    private void calculateNextSchedule() {
        startTime = (startTime == null) ? mornStartTime : endTime;
        endTime = DateUtils.nextMinutes(startTime, period);
        // 根据时间段判断起止时间是否合理
        if (isMorning && DateUtils.compare(endTime, mornEndTime) > 0) {
            isMorning = false;
            period = afterPeriod;
            startTime = afterStartTime;
            endTime = DateUtils.nextMinutes(startTime, period);
        } else if (!isMorning && DateUtils.compare(endTime, afterEndTime) > 0) {
            // 下午时间排满之后需要从第二天计算时间
            mornStartTime = DateUtils.nextDays(mornStartTime, 1);
            mornEndTime = DateUtils.nextDays(mornEndTime, 1);
            afterStartTime = DateUtils.nextDays(afterStartTime, 1);
            afterEndTime = DateUtils.nextDays(afterEndTime, 1);
            isMorning = true;
            period = mornPeriod;
            startTime = mornStartTime;
            endTime = DateUtils.nextMinutes(startTime, period);
        }
    }

    /**
     * 对矩阵一行中数据进行排列并且计算出对应的时间段<br/>
     * 排列规则如下<br/>
     *
     * @param elementSize     每行中的元素大小
     * @param frequencyResult 每行中各个值和出现的次数关系
     * @param sortResults     已经计算好的矩阵每行中数据进行排列结果集合，是当前行之前行的排列结果的集合
     * @return List<MeetingRoom>矩阵当前行数据排列结果
     * @author zsl
     * @date 2019/10/10 11:59
     */
    private List<MeetingRoom> sortMatrixRow(int elementSize, Map<String, Integer> frequencyResult, List<List<MeetingRoom>> sortResults) {
        // 某元素连续出现次数
        int consecutiveCount = 0;
        // 上一次出现的元素
        String lastElement = null;
        // avoidElement需规避的元素 ，currentElement当前元素
        String avoidElement, currentElement = null;
        List<MeetingRoom> sortResult = new ArrayList<>(elementSize);
        for (int i = 0; i < elementSize; i++) {

            if (consecutiveCount >= SpaceSortConstants.MAX_CONSECUTIVE_NUM) {
                avoidElement = lastElement;
                consecutiveCount = 0;
            } else {
                avoidElement = null;
            }

            // 计算时间
            calculateNextSchedule();

            // 是否与矩阵中在相同纵坐标的这一列的元素值有重复
            boolean isConflict = false;
            // 获取数据大小的参考值,为1表示取最大的，为2表示取第二大的，依次类推
            int sortNum = 0;
            // 通过循环从当前元素集合中获取
            while (true) {
                ++sortNum;
                // 当前待排列的数组中的任意一个元素与与之前排好序的数组，相同坐标位置的元素都有冲突，则结束本次循环。
                if (sortNum > frequencyResult.size()) {
                    isConflict = true;
                    break;
                }
                currentElement = grabElement(frequencyResult, avoidElement, sortNum);
                if (currentElement == null) {
                    break;
                }

                int sortResultsSize = sortResults.size();
                int loopCount = 0;
                for (List<MeetingRoom> oldSortResult : sortResults) {
                    loopCount++;

                    isConflict = judgeIsConflict(currentElement, startTime, endTime, oldSortResult);
                    if (isConflict) {
                        break;
                    }
                }
                // 若与之前排好序的数组，相同坐标上都没有冲突则表示获取到的该当前元素可以使用
                if (loopCount == sortResultsSize && !isConflict) {
                    break;
                }
            }

            if (isConflict) {
                currentElement = SpaceSortConstants.CONFLICT_PLACE_HOLDER;
                elementSize++;
            } else {
                decreaseFrequency(frequencyResult, currentElement);
            }
            MeetingRoom room = new MeetingRoom();
            room.setRoomName(currentElement);
            room.setStartTime(startTime);
            room.setEndTime(endTime);
            sortResult.add(room);
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
     * 判断是否与已经排好序的会场元素集合中，相同元素所对应的时间段存在交集
     *
     * @param currentElement 当前元素
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param oldSortResult  已经排好序的会场元素集合
     * @return boolean true:存在冲突 false:不存在冲突
     */
    private boolean judgeIsConflict(String currentElement, Date startTime, Date endTime, List<MeetingRoom> oldSortResult) {
        boolean isConflict = false;
        for (MeetingRoom oldRoom : oldSortResult) {
            // 判断是否是同一个会场，并且时间有交集
            String oldRoomName = oldRoom.getRoomName();
            Date oldStartTime = oldRoom.getStartTime();
            Date oldEndTime = oldRoom.getEndTime();
            // 如果比较的会场开始时间大于当前时间的结束时间则可以结束当前循环
            if (DateUtils.compare(oldStartTime, endTime) > 0) {
                break;
            }
            // 判断同一个元素在已经排出来的数据列中所对应的时间段是否有冲突
            isConflict = currentElement.equals(oldRoomName) && DateUtils.isOverlap(oldStartTime, oldEndTime, startTime, endTime);
            if (isConflict) {
                break;
            }
        }
        return isConflict;
    }


    /**
     * 从集合中抓取数量第sortNum多的元素，如果抓取完了之后元素的数量将减少1个
     *
     * @param frequencyResult 存放元素和数量对应关系的集合
     * @param avoidElement    需排除的元素。如果该参数有值，且集合中数量最多的元素是此元素，那么将继续计算获取下一个数量最多的元素
     * @param sortNum         排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return String 返回抓取到的数据
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
     * @return String value值在map集合中出现次数第sortNum大的entry所对应的key值
     * @author zengsl
     * @date 2019-10-8 21:20
     */
    public static String getKeyByMaxValueSort(Map<String, Integer> map, int sortNum) {
        return getEntryByMaxValueSort(map, sortNum).getKey();
    }

    /**
     * 从集合中获取第sortNum大的值的entry
     *
     * @param map     待获取数据的集合
     * @param sortNum 排序号 为大于1的数字 1表示获取值最大的key,2表示取值为第二大的元素的key 以此类推
     * @return Map.Entry<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Integer>
     * @author zengsl
     * @date 2019-10-8 21:20
     */
    public static Map.Entry<String, Integer> getEntryByMaxValueSort(Map<String, Integer> map, int sortNum) {
        if (map == null) {
            return null;
        }
        sortNum = sortNum <= 0 ? 1 : sortNum;
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Integer>,将数组中的元素作为key,元素在数组中出现的次数为value返回。
     * @author zengsl
     * @date 2019-10-8 21:20
     */
    private Map<String, Integer> analyzeElementFrequency(List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        Map<String, Integer> frequencyResult = new HashMap<String, Integer>(elements.size());
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
     * @author zengsl
     * @date 2019-10-8 23:19
     */
    void printMap(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }
}
