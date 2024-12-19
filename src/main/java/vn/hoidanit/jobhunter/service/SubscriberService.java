package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    private final SkillRepository skillRepository;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }

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
}
