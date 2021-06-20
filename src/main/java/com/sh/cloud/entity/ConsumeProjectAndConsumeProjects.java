package com.sh.cloud.entity;

import com.sft.member.bean.ConsumeProject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ConsumeProjectAndConsumeProjects {
    ConsumeProject project;

    List<ConsumeProject> projects;
}
