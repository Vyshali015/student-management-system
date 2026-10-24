package com.example.sms.controller;

import com.example.sms.model.Mark;
import com.example.sms.model.Student;
import com.example.sms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students",
                (keyword == null || keyword.isBlank()) ? repo.findAll() : repo.findByNameContainingIgnoreCase(keyword));
        model.addAttribute("keyword", keyword);
        return "students";
    }

    @GetMapping("/students/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/students/save")
    public String save(@ModelAttribute Student student) {
        if (student.getId() != null) {
            // keep existing marks when editing
            Student existing = repo.findById(student.getId()).orElseThrow();
            existing.setName(student.getName());
            existing.setEmail(student.getEmail());
            existing.setCourse(student.getCourse());
            repo.save(existing);
        } else {
            repo.save(student);
        }
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("student", repo.findById(id).orElseThrow());
        return "student-form";
    }

    @GetMapping("/students/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/marks")
    public String marks(@PathVariable Long id, Model model) {
        model.addAttribute("student", repo.findById(id).orElseThrow());
        return "marks";
    }

    @PostMapping("/students/{id}/marks")
    public String addMark(@PathVariable Long id, @RequestParam String subject, @RequestParam double score) {
        Student s = repo.findById(id).orElseThrow();
        Mark m = new Mark();
        m.setSubject(subject);
        m.setScore(score);
        m.setStudent(s);
        s.getMarks().add(m);
        repo.save(s);
        return "redirect:/students/" + id + "/marks";
    }

    @GetMapping("/students/{id}/marks/delete/{markId}")
    public String deleteMark(@PathVariable Long id, @PathVariable Long markId) {
        Student s = repo.findById(id).orElseThrow();
        s.getMarks().removeIf(m -> m.getId().equals(markId));
        repo.save(s);
        return "redirect:/students/" + id + "/marks";
    }
}
