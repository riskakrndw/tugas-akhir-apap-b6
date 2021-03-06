package com.apap.finalprojectB6.controller;

import java.util.List;

import com.apap.finalprojectB6.model.PengadaanModel;
import com.apap.finalprojectB6.service.PengadaanService;
import com.apap.finalprojectB6.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pengadaan")
@CrossOrigin(origins = "*")
public class PengadaanController {
	@Autowired
	private PengadaanService pengadaanService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/viewall", method = RequestMethod.GET)
	private List<PengadaanModel> index(Model model) {
		List<PengadaanModel> pengadaan = pengadaanService.getAllPengadaan();
		return pengadaan;
	}

	@GetMapping(value = "/detail/{id}")
	private PengadaanModel detail(@PathVariable int id){
		PengadaanModel pengadaan = pengadaanService.getPengadaanById(id);
		pengadaan.setNama(userService.getUserByUuid(pengadaan.getUuid_user()).getNama());
		return pengadaan;
	}

	@GetMapping(value = "/delete/{id}")
	private PengadaanModel delete(@PathVariable int id){
		PengadaanModel pengadaan = pengadaanService.getPengadaanById(id);
		pengadaan.setNama(userService.getUserByUuid(pengadaan.getUuid_user()).getNama());
		return pengadaan;
	}

	@PostMapping(value = "/delete/{id}")
	private PengadaanModel deleteSubmit(@PathVariable int id){
		return pengadaanService.deletePengadaan(id);
	}
}