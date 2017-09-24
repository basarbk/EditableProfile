package com.basarbk.editableprofile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
/**
 * This is just a placeholder service. It pretends to do file storage actions and returns the filename back to the calling service
 * 
 * @author basarb
 *
 */
public class PhotoService {
	
	public String savePhoto(MultipartFile photo) {
		// TODO save file and return it's name
		return "http://placehold.it/256x256";
	}

}
