package com.cccuu.project.service.remarks.impl;

import com.cccuu.project.mapper.remarks.RemarksMapper;
import com.cccuu.project.service.remarks.RemarksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wuyongjie on 2017/10/26 11:40.
 */
@Service("remarksService")
public class RemarksServiceImpl implements RemarksService {

    @Resource
    private RemarksMapper remarksMapper;

    @Override
    public List<Map> getRemarksList(Map<String,String> map) {
        return remarksMapper.getRemarksList(map);
    }

    @Override
    @Transactional
    public void updateRemark(Map map) {
        remarksMapper.updateRemark(map);
    }
}
