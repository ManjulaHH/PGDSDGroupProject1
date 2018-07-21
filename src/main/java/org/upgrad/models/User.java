package org.upgrad.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="username")
    private String userName;


    @Column
    @JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String role ;

   /* @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonManagedReference
   // private ProfilePhoto profile_Photo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    //private List<Post> posts = new ArrayList<Post>();*/

    public User() {
    }

    public User(String user_Name, String _email,String role) {
        this.userName = user_Name;
        this.email = _email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user_Name) {
        this.userName = user_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + email + '\''+
                '}';
    }
/*public ProfilePhoto getProfilePhoto() {
        return profile_Photo;
    }

    public void setProfilePhoto(ProfilePhoto profile_Photo) {
        this.profile_Photo = profile_Photo;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }*/
}
