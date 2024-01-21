package siliconDream.jaraMe.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Entity

@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long userId;

    @Getter
    @Column(nullable = true)
    private String profileImage;

    @Getter
    @Column(length=20, nullable = false, columnDefinition = "VARCHAR (255)")
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable=true)
    private LocalDate birthDate;

    @Column
    private boolean checkIn;

    private int point=0;

    private int passTicket=0;


    //FK
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MissionPost> missionPost;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comment;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> reaction;


    //FK
    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<JoinUsers> joinUsers;
/*
    @OneToMany(mappedBy="userId")
    @JsonIgnore
    private List<MissionHistory> missionHistory;

    @OneToMany(mappedBy="userId")
    @JsonIgnore
    private List<ToDoList> toDoList;
*/

    //TODO: getter and setter
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPassTicket() {
        return passTicket;
    }

    public void setPassTicket(int passTicket) {
        this.passTicket = passTicket;
    }

    public List<MissionPost> getMissionPost() {
        return missionPost;
    }

    public void setMissionPost(List<MissionPost> missionPost) {
        this.missionPost = missionPost;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Reaction> getReaction() {
        return reaction;
    }

    public void setReaction(List<Reaction> reaction) {
        this.reaction = reaction;
    }

    public List<JoinUsers> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<JoinUsers> joinUsers) {
        this.joinUsers = joinUsers;
    }

    // 프로필 이미지 설정 메서드
    public void setUserProfileImage(String userProfileImage) {
        this.profileImage = userProfileImage;
    }

    // 프로필 이미지 가져오는 메서드
    public String getUserProfileImage() {
        return profileImage;
    }
/*
    public List<MissionHistory> getMissionHistory() {
        return missionHistory;
    }

    public void setMissionHistory(List<MissionHistory> missionHistory) {
        this.missionHistory = missionHistory;
    }

    public List<ToDoList> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<ToDoList> toDoList) {
        this.toDoList = toDoList;
    }
*/

}
