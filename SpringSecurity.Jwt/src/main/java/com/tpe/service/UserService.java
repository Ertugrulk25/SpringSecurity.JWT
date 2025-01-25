package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.RoleType;
import com.tpe.domain.User;
import com.tpe.dto.UserDTO;
import com.tpe.exception.ConflictException;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public void saveUser(UserDTO userDTO) {
        //username tabloda var mı
        boolean existsUser=userRepository.existsByUserName(userDTO.getUserName());
        if (existsUser){
            throw new ConflictException("User already exists!!!");
        }

        User user=new User();
        user.setFirstName(userDTO.getFirstName());
        user.setUserName(userDTO.getUserName());
        //userın passwordünü DB ye hashleyerek kaydedelim
        String encodedPassword=passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

        //userın rolünün verilmesi
        //NOT:genellikle default olarak usera en düşük yetkideki rol verilir
        //gerekirse daha sonra admin tarafından değiştirilir
        Set<Role> roleSet=new HashSet<>();
        Role role=roleService.getRoleByType(RoleType.ROLE_STUDENT);
        roleSet.add(role);

        user.setRoles(roleSet);

        userRepository.save(user);

    }
}