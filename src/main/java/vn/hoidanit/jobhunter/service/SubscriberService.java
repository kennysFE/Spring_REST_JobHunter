package vn.hoidanit.jobhunter.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.domain.response.email.ResEmailJob;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    private final SkillRepository skillRepository;

    private final JobRepository jobRepository;

    private final EmailService emailService;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository,
            JobRepository jobRepository, EmailService emailService) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    // @Scheduled(cron = "*/10 * * * * *")
    // public void testCron() {
    // System.out.println(" >>> TEXT CRON");
    // }

    // isExistsByEmail
    public boolean isExistsByEmail(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    // create(Subscriber subs)
    public Subscriber create(Subscriber subs) {
        // Check skill
        if (subs.getSkills() != null) {
            List<Long> listSkillId = subs.getSkills().stream().map(item -> item.getId()).collect(Collectors.toList());

            List<Skill> skills = skillRepository.findByIdIn(listSkillId);

            subs.setSkills(skills);
        }

        return subscriberRepository.save(subs);
    }

    // update(Subscriber subsDB, Subscriber subsRequest)
    public Subscriber update(Subscriber subsDB, Subscriber subsRequest) {
        if (subsRequest.getSkills() != null) {
            List<Long> listSkillId = subsRequest.getSkills().stream().map(item -> item.getId())
                    .collect(Collectors.toList());

            List<Skill> skills = skillRepository.findByIdIn(listSkillId);

            subsDB.setSkills(skills);

        }

        return subscriberRepository.save(subsDB);
    }

    // findById(long id)
    public Subscriber findById(long id) {
        Optional<Subscriber> subsOptional = subscriberRepository.findById(id);
        if (subsOptional.isPresent()) {
            return subsOptional.get();
        }
        return null;
    }

    // Convert to send email (template not error)
    public ResEmailJob convertJobToSendEmail(Job job) {
        ResEmailJob res = new ResEmailJob();
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
                .collect(Collectors.toList());
        res.setSkills(s);
        return res;
    }

    // Send email for subscriber
    public void sendSubscribersEmailJobs() {
        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {

            for (Subscriber sub : listSubs) {

                List<Skill> listSkills = sub.getSkills();

                if (listSkills != null && listSkills.size() > 0) {

                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);

                    if (listJobs != null && listJobs.size() > 0) {

                        List<ResEmailJob> arr = listJobs.stream().map(
                                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(),
                                arr);
                    }
                }
            }
        }
    }

    public Subscriber findByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

}
