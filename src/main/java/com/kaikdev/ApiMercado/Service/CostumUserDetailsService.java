package com.kaikdev.ApiMercado.Service;

import com.kaikdev.ApiMercado.Model.User;
import com.kaikdev.ApiMercado.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Service
public class CostumUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not"));
       return new UserDetails() {
           @Override
           public Collection<? extends GrantedAuthority> getAuthorities() {
               GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
               return List.of(authority);
           }

           @Override
           public String getPassword() {
               return user.getPassword();
           }

           @Override
           public String getUsername() {
               return user.getUsername();
           }

       };
    }
}
