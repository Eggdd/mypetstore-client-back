package org.csu.mypetstoreclient.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstoreclient.vo.AccountVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.BannerData;
import org.csu.mypetstoreclient.persistence.BannerDataMapper;
import org.csu.mypetstoreclient.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/accounts/")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private BannerDataMapper bannerDataMapper;

    @PostMapping("loginByUsername")
    @ResponseBody
    public CommonResponse<AccountVO> loginByUsername(
            @RequestParam String username,
            @RequestParam String passwprd,
            HttpSession session) {
        CommonResponse<AccountVO> response = accountService.getAccountByUsernameAndPassword(username, passwprd);
        if (response.isSuccess()) {
            session.setAttribute("loginByUsername_account", response.getData());
        }
        return response;
    }

    @PostMapping("loginByPhone")
    @ResponseBody
    public CommonResponse<AccountVO> loginByPhone(
            @RequestParam String phone,
            HttpSession session) {
        CommonResponse<AccountVO> response = accountService.getAccountByPhone(phone);
        if (response.isSuccess()) {
            return CommonResponse.createForSuccess("登陆成功", response.getData());
        } else {
            return  CommonResponse.createForError("登陆失败！该电话号码未注册！");
        }
    }

    @PostMapping("register")
    @ResponseBody
    public CommonResponse<AccountVO> register(
            @RequestParam String username,
            @RequestParam String passwprd,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address1,
            @RequestParam String address2,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zip,
            @RequestParam String country,
            @RequestParam String favouriteCategoryId,
            @RequestParam String languagePreference,
            @RequestParam boolean listOption,
            @RequestParam boolean bannerOption,
            HttpSession session) {

        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(username);
        accountVO.setPassword(passwprd);
        accountVO.setEmail(email);
        accountVO.setFirstName(firstName);
        accountVO.setLastName(lastName);
        accountVO.setStatus("OK");
        accountVO.setAddress1(address1);
        accountVO.setAddress2(address2);
        accountVO.setCity(city);
        accountVO.setState(state);
        accountVO.setCountry(country);
        accountVO.setZip(zip);
        accountVO.setPhone(phone);

        accountVO.setLanguagePreference(languagePreference);
        accountVO.setBannerOption(bannerOption);
        accountVO.setListOption(listOption);
        if (bannerOption) {
            accountVO.setFavouriteCategoryId(favouriteCategoryId);

            QueryWrapper<BannerData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("favcategory", favouriteCategoryId);
            BannerData bannerdata = bannerDataMapper.selectOne(queryWrapper);
            accountVO.setBannerName(bannerdata.getBannerName());
        } else {
            accountVO.setFavouriteCategoryId("");
            accountVO.setBannerName("");
        }
        if (accountService.getAccountByUsername(accountVO.getUsername()).isSuccess()) {
            return CommonResponse.createForError("注册失败！该用户名已存在！");
        }
        if (accountService.getAccountByPhone(accountVO.getPhone()).isSuccess()) {
            return CommonResponse.createForError("注册失败！该手机号已被注册！");
        }
        CommonResponse<AccountVO> response = accountService.insertAccount(accountVO);
        if (response.isSuccess()) {
            session.setAttribute("register_account", response.getData());
        }
        return response;
    }

    @GetMapping("{username}")
    @ResponseBody
    public CommonResponse<AccountVO> getAccountByUsername(@PathVariable("username")String username){
        return accountService.getAccountByUsername(username);
    }

    @GetMapping("phones")
    @ResponseBody
    public CommonResponse<AccountVO> getAccountByPhone(@RequestParam("phone")String phone){
        return accountService.getAccountByPhone(phone);
    }

    @PostMapping("sendMsg")
    @ResponseBody
    public CommonResponse<Integer> sendMsg(@RequestParam("phone")String phone){
        return accountService.sendMsgToPhone(phone);
    }

    @PostMapping("modifyPassword")
    @ResponseBody
    public CommonResponse modifyPasswordByPhone(@RequestParam("phone")String phone,
                                                @RequestParam("password")String password)
    {
        return accountService.modifyPwdByPhone(phone, password);
    }

    @PutMapping("{username}/changePhone")
    @ResponseBody
    public CommonResponse changePhone(@PathVariable("username")String username, @RequestParam("phone") String phone){
        return accountService.changePhone(username, phone);
    }
}
