package com.enclouds.enpoint.user.service;

import com.enclouds.enpoint.cmm.login.service.LoginService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.mapper.UserMapper;
import com.enclouds.enpoint.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 정보 Service
 * Security에 사용 하는 UserDetailService Interface
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;

    /**
     * 사용자 정보 조회(로그인 정보)
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto params = new UserDto();
        params.setId(username);
        UserDto userInfo = (UserDto) userMapper.selectUserInfo(params);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(username.equals("enclouds")){
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN_SUB"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_JACKPOT"));
        }else if(username.equals("hunters")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_JACKPOT"));
        }else if(username.equals("kingslounge")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN_SUB"));
        }else if(username.equals("raise")){
            authorities.add(new SimpleGrantedAuthority("ROLE_JACKPOT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_GAME"));
        }else if(username.equals("admin001")){
            authorities.add(new SimpleGrantedAuthority("ROLE_VIEW"));
        }else if(username.equals("k_online")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN_SUB"));
            authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
        }else if(username.equals("k_game")){
            authorities.add(new SimpleGrantedAuthority("ROLE_GAME"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));
        }

        return new User(userInfo.getId(), userInfo.getPassword(), authorities);
    }

    /**
     * 사용자 정보 조회
     *
     * @param username
     * @return
     * @throws Exception
     */
    public UserDto getUserInfo(String username) throws Exception{
        UserDto params = new UserDto();
        params.setId(username);
        UserDto userInfo = (UserDto) userMapper.selectUserInfo(params);

        return userInfo;
    }

}
