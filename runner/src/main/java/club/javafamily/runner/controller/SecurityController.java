package club.javafamily.runner.controller;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.vo.CustomerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class SecurityController {

  @Autowired
  public SecurityController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/api/1.0/login")
  public String login(@RequestParam String userName, @RequestParam String password,
                      @RequestParam(required = false) boolean rememberMe) {
    Subject currentUser = SecurityUtils.getSubject();

    // 如果用户没有登录
    if (!currentUser.isAuthenticated()) {
      // 封装用户和密码为 UsernamePasswordToken
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

      // 设置记住我, 在整个会话中记住身份.
      token.setRememberMe(rememberMe);

      try {
        // 执行登录
        currentUser.login(token);
      } catch (AuthenticationException e) {
        throw e;
      }
    }

    // redirect index page when login success.
    return "redirect:/";
  }

  @GetMapping("signup")
  public String gotoSignupPage(ModelMap map) {
    CustomerVO customerVO = new CustomerVO();

    map.put("customer", customerVO);

    return "signup";
  }

  @PostMapping("/api/1.0/signup")
  public String signup(@Valid @ModelAttribute CustomerVO customer,
                       BindingResult bindingResult,
                       HttpServletRequest request)
  {
    List<ObjectError> allErrors = bindingResult.getAllErrors();
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < allErrors.size(); i++) {
      ObjectError error = allErrors.get(i);

      if(error instanceof FieldError) {
        sb.append(((FieldError) error).getField())
           .append(": ")
           .append(error.getDefaultMessage());
      }
      else {
        sb.append(error.getDefaultMessage());
      }

      if(i < allErrors.size() - 1) {
        sb.append("\n");
      }
    }

    if(StringUtils.hasText(sb)) {
      throw new MessageException(sb.toString());
    }

    // goto login after sign up.
    return "login";
  }

  @RequiresAuthentication
  @GetMapping("/api/1.0/principal")
  @ResponseBody
  public String getCurrentUser() {
    Subject subject = SecurityUtils.getSubject();
    Object principal = subject.getPrincipal();

    return Objects.toString(principal, "");
  }

  @GetMapping("/error")
  public String gotoErrorPage() {
    return "/error/exception";
  }

  private final CustomerService customerService;
}
