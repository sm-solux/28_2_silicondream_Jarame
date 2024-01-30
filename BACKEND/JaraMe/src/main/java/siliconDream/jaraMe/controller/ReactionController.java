package siliconDream.jaraMe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siliconDream.jaraMe.dto.MissionReactionDTO;
import siliconDream.jaraMe.service.ReactionService;

@RestController
@RequestMapping("/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }


    //리액션 추가
    @PostMapping("/add")
    public ResponseEntity<String> addReaction(@RequestBody MissionReactionDTO missionReactionDTO,@SessionAttribute(name="userId", required=true) Long userId) {
        //예외처리 : 해당 미션인증글에 리액션 추가한 적 없는지 확인
        return reactionService.addReaction(missionReactionDTO, userId);
    }

    //리액션 삭제
    //예외처리 : 눌렀던 리액션타입과 일치하지않는 경우
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReaction(@RequestBody MissionReactionDTO missionReactionDTO,@SessionAttribute(name="userId", required=true) Long userId) {
        //예외처리 : 해당 타입의 리액션 달았던 게 맞는지 확인

        String resultMessage = reactionService.deleteReaction(missionReactionDTO, userId);
        if (resultMessage.equals("리액션이 취소되었습니다.")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }
}
