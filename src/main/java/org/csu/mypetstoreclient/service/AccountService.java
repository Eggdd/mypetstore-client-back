package org.csu.mypetstoreclient.service;


import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.vo.AccountVO;

public interface AccountService {

    CommonResponse<AccountVO> getAccountByPhoneAndPassword(String phone, String password);

    CommonResponse<AccountVO>  getAccountByUsername(String username);

    CommonResponse<AccountVO>  getAccountByUsernameAndPassword(String username , String password);

    CommonResponse<AccountVO> insertAccount(AccountVO accountVO);


}
