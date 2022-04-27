package com.example.workflow.service;

import com.example.workflow.component.LdapResponseMapper;
import com.example.workflow.data.ldap.LdapDetail;
import com.example.workflow.data.repositories.RoleRepository;
import com.example.workflow.data.request.LoginRequest;
import com.example.workflow.data.response.LoginResponse;
import com.example.workflow.mapper.LoginMapper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LoginService {

    @Autowired
    @Qualifier("ldapTemplate")
    private LdapTemplate ldapTemplate;

    @Value("${ldap.searchFilter}")
    private String searchFilter;

    @Autowired
    private RoleRepository roleRepository;

    public LoginResponse execute(LoginRequest loginRequest){

        var query = query().filter(searchFilter,loginRequest.getUsername());

        var result = ldapTemplate.search(query, new LdapResponseMapper());

        return mapToResponse(result);
    }

    private LoginResponse mapToResponse(List<LdapDetail> ldapDetailList){

        var detail = ldapDetailList.stream()
                .map(it -> LoginResponse.Detail.builder()
                        .email(String.valueOf(it.get("mail")))
                        .userId(String.valueOf(it.get("sAMAccountName")))
                        .username(String.valueOf(it.get("displayName")))
                        .build())
                .findFirst().orElse(null);

        var role = roleRepository.findAll().stream()
                .filter(it -> it.getUserId().equals(Objects.requireNonNull(detail).getUserId()))
                .map(LoginMapper.INSTANCE::toRole)
                .findFirst().orElse(null);

        var isSuccess = detail != null && role != null;

        return LoginMapper.INSTANCE.toResponse(isSuccess,role,detail);
    }

}
