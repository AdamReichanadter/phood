package com.doughepi.services;

import com.doughepi.models.UserModel;
import com.doughepi.repositories.RoleRepository;
import com.doughepi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by dough on 2017-02-06.
 */
@Service
public class UserServiceImpl implements UserService
{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RoleRepository roleRepository,
            UserRepository userRepository)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserModel findByUsername(String username)
    {
        return userRepository.findByUserUsername(username);
    }

    @Override
    public UserModel findByEmail(String email)
    {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public UserModel save(UserModel userModel)
    {
        userModel.setUserPassword(bCryptPasswordEncoder.encode(userModel.getUserPassword()));
        userModel.setRoleSet(new HashSet<>());
        return userRepository.save(userModel);
    }
}
