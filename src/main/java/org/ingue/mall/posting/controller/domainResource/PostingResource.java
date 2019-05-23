package org.ingue.mall.posting.controller.domainResource;

import org.ingue.mall.posting.domain.Postings;
import org.ingue.mall.posting.controller.PostingController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//Spring HATEOAS 적용을 위한 클래스
public class PostingResource extends Resource<Postings> {

    public PostingResource(Postings posting, Link... links) {
        super(posting, links);
        add(linkTo(PostingController.class).slash(posting.getPostingId()).withSelfRel());
    }
}
