package perseverance.li.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
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
        Page<EmpBean> empBeanPage = empDao.selectPage(new Page<>(2, 3), null);
        for (EmpBean record : empBeanPage.getRecords()) {
            logger.info(record.toString());
        }
        String countId = empBeanPage.getCountId();
        Long maxLimit = empBeanPage.getMaxLimit();
        long total = empBeanPage.getTotal();
        long size = empBeanPage.getSize();
        boolean next = empBeanPage.hasNext();
        boolean previous = empBeanPage.hasPrevious();
        logger.info("countid:{} , maxLimit:{} , total:{} , size:{} , next:{} , previous:{}",
                countId, maxLimit, total, size, next, previous);
        List<OrderItem> orders = empBeanPage.getOrders();
        for (OrderItem order : orders) {
            logger.info(order.toString());
        }
    }

    @Test
    void test03() {
        //开启了进制全表更新删除后 ，如果执行会出现如下异常提示
        //com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation
        EmpBean empBean = new EmpBean();
        empBean.setName("福克斯");
        empBean.setAge(1001);
        int update = empDao.update(empBean, null);
        logger.info("update :" + update);
    }

    @Test
    void test04() {
        EmpBean empBean = new EmpBean();
        empBean.setName("福克斯");
        empBean.setAge(1001);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("emp_id", 1);
        int update = empDao.update(empBean, updateWrapper);
        logger.info("update :" + update);
    }

    @Test
    void test05() {
        //IllegalSQLInnerInterceptor 非法sql拦截器
        //com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: 非法SQL，必须要有where条件
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.or();
        List list = empDao.selectList(wrapper);
        logger.info(list.toString());
    }
}
