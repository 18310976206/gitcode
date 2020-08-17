package com;

import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.activiti.bean.Users;
import com.activiti.dao.UserDao;

/************************************************/
/*FileName     :    TestClass.java                            
Author        :	   gzl 
Date          :    2020年8月12日 下午3:59:41                                                            
Brief Description: TODO                  */
/************************************************/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BpmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
public class TestClass {

	 /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private int port;
 
    private URL base;
 
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserDao userDao;
 
    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);
    }
 
    /**
     * 向"/test"地址发送请求，并打印返回结果
     * @throws Exception
     */
    //@Test
//    public void test1() throws Exception {
// 
//        ResponseEntity<String> response = this.restTemplate.getForEntity(
//                this.base.toString() + "/test", String.class, "");
//        System.out.println(String.format("测试结果为：%s", response.getBody()));
//    }
    
    //@Test
//    public void getAllTest() throws Exception {
// 
//        ResponseEntity<List> response = this.restTemplate.getForEntity(
//                this.base.toString() + "/getAll", List.class, "");
//        System.out.println(String.format("测试结果为：%s", response.getBody()));
//    }
//    
//    @Test
//    public void getUserByIdTest() throws Exception {
// 
//        ResponseEntity<User> response = this.restTemplate.getForEntity(
//                this.base.toString() + "/getUserById?id=1", User.class, "");
//        System.out.println(String.format("测试结果为：%s", response.getBody().toString()));
//    }

	@Test
	public void save() {
		Users users = new Users();
		users.setName("老三");
		users.setAge(100);
		users.setAddress("南京");
		this.userDao.save(users);
	}
	/**
	 * Repository--@Query测试
	 */
//	@Test
//	public void testQueryByNameUseSQL() {
//		List<Users> list = this.userDao.queryByNameUseSQL("张三");
//		for (Users users : list) {
//			System.out.println(users);
//		}
//	}

	/**
	 * Repository--@Query测试
	 */
//	@Test
//	@Transactional //@Transactional与@Test 一起使用时 事务是自动回滚的。
//	@Rollback(false) //取消自动回滚
//	public void testUpdateUsersNameById() {
//		this.userDao.updateUsersNameById("张三三", 1);
//	}
}

 
 
