package com.example.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.service.ContentProcessorService;

/**
 * This class handles all the input request from the browser and redirects to
 * the appropriate templates
 * 
 * @version 1.0
 * @since 2018-02-14
 */
@Controller
public class FileUploadController {
	private static final Logger logger = LogManager.getLogger(FileUploadController.class);

	@Autowired
	ContentProcessorService processorService;

	@GetMapping("gtog")
	public String mymethod() {
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("Oops! We have an Error. OK");
		logger.fatal("Damn! Fatal error. Please fix me.");
		return "ready suriya";
	}

	/**
	 * This method redirects to the upload template
	 * 
	 * @param redirectAttributes
	 * @return String
	 */
	@GetMapping("/")
	public String index(RedirectAttributes redirectAttributes) {
		return "upload";
	}

	@PostMapping("/uploadfile")
	public String fileUpdload(@RequestParam("inputfile") MultipartFile inputfile, RedirectAttributes redirectAttributes)
			throws GtogException {

		String answer = GtogConstants.EMPTY_STRING;
		if (inputfile.isEmpty()) {
			redirectAttributes.addFlashAttribute("result", "Please select a file to upload");
			return "redirect:result";
		}

		if (!inputfile.getOriginalFilename().endsWith(".txt")) {
			redirectAttributes.addFlashAttribute("result", "Please upload text file");
		} else {

			try {
				answer = processorService.handleValidInput(inputfile);
				redirectAttributes.addFlashAttribute("result", answer);
			} catch (GtogException e) {
				logger.error("Excpetion captured in controller");
				throw new GtogException("Exception in controller", e);
			}
		}

		return "redirect:/result";
	}

	@GetMapping("/result")
	public String result() {
		return "result";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}
}
