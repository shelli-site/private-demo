package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * 自定义权限配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()// 都可以访问
				.antMatchers("/users/**").hasRole("ADMIN")// 需要相应角色权限才能访问
				.and().formLogin()// 基于Form表单验证
				.loginPage("/login").failureUrl("/login-error");// 自定义登陆界面
		http.csrf().disable();// 禁用security的csrf功能
	}

	/**
	 * 用户认证
	 * 添加用户名为admin密码为12345的ADMIN权限用户
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()// 认证信息储存在内存中
				.withUser("admin").password("12345").roles("ADMIN");
	}

}
