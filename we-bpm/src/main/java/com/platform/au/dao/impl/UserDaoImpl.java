package com.platform.au.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.platform.au.dao.UserDao;
import com.platform.au.entity.User;


@Repository("userDaoImpl")
public class UserDaoImpl  implements UserDao {


    @Override
    public String getUserIdByName(String userName) {
		return null;
    }

    @Override
    public String getUserNameById(String userId) {
		return null;
    }

	@Override
	public User getUserByName(String userName) {
		return null;
	}

    @Override
    public String getUserEmpNameById(String userId) {
		return null;
    }
    
    public HashMap<String,Object> getUsers(List<String> users) throws Exception {
		return null;
    }
    
 
    public User getUserById(String userId)throws Exception {
		return null;
    }

	@Override
	public List<Map> getEmpIdByEmpCode(String empCode) {
		return null;
	}


	@Override
	public List<Map> getUserByEmpId(String empId) {
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#selectForPage(java.util.Map)
	 */
	@Override
	public List<User> selectForPage(Map<String, Object> param) throws Exception {
		// TODO 
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#selectCount(java.util.Map)
	 */
	@Override
	public Integer selectCount(Map<String, Object> param) throws Exception {
		// TODO 
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#selectByPK(java.util.Map)
	 */
	@Override
	public User selectByPK(Map<String, Object> params) throws Exception {
		// TODO 
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#selectByPropertys(java.util.Map)
	 */
	@Override
	public User selectByPropertys(Map<String, Object> params) throws Exception {
		// TODO 
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#checkData(java.lang.Object)
	 */
	@Override
	public void checkData(User entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#checkDataBeforeDalete(java.lang.Object)
	 */
	@Override
	public void checkDataBeforeDalete(User entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#insert(java.lang.Object)
	 */
	@Override
	public void insert(User entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(User entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(User entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#deleteByParam(java.util.Map)
	 */
	@Override
	public void deleteByParam(Map<String, Object> param) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#delete(java.util.List)
	 */
	@Override
	public void delete(List<User> entity) throws Exception {
		// TODO 
		
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#findAll(java.util.Map)
	 */
	@Override
	public List<User> findAll(Map<String, Object> param) throws Exception {
		// TODO 
		return null;
	}

	/* （非 Javadoc）
	 * @see com.platform.common.dao.BaseDao#checkDataBeforeDalete(java.util.Map)
	 */
	@Override
	public void checkDataBeforeDalete(Map<String, Object> params) throws Exception {
		// TODO 
		
	}

}