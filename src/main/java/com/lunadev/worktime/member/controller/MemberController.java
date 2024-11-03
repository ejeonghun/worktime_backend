package com.lunadev.worktime.member.controller;

import com.lunadev.worktime.member.service.MemberService;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("uploadImage")
    public ResultDTO<Object> uploadMemberImage(@RequestParam("file") MultipartFile file) {
        return memberService.uploadImage(file);
    }
}
