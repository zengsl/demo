package com.example.sort;

import com.example.sort.core.MatrixSortStrategy;
import com.example.sort.model.MeetingRoom;
import com.example.sort.utils.DateFormator;
import com.example.sort.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 会场生产器基础类
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 16:03
 */
abstract public class BaseMeetingRoomsBuilder implements MeetingRoomsBuilder {


    @Override
    public void build(Map<String,List<String>> rooms) {
        convertArea2Room(rooms);
        Map<String, List<MeetingRoom>> sortResult = getMatrixSortStrategy().doOperate(rooms);

       // TODO 临时测试用的打印代码
        for(Map.Entry<String, List<MeetingRoom>> entry : sortResult.entrySet()) {
            String key = entry.getKey();
            for(MeetingRoom room : entry.getValue()) {
                System.out.println(key + ":" + room.getRoomName() + ":" + DateUtils.toString(room.getStartTime(), DateFormator.YEAR_MONTH_DAY_HH_MM_SS) + ":" + DateUtils.toString(room.getEndTime(), DateFormator.YEAR_MONTH_DAY_HH_MM_SS));
            }
            System.out.println("************************************");
        }

       // generateSchedule(sortResult);
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
