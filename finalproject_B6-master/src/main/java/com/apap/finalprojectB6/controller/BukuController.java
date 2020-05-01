package com.apap.finalprojectB6.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.finalprojectB6.model.BukuModel;
import com.apap.finalprojectB6.model.JenisModel;
import com.apap.finalprojectB6.model.PeminjamanModel;
import com.apap.finalprojectB6.model.UserModel;
import com.apap.finalprojectB6.service.BukuService;
import com.apap.finalprojectB6.service.JenisService;
import com.apap.finalprojectB6.service.PeminjamanService;
import com.apap.finalprojectB6.service.UserService;



@Controller
public class BukuController {
	@Autowired
	private BukuService bukuService;
	
	@Autowired
	private JenisService jenisService;
	
	@Autowired
	private PeminjamanService peminjamanService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/buku", method = RequestMethod.GET)
	private String view(Model model) {
		List<BukuModel> buku = bukuService.getAllBuku();
		String navigation = "SIP";
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		model.addAttribute("buku", buku);
		return "buku/index";
	}
	
	@RequestMapping(value = "/buku/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		List <JenisModel> jenisList = jenisService.getAllJenis();
		String navigation = "Tambah Buku";
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		model.addAttribute("jenis_buku", jenisList);
		model.addAttribute("buku", new BukuModel());
		return "buku/addForm";	
	}
	
	@RequestMapping(value = "/buku/tambah", method = RequestMethod.POST, params={"submit"})
	private String addSubmit(@ModelAttribute BukuModel buku, Model model) {
		String navigation = "Berhasil";
		// JenisModel jenisId = jenisService.getJenisById(jenis_buku.getId()).get();
		//Field error in object 'bukuModel' on field 'buku_jenis': rejected value [1]; codes [typeMismatch.bukuModel.buku_jenis,typeMismatch.buku_jenis,typeMismatch.com.apap.finalprojectB6.model.JenisModel,typeMismatch];
		// buku.setBuku_jenis(jenisId);
		bukuService.addBuku(buku);
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		return "buku/add-success";
	} 
	
	@RequestMapping(value = "/detail-buku", method = RequestMethod.GET)
	private String detail(@RequestParam(value = "id") int id, Model model) {
		BukuModel buku = bukuService.getBukuById(id);
		String navigation = "Detail Buku";
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		model.addAttribute("buku", buku);
		return "buku/detail-buku";
	}
	
	@RequestMapping(value = "/detail-buku", method = RequestMethod.POST, params={"submit"})
	private String detail1(@RequestParam(value = "id") int id, @ModelAttribute PeminjamanModel peminjaman, Model model) {
		UserModel user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		BukuModel buku = bukuService.getBukuById(id);
		LocalDate today = LocalDate.now();
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		Date date = Date.valueOf(today);
		Date duedate = Date.valueOf(nextWeek);
		String navigation = "Detail Buku";
		peminjaman.setPinjamBuku(buku);
		peminjaman.setStatus(0);
		peminjaman.setTanggal_peminjaman(date);
		peminjaman.setTanggal_pengembalian(duedate);
		peminjaman.setUser_peminjaman(user);
		peminjaman.setPinjamBuku(buku);
		peminjamanService.addPeminjaman(peminjaman);
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		bukuService.updateJumlahKurang(id, buku);
		model.addAttribute("navigation", navigation);
		return "peminjaman/peminjaman-success";
	}
	
	@RequestMapping(value = "/buku/ubah/{id}", method = RequestMethod.GET)
	private String updateBuku(@PathVariable(value = "id") int id, Model model) {
		BukuModel old = bukuService.getBukuById(id);
		List <JenisModel> jenisList = jenisService.getAllJenis();
		String navigation = "Ubah Buku";
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		model.addAttribute("old", old);
		model.addAttribute("jenis_buku", jenisList);
		model.addAttribute("new", new BukuModel());
		return "buku/ubah-buku";
	}
	
	@RequestMapping(value = "/buku/ubah/{id}", method = RequestMethod.POST)
	private String updateBuku(@ModelAttribute BukuModel newBuku, 
			@PathVariable(value = "id") int id, Model model) {
		bukuService.updateBuku(id, newBuku);
		BukuModel buku = bukuService.getBukuById(id);
		String navigation = "Berhasil";
		UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("detailUser", detailUser);
		model.addAttribute("navigation", navigation);
		model.addAttribute("buku", buku);
		return "buku/update-success";
	}
	
	@RequestMapping(value = "/buku/hapus/{id}", method = RequestMethod.GET)
	private String deleteBuku(@PathVariable(value = "id") int id, Model model) {
			BukuModel buku = bukuService.getBukuById(id);
			model.addAttribute("buku", buku);
			String navigation = "Hapus Buku";
			model.addAttribute("navigation", navigation);
			UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("detailUser", detailUser);
			return "buku/hapus-buku";
		}
	
	@RequestMapping(value = "/buku/hapus/{id}", method = RequestMethod.POST)
	private String delete(@PathVariable(value = "id") int id, Model model) {
			bukuService.deleteBuku(id);
			String navigation = "Berhasil";
			model.addAttribute("navigation", navigation);
			UserModel detailUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("detailUser", detailUser);
			return "buku/delete-success";
	}

}
