package com.i2iintern.AOM_demo;

import com.i2iintern.AOM_demo.Connecters.OracleDbConnecter;
import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Controllers.BalanceController;
import com.i2iintern.AOM_demo.Repository.BalanceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class AomDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AomDemoApplication.class, args);
		VoltDbWrapper voltDbWrapper = new VoltDbWrapper();
		BalanceRepository balanceRepository=new BalanceRepository();
		// Spring Boot çalışırken terminalde veri görüntülemek için aşağıdaki kodu da çalıştırabilirsiniz
		try {
			Connection conn = OracleDbConnecter.getConnection();
			balanceRepository.GetRemainingCustomerBalanceByMsisdn(11144467890L);
			if (conn != null) {
				System.out.println("Connection Successful!");

			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
