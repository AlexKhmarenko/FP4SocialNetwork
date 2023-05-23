package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.post.PostLikeDto;
import com.danit.socialnetwork.dto.post.PostSharedDtoSave;
import com.danit.socialnetwork.model.PostShared;
import com.danit.socialnetwork.service.PostSharedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostSharedRestController {

  private final PostSharedService postSharedService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping(path = "/reposts", consumes = "application/json", produces = "application/json")
  public ResponseEntity<PostSharedDtoSave> addPostShared(@RequestBody PostSharedDtoSave thePostSharedDto) {
    PostShared postShared = postSharedService.savePostShared(thePostSharedDto);
    return new ResponseEntity<>(PostSharedDtoSave.from(postShared), HttpStatus.CREATED);
  }

  @GetMapping("/reposts")
  @ResponseBody
  public List<PostShared> getAllRepostsByUserId(@RequestParam(name = "userId",
      defaultValue = "0") Integer userId) {
    if (userId == 0) {
      return new ArrayList<>();
    }
    return postSharedService.getAllRepostsByUserId(userId);

  }



}
