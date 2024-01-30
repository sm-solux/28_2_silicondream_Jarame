package siliconDream.jaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.LoginResponse;
import siliconDream.jaraMe.dto.UserDto;
import siliconDream.jaraMe.service.UserService;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입을 위한 엔드포인트
    @PostMapping("signup")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        // 비밀번호 확인 유효성 검사
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("비밀번호 확인이 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있는 경우, 잘못된 요청 응답을 반환
            return ResponseEntity.badRequest().body("유효성 검사 오류");
        } else {
            boolean isSuccess = userService.create(userDto);

            if (!isSuccess) {
                // 사용자 생성에 문제가 있는 경우, 잘못된 요청 응답을 반환
                return ResponseEntity.badRequest().body("사용자 생성에 실패했습니다.");
            } else {
                // 사용자 생성이 성공한 경우, 성공 응답을 반환
                return ResponseEntity.ok("사용자가 성공적으로 생성되었습니다.");
            }
        }
    }

    // 중복 이메일 확인을 위한 엔드포인트
    @GetMapping("emailCheck")
    public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
        // 중복된 이메일 값이 있는지 확인
        String check = userService.emailCheck(email);
        return ResponseEntity.ok(check != null);
    }


    // 로그인 처리
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        LoginResponse response = userService.login(userDto.getEmail(), userDto.getPassword());

        if (!response.isSuccess()) {
            // 로그인 실패 시 오류 응답 반환
            return ResponseEntity.badRequest().body(response);
        } else {
            // 로그인 성공 시 세션에 사용자 정보 저장
            HttpSession session = request.getSession(); // 세션 가져오기 또는 생성하기
            session.setAttribute("user", response.getUser()); // 세션에 사용자 정보 저장

            log.info("session.getId:{}", session.getId());


            // 성공 응답 반환
            return ResponseEntity.ok(response);
        }
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션 가져오기, 새 세션은 생성하지 않음

        if (session != null) {
            session.invalidate(); // 세션 무효화
            return ResponseEntity.ok().body("Logged out successfully");
        } else {
            // 활성 세션이 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active session found");
        }
    }

    //프로필 이미지 업데이트
    @PostMapping("/updateProfileImage")
    public ResponseEntity<?> updateProfileImage(@RequestParam("userId") Long userId,
                                                @RequestParam("image") MultipartFile image) {
        try {
            String imageUrl = userService.updateProfileImage(userId, image);
            return ResponseEntity.ok().body("Profile image updated successfully. New Image URL: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating profile image");
        }
    }

    //회원탈퇴
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in deleting user");
        }
    }

    //닉네임 변경
    @PostMapping("/changeNickname")
    public ResponseEntity<?> changeNickname(@RequestBody UserDto userDto) {
        try {
            boolean success = userService.changeNickname(userDto.getUserid(), userDto.getNickname(), userDto.getPassword());
            if (success) {
                return ResponseEntity.ok().body("Nickname changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Nickname is already in use");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing nickname");
        }
    }

    //프로필 정보 반환 (패스권, 포인트, 자라어스 개수)
    @GetMapping("/profile/info/{userId}")
    public ResponseEntity<UserProfileInfoDTO> getUserProfileInfo(@PathVariable Long userId) {
        UserProfileInfoDTO userInfo = userService.getUserProfileInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

}
