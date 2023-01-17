package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepo repo;


    @Override
    public UserDto createUser(UserDto userDto)
    {
        User user = this.userDtoToUser(userDto);

        User CreatedUser =this.repo.save(user);

        return this.userToUserDto(CreatedUser);
    }

    @Override
    public UserDto getUserById(Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                   (() -> new ResourceNotFoundException("User", "Id",uId));

        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = this.repo.findAll();

        List<UserDto> userDto = users.stream().map(user  -> userToUserDto(user)).collect(Collectors.toList());

        return userDto;
    }

    @Override
    public UserDto updatingUser(UserDto userDto, Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                (() -> new ResourceNotFoundException("User", "Id",uId));

        user.setUName(userDto.getName());
        user.setUPass(userDto.getPass());

        User updatedUser = this.repo.save(user);

        return this.userToUserDto(updatedUser);
    }

    @Override
    public void deleteUserById(Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                (() -> new ResourceNotFoundException("User", "Id",uId));

        this.repo.delete(user);

    }

    //converting userDto to user
    public User userDtoToUser(UserDto userDto)
    {

        User user = new User();
        user.setUId(userDto.getUId());
        user.setUName(userDto.getName());
        user.setUEmail(userDto.getEmail());
        user.setUPass(userDto.getPass());
        user.setUAbout(userDto.getAbout());

        return user;
    }

    //converting user to userDto
    public UserDto userToUserDto(User user)
    {
        UserDto dto = new UserDto();
        dto.setUId(user.getUId());
        dto.setName(user.getUName());
        dto.setEmail(user.getUEmail());
        dto.setPass(user.getUPass());
        dto.setAbout(user.getUAbout());

        return dto;
    }
}
