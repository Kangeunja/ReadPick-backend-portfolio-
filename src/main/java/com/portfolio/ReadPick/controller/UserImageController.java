package com.portfolio.ReadPick.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.UserImageVo;
import com.portfolio.ReadPick.vo.UserSessionDTO;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserImageController {

    @Autowired
    HttpSession session;

    @Autowired
    UserMapper userMapper;

    // String fileUploadPath = "/home/ubuntu/ReadPickImages/";
    String fileUploadPath = "C:/Users/hoyeong/Desktop/ReadPickImages/";

    // user 프로필 이미지 추가
    @PostMapping(value = "/api/userImageInsert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> userImageInsert(@RequestPart("file") MultipartFile file) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null)
            return ResponseEntity.ok("login:fail");

        if (file.isEmpty())
            return ResponseEntity.ok("fail");

        try {
            // UUID 기반 파일명 생성 (충돌 방지)
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String newFileName = UUID.randomUUID() + extension;

            // 파일 저장
            Path targetPath = Paths.get(fileUploadPath).resolve(newFileName);
            file.transferTo(targetPath);

            // DB 저장
            UserImageVo userImage = new UserImageVo();
            userImage.setUserIdx(user.getUserIdx());
            userImage.setFileName(newFileName);
            userMapper.insertUserImage(userImage);

            // 세션 업데이트
            user.setFileName("http://localhost:8080/ReadPickImages/" + newFileName);
            session.setAttribute("user", user);

            return ResponseEntity.ok(user.getFileName());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

    // ========================== 프로필 이미지 삭제 ==========================
    @PostMapping("/api/userImageDelete")
    public ResponseEntity<String> userImageDelete() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null)
            return ResponseEntity.ok("login:fail");

        int userIdx = user.getUserIdx();
        String deleteFileName = userMapper.selectUserImageFromUserIdx(userIdx);
        if (deleteFileName != null) {
            Path deletePath = Paths.get(fileUploadPath).resolve(deleteFileName).normalize();
            if (!deletePath.startsWith(fileUploadPath)) {
                return ResponseEntity.ok("fail"); // 디렉터리 트래버설 방지
            }

            File deleteFile = deletePath.toFile();
            if (deleteFile.exists())
                deleteFile.delete();
        }

        userMapper.deleteUserImage(userIdx);

        user.setFileName("default");
        session.setAttribute("user", user);

        return ResponseEntity.ok("success");
    }

    // ========================== 프로필 이미지 업데이트 ==========================
    @PostMapping(value = "/api/userImageUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> userImageUpdate(@RequestPart("file") MultipartFile file) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null)
            return ResponseEntity.ok("login:fail");

        int userIdx = user.getUserIdx();
        if (file.isEmpty())
            return ResponseEntity.ok("fail");

        try {
            // 기존 파일 삭제
            String oldFileName = userMapper.selectUserImageFromUserIdx(userIdx);
            if (oldFileName != null) {
                Path oldPath = Paths.get(fileUploadPath).resolve(oldFileName).normalize();
                if (oldPath.startsWith(fileUploadPath) && oldPath.toFile().exists()) {
                    oldPath.toFile().delete();
                }
            }

            // 새 파일 업로드
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String newFileName = UUID.randomUUID() + extension;

            Path targetPath = Paths.get(fileUploadPath).resolve(newFileName);
            file.transferTo(targetPath);

            UserImageVo userImage = new UserImageVo();
            userImage.setUserIdx(userIdx);
            userImage.setFileName(newFileName);
            userMapper.updateUserImage(userImage);

            // 세션 업데이트
            user.setFileName("http://localhost:8080/ReadPickImages/" + newFileName);
            session.setAttribute("user", user);

            return ResponseEntity.ok(user.getFileName());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

    // 프로필 이미지 가져오기
    @GetMapping("/ReadPickImages/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {

        System.out.println("Working dir: " + System.getProperty("user.dir"));
        System.out.println("fileUploadPath: " + fileUploadPath);
        System.out.println("Full image path: " + Paths.get(fileUploadPath).resolve(imageName).toAbsolutePath());
        System.out.println("Exists? " + Files.exists(Paths.get(fileUploadPath).resolve(imageName)));
        // 요청된 이미지 파일 경로 설정
        Path imagePath = Paths.get(fileUploadPath).resolve(imageName);

        // 이미지가 존재하지 않으면 404 반환
        if (!imagePath.toFile().exists()) {
            System.out.println(imagePath);
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일 반환
        Resource resource = new FileSystemResource(imagePath);

        // 파일 확장자 추출
        String extension = Optional.ofNullable(imageName)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(imageName.lastIndexOf(".") + 1).toLowerCase())
                .orElse("");

        // MIME 타입 결정
        MediaType mediaType;
        switch (extension) {
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            case "bmp":
                mediaType = MediaType.parseMediaType("image/bmp");
                break;
            case "webp":
                mediaType = MediaType.parseMediaType("image/webp");
                break;
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM; // 알 수 없는 타입은 일반 바이너리
                break;
        }

        return ResponseEntity.ok()
                .contentType(mediaType) // 이미지의 MIME 타입 설정
                .body(resource); // 이미지 리소스를 바디로 반환
    }

}
