package com.example.demo.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.pojo.Category;

@Controller
public class HelloController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<div style='color:green'>Hello World!</div>";
	}

	@RequestMapping("/")
	public String index(HttpServletRequest request, ModelMap map) {
		HttpSession sessoin = request.getSession();
		if (sessoin.getAttribute("username") != null) {
			map.addAttribute("host", "Hello");
			map.addAttribute("user", "User: " + sessoin.getAttribute("username"));
			map.addAttribute("name", "Name: " + sessoin.getAttribute("name"));
			map.addAttribute("age", "Age: " + sessoin.getAttribute("age"));
			return "index";
		} else {

			return "redirect:/login";
		}
	}

	@Autowired
	CategoryMapper categoryMapper;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap map) {
		map.addAttribute("msg", "Hello!");
		return "login";
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Category user, Model model) {
		/*
		 * if (1 == 1) { throw new RuntimeException("eror!"); }
		 */
		String data = request.getParameter("data");
		logger.info("获取提交数据：" + data);
		YamlJsonParser json = new YamlJsonParser();
		Map<String, Object> parser = json.parseMap(data);

		String username = parser.get("username").toString();
		String password = parser.get("password").toString();
		logger.info("转换为JSON对象：" + username + password);

		boolean check;
		Category test = categoryMapper.findByUsername(username);

		if (test != null) {

			if (test.getPassword().equals(password))
				check = true;
			else
				check = false;
		} else {
			logger.warn(username + "用户不存在！");
			return "404";
		}

		if (check) {

			HttpSession sessoin = request.getSession();
			sessoin.setAttribute("username", username);
			sessoin.setAttribute("password", password);
			sessoin.setAttribute("name", test.getName());
			sessoin.setAttribute("age", test.getAge());
			logger.info("登陆成功，跳转");
			return "0";
		} else {
			logger.warn("密码不正确！" + test.getPassword() + password);

			return "error";
		}
	}

	/*
	 * @Autowired CategoryMapper categoryMapper;
	 * 
	 * 显示用户数据
	 * 
	 * @RequestMapping("/list") public String hello(ModelMap m) { List<Category>
	 * userList = categoryMapper.findAll(); m.addAttribute("userList", userList);
	 * return "userList"; }
	 * 
	 * 用于删除用户数据
	 * 
	 * @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET) public
	 * String deleteUser(@PathVariable int id) {
	 * 
	 * categoryMapper.delete(id); return "redirect:/list"; }
	 * 
	 * 用于添加用户数据
	 * 
	 * @RequestMapping(value = "/create", method = RequestMethod.GET) public String
	 * createUserForm(ModelMap map) { map.addAttribute("user", new Category());
	 * map.addAttribute("action", "create"); return "userForm"; }
	 * 
	 * @RequestMapping(value = "/create", method = RequestMethod.POST) public String
	 * postUser(ModelMap map,
	 * 
	 * @ModelAttribute @Valid Category user, BindingResult bindingResult) {
	 * 
	 * if (bindingResult.hasErrors()) { map.addAttribute("action", "create"); return
	 * "userForm"; }
	 * 
	 * categoryMapper.insert(user.getName(),user.getAge());
	 * 
	 * return "redirect:/list"; }
	 * 
	 * 用于更新用户数据
	 * 
	 * @RequestMapping(value = "/update/{id}", method = RequestMethod.GET) public
	 * String getUser(@PathVariable int id, ModelMap map) { map.addAttribute("user",
	 * categoryMapper.findById(id)); map.addAttribute("action", "update"); return
	 * "userForm"; }
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.POST) public String
	 * putUser(ModelMap map,
	 * 
	 * @ModelAttribute @Valid Category user, BindingResult bindingResult) {
	 * 
	 * if (bindingResult.hasErrors()) { map.addAttribute("action", "update"); return
	 * "userForm"; }
	 * 
	 * categoryMapper.update(user); return "redirect:/list"; }
	 */
}
