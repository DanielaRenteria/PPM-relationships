package com.ppm.springboot.web.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ppm.springboot.web.app.dao.MemberDAO;
import com.ppm.springboot.web.app.entity.Member;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberDAO memberDAO;

	@GetMapping
	public ResponseEntity<List<Member>> getMember() {
		List<Member> member = memberDAO.findAll();
		return ResponseEntity.ok(member);
	}

	@RequestMapping(value = "{memberId}")
	public ResponseEntity<Member> getMemberById(@PathVariable("memberId") Long memberId) {
		Optional<Member> optionalMember = memberDAO.findById(memberId);
		if (optionalMember.isPresent()) {
			return ResponseEntity.ok(optionalMember.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// Insertar
	@PostMapping
	public ResponseEntity<Member> createMember(@RequestBody Member member) {
		Member newMember = memberDAO.save(member);
		return ResponseEntity.ok(newMember);
	}

	@DeleteMapping(value = "{memberId}") // /members(DELETE)
	public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
		memberDAO.deleteById(memberId);
		return ResponseEntity.ok(null);
	}

	@PutMapping
	public ResponseEntity<Member> updateMember(@RequestBody Member member) {
		Optional<Member> optionalMember = memberDAO.findById(member.getId());
		if (optionalMember.isPresent()) {
			Member updateMember = optionalMember.get();
			updateMember.setName(member.getName());
			updateMember.setLastName(member.getLastName());
			updateMember.setRole(member.getRole());
			updateMember.setCreateAt(member.getCreateAt());
			updateMember.setUpdateAt(member.getUpdateAt());
			updateMember.setCreated_by(member.getCreated_by());
			updateMember.setUpdateBy(member.getUpdateBy());
			updateMember.setCreated_by(member.getCreated_by());
			updateMember.setActive(member.getActive());
			return ResponseEntity.ok(updateMember);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
