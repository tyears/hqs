package com.cccuu.project.service.remarks;

import java.util.List;
import java.util.Map;

/**
 * Created by wuyongjie on 2017/10/26 11:39.
 */
public interface RemarksService {

    public List<Map> getRemarksList(Map<String,String> map);

    public void updateRemark(Map map);
}
