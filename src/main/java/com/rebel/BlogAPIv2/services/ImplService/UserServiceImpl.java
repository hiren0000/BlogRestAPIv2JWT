package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.config.AppiConsta;
import com.rebel.BlogAPIv2.enitities.Email.AccountVerificationEmailCon;
import com.rebel.BlogAPIv2.enitities.Email.EmailDetails;
import com.rebel.BlogAPIv2.enitities.Email.SecureEmailToken;
import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.enitities.UserRole;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.GenerateOtp;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.repo.SecureEmailTokenRepo;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.repo.UserRoleRepo;
import com.rebel.BlogAPIv2.services.EmailService;
import com.rebel.BlogAPIv2.services.SecureEmailTokenService;
import com.rebel.BlogAPIv2.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo repo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRoleRepo userRoleRepo;

    //@Autowired
    //private SecureEmailTokenService secureEmailTokenService;

   // @Autowired
    //private SecureEmailTokenRepo secureEmailTokenRepo;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    public UserDto createUser(UserDto userDto, Set<UserRole> userRoles)
    {
        // we can directly use the model mapper to convert userdto to user or Vice versa
        User user = this.modelMapper.map(userDto, User.class);

      /*  if(this.repo.findByEmail(user.getEmail()).isPresent())
        {
            try {
                throw new AccountException("User is already exists with this username !! ");
            } catch (AccountException e) {
                throw new RuntimeException(e);

            }
        }*/

        //Generating random OTP
        Long otp = GenerateOtp.otpGenerate();

        //Setting email details and
        String sub = "Blog-APP Registration";
        String body = "Hi,"+" "+user.getName()+","+"\n"+"\n Welcome to Blog-App."+ ", \n Please use below code to verify your account !! "
                +"\n One Time Password: "+otp+ "\n"+" \n" + "Regards, "+ " \n Blog Post  ";

        System.out.println(otp);

        EmailDetails details = new EmailDetails();
        details.setSubject(sub);
        details.setMsgBody(body);
        details.setRecipientEmail(user.getEmail());

        //sending an email to user who just wanted to register with us
        String EmailStatus = this.emailService.sendSimpleMail(details);
        System.out.printf(EmailStatus);

        //Saving user data into DB with Encoded password
        String encodePass =  encoder.encode(user.getPassword());
        System.out.println("Password encoded !!"+ encodePass );


        user.setPass(encodePass);
        user.setOtp(otp);

        //setting user as deactivated. After OTP verification it will be activated..
        user.setIsActive("deactivate");


        //assigning the user role
        for(UserRole ur: userRoles)
        {
            userRoleRepo.save(ur);
        }

        user.getRoles().addAll(userRoles);


        System.out.println(user.getOtp());
        User CreatedUser =this.repo.save(user);

       // sendRegistrationConfirmationEmail(CreatedUser);

        return this.modelMapper.map(CreatedUser, UserDto.class);
    }

    //------------------------------------------------------------------------------------------------------
    @Override
    public String getOtp(Integer uId, Long otpVeri)
    {
        User user = this.repo.findById(uId).
                orElseThrow(() -> new ResourceNotFoundException("User", "Id",uId));

        if(user.getOtp().equals(otpVeri))
        {
            user.setIsActive("active");

            User enableUser = this.repo.save(user);

            return "user status has been updated...!!";

        }

        return "user status has not been updated...!!";
    }

    //sending Registration Email with token---------------------======================================================================
    @Override
    public void sendRegistrationConfirmationEmail(User user)
    {
      /*  SecureEmailToken secureEmailToken = secureEmailTokenService.createSecureToken();
        secureEmailToken.setUser(user);
        secureEmailTokenRepo.save(secureEmailToken);
        AccountVerificationEmailCon accountVerificationEmailCon = new AccountVerificationEmailCon();
        accountVerificationEmailCon.init(user);
        accountVerificationEmailCon.setToken(secureEmailToken.getToken());
        accountVerificationEmailCon.buildVerificationUrl(baseURL, secureEmailToken.getToken());

        try {
            emailServiceImpl.sendMailWithAttachment(accountVerificationEmailCon);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
*/

    }

    //verifying user registration with the help of secure token
    @Override
    public boolean verifyUser(String token)
    {   /*SecureEmailToken secureEmailToken = secureEmailTokenService.findByToken(token);
        if(secureEmailToken == null || !token.equals(secureEmailToken.getToken()) || secureEmailToken.isExpired())
        {
            try {
                throw new Exception("Token is not valid !!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //getting user from the token
        User user = repo.getById(secureEmailToken.getUser().getId());

         if(user == null)
         {
             return false;
         }
         user.setIsActive("active");
         repo.save(user);

         secureEmailTokenService.removeToken(secureEmailToken);*/

        return true;
    }

    //Getting user from Otp and then change the status of the User profile
    @Override
    public UserDto getUserByOtp(Long otp)
    {
        User user = this.repo.findByOtp(otp).
                orElseThrow(() -> new ResourceNotFoundException("User", "otp",otp));

        //During Registration - will change the account status
        if(user.getIsActive().equals("deactivate"))
        {
            user.setIsActive("active");
            user.setOtp(null);
            User enableUser = this.repo.save(user);
            return this.modelMapper.map(enableUser, UserDto.class);
        }

        //During forget password attempt
        else if (user.getIsActive().equals("active"))
        {
            user.setOtp(null);
            User forgotPassUser = this.repo.save(user);
            return this.modelMapper.map(forgotPassUser, UserDto.class);
        }

         return this.modelMapper.map(user, UserDto.class);
    }

    //getting user by email this method is helpful for forget password as well
    @Override
    public UserDto getUserByEmail(String email)
    {
        User user = this.repo.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("User", "otp", 0));

        if(user != null)
        {
            //
            //Generating random OTP
            Long otp = GenerateOtp.otpGenerate();

            //Setting email details and
            String sub = "Reset Password";
            String body = "Hi,"+" "+user.getName()+","+"\n"+"\n Here is the OTP to let you reset your password."+ ", \n Please use below code to reset your password !! "
                    +"\n One Time Password: "+otp+ "\n"+" \n" + "Regards, "+ " \n Development Team. ";

            System.out.println(otp);

            EmailDetails details = new EmailDetails();
            details.setSubject(sub);
            details.setMsgBody(body);
            details.setRecipientEmail(user.getEmail());

            //sending an email to user who just wanted to register with us
            String EmailStatus = this.emailService.sendSimpleMail(details);
            System.out.printf(EmailStatus);

            //updating otp in the db
            user.setOtp(otp);
            User updatedUserOtp = this.repo.save(user);


            return this.modelMapper.map(updatedUserOtp, UserDto.class);
        }
        else
        {  return null;}
    }

    //updating password for forget pass function--------------------------------------------------------------
    @Override
    public UserDto updatePass(String password, Integer id)
    {
        User user = this.repo.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("User", "Id",id));

        if(user != null)
        {
            //Encoding new password && setting new pass in db

            String encodePass = this.encoder.encode(password);
            System.out.println("near the encode pass");
            System.out.println(encodePass);
            user.setPass(encodePass);

            //updating user in db
            User updatedUser = this.repo.save(user);
            return this.modelMapper.map(updatedUser, UserDto.class);
        }

        return null;
    }

    //Fetching User by id----------------------------------------------------------------------------------------------------
    @Override
    public UserDto getUserById(Integer uId)
    {

        User user = this.repo.findById(uId).orElseThrow
                   (() -> new ResourceNotFoundException("User", "Id",uId));

        return this.userToUserDto(user);
    }

    //Fetching list of users
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

    //Updating user details-----
    @Override
    public UserDto updatingUser(UserDto userDto, Integer uId) {

        User user = this.repo.findById(uId).orElseThrow
                (() -> new ResourceNotFoundException("User", "Id",uId));

        user.setName(userDto.getName());
        user.setPass(userDto.getPass());

        User updatedUser = this.repo.save(user);

        return this.userToUserDto(updatedUser);
    }

    //Deleting user by id
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
