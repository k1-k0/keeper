package com.electricity.keeper.controller;


import com.electricity.keeper.repository.PhotoRepository;
import com.electricity.keeper.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoService photoService;

    @GetMapping(path = "/photo/{id}")
    public ResponseEntity<?> getImage(@PathVariable long id) {
        try {
            var fileStream = photoService.getImage(id);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<byte[]>(fileStream.readAllBytes(), httpHeaders, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(path = "/history/{id}/photo")
    public ResponseEntity<?> saveImage(@PathVariable long id, @RequestParam MultipartFile file) {
        try {
            photoService.saveImage(file.getInputStream(), id);
            return ResponseEntity.ok("Image has been saved");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/photo/{id}")
    public ResponseEntity<?> changeImage(@PathVariable long id, @RequestParam MultipartFile file) {
        try {
            photoService.changeImage(file.getInputStream(), id);
            return ResponseEntity.ok("Image has been changed");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/photo/{id}")
    public ResponseEntity<?> changeImage(@PathVariable long id) {
        try {
            photoService.removeImage(id);
            return ResponseEntity.ok("Image has been changed");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
