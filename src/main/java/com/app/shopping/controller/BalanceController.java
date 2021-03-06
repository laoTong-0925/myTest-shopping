package com.app.shopping.controller;

import com.app.shopping.model.User;
import com.app.shopping.model.entity.UserImg;
import com.app.shopping.model.entity.UserInfo;
import com.app.shopping.service.UserImgService;
import com.app.shopping.service.UserInfoService;
import com.app.shopping.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class BalanceController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserImgService userImgService;

    /**
     * 余额
     *
     * @return
     */
    @RequestMapping("/balance")
    public ModelAndView myBalance(@RequestParam(value = "nkName") String nkName) {
        ModelAndView mv = new ModelAndView();
        User user = userService.selectByNkname(nkName);
        UserInfo userInfo = userInfoService.queryByUserId(user.getId());
        String balance = userInfo.getBalance();
        UserImg userImg = userImgService.queryById(user.getId());
        if (StringUtils.isBlank(balance)) {
            mv.addObject("balanceH", 0);
            mv.addObject("balanceT", 0);
        } else {
            String[] split = StringUtils.split(balance, ".");
            mv.addObject("balanceH", split[0]);
            if (split.length == 2)
                mv.addObject("balanceT", split[1]);
            mv.addObject("balanceT", 0);
        }
        mv.addObject("userName", user.getNkName());
        mv.addObject("userImg", userImg.getUserImg());

        mv.setViewName("balance");
        return mv;
    }
}
