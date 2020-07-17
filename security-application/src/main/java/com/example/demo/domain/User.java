package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author shinelon
 *
 */
@Entity // 实体
@XmlRootElement // MediaType 转为 XML
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id;// 唯一标识

	// @Column(nullable = false) // 映射为字段，值不能为空
	private String name;

	// @Column(nullable = false) // 映射为字段，值不能为空
	private String email;

	protected User() {
	}

	public User(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("User[id=%d, name='%s', email='%s']", id, name, email);
	}

}