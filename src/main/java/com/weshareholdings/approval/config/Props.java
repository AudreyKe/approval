package com.weshareholdings.approval.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "props")
public class Props {

    private String uploadFilePathProp;

    private String projectProcessId;

    private String reviewProcessId;

    private String vetoProcessId;

    private String goldProcessId;

    private String sessionTimeOut;

    private String url;

    private String baseDN;

    private String factory;

    private String root;

    private String password;

    private String username;

    private String compliApprovalUser;

    private String riskApprovalUser;

    private String techApprovalUser;

    private String vetoUser;
}
