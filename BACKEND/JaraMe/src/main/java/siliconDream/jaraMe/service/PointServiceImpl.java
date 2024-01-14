package siliconDream.jaraMe.service;

import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.repository.PointRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public boolean checkIn(Long userId, LocalDateTime dateTime) {
        boolean checkInResult = false;
        Optional<User> user = pointRepository.findById(userId);
        if (user.isPresent()) {
            boolean updateResult = pointRepository.updateCheckIn(userId);
            checkInResult = updateResult;
            return checkInResult;

        } else {
            return checkInResult;
        }

    }

    public boolean passTicket(Long userId) {
        //dao를 통해 userId로 해당 레코드 가져온 후, point컬럼 값 추출하기
        boolean passTicketResult = false;
        Optional<User> user = pointRepository.findById(userId);
        Long point =user.get().getPoint();

        if (point >= 60) {
            //dao 통해 passTicket은 +1, point는 -60
            boolean updateResult = pointRepository.updatePassTicket(userId);
            passTicketResult = updateResult;
            return passTicketResult;
        } else {
            return passTicketResult;
        }
    }

}
