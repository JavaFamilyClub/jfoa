package club.javafamily.runner.controller;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.domain.Customer;
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

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;

@Controller
public class SecurityController {

  @Autowired
  public SecurityController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping(API_VERSION + "/login")
  public String login(@RequestParam String userName, @RequestParam String password,
                      @RequestParam(required = false) boolean rememberMe) {
    Subject currentUser = SecurityUtils.getSubject();

    if (!currentUser.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

      token.setRememberMe(rememberMe);

      try {
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

  @PostMapping(API_VERSION + "/signup")
  public String signup(@Valid @ModelAttribute CustomerVO customerVO,
                       BindingResult bindingResult,
                       HttpServletRequest request) throws CloneNotSupportedException {
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

    Customer customer = customerVO.convert();

    Customer cloneCustomer = (Customer) customer.clone(); // for keep original password.
    Integer id = customerService.insertCustomer(customer);

    if(id == null) {
      throw new MessageException("Registration User Failed. " + customer.getAccount());
    }

    cloneCustomer.setId(id);

    // sign up success
    customerService.notifySignUpSuccess(cloneCustomer);

    // goto login after sign up.
    return "signupSuccess";
  }

  @GetMapping(API_VERSION + "/customer/verify")
  public String verify(String token, String customerId, Long dateTime) {
    // TODO user verify

    return "verifyResult";
  }

  @RequiresAuthentication
  @GetMapping(API_VERSION + "/principal")
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
