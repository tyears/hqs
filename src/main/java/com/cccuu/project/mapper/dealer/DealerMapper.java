package com.cccuu.project.mapper.dealer;

import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 客户（经销商或大厂部）Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月14日 11:50:55
 */
public interface DealerMapper extends BaseMapper<Dealer>{
    /**
     * 分页查询
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByPage(Map<String,Object> params);
    /**
     * 前端客户查询
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryDealerList(Map<String,Object> params);
    /**
     * 查询数量
     * @param params
     * @return
     */
    public Integer countDealer(Map<String,Object> params);

    /**
     * 修改总评价或合作情况	type 0:总评价 1:合作评价
     * @param params
     */
    public void updatePingText(Map<String,String > params);

    /**
     * 获取当前最大编号
     * @return
     */
    public Integer codeNum();

    /**
     * 检查注册手机号和其他号码是否存在
     * @param array
     * @return
     */
    public List<Map> isHasPhone(Map map);

    /**
     * 分页查询经销商导入信息列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListImportByPage(Map<String,Object> params);

    /**
     * 更新经销商导入状态
     */
    public void updateImport();

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int addMulti(List<Dealer> list);

    /**
     * 经销商和经销商下的产品
     * @param params
     * @return
     */
    public List<Dealer> queryListByMapDouble(Map<String,String> params);
    public void updateDealerMerit();
    public List<Map<String,Object>> historyGiveByDealerId(String dealerID);
}