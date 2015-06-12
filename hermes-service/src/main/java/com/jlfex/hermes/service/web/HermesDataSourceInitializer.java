package com.jlfex.hermes.service.web;
import java.sql.SQLException;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.service.common.Query;

@Component
public class HermesDataSourceInitializer {

	@Autowired
	private CommonRepository commonRepository;
	@Value("classpath:sql/mysqlAndH2/init_db.sql")
	private Resource dataScript;
	@Autowired
	private DataSource dataSource;

	/**
	 * DriverName: 
	 * 1:MySQL Connector Java; 
	 * 2:Oracle JDBC driver; 
	 * 3:H2 JDBC Driver
	 * @throws SQLException
	 */
	@PostConstruct
	public void initData() throws SQLException {
		String dbDriverName = getDBDriveName();
		Logger.info("当前数据库驱动名称："+dbDriverName);
		Logger.info("检查是否需要初始化脚本");
		Query query = new Query("from User");
		Long userCount = commonRepository.count(query.getCount(), new HashMap<String, Object>());
		if (userCount == 0) {
			Logger.info("开始进行初始化脚本");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.setSqlScriptEncoding("utf-8");
			populator.addScript(dataScript);
			populator.populate(dataSource.getConnection());
			Logger.info("初始化脚本已完成");
			return;
		}else{
			Logger.info("不需要初始化脚本");
		}
	}
    /**
     * 获取数据库驱动名称
     * @return
     * @throws SQLException
     */
	public String getDBDriveName() throws SQLException {
		try{
			return dataSource.getConnection().getMetaData().getDriverName();
		}catch(Exception e){
			Logger.error("获取数据库类型异常",e);
			return null;
		}
	}
}