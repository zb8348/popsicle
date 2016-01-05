package com.pfw.popsicle.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pfw.popsicle.web.controller.form.LoginForm;


@Controller
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
//	/** 
//     * 获取验证码图片和文本(验证码文本会保存在HttpSession中) 
//     */  
//    @RequestMapping("/getVerifyCodeImage")  
//    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
//        //设置页面不缓存  
//        response.setHeader("Pragma", "no-cache");  
//        response.setHeader("Cache-Control", "no-cache");  
//        response.setDateHeader("Expires", 0);  
//        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);  
//        //将验证码放到HttpSession里面  
//        request.getSession().setAttribute("verifyCode", verifyCode);  
//        System.out.println("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");  
//        //设置输出的内容的类型为JPEG图像  
//        response.setContentType("image/jpeg");  
//        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);  
//        //写给浏览器  
//        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());  
//    }  
      
      
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
    /** 
     * 用户登录 
     */  
    @RequestMapping(value="/login", method=RequestMethod.POST)  
    public String login(@ModelAttribute("loginForm")@Valid LoginForm user,BindingResult result, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request){
    	if (result.hasErrors()) {  
			logger.debug("login fail!");
           return "login";
		}
    	
//        //获取HttpSession中的验证码  
//        String verifyCode = (String)request.getSession().getAttribute("verifyCode");  
//        //获取用户请求表单中输入的验证码  
//        String submitCode = WebUtils.getCleanParam(request, "verifyCode");  
//        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){  
//            request.setAttribute("message_login", "验证码不正确");  
//            return resultPageURL;  
//        }  
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());  
//        token.setRememberMe(true);  
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();  
        try {  
            currentUser.login(token);  
            logger.info("用户'{}'登录成功!",user.getUsername());
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";  
        }catch(UnknownAccountException uae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "未知账户");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(IncorrectCredentialsException ice){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "密码不正确");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(LockedAccountException lae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "账户已锁定");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(ExcessiveAttemptsException eae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "用户名或密码错误次数过多");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(DisabledAccountException ae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "账号已失效");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(AuthenticationException ae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "用户名或密码不正确");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }catch(Exception ae){  
            redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "验证失败");
			redirectAttributes.addFlashAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
        }  
        //验证是否登录成功  
        if(!currentUser.isAuthenticated()){  
            token.clear();  
        }  
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login";  
    }  
      
      
    /** 
     * 用户登出 
     */  
    @RequestMapping("/logout")  
    public String logout(HttpServletRequest request){  
         SecurityUtils.getSubject().logout();  
         return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login";  
    }  
}
