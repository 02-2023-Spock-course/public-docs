package spock.course.badproject.service;

import spock.course.badproject.domain.Attribute;
import spock.course.badproject.domain.AttributeGroup;

import java.util.List;

public class AuditService {

    public boolean sendListToAudit(List<Attribute> attributes) {
        return false;
    }

    public boolean sendGroupToAudit(AttributeGroup attributeGroup) {
        return false;
    }

    public boolean sendAttributeToAudit(Attribute attribute) {
        return false;
    }
}
