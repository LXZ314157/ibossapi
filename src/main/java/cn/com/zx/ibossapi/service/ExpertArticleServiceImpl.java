package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.ExpertArticle;
import cn.com.zx.ibossapi.mapper.iboss.AccountUserMapper;
import cn.com.zx.ibossapi.mapper.iboss.ExpertArticleMapper;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 专家文章 服务层实现
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Service
public class ExpertArticleServiceImpl implements ExpertArticleService
{
	@Autowired
	private ExpertArticleMapper expertArticleMapper;

	@Autowired
	private AccountUserMapper accountUserMapper;

    /**
     * 分页查询专家文章列表--不含参数查询
     */
	@Override
    public PageInfo<ExpertArticle> selectExpertArticleList(String pageIndex, Integer pageSize,
                                                           String userCode, HashOperations hashOperations,String orgTg){
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        List<Map<String,Integer>> proIdAndReadNumFilterList = null;
        List<ExpertArticle> list = expertArticleMapper.selectExpertArticleList(orgTg);
        List<Map<String,Integer>> proIdAndReadNumList = accountUserMapper.getProIdAndReadNum();
        if(!ListUtil.isBlank(proIdAndReadNumList)){
            proIdAndReadNumFilterList = proIdAndReadNumList.stream().filter(a->a.get("proType")
                    .equals(Constant.EXPERTARTICLE)).collect(Collectors.toList());

        }
        if(!ListUtil.isBlank(list)){
            for(ExpertArticle expertArticle : list){
                String supportKey = RedisUtil.getSupportKey(Constant.EXPERTARTICLE,expertArticle.getEaId());
                Object supportObj = hashOperations.get(Constant.USERSUPPORT,supportKey);
                int supportSum = supportObj==null?0:Integer.parseInt(supportObj.toString());
                expertArticle.setSupportSum(supportSum);
                if(proIdAndReadNumFilterList != null){
                    Map<String,Integer> readNumMap = proIdAndReadNumFilterList.stream().filter(a->a.get("proId").equals(expertArticle.getEaId())).findFirst().orElse(null);
                    if(readNumMap==null){
                        expertArticle.setReadNum(0);
                    }else{
                        expertArticle.setReadNum(readNumMap.get("readNum"));
                    }
                }
            }
        }
        PageInfo<ExpertArticle> pageInfo = new PageInfo<>(list);
        return pageInfo;
	}

    /**
     * 分页查询专家文章列表--含参数多条件查询
     */
	@Override
    public PageInfo<ExpertArticle> selectExpertArticleListByParams(String pageIndex, Integer pageSize, String eaTitle){
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        Map<String,Object> map=new HashMap<>();
        map.put("eaTitle",eaTitle);
        List<ExpertArticle> list = expertArticleMapper.selectExpertArticleListByParams(map);
        PageInfo<ExpertArticle> pageInfo = new PageInfo<>(list);
        return pageInfo;
	}

    /**
     * 查看专家文章详细信息
     * @param eaId
     * @return
     */
    @Override
    public ExpertArticle getExpertArticleInfo(String eaId) {
        return expertArticleMapper.getExpertArticleInfo(eaId);
    }

    /**
     * 获取收藏的专家文章列表
     * @param proIdList
     * @return
     */
    @Override
    public List<ExpertArticle> selectCollectExpertArticleList(List<String> proIdList) {
        return expertArticleMapper.selectCollectExpertArticleList(proIdList);
    }

}
