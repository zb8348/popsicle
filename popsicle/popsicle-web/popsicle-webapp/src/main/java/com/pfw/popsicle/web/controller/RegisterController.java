package com.pfw.popsicle.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.service.UserService;
import com.pfw.popsicle.web.controller.form.UserForm;

@Controller
public class RegisterController {
	private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult result, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()) {
			logger.debug("register fail!");
			return "register";
		}

		User user = userService.register(userForm.getName(), userForm.getLoginName(),  userForm.getPassword(), userForm.getEmail());
		if (user != null && user.getId() > 0) {
			UsernamePasswordToken token = new UsernamePasswordToken(userForm.getLoginName(), userForm.getPassword());
			// 获取当前的Subject
			Subject currentUser = SecurityUtils.getSubject();
			try {
				currentUser.login(token);
				logger.info("用户'{}'登录成功!", user.getEmail());
				return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
			} catch (UnknownAccountException uae) {
			}
			// 验证是否登录成功
			if (!currentUser.isAuthenticated()) {
				token.clear();
			}
		}
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login";
	}
}
