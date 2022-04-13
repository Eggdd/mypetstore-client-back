package org.csu.mypetstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstoreclient.vo.AccountVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.*;
import org.csu.mypetstoreclient.persistence.*;
import org.csu.mypetstoreclient.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        Profile profile = profileMapper.selectById(username);
        BannerData bannerdata = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
        if(account == null){
            return CommonResponse.createForError("登陆失败");
        }
        AccountVO accountVO = entityToVO(account, profile, bannerdata);
        return CommonResponse.createForSuccess("登陆成功",accountVO);
    }

    @Override
    public CommonResponse<AccountVO> getAccountByUsernameAndPassword(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username" , username);
        queryWrapper.eq("password" , password);

        SignOn signOn = signOnMapper.selectOne(queryWrapper);
        if(signOn == null){
            return CommonResponse.createForError("登陆失败");
        }

        return getAccountByUsername(username);
    }

    @Override
    public CommonResponse<AccountVO> getAccountByPhone(String phone, String password) {
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

//        accountVO.setLanguagePreference(profile.getLanguagePreference());
//        accountVO.setBannerOption(profile.isBannerOption());
//        accountVO.setListOption(profile.isListOption());
//        if(profile.isBannerOption()){
//            accountVO.setFavouriteCategoryId(profile.getFavouriteCategoryId());
//            accountVO.setBannerName(bannerdata.getBannerName());
//        }else {
//            accountVO.setFavouriteCategoryId("");
//            accountVO.setBannerName("");
//        }

        return accountVO;
    }
}
