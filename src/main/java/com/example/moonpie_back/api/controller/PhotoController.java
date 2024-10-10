package com.example.moonpie_back.api.controller;


import com.example.moonpie_back.core.entity.Photo;
import com.example.moonpie_back.core.service.PhotoService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") String itemName,
                           @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        String id = photoService.addPhoto(itemName, image);
        return "redirect:/photos/" + id;
    }

    @GetMapping("/photos")
    public Photo addPhoto(@PathParam("id") String photoId)
            throws IOException {
        return photoService.getPhoto(photoId);
    }


}
