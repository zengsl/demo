package com.example.sort2;

import com.example.sort2.core.MatrixSortStrategy;

import java.util.List;
import java.util.Map;

/**
 * 会场生产器基础类
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 16:03
 */
abstract public class BaseMeetingRoomsBuilder implements MeetingRoomsBuilder {


    @Override
    public void build(Map<String,List<String>> rooms, String currentMeeting) {
        convertArea2Room(rooms);
        Map<String,List<String>> sortResult = getMatrixSortStrategy().doOperate(rooms);

        // TODO 临时测试用的打印代码
        for(Map.Entry<String,List<String>> entry : sortResult.entrySet()) {
            String key = entry.getKey();
            for(String roomName : entry.getValue()) {
                System.out.println(key + ":" + roomName);
            }
            System.out.println("************************************");
        }

        generateSchedule(sortResult);
    }

    /**
     * 将归属地转换为对应的答辩会场
     *
     * @param areas 归属地
     */
    private void convertArea2Room(Map<String,List<String>> areas) {
        // TODO 将归属地转换为对应的答辩会场
    }

    /**
     * 生成会场排课时间并且持久化
     *
     * @param sortResult 会场中场地的有序排列结果
     */
    private void generateSchedule(Map<String,List<String>> sortResult) {

        // TODO 计算时间周期
        // TODO 持久化数据库
    }

    /**
     * 获取排序算法
     *
     * @return MatrixSortStrategy 排序算法
     */
    abstract public MatrixSortStrategy getMatrixSortStrategy();
}
