package com.matjzing.mapper;

import com.matjzing.dto.login.*;
import com.matjzing.dto.security.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLoginMapper {

    User findByUserId(String username);
    AdminLoginSelectResponse selectLogin(AdminLoginRequest req);
    AdminLoginSelectResponse selectManager(AdminLoginRequest req);
    Long updateLoginInfo(AdminLoginRequest req);
//    AdminFindPasswordResponse findPassword(AdminFindPasswordRequest req);
//    int updatePassword(AdminUpdatePasswordRequest req);

}
