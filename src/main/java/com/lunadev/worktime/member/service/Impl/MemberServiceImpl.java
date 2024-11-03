package com.lunadev.worktime.member.service.Impl;

import com.lunadev.worktime.auth.service.AuthService;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.member.repository.MemberRepository;
import com.lunadev.worktime.member.service.MemberService;
import com.lunadev.worktime.utils.AuthUserInfo;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthUserInfo authUserInfo;

    @Value("${file.upload-dir}")
    private String uploadPath;

    @Override
    public ResultDTO<Object> uploadImage(MultipartFile file) {
        Member member = authUserInfo.getAuthenticatedMember();

        // 고유한 파일 이름 생성
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID() + extension;

        // 파일 경로 설정 및 저장
        Path filePath = Paths.get(uploadPath + File.separator + uniqueFilename);
        try {
            Files.createDirectories(filePath.getParent());  // 디렉토리가 없을 경우 생성
            file.transferTo(filePath.toFile());

            // 멤버 엔티티에 이미지 경로 저장
            member.setImagePath(uniqueFilename);
            memberRepository.save(member);

            return ResultDTO.of(true, "IMAGE_UPLOAD_SUCCESS", "이미지 업로드 성공", uniqueFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultDTO.of(false, "IMAGE_UPLOAD_FAIL", "이미지 업로드 실패", null);
        }
    }
}

