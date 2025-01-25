package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,length = 50,unique = true)
    private String userName;




    @Column(nullable = false,length = 255)
    //password DB ye kaydedilmeden önce şifrelenecek(hashlenecek)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //userın yetki kontrolü için rollerini alması gerekli
    //bu yüzden user getirirken rollerini de DB den getir
    private Set<Role> roles=new HashSet<>();






}