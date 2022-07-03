package com.reliable.yang.controller;

import com.reliable.yang.utils.QiniuUtils;
import com.reliable.yang.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author Administrator
 * @date 2022-07-02 17:28
 */
@RestController
@RequestMapping("upload")
public class UploadController {
	@Autowired
	private QiniuUtils qiniuUtils;
	@PostMapping
	public Result upload(@RequestParam("image") MultipartFile file){
		String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		boolean upload = qiniuUtils.upload(file, fileName);
		if (upload){
			return Result.success(QiniuUtils.url + fileName);
		}
		return Result.fail(7777,"上传失败");
	}
}