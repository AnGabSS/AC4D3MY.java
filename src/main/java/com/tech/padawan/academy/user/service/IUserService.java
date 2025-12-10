package com.tech.padawan.academy.user.service;

import com.tech.padawan.academy.user.dto.*;
import com.tech.padawan.academy.user.model.User;
import org.springframework.data.domain.Page;

public interface IUserService {
    Page<UserSearchedDTO> listAll(Integer page, Integer size, String orderBy, String direction);
    UserSearchedDTO getById(Long id);
    User getByEmail(String email);
    User create(CreateUserDTO user);
    RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto);
    User update(Long id, UpdateUserDTO user);
    String delete(Long id);
    User updateUserCompleted(User user);
    User getUserEntityById(Long id);
}
