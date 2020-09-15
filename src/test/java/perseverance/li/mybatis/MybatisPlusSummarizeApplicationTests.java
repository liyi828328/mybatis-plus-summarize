package perseverance.li.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import perseverance.li.mybatis.bean.EmpBean;
import perseverance.li.mybatis.dao.EmpDao;

import java.util.List;

@SpringBootTest
class MybatisPlusSummarizeApplicationTests {

    private Logger logger = LoggerFactory.getLogger(MybatisPlusSummarizeApplicationTests.class);
    @Autowired
    private EmpDao empDao;

    @Test
    void contextLoads() {
        List<EmpBean> beanList = empDao.selectList(null);
        for (EmpBean empBean : beanList) {
            logger.info(empBean.toString());
        }
        logger.info("------");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("emp_id", 1);
        EmpBean empBean = empDao.selectOne(wrapper);
        logger.info(empBean.toString());
    }

    @Test
    void test01() {
        EmpBean empBean = new EmpBean();
        empBean.setAge(100);
        empBean.setName("王广峰");
        empBean.setIdCard("110110109010391031");
        int insert = empDao.insert(empBean);
        logger.info("insert :" + insert);
    }

    @Test
    void test02() {
        logger.info("=====================");
        Page<EmpBean> empBeanPage = empDao.selectPage(new Page<>(2, 3), null);
        for (EmpBean record : empBeanPage.getRecords()) {
            logger.info(record.toString());
        }
    }

}
