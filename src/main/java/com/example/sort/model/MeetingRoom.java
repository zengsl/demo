package com.example.sort.model;

import java.util.Date;

/**
 * TODO (用一句话描述该文件做什么)
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/11/5 9:40
 */
public class MeetingRoom {

    private String roomName;

    private Date startTime;

    private Date endTime;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
