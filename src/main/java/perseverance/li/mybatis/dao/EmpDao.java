package perseverance.li.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import perseverance.li.mybatis.bean.EmpBean;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Email: yi.li@yulore.com
 * Create: 2020-09-14 14:49
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2020-09-14 14:49 : Create by LiYi
 * ---------------------------------------------------------------
 */
@Component
@Mapper
public interface EmpDao extends BaseMapper<EmpBean> {
}
