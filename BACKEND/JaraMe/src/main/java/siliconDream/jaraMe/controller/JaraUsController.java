package siliconDream.jaraMe.controller;

<<<<<<< HEAD

=======
import jakarta.validation.Valid;
>>>>>>> e53b2f3cc0426862d36a95848f3a4c6369638dfd
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import siliconDream.jaraMe.domain.JaraUs;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.dto.JaraUsDTO;
import siliconDream.jaraMe.service.JaraUsService;
import siliconDream.jaraMe.service.UserService;

@Controller
@RequiredArgsConstructor
public class JaraUsController {

    @Autowired
    private final JaraUsService jaraUsService;
    private final UserService userService;

    @InitBinder("jaraUsDTO")
    public void jaraUsDTOInitBinder(WebDataBinder webDataBinder) {
    }

    @GetMapping("/new-jaraUs")
    public String newJaraUsForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //수정한 부분
        String userIdString = authentication.getName();
        Long userId = Long.parseLong(userIdString);
        User participant = userService.findUserByUserId(userId);

        model.addAttribute("participant", participant);
        model.addAttribute("jaraUsDTO", new JaraUsDTO());
        return "jaraUs/form";
    }
<<<<<<< HEAD

    @PostMapping("/create-jaraUs")
    public String createNewJaraUs(@ModelAttribute JaraUsDTO jaraUsDTO) {
        JaraUs createdJaraUs = jaraUsService.createNewJaraUs(jaraUsDTO);

        Long createdJaraUsId = createdJaraUs.getJaraUsId();

        return "redirect:/jaraUs/details/" + createdJaraUsId;
    }

     // 검색 기능 추가
    @GetMapping("/search")
    public ResponseEntity<List<JaraUsDTO>> searchJaraUs(@RequestParam String keyword) {
        List<JaraUsDTO> searchResults = jaraUsService.searchJaraUs(keyword);
        return ResponseEntity.ok(searchResults);
    }
 }
=======
}
>>>>>>> e53b2f3cc0426862d36a95848f3a4c6369638dfd
