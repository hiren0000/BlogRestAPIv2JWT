package com.rebel.BlogAPIv2.enitities.Email;

import com.rebel.BlogAPIv2.enitities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;

/*@Entity
@Table(name = "verification_token")
@Getter
@Setter*/

public class SecureEmailToken
{

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

   /* @CreationTimestamp
    @Column(updatable = false)
    private Timestamp timeStamp;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName ="id")
    private User user;

    @Transient
    private boolean isExpired;

    public boolean isExpired()
    {
        return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
    }

*/
}
