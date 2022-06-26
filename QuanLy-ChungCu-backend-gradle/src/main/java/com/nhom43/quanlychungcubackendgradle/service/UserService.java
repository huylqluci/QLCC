package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.UserDto;
import com.nhom43.quanlychungcubackendgradle.entity.User;
import com.nhom43.quanlychungcubackendgradle.mapper.UserMapper;
import com.nhom43.quanlychungcubackendgradle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserDto save(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        return userMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserDto findById(Long id) {
        return userMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<UserDto> findByCondition(UserDto userDto, Pageable pageable) {
        User user = userMapper.toEntity(userDto);
        Page<User> entityPage = repository.findAll(pageable);
        List<User> entities = entityPage.getContent();
        return new PageImpl<>(userMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public UserDto update(UserDto userDto, Long id) {
        UserDto data = findById(id);
        BeanUtils.copyProperties(userDto, data);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //


    public List<UserDto> findAllByRole(String role) {
        List<User> list = repository.findAllByRole(role);
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại tài khoản nào");
        return userMapper.toDto(list);
    }

}