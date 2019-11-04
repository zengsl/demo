package com.example.sort;

import com.example.sort.core.MatrixSortStrategy;
import com.example.sort.core.PeriodSortStrategy;
import com.example.sort.utils.DateUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * TODO (用一句话描述该文件做什么)
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/11/4 16:17
 */
public class PeriodMeetingRoomsBuilder extends BaseMeetingRoomsBuilder {
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
        meetingRooms.put("room1_group1_2019-11-04_08:00_11:00_30_14:00_16:00_35", areas);
        meetingRooms.put("room1_group2_2019-11-04_08:00_11:00_30_14:00_16:00_35", areas2);
        meetingRooms.put("room2_group2_2019-11-04_08:00_11:00_30_14:00_16:00_35", areas3);

        String currentKey = "room1_group1_2019-11-04_08:00_11:00_30_14:00_16:00_35";
        String[] params = currentKey.split("_");
        String roomKey = params[0] + "_" + params[1];
        Date startDate = DateUtils.toDate(params[2]);
        String []mornStartTime = params[3].split(":");
        Date mSth = DateUtils.setHour(startDate,Integer.parseInt(mornStartTime[0]));
        Date mstartDate = DateUtils.setMinute(mSth,Integer.parseInt(mornStartTime[1]));

        String []mornEndTime = params[4].split(":");
        Date mEth = DateUtils.setHour(startDate,Integer.parseInt(mornEndTime[0]));
        Date msEndDate = DateUtils.setMinute(mEth,Integer.parseInt(mornEndTime[1]));

        String []afterStartTime = params[6].split(":");
        Date aSth = DateUtils.setHour(startDate,Integer.parseInt(afterStartTime[0]));
        Date aStartDate = DateUtils.setHour(aSth,Integer.parseInt(afterStartTime[1]));

        String []afterEndTime = params[7].split(":");
        Date aEth = DateUtils.setHour(startDate,Integer.parseInt(afterEndTime[0]));
        Date aEndDate = DateUtils.setHour(aEth,Integer.parseInt(afterEndTime[1]));

        String mornPeriod = params[5];
        String afterPeriod = params[8];



        MeetingRoomsBuilder builder = new PeriodMeetingRoomsBuilder();
//        builder.build(meetingRooms);

    }
}
