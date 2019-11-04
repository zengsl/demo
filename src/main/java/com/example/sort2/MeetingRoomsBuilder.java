package com.example.sort2;

import java.util.List;
import java.util.Map;

/**
 * 会场生成器
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 15:59
 */
public interface MeetingRoomsBuilder {

    /**
     * 生成会场信息
     *
     * @param rooms 多个会场集合，每个会场中存放当前会场所有的答辩会场名称
     * @param currentMeeting 当前会场标识
     */
    void build(Map<String, List<String>> rooms, String currentMeeting);
}
