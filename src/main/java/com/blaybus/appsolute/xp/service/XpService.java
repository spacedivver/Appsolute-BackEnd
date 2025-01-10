package com.blaybus.appsolute.xp.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import com.blaybus.appsolute.xp.domain.request.CreateXpRequest;
import com.blaybus.appsolute.xp.domain.request.DeleteXpRequest;
import com.blaybus.appsolute.xp.domain.request.UpdateXpRequest;
import com.blaybus.appsolute.xp.repository.JpaXpDetailRepository;
import com.blaybus.appsolute.xp.repository.JpaXpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class XpService {

    private final JpaXpRepository xpRepository;
    private final JpaXpDetailRepository xpDetailRepository;
    private final JpaUserRepository userRepository;

    public void createXp(CreateXpRequest request) {

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        long xpPoint = Long.parseLong(request.xp());

        Xp xp = Xp.builder()
                .user(user)
                .year(request.year())
                .build();

        xpRepository.save(xp);

        XpDetail xpDetail = XpDetail.builder()
                .point(xpPoint)
                .createdAt(LocalDateTime.of(request.year(), 1, 1, 0, 0))
                .xp(xp)
                .build();

        xpDetailRepository.save(xpDetail);
    }

    public void updateYearXp(UpdateXpRequest request) {

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        long xpPoint = Long.parseLong(request.xp());

        Xp xp = xpRepository.findByUserAndYear(user, request.year())
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 경험치가 없습니다.", 404, LocalDateTime.now())
                        ));

        xpDetailRepository.deleteByXp(xp);

        XpDetail xpDetail = XpDetail.builder()
                .point(xpPoint)
                .createdAt(LocalDateTime.of(request.year(), 1, 1, 0, 0))
                .xp(xp)
                .build();

        xpDetailRepository.save(xpDetail);
    }

    public void deleteYearXp(DeleteXpRequest request) {

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        Xp xp = xpRepository.findByUserAndYear(user, request.year())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 경험치가 없습니다.", 404, LocalDateTime.now())
                ));

        xpDetailRepository.deleteByXp(xp);
    }
}
