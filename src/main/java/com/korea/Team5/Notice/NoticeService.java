package com.korea.Team5.Notice;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {


  private final NoticeRepository noticeRepository;

  public void create(String subject, String content, Member member) {
    Notice notice = new Notice();
    notice.setSubject(subject);
    notice.setContent(content);
    notice.setCreateDate(LocalDateTime.now());
    notice.setMember(member);
    this.noticeRepository.save(notice);
  }

  //공지사항 리스트가져오는 메서드
  public List<Notice> getnoticeList() {
    List<Notice> noticeList = this.noticeRepository.findAll();
    if (noticeList.isEmpty()) {
      throw new DataNotFoundException("No notices found");
    }
    return noticeList;
  }
  //공지사항 하나만 가져오는 메서드
  public Notice getNotice(Integer id) {
    Optional<Notice> notice = this.noticeRepository.findById(id);
    if (notice.isPresent()) {
      return notice.get();
    } else {
      throw new DataNotFoundException("review not found");
    }
  }

  public void delete(Notice notice) {
    this.noticeRepository.delete(notice);
  }
  public void modify(Notice notice,String subject,String content){
    notice.setSubject(subject);
    notice.setContent(content);
    notice.setModifyDate(LocalDateTime.now());
    this.noticeRepository.save(notice);
  }
}
