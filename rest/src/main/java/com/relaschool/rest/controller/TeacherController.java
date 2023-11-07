package com.relaschool.rest.controller;

import com.relaschool.rest.dto.output.JobOfferApplyOutput;
import com.relaschool.rest.dto.output.JobOfferOutput;
import com.relaschool.rest.dto.output.TeacherOutput;
import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import lombok.AllArgsConstructor;
import model.Teacher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.stubs.InitClass;
import usecase.TeacherManagement;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeacherController {

    private final TeacherManagement teacherManagement;

    public TeacherController(TeacherManagement teacherManagement) {
        this.teacherManagement = teacherManagement;
        InitClass.initData();
    }

    @GetMapping("/teacher")
    public ResponseEntity<TeacherOutput> getTeacher(@RequestParam("teacherId") Long teacherId) {
        return ResponseEntity.ok(TeacherOutput.from(teacherManagement.getTeacherData(teacherId)));
    }

    @PostMapping("/teacher")
    public ResponseEntity<TeacherOutput> createTeacher(@RequestBody TeacherControlCommand command) {
        return ResponseEntity.ok(TeacherOutput.from(teacherManagement.registerTeacher(command)));
    }

    @PatchMapping("/teacher")
    public ResponseEntity<Void> updateTeacher(@RequestBody TeacherControlCommand command) {
        teacherManagement.updateTeacherProfile(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/teacher")
    public ResponseEntity<Void> deleteTeacher(@RequestParam("teacherId") Long teacherId) {
        teacherManagement.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/job-offers")
    public ResponseEntity<List<JobOfferOutput>> getJobOffers() {

        List<JobOfferOutput> jobOffers = teacherManagement.viewAvailableJobOffers().stream()
                .map(JobOfferOutput::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobOffers);
    }

    @GetMapping("/job-offers/application")
    public ResponseEntity<List<JobOfferApplyOutput>> getJobOffersApply(@RequestParam("teacherId") Long teacherId) {

        List<JobOfferApplyOutput> jobOffers = teacherManagement.viewAppliedJobOffers(teacherId).stream()
                .map(jobOffer -> JobOfferApplyOutput.from(teacherId, jobOffer))
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobOffers);
    }

    @PostMapping("/apply")
    public ResponseEntity<Void> applyForJobOffer(@RequestBody ApplyForJobOfferCommand command) {
        teacherManagement.applyForJobOffer(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/retract")
    public ResponseEntity<Void> retractForJobOffer(
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("applicationId") Long applicationId) {
        teacherManagement.retractApplication(teacherId, applicationId);
        return ResponseEntity.noContent().build();
    }
}
