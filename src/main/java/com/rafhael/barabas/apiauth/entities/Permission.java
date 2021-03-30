package com.rafhael.barabas.apiauth.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Permission implements GrantedAuthority, Serializable {

    public static final long serialVersionUID = 5700692171491365848L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
