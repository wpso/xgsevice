package com.homingpigeon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homingpigeon.entity.Pigeon;
import com.homingpigeon.service.PigeonService;

@Controller
@RequestMapping("/pigeon")
public class PigeonController {
	@Autowired
	private PigeonService pigeonService;
	
//	@RequestMapping("/createPigeon")
//	@ResponseBody
//	public String createPigeon(Pigeon pigeon){
//		String result = pigeonService.insertPigeon(pigeon);
//		return result;
//	}
	
	
}
