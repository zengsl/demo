package com.example.sort.core;

import java.util.List;
import java.util.Map;


/**
 * 矩阵排序策略接口
 *
 * @author Zengsl
 * @version V1.0
 * @date 2019/10/10 15:20
 */
public interface MatrixSortStrategy {

    /**
     * 排序操作
     *
     * @param elements 待排序的矩阵
     * @return List<List<String>> 排好序之后的矩阵
     */
    Map<String,List<String>> doOperate(Map<String,List<String>> elements);
}
