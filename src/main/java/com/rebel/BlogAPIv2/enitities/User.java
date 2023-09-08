package com.rebel.BlogAPIv2.enitities;

import com.rebel.BlogAPIv2.enitities.Email.SecureEmailToken;
import com.rebel.BlogAPIv2.payloads.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String pass;
    private String about;

    private Long otp;

    private String isActive;

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    //getting set of tokens for a verification
    /*@OneToMany(mappedBy = "user")
    private Set<SecureEmailToken> tokens;*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<Authority> authorities = new ArrayList<>();
       this.roles.forEach(role ->
       {
           authorities.add(new Authority(role.getRName()));
       });

        return authorities;
    }

    @Override
    public String getPassword() {
       return this.pass;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        if(this.isActive.equals(null)){return false;}

        if(!this.isActive.equals("active")){return false;}

        if(this.isActive.equals("deactivate")){return false;}


        return true;
    }
}
