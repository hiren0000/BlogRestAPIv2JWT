package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo repo;


    @Override
    public UserDto createUser(UserDto userDto)
    {
        // we can directly use the model mapper to convert userdto to user or Vice versa
        User user = this.modelMapper.map(userDto, User.class);

        User CreatedUser =this.repo.save(user);

        return this.modelMapper.map(CreatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                   (() -> new ResourceNotFoundException("User", "Id",uId));

        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser(Integer pageNumber, Integer pageSize)
    {
        //trying to obtain pagination and it will show only 5 users details per page

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = this.repo.findAll(pageable);
        List<User> users = page.getContent();

        List<UserDto> userDto = users.stream().map(user  -> userToUserDto(user)).collect(Collectors.toList());

        return userDto;
    }

    @Override
    public UserDto updatingUser(UserDto userDto, Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                (() -> new ResourceNotFoundException("User", "Id",uId));

        user.setName(userDto.getName());
        user.setPass(userDto.getPass());

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
        User user = this.modelMapper.map(userDto, User.class);

       /*
        user.setUId(userDto.getUId());
        user.setUName(userDto.getName());
        user.setUEmail(userDto.getEmail());
        user.setUPass(userDto.getPass());
        user.setUAbout(userDto.getAbout());
*/
        return user;
    }

    //converting user to userDto
    public UserDto userToUserDto(User user)
    {
        UserDto dto = this.modelMapper.map(user, UserDto.class);

        return dto;
    }
}
