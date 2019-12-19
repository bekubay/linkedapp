package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.Advert;
import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.IAdvertService;
import edu.mum.linkedapp.service.impl.UserService;
import edu.mum.linkedapp.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdvertController {

    private static final String ROOT = "upload-dir";

    private final StorageService storageService;
    @Autowired
    IAdvertService advertService;
    @Autowired
    public AdvertController (StorageService storageService){
        this.storageService = storageService;
    }
    @Autowired
    UserService userService;
    @GetMapping("/advert/adverts")
    public String advertsPage(Model model){
        model.addAttribute("adverts",advertService.findAllOrdered());
        return "adverts_admin";
    }

    @PostMapping(value = "/pushAdvertise")
    public String pushAdverise(@RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam(value = "url",required = false) String url) {
//        if (!file.isEmpty()) {
////            System.out.println("error");
//            storageService.store(file);
//        }
//        System.out.println(url);
//        List<String> list = storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList());
        Long nextId = advertService.findMaxId();
        if (nextId != null) {
            nextId += 1;
        }else{
            nextId=1L;
        }
        Advert advert = new Advert();
        String fileName = "";
        try {
            fileName = "advert_"+ nextId +
                    "."+file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            Files.copy(file.getInputStream(), Paths.get(ROOT).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        advert.setUrl(url);
        if (!file.isEmpty()) {
            advert.setAttachment(fileName);
        }
        advertService.save(advert);
        return "redirect:/advert/adverts";
    }
}
