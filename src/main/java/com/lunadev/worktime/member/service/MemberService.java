package com.lunadev.worktime.member.service;

import com.lunadev.worktime.utils.ResultDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    ResultDTO<Object> uploadImage(MultipartFile file);
}
