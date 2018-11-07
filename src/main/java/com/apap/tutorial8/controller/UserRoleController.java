package com.apap.tutorial8.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping( value = "/addUser", method = RequestMethod.POST)
	private ModelAndView addUserSubmit(@ModelAttribute UserRoleModel user,RedirectAttributes redirect) {
		String message="";
		
		if(this.validatePass(user.getPassword())) {
			userService.addUser(user);
			message=null; 
		}
		else {
			message="password tidak sesuai ketentuan";
		}
		ModelAndView redir = new ModelAndView("redirect:/");
		redirect.addFlashAttribute("msg",message);
		return redir;
		
	}
	@RequestMapping( value = "/update")
	private String updatePassword() {
		return "update-password";
	}
	@RequestMapping(value="/submitPassword",method=RequestMethod.POST)
	private ModelAndView updatePasswordSubmit(@ModelAttribute PasswordModel pass, Model model, RedirectAttributes redir) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//mengambil username yang sedang login
		UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String message= "";
		if(pass.getConfirmPassword().equals(pass.getNewPassword())) {
			if(passwordEncoder.matches(pass.getOldPassword(), user.getPassword())) {
				if(validatePass(pass.getNewPassword())) {
					userService.changePassword(user, pass.getNewPassword());
					message = "password berhasil diubah";
				}
				else {
					message = "password tidak sesuai ketentuan";
					
				}
			}
			else {
				message = "password lama anda salah";
			}
		}
		else {
			message = "password baru anda tidak cocok";
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/update");
		redir.addFlashAttribute("msg",message);
		return modelAndView;
	}
	
	public boolean validatePass(String pass) {
		if(pass.length()>=8 && Pattern.compile("[0-9]").matcher(pass).find() && Pattern.compile("[a-zA-Z]").matcher(pass).find()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

}
