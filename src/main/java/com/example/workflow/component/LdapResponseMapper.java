package com.example.workflow.component;

import com.example.workflow.data.ldap.LdapDetail;
import lombok.var;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.nio.charset.StandardCharsets;

@Component
public class LdapResponseMapper implements AttributesMapper<LdapDetail> {

    @Override
    public LdapDetail mapFromAttributes(Attributes attrs) throws NamingException {

        var all = attrs.getAll();

        var ldapDetail = new LdapDetail();

        while (all.hasMoreElements()) {
            var next = all.next();
            var byteBuffer = StandardCharsets.UTF_8.encode(next.get().toString());
            var decode = StandardCharsets.UTF_8.decode(byteBuffer);
            var clazz = next.get().getClass();
            ldapDetail.put(next.getID(),next.get());
        }

        return ldapDetail;
    }
}
