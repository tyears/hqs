package com.cccuu.project.mapper.remarks;

import java.util.List;
import java.util.Map;

/**
 * Created by wuyongjie on 2017/10/26 11:44.
 */
public interface RemarksMapper {
    public List<Map> getRemarksList(Map<String,String> map);

    public void updateRemark(Map map);
}
