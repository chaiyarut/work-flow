package com.example.workflow.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@Slf4j
public class LdapConfiguration {

    @Value("${ldap.providerUrl}")
    private String providerUrl;

    @Value("${ldap.searchBase}")
    private String searchBase;

    @Value("${ldap.searchFilter}")
    private String searchFilter;

    @Bean(name = "ldapTemplate")
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean(name = "contextSource")
    public LdapContextSource ldapContextSource() {

        if (isConfigurationValid(providerUrl, searchBase)) {
            LdapContextSource ldapContextSource = new LdapContextSource();
            ldapContextSource.setUrl(providerUrl);
            ldapContextSource.setBase(searchBase);
            ldapContextSource.setUserDn("ktbsearch@domain.ktb.co.th");
            ldapContextSource.setPassword("!QAZcde3");
            ldapContextSource.afterPropertiesSet();
            return ldapContextSource;
        }
        return null;
    }

    public boolean isConfigurationValid(String url, String base) {
        if ((url == null) || url.isEmpty() || (base == null) || base.isEmpty()) {
            log.error("Warning! Your LDAP server is not configured.");
            log.info("Did you configure your LDAP settings in your application.yml?");
            return false;
        } else {
            return true;
        }
    }
}

