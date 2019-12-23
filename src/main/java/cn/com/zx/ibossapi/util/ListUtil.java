package cn.com.zx.ibossapi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lvxuezhan
 * @apiNote
 */
public class ListUtil {
    /**
     * 这个类用来将一个大的数组拆成若干个小的数组
     *
     * @param list
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitArrayList(List<T> list, int pageSize) {
        if (ListUtil.isBlank(list) || pageSize <= 0) {
            return null;
        }
        List<List<T>> dataList = new ArrayList<>();
        if (list.size() < pageSize) {
            dataList.add(list);
            return dataList;
        }
        int totalCount = list.size();
        List<T> subList;
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        for (int i = 1; i <= pageCount; i++) {
            if (m == 0) {
                subList = list.subList((i - 1) * pageSize, pageSize * (i));
                dataList.add(subList);
            } else {
                if (i == pageCount) {
                    subList = list.subList((i - 1) * pageSize, totalCount);
                    dataList.add(subList);
                } else {
                    subList = list.subList((i - 1) * pageSize, pageSize * (i));
                    dataList.add(subList);
                }
            }
        }
        return dataList;
    }

    /**
     * 判断某个list数组是否为空或大小是否为0
     *
     * @param list
     * @return
     */
    public static boolean isBlank(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 当same为true时获取 serverList 和 clientList中都存在的数组
     * 当same为false时 获取在clientList中存在而在serverList中不存在的数组
     * 使用这个方法需要重写T的equals方法，具体判断逻辑视具体的情况而定
     *
     * @param same
     * @param serverList
     * @param clientList
     * @param <T>
     * @return
     */
    public static <T> List<T> getSameOrDiffList(List<T> serverList, List<T> clientList, boolean same) {
        if (ListUtil.isBlank(serverList) || ListUtil.isBlank(clientList)) {
            return clientList;
        }
        List<T> result = new ArrayList<>();
        for (T t : clientList) {
            if (same && serverList.contains(t)) {
                result.add(t);
            } else if (!same && !serverList.contains(t)) {
                result.add(t);
            }
        }
        return result;
    }

}

