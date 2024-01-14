package siliconDream.jaraMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.dao.UserDao;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.UserDto;
import siliconDream.jaraMe.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public boolean create(UserDto userDto) {
        // 비밀번호 확인 로직 추가
        if (!isPasswordConfirmed(userDto)) {
            return false; // 비밀번호 확인이 일치하지 않으면 가입 실패
        }

        // 중복 체크
        String checkTF = userDao.signUpCheck(userDto);
        if (checkTF == null) {
            userDao.insert(userDto);
            return true; // 중복값이 없으면 가입 성공
        } else {
            return false; // 중복값이 있으면 가입 실패
        }
    }

    @Override
    public String emailCheck(String email) {
        return userDao.emailCheck(email);
    }

    @Override
    public boolean isPasswordConfirmed(UserDto userDto) {
        // 비밀번호 확인 로직을 수행하여 결과 반환
        return userDto.getPassword().equals(userDto.getConfirmPassword());

    }

    //출석체크 컬럼 초기화 설정에 사용되는 메서드
    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //출석체크 컬럼 초기화 설정에 사용되는 메서드
    @Override
    public void saveUser(User user){
        userRepository.save(user);
    }
}