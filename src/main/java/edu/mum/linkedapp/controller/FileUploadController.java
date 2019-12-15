package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.service.impl.UserService;
import edu.mum.linkedapp.storage.StorageService;
import edu.mum.linkedapp.storage.StorageFileNotFoundException;
import edu.mum.linkedapp.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class FileUploadController {

	private static final String ROOT = "upload-dir";
	private final StorageService storageService;

	@Autowired
	private UserService userService;

//	private Path rootLocation;
//	@Value("${app.profile.upload.path}")
//	private String uploadPath;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping(value = "/uploadFile", produces = "text/plain")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		storageService.store(file);
		List<String> list = storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toString())
				.collect(Collectors.toList());
		String fileName = "";
		for (String name: list) {
			if (name.contains(file.getOriginalFilename())) {
				fileName = name;
			}
		}
		return fileName;
	}
	@PostMapping(value = "/updateProfile")
	public String updateProfilePicture(@RequestParam("file") MultipartFile file, Principal principal) {
		storageService.store(file);
		System.out.println("path::" + new File("").getAbsolutePath());
		List<String> list = storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toString())
				.collect(Collectors.toList());
		String fileName = "profile_pic_"+principal.getName()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

		System.out.println(fileName);
		try {
            Files.copy(file.getInputStream(), Paths.get(ROOT).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
		userService.updateProfilePicture(principal.getName(),fileName);
//		for (String name: list) {
//			if (name.contains(file.getOriginalFilename())) {
//				fileName = name;
//			}
//		}
		return "redirect:/user/profile";
	}

	@GetMapping("user/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveProfilePic(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		System.out.println(storageService.load(filename));
		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
