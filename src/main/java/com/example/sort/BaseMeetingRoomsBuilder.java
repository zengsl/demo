package com.example.sort;

import com.example.sort.core.MatrixSortStrategy;

import java.util.List;

/**
 * 会场生产器基础类
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 16:03
 */
abstract public class BaseMeetingRoomsBuilder implements MeetingRoomsBuilder {


    @Override
    public void build(List<List<String>> rooms, String currentMeeting) {
        convertArea2Room(rooms);
        List<List<String>> sortResult = getMatrixSortStrategy().doOperate(rooms);
        generateSchedule(sortResult);
    }

    /**
     * 将归属地转换为对应的答辩会场
     *
     * @param areas 归属地
     */
    private void convertArea2Room(List<List<String>> areas) {
        // TODO 将归属地转换为对应的答辩会场
    }

    /**
     * 生成会场排课时间摈弃持久化
     *
     * @param sortResult 会场中场地的有序排列结果
     */
    private void generateSchedule(List<List<String>> sortResult) {

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
