package com.example.sort;

import com.example.sort.core.MatrixSortStrategy;
import com.example.sort.core.PeriodSortStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用矩阵间隔排序策略排列出会场的时间表
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 16:11
 */
public class SpaceMeetingRoomsBuilder extends BaseMeetingRoomsBuilder {

    @Override
    public MatrixSortStrategy getMatrixSortStrategy() {
        return new PeriodSortStrategy();
    }

    public static void main(String[] args) {
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

        Map<String,List<String>> meetingRooms = new HashMap<>(3);
        meetingRooms.put("room1_group1_20191104_0800_1100_30_1400_1600_35", areas);
        meetingRooms.put("room1_group2_20191104_0800_1100_30_1400_1600_35", areas2);
        meetingRooms.put("room2_group2_20191104_0800_1100_30_1400_1600_35", areas3);

        MeetingRoomsBuilder builder = new SpaceMeetingRoomsBuilder();
        builder.build(meetingRooms);

    }
}
