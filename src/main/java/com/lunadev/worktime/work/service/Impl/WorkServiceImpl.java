package com.lunadev.worktime.work.service.Impl;

import com.lunadev.worktime.Enum.ApiResponseCode;
import com.lunadev.worktime.Enum.WorkType;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.member.repository.MemberRepository;
import com.lunadev.worktime.utils.AuthUserInfo;
import com.lunadev.worktime.utils.ResultDTO;
import com.lunadev.worktime.work.dto.*;
import com.lunadev.worktime.work.entity.Work;
import com.lunadev.worktime.work.repository.WorkMapper;
import com.lunadev.worktime.work.repository.WorkRepository;
import com.lunadev.worktime.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final AuthUserInfo authUserInfo;
    private final MemberRepository memberRepository;
    private final WorkMapper workMapper;

    /**
     * Haversine Formula로 거리 계산 메소드
     * */
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS = 6371; // 지구 반지름 (km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // 거리를 미터 단위로 변환

        return distance;
    }

    @Override
    public ResultDTO<Object> checkIn(LocationDto locationDto) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 인증된 사용자의 회사 정보 가져오기
        Member member = authUserInfo.getAuthenticatedMember();
        double companyLat = company.getLatitude();
        double companyLng = company.getLongitude();

        double distance = calculateDistance(locationDto.getLatitude(), locationDto.getLongitude(), companyLat, companyLng);

        if (distance > 200) {
            // 200m 범위 밖인 경우
            return ResultDTO.of(false, "CHECK_IN_FAIL", "200m 범위 밖입니다.", null);
        }

        // 같은 날에 이미 출근 기록이 있는지 확인
        Work existingWork = workRepository.findByMemberAndDate(member, LocalDate.now());
        if (existingWork != null) {
            // 당일 출근 기록이 있는 경우 추가 근로로 처리
            // 당일 출근 기록이 있는 경우 추가 근로로 처리
            existingWork.setWorkType(WorkType.OVERTIME); // 근무 유형을 추가 근로로 변경
            existingWork.setLatitude(locationDto.getLatitude()); // 위치 업데이트
            existingWork.setLongitude(locationDto.getLongitude());
            existingWork.setEndTime(null); // 퇴근 시간 초기화
            existingWork.setWorkTime(null); // 근무시간 초기화

            try {
                workRepository.save(existingWork);
                return ResultDTO.of(true, "OVERTIME_SUCCESS", "추가 근로 처리 성공", existingWork.getStartTime());
            } catch (Exception e) {
                return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
            }
        }

        // 출근 기록이 없는 경우 체크인 처리
        try {
            Work work = Work.builder()
                    .workTime(null) // 근무시간 (체크아웃 때 계산)
                    .date(LocalDate.now())
                    .startTime(LocalDateTime.now())
                    .workType(WorkType.CHECK_IN)
                    .latitude(locationDto.getLatitude())
                    .longitude(locationDto.getLongitude())
                    .member(member)
                    .company(company)
                    .build();

            workRepository.save(work);
            return ResultDTO.of(true, "CHECK_IN_SUCCESS", "체크인 성공", work.getStartTime());
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }


    @Override
    public ResultDTO<Object> checkOut(LocationDto locationDto) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 인증된 사용자의 회사 정보 가져오기
        Member member = authUserInfo.getAuthenticatedMember();
        double companyLat = company.getLatitude();
        double companyLng = company.getLongitude();

        double distance = calculateDistance(locationDto.getLatitude(), locationDto.getLongitude(), companyLat, companyLng);

        if (distance > 200) {
            // 200m 범위 밖인 경우
            return ResultDTO.of(false, "CHECK_OUT_FAIL", "200m 범위 밖입니다.", null);
        }

        // 당일 출근 기록을 가져오기
        Work existingWork = workRepository.findByMemberAndDate(member, LocalDate.now());
        if (existingWork == null || existingWork.getStartTime() == null) {
            // 출근 기록이 없거나 출근 시간이 없는 경우 체크아웃 불가
            return ResultDTO.of(false, "CHECK_OUT_FAIL", "출근 기록이 없습니다.", null);
        }

        // 현재 시간을 endTime으로 설정
        LocalDateTime endTime = LocalDateTime.now();

        // 근무 시간 계산 (분 단위로 계산 후 저장)
        long workMinutes = Duration.between(existingWork.getStartTime(), endTime).toMinutes();

        // Work 엔티티 업데이트
        existingWork.setEndTime(endTime);
        existingWork.setWorkTime(workMinutes); // 근무 시간(분 단위)
        existingWork.setWorkType(WorkType.CHECK_OUT);
        existingWork.setLatitude(locationDto.getLatitude());
        existingWork.setLongitude(locationDto.getLongitude());

        try {
            workRepository.save(existingWork);
            return ResultDTO.of(true, "CHECK_OUT_SUCCESS", "체크아웃 성공", endTime);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }


    @Override
    public ResultDTO<Object> workList(String date) {
        try {
            Company company = authUserInfo.getAuthenticatedCompany();
            Member member = authUserInfo.getAuthenticatedMember();
            Long companyId = company.getCompanyId();
            String companyName = company.getCompanyName();
            List<WorkListDto> workList = workMapper.getWorkList(date, companyId);

            // 현재 로그인한 사용자의 출근 정보 찾기
            WorkListDto userWorkInfo = workList.stream()
                    .filter(work -> work.getMemberId().equals(member.getMemberId()))
                    .findFirst()
                    .orElse(null);

            // 사용자 정보 DTO 생성
            WorkMemberDto userInfo = new WorkMemberDto(
                    member.getMemberId(),
                    member.getName(),
                    member.getImagePath(),
                    userWorkInfo != null ? WorkType.getValue(userWorkInfo.getWorkType().intValue()) : WorkType.NOT_CHECK_IN,
                    member.getPosition() != null ? member.getPosition() : "직급 없음",
                    userWorkInfo != null ? userWorkInfo.getStartTime() : null
            );

            // 부서별로 그룹화
            Map<String, List<WorkMemberDto>> deptGroupMap = workList.stream()
                    .collect(Collectors.groupingBy(
                            dto -> dto.getDeptName() == null ? "기타" : dto.getDeptName(),
                            Collectors.mapping(
                                    work -> new WorkMemberDto(
                                            work.getMemberId(),
                                            work.getMemberName(),
                                            work.getImagePath(),
                                            WorkType.getValue(work.getWorkType().intValue()),
                                            work.getPosition() == null ? "직급 없음" : work.getPosition(),
                                            work.getStartTime()
                                    ),
                                    Collectors.toList()
                            )
                    ));

            // 부서 리스트 생성 및 정렬
            List<WorkListDeptDto> deptList = deptGroupMap.entrySet().stream()
                    .map(entry -> new WorkListDeptDto(
                            entry.getKey(),
                            entry.getValue()
                    ))
                    .sorted((a, b) -> {
                        if (a.getDeptName().equals("기타")) return 1;
                        if (b.getDeptName().equals("기타")) return -1;
                        return a.getDeptName().compareTo(b.getDeptName());
                    })
                    .collect(Collectors.toList());

            // 최종 응답 데이터 구조 생성
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("userInfo", userInfo);
            resultData.put("deptList", deptList);
            resultData.put("companyInfo", companyName);

            return ResultDTO.of(
                    true,
                    "WORK_LIST_SUCCESS",
                    "출근부 조회 성공",
                    resultData
            );

        } catch (Exception e) {
            return ResultDTO.of(
                    false,
                    ApiResponseCode.FAILED.getCode(),
                    "오류 발생: " + e.getMessage(),
                    null
            );
        }
    }
}
