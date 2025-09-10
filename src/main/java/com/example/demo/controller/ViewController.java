package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.AdminService;
import com.example.demo.service.StudentService;
import com.example.demo.model.Application;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
@RequestMapping("/admin") // ✅ This handles HTML views only
public class ViewController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseRepository courseRepo;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", studentService.getAllStudents().size());
        model.addAttribute("courseCount", courseRepo.findAll().size());
        model.addAttribute("pendingApplications", adminService.getPendingApplications().size());
        return "dashboard";
    }


 // View Courses (Thymeleaf)
    @GetMapping("/courses/view")
    public String viewCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "courses";
    }


    // View Students
    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
    
 // Show Student Registration Form
    @GetMapping("/students/register")
    public String showStudentRegistrationForm(Model model) {
        model.addAttribute("student", new Student()); // Empty student object for form binding
        model.addAttribute("courses", courseRepo.findAll()); // List of available courses for dropdown
        return "register-student"; // Thymeleaf template name
    }

    // Save Student with Selected Course
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student) {
        Long courseId = student.getCourse().getId();
        Course course = courseRepo.findById(courseId).orElseThrow();
        student.setCourse(course); // ✅ attach managed entity

        studentService.saveStudent(student);
        return "redirect:/admin/students";
    }
    
    @GetMapping("/courses/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "addcourse"; // ✅ Must match template name exactly
    }

    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course) {
        courseRepo.save(course); // ✅ Persists the course
        return "redirect:/admin/courses/view"; // ✅ Redirects to course list
    }
    
    @GetMapping("/courses/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "editcourse"; // You’ll need a new template for this
    }
    
    
    
    @GetMapping("/courses/details/{id}")
    public String viewCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "course-details"; // Create a simple template to show course info
    }
    
    @GetMapping("/applications")
    public String viewApplications(Model model) {
        List<Application> applications = applicationRepository.findAll();

        // 🔍 Debug: Print application details to console
        applications.forEach(app -> {
            System.out.println("App ID: " + app.getId());
            System.out.println("Student: " + (app.getStudent() != null ? app.getStudent().getName() : "null"));
            System.out.println("Course: " + (app.getCourse() != null ? app.getCourse().getName() : "null"));
        });

        model.addAttribute("applications", applications);
        return "fragments/applications";
    }
    
    @PostMapping("/applications/submit")
    public String submitApplication(@RequestParam Long studentId,
                                    @RequestParam Long courseId,
                                    Model model) {

        Student student = studentService.getStudentById(studentId);
        Course appliedCourse = courseRepo.findById(courseId).orElse(null);

        if (student == null || appliedCourse == null) {
            model.addAttribute("error", "Invalid student or course selection.");
            return "dashboard";
        }

        // Check for course mismatch
        Course registeredCourse = student.getCourse();
        if (registeredCourse != null && !registeredCourse.getId().equals(appliedCourse.getId())) {
        	 log.warn("Course mismatch: Student {} tried to apply for {}", student.getName(), appliedCourse.getName());

        	
        	model.addAttribute("error", "Student is already registered for a different course.");
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseRepo.findAll());
            return "fragments/submit-application";
        }

        // Step 2: Prevent duplicate application
        boolean exists = applicationRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (exists) {
            model.addAttribute("error", "Application already submitted for this course.");
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseRepo.findAll());
            return "fragments/submit-application";
        }

        // Proceed with application creation
        Application app = new Application();
        app.setStudent(student);
        app.setCourse(appliedCourse);

        int score = student.getMarks(); // assuming getMarks() exists
        app.setStatus(score >= appliedCourse.getCutoffMarks() ? "Approved" : "Rejected");

        applicationRepository.save(app);

        return "redirect:/admin/applications";
    }
    
    @GetMapping("/courses/edit-cutoff/{id}")
    public String showCutoffEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        model.addAttribute("course", course);
        return "edit-cutoff";
    }

    @PostMapping("/courses/update-cutoff")
    public String updateCutoff(@RequestParam Long courseId,
                               @RequestParam int cutoffMarks) {
        Course course = courseRepo.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        course.setCutoffMarks(cutoffMarks);
        courseRepo.save(course);

        return "redirect:/admin/courses/view";
    }
    
    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return "redirect:/admin/courses/view";
    }
    
    @GetMapping("/applications/form")
    public String showApplicationForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseRepo.findAll());
        return "fragments/submit-application"; // or wherever your form lives
    }
    
    @PostMapping("/applications/update-status")
    public String updateApplicationStatus(@RequestParam Long applicationId,
                                          @RequestParam String newStatus) {
        Application app = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));

        app.setStatus(newStatus);
        applicationRepository.save(app);

        return "redirect:/admin/applications";
    }
    
    
    
    

}