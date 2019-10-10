package com.example.sort;

import com.example.sort.core.MatrixSortStrategy;
import com.example.sort.core.SpaceSortStrategy;

import java.util.ArrayList;
import java.util.List;

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
        return new SpaceSortStrategy();
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

        List<List<String>> meetingRooms = new ArrayList<>();
        meetingRooms.add(areas);
        meetingRooms.add(areas2);
        meetingRooms.add(areas3);
        MeetingRoomsBuilder builder = new SpaceMeetingRoomsBuilder();
        builder.build(meetingRooms,null);
    }
}
