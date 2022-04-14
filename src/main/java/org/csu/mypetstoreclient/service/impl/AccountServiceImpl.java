package org.csu.mypetstoreclient.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhenzi.sms.ZhenziSmsClient;
import org.csu.mypetstoreclient.vo.AccountVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.*;
import org.csu.mypetstoreclient.persistence.*;
import org.csu.mypetstoreclient.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    //自动注入Mapper
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BannerDataMapper bannerDataMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private SignOnMapper signOnMapper;

    @Override
    public CommonResponse<AccountVO> getAccountByUsername(String username) {
        Account account = accountMapper.selectById(username);
        if(account == null){
            return CommonResponse.createForError("用户不存在");
        }
        Profile profile = profileMapper.selectById(username);
        BannerData bannerdata = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
        AccountVO accountVO = entityToVO(account, profile, bannerdata);
        return CommonResponse.createForSuccess(accountVO);
    }

    @Override
    public CommonResponse<AccountVO> getAccountByUsernameAndPassword(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username" , username);
        queryWrapper.eq("password" , password);

        SignOn signOn = signOnMapper.selectOne(queryWrapper);
        if(signOn == null){
            return CommonResponse.createForError("登陆失败！用户名或密码不存在");
        }

        return getAccountByUsername(username);
    }

    @Override
    public CommonResponse<AccountVO> getAccountByPhoneAndPassword(String phone, String password) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone" , phone);
        QueryWrapper<SignOn> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("password" , password);

        Account account = accountMapper.selectOne(queryWrapper);
        SignOn signOn = signOnMapper.selectOne(queryWrapper1);
        if(account == null || !Objects.equals(signOn.getUsername(), account.getUsername())){
            return CommonResponse.createForError("登陆失败");
        }
        return getAccountByUsername(account.getUsername());
    }

    @Override
    public CommonResponse<AccountVO> insertAccount(AccountVO accountVO) {
        Account account = new Account();
        account.setUsername(accountVO.getUsername());
        account.setEmail(accountVO.getEmail());
        account.setFirstName(accountVO.getFirstName());
        account.setLastName(accountVO.getLastName());
        account.setStatus("ok");
        account.setAddress1(accountVO.getAddress1());
        account.setAddress2(accountVO.getAddress2());
        account.setCity(accountVO.getCity());
        account.setState(accountVO.getState());
        account.setCountry(accountVO.getCountry());
        account.setZip(accountVO.getZip());
        account.setPhone(accountVO.getPhone());

        SignOn signOn = new SignOn();
        signOn.setUsername(accountVO.getUsername());
        signOn.setPassword(accountVO.getPassword());

        Profile profile = new Profile();
        profile.setUsername(accountVO.getUsername());
        profile.setLanguagePreference(accountVO.getLanguagePreference());
        profile.setFavouriteCategoryId(accountVO.getFavouriteCategoryId());
        profile.setBannerOption(Boolean.compare(accountVO.isBannerOption(),Boolean.TRUE)+1);
        profile.setListOption(Boolean.compare(accountVO.isListOption(),Boolean.TRUE)+1);

        BannerData bannerdata = new BannerData();
        if(profile.getBannerOption()==1){
            bannerdata.setFavouriteCategoryId(accountVO.getFavouriteCategoryId());
            bannerdata.setBannerName(accountVO.getBannerName());
        }else {
            bannerdata.setFavouriteCategoryId("");
            bannerdata.setBannerName("");
        }

        accountMapper.insert(account);
        signOnMapper.insert(signOn);
        profileMapper.insert(profile);

        if(accountMapper.selectById(accountVO.getUsername()) != null){
            return CommonResponse.createForError("注册失败！用户名已存在");
        }
        return CommonResponse.createForSuccess("注册成功！",accountVO);
    }

    @Override
    public CommonResponse<AccountVO> getAccountByPhone(String phone){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Account account = accountMapper.selectOne(queryWrapper);
        if (account == null){
            return CommonResponse.createForError("电话号码未被注册");
        }
        else
        {
            Profile profile = profileMapper.selectById(account.getUsername());
            BannerData bannerData = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
            AccountVO accountVO = entityToVO(account, profile, bannerData);
            return  CommonResponse.createForSuccess(accountVO);
        }
    }

    @Override
    public CommonResponse<Integer> sendMsgToPhone(String phone){
        try{
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","111394", "ed030c42-5414-4b57-881f-e61180c8a026");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phone);
            params.put("templateId", "8942");
            String[] templateParams = new String[2];

            // 生成一个随机4位数当做验证码
            Random random = new Random();
            int vertificationCode = 1000 + random.nextInt(9000);
            templateParams[0] = String.valueOf(vertificationCode);
            templateParams[1] = "3";    // 有效时间
            params.put("templateParams", templateParams);

            String result = client.send(params);
            SendResult sendResult = JSON.parseObject(result, SendResult.class);
            if(sendResult.getCode()==0){
                return CommonResponse.createForSuccess(vertificationCode);
            } else if (sendResult.getCode()==100){
                return CommonResponse.createForError("电话不存在！请检查电话号码是否输入正确或已注销");
            } else {
                return CommonResponse.createForError("系统请求出错");
            }
        } catch (Exception e){
             return CommonResponse.createForError("系统请求出错");
        }
    }

    @Override
    public CommonResponse updateAccount(AccountVO accountVO){
        Account account = new Account();
        account.setUsername(accountVO.getUsername());
        account.setEmail(accountVO.getEmail());
        account.setFirstName(accountVO.getFirstName());
        account.setLastName(accountVO.getLastName());
        account.setStatus("ok");
        account.setAddress1(accountVO.getAddress1());
        account.setAddress2(accountVO.getAddress2());
        account.setCity(accountVO.getCity());
        account.setState(accountVO.getState());
        account.setCountry(accountVO.getCountry());
        account.setZip(accountVO.getZip());
        account.setPhone(accountVO.getPhone());

        Profile profile = new Profile();
        profile.setUsername(accountVO.getUsername());
        profile.setLanguagePreference(accountVO.getLanguagePreference());
        profile.setFavouriteCategoryId(accountVO.getFavouriteCategoryId());
        profile.setBannerOption(Boolean.compare(accountVO.isBannerOption(),Boolean.TRUE)+1);
        profile.setListOption(Boolean.compare(accountVO.isListOption(),Boolean.TRUE)+1);

        UpdateWrapper<Account> accountUpdateWrapper = new UpdateWrapper<>();
        accountUpdateWrapper.eq("userid", account.getUsername());
        int result = accountMapper.update(account, accountUpdateWrapper);
        if (result == 0){
            return CommonResponse.createForSuccessMessage("修改失败");
        } else{
            return CommonResponse.createForError("修改成功");
        }
    }

    public CommonResponse changePhone(String username, String phone){
        Account account = accountMapper.selectById(username);
        account.setPhone(phone);
        int result = accountMapper.updateById(account);
        if (result == 0){
            return CommonResponse.createForSuccessMessage("修改失败");
        } else{
            return CommonResponse.createForError("修改成功");
        }
    }

    private AccountVO entityToVO(Account account, Profile profile, BannerData bannerdata){
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(account.getUsername());
        accountVO.setPassword("");
        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getStatus());
        accountVO.setAddress1(account.getAddress1());
        accountVO.setAddress2(account.getAddress2());
        accountVO.setCity(account.getCity());
        accountVO.setState(account.getState());
        accountVO.setCountry(account.getCountry());
        accountVO.setZip(account.getZip());
        accountVO.setPhone(account.getPhone());

        accountVO.setLanguagePreference(profile.getLanguagePreference());
        accountVO.setBannerOption(profile.getBannerOption()==1);
        accountVO.setListOption(profile.getListOption()==1);
        if(profile.getBannerOption()==1){
            accountVO.setFavouriteCategoryId(profile.getFavouriteCategoryId());
            accountVO.setBannerName(bannerdata.getBannerName());
        }else {
            accountVO.setFavouriteCategoryId("");
            accountVO.setBannerName("");
        }

        return accountVO;
    }

    public CommonResponse modifyPwdByPhone(String phone, String password){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Account account = accountMapper.selectOne(queryWrapper);
        SignOn signOn = new SignOn();
        signOn.setUsername(account.getUsername());
        signOn.setPassword(password);
        UpdateWrapper<SignOn> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", account.getUsername());
        int result = signOnMapper.update(signOn, updateWrapper);
        if (result == 0){
            return CommonResponse.createForSuccessMessage("修改失败！");
        } else{
            return CommonResponse.createForError("修改成功！");
        }
    }
}
