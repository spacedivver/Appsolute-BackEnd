package com.blaybus.appsolute.user.service;

import com.blaybus.appsolute.character.domain.entity.Characters;
import com.blaybus.appsolute.character.repository.JpaCharacterRepository;
import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.commons.jwt.JWTUtil;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.domain.request.LoginUserRequest;
import com.blaybus.appsolute.user.domain.request.UpdateCharacterRequest;
import com.blaybus.appsolute.user.domain.request.UpdatePasswordRequest;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.XP;
import com.blaybus.appsolute.xp.repository.JpaXPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JpaUserRepository userRepository;
    private final JpaXPRepository xpRepository;
    private final JpaCharacterRepository characterRepository;
    private final JWTUtil jwtUtil;

    public LoginUserResponse login(LoginUserRequest request) {

        Optional<User> userOptional = userRepository.findByUserIdAndChangedPassword(request.userId(), request.password());

        if(userOptional.isPresent()) {
            return LoginUserResponse.builder()
                    .jwtToken(jwtUtil.createJwt(userOptional.get().getId()))
                    .firstLogin(false)
                    .build();
        }

        User user = userRepository.findByUserIdAndInitialPassword(request.userId(), request.password())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("일치하지 않는 비밀번호입니다.", 404, LocalDateTime.now())
                ));

        if(user.getChangedPassword() != null) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("일치하지 않는 비밀번호입니다.", 404, LocalDateTime.now())
            );
        }

        return LoginUserResponse.builder()
                .jwtToken(jwtUtil.createJwt(user.getId()))
                .firstLogin(true)
                .build();
    }

    @Transactional(readOnly = true)
    public ReadUserResponse getUserById(Long id) {

        LocalDateTime now = LocalDateTime.now();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        List<XP> xpList = xpRepository.findByUser_Id(id);

        long lastYearXP = xpList.stream()
                .mapToLong(XP::getPoint)
                .sum();

        long thisYearXP = 0;

        for(XP xp : xpList) {
            if(xp.getYear() == now.getYear()) {
                thisYearXP = xp.getPoint();
            }
        }

        lastYearXP = lastYearXP - thisYearXP;

        return ReadUserResponse.from(user, lastYearXP, thisYearXP);
    }

    public void updatePassword(Long id, UpdatePasswordRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        user.updatePassword(request.password());
    }

    public void updateCharacter(Long id, UpdateCharacterRequest request) {

        Characters newCharacters = characterRepository.findById(request.characterId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 캐릭터가 없습니다.", 404, LocalDateTime.now())
                ));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        user.updateCharacters(newCharacters);
    }
}
