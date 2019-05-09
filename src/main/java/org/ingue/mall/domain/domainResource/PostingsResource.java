package org.ingue.mall.domain.domainResource;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.ingue.mall.controller.PostingController;
import org.ingue.mall.domain.Postings;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//BeanSerialization 기본적으로 이름을 사용
public class PostingsResource extends Resource<Postings> {

    public PostingsResource(Postings posting, Link... links) {
        super(posting, links);
        add(linkTo(PostingController.class).slash(posting.getPostingId()).withSelfRel());
    }
}
